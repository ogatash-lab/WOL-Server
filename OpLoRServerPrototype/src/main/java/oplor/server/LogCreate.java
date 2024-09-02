package oplor.server;

import com.google.gson.Gson;
import oplor.server.event.*;
import oplor.server.node.*;
import org.sqlite.SQLiteConfig;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.*;
import java.util.Calendar;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;

import static java.lang.Math.abs;

//MySQLテスト
//HTTP port 8080
//JMX port 2020

//MySQLデータベースに保存するクラス
@WebServlet(name = "LogCreate", urlPatterns = {"/HW"})
public class LogCreate extends HttpServlet {
    //public Event event = new Event();
    //public Node node = new Node();    // ローカルで呼ぶことで上書きを防ぐ
    private final LinkedBlockingQueue<Log> logs = new LinkedBlockingQueue<>();
    public boolean isConnection = false;

    public int OneConnectionSize = 10;
    //マウスイベントでなんか，，，支障がでたら使う
    public int MouseFrequency = 0;

    public Connection conn = null;
    public PreparedStatement ps = null;
    public ResultSet rs = null;

    //HTTP POSTリクエストを処理
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //response.setContentType("application/json;charset=UTF-8");

        //レスポンスのコンテンツタイプをJSONに設定
        response.setContentType("application/json;charset=ASCII");

        //リクエストボディを読み込む
        BufferedReader reader = request.getReader();
        String body, line;
        HttpSession session = request.getSession();
        String userID = (String) session.getAttribute("userID");

        //前回のマウスアクションの時間を保持
        Calendar calendar = Calendar.getInstance();
        Calendar lastcal = Calendar.getInstance();
        lastcal = (Calendar) session.getAttribute("lastMouseAction");
        if (lastcal == null) {
            session.setAttribute("lastMouseAction", calendar);
        }

        //userIDがnullの場合，新しいUUIDを生成してセッションに設定
        if (userID == null) {
            UUID uuid = UUID.randomUUID();
            userID = uuid.toString();
            session.setAttribute("userID", userID);
        }

        //リクエストボディを文字列に読み込む(区切り文字：@@)
        body = "";
        line = null;
        while ((line = reader.readLine()) != null) {
            body += line;
        }
        String[] strs = body.split("@@", 0);

        String EventType, NodeType, LogBody;
        String EventBody, NodeBody;

        if (strs.length >= 3) {
            //リクエストからイベントタイプ，ノードタイプ，ログボディを解析
            EventType = strs[0];
            NodeType = strs[1];
            LogBody = strs[2];

            //ログボディをイベントボディとノードボディに分割
            String[] logbody = LogBody.split("},", 2);
            EventBody = logbody[0];
            EventBody += "}";
            EventBody = EventBody.replace("[", "");

            NodeBody = logbody[1];
            NodeBody = NodeBody.replace("}]", "}");

            //NodeBody文字列をASCIIに変換
            byte[] bytes = NodeBody.getBytes("ASCII");
            String newStr3 = new String(bytes, "ASCII");

            String path = "jdbc:mysql://localhost:3306/test?autoReconnect=true&useSSL=false";  //接続パス
            String id = "root";    //ログインID
            String pw = "Usagi3.0807";  //ログインパスワード

            //JSONデシリアライズ用のGsonオブジェクトを初期化(JSON->Object)
            Gson egson = new Gson();

            // ローカルで作成(上書きを防ぐため)
            Event event = new Event();
            Node node = new Node();
            

            //EventTypeに基づいてイベントをデシリアライズ
            if (EventType.equals("Event")) { //event->Event changed by makino
                event = egson.fromJson(EventBody, Event.class);//JSON形式からオブジェクトへ
                event.update();
            } else if (EventType.equals("UIEvent")) {
                UIEvent uievent = egson.fromJson(EventBody, UIEvent.class);
                uievent.update();
                event = uievent;
            } else if (EventType.equals("FocusEvent")) {
                FocusEvent focusevent = egson.fromJson(EventBody, FocusEvent.class);
                focusevent.update();
                event = focusevent;
            } else if (EventType.equals("MouseEvent")) {
                MouseEvent mouseevent = egson.fromJson(EventBody, MouseEvent.class);
                mouseevent.update();
                
                System.out.println("Deserialized MouseEvent: " + mouseevent);
                
                event = mouseevent;
                //差を取得して一定時間たっていないならMouseEventを無視する。
                //long diffTime = lastcal.getTimeInMillis() - calendar.getTimeInMillis();
                //if (abs(diffTime) <= MouseFrequency) {
                //    return;
                //}
                session.setAttribute("lastMouseAction", calendar);
            } else if (EventType.equals("TouchEvent")) {
                TouchEvent touchevent = egson.fromJson(EventBody, TouchEvent.class);
                touchevent.update();
                event = touchevent;
            } else if (EventType.equals("KeyboardEvent")) {
                KeyboardEvent keyboardevent = egson.fromJson(EventBody, KeyboardEvent.class);
                keyboardevent.update();
                event = keyboardevent;
            } else if (EventType.equals("InputEvent")) {
                InputEvent inputevent = egson.fromJson(EventBody, InputEvent.class);
                inputevent.update();
                event = inputevent;
            } else if (EventType.equals("CompositionEvent")) {
                CompositionEvent compositionevent = egson.fromJson(EventBody, CompositionEvent.class);
                compositionevent.update();
                event = compositionevent;
            } else if (EventType.equals("WheelEvent")) {
                WheelEvent wheelevent = egson.fromJson(EventBody, WheelEvent.class);
                wheelevent.update();
                event = wheelevent;

                long diffTime = lastcal.getTimeInMillis() - calendar.getTimeInMillis();
                if (abs(diffTime) < MouseFrequency) {
                    return;
                }
                session.setAttribute("lastMouseAction", calendar);
            } else if (EventType.equals("DragEvent")) {
                DragEvent dragevent = egson.fromJson(EventBody, DragEvent.class);
                dragevent.update();
                event = dragevent;
            }

            //NodeTypeに基づいてノードをデシリアライズ
            Gson ngson = new Gson();
            if (NodeType.equals("node")) {
                //System.out.println("node");
                node = ngson.fromJson(NodeBody, Node.class);
                node.update();
                //node.send(userID);
            } else if (NodeType.equals("DocumentType")) {
                DocumentType documenttype = ngson.fromJson(NodeBody, DocumentType.class);
                documenttype.update();
                //node.send(userID);
                node = documenttype;
            } else if (NodeType.equals("Document")) {
                Document document = ngson.fromJson(NodeBody, Document.class);
                document.update();
                //node.send(userID);
                node = document;
            } else if (NodeType.equals("Text")) {
                Text text = ngson.fromJson(NodeBody, Text.class);
                text.update();
                //node.send(userID);
                node = text;
            } else if (NodeType.equals("DocumentFragment")) {
                DocumentFragment documentfragment = ngson.fromJson(NodeBody, DocumentFragment.class);
                documentfragment.update();
                //documentfragment.send(userID);
                node = documentfragment;
            } else if (NodeType.equals("Element")) {
                //System.out.println("Element");
                Element element = ngson.fromJson(NodeBody, Element.class);
                element.update();
                //node.send(userID);
                node = element;
            } else if (NodeType.equals("HTMLElement")) {
                //System.out.println("HTMLElement");
                HTMLElement htmlelement = ngson.fromJson(NodeBody, HTMLElement.class);
                htmlelement.update();
                //node.send(userID);
                node = htmlelement;
            } else if (NodeType.equals("HTMLAnchorElement")) {
                HTMLAnchorElement htmlanchorelement = ngson.fromJson(NodeBody, HTMLAnchorElement.class);
                htmlanchorelement.update();
                //node.send(userID);
                node = htmlanchorelement;
            } else if (NodeType.equals("HTMLAreaElement")) {
                HTMLAreaElement htmlareaelement = ngson.fromJson(NodeBody, HTMLAreaElement.class);
                htmlareaelement.update();
                //node.send(userID);
                node = htmlareaelement;
            } else if (NodeType.equals("HTMLBaseElement")) {
                HTMLBaseElement htmlbaseelement = ngson.fromJson(NodeBody, HTMLBaseElement.class);
                htmlbaseelement.update();
                //node.send(userID);
                node = htmlbaseelement;
            } else if (NodeType.equals("HTMLButtonElement")) {
                HTMLButtonElement htmlbuttonelement = ngson.fromJson(NodeBody, HTMLButtonElement.class);
                htmlbuttonelement.update();
                //node.send(userID);
                node = htmlbuttonelement;
            } else if (NodeType.equals("HTMLCanvasElement")) {
                HTMLCanvasElement htmlcanvaselement = ngson.fromJson(NodeBody, HTMLCanvasElement.class);
                htmlcanvaselement.update();
                //node.send(userID);
                node = htmlcanvaselement;
            } else if (NodeType.equals("HTMLDataElement")) {
                HTMLDataElement htmldataelement = ngson.fromJson(NodeBody, HTMLDataElement.class);
                htmldataelement.update();
                //node.send(userID);
                node = htmldataelement;
            } else if (NodeType.equals("HTMLDataListElement")) {
                HTMLDataListElement htmldatalistelement = ngson.fromJson(NodeBody, HTMLDataListElement.class);
                htmldatalistelement.update();
                //node.send(userID);
                node = htmldatalistelement;
            } else if (NodeType.equals("HTMLDialogElement")) {
                HTMLDialogElement htmldialogelement = ngson.fromJson(NodeBody, HTMLDialogElement.class);
                htmldialogelement.update();
                //node.send(userID);
                node = htmldialogelement;
            } else if (NodeType.equals("HTMLEmbedElement")) {
                HTMLEmbedElement htmlembedelement = ngson.fromJson(NodeBody, HTMLEmbedElement.class);
                htmlembedelement.update();
                //node.send(userID);
                node = htmlembedelement;
            } else if (NodeType.equals("HTMLFieldSetElement")) {
                HTMLFieldSetElement htmlfieldsetelement = ngson.fromJson(NodeBody, HTMLFieldSetElement.class);
                htmlfieldsetelement.update();
                //node.send(userID);
                node = htmlfieldsetelement;
            } else if (NodeType.equals("HTMLFormElement")) {
                HTMLFormElement htmlformelement = ngson.fromJson(NodeBody, HTMLFormElement.class);
                htmlformelement.update();
                //node.send(userID);
                node = htmlformelement;
            } else if (NodeType.equals("HTMLFrameElement")) {
                HTMLFrameElement htmlframeelement = ngson.fromJson(NodeBody, HTMLFrameElement.class);
                htmlframeelement.update();
                //node.send(userID);
                node = htmlframeelement;
            } else if (NodeType.equals("HTMLInputElement")) {
                //System.out.println("HTMLInputElement");
                HTMLInputElement htmlinputelement = ngson.fromJson(NodeBody, HTMLInputElement.class);
                htmlinputelement.update();
                //node.send(userID);
                node = htmlinputelement;
            } else if (NodeType.equals("HTMLLabelElement")) {
                HTMLLabelElement htmllabelelement = ngson.fromJson(NodeBody, HTMLLabelElement.class);
                htmllabelelement.update();
                node = htmllabelelement;
            } else if (NodeType.equals("HTMLLegendElement")) {
                HTMLLegendElement htmllegendelement = ngson.fromJson(NodeBody, HTMLLegendElement.class);
                htmllegendelement.update();
                node = htmllegendelement;
            } else if (NodeType.equals("HTMLLIElement")) {
                HTMLLIElement htmllielement = ngson.fromJson(NodeBody, HTMLLIElement.class);
                htmllielement.update();
                //node.send(userID);
                node = htmllielement;
            } else if (NodeType.equals("HTMLLinkElement")) {
                HTMLLinkElement htmllinkelement = ngson.fromJson(NodeBody, HTMLLinkElement.class);
                htmllinkelement.update();
                node = htmllinkelement;
            } else if (NodeType.equals("HTMLMapElement")) {
                HTMLMapElement htmlmapelement = ngson.fromJson(NodeBody, HTMLMapElement.class);
                htmlmapelement.update();
                node = htmlmapelement;
            } else if (NodeType.equals("HTMLMetaElement")) {
                HTMLMetaElement htmlmetaelement = ngson.fromJson(NodeBody, HTMLMetaElement.class);
                htmlmetaelement.update();
                node = htmlmetaelement;
            } else if (NodeType.equals("HTMLMeterElement")) {
                HTMLMeterElement htmlmeterelement = ngson.fromJson(NodeBody, HTMLMeterElement.class);
                htmlmeterelement.update();
                node = htmlmeterelement;
            } else if (NodeType.equals("HTMLOListElement")) {
                HTMLOListElement htmlolistelement = ngson.fromJson(NodeBody, HTMLOListElement.class);
                htmlolistelement.update();
                node = htmlolistelement;
            } else if (NodeType.equals("HTMLObjectElement")) {
                HTMLObjectElement htmlobjectelement = ngson.fromJson(NodeBody, HTMLObjectElement.class);
                htmlobjectelement.update();
                node = htmlobjectelement;
            } else if (NodeType.equals("HTMLOptGroupElement")) {
                HTMLOptGroupElement htmloptgroupelement = ngson.fromJson(NodeBody, HTMLOptGroupElement.class);
                htmloptgroupelement.update();
                node = htmloptgroupelement;
            } else if (NodeType.equals("HTMLParamElement")) {
                HTMLParamElement htmlparamelement = ngson.fromJson(NodeBody, HTMLParamElement.class);
                htmlparamelement.update();
                node = htmlparamelement;
            } else if (NodeType.equals("HTMLProgressElement")) {
                HTMLProgressElement htmlprogresselement = ngson.fromJson(NodeBody, HTMLProgressElement.class);
                htmlprogresselement.update();
                node = htmlprogresselement;
            } else if (NodeType.equals("HTMLQuoteElement")) {
                HTMLQuoteElement htmlquoteelement = ngson.fromJson(NodeBody, HTMLQuoteElement.class);
                htmlquoteelement.update();
                node = htmlquoteelement;
            } else if (NodeType.equals("HTMLScriptElement")) {
                HTMLScriptElement htmlscriptelement = ngson.fromJson(NodeBody, HTMLScriptElement.class);
                htmlscriptelement.update();
                node = htmlscriptelement;
            } else if (NodeType.equals("HTMLSelectElement")) {
                HTMLSelectElement htmlselectelement = ngson.fromJson(NodeBody, HTMLSelectElement.class);
                htmlselectelement.update();
                node = htmlselectelement;
            } else if (NodeType.equals("HTMLSourceElement")) {
                HTMLSourceElement htmlsourceelement = ngson.fromJson(NodeBody, HTMLSourceElement.class);
                htmlsourceelement.update();
                node = htmlsourceelement;
            } else if (NodeType.equals("HTMLStyleElement")) {
                HTMLStyleElement htmlstyleelement = ngson.fromJson(NodeBody, HTMLStyleElement.class);
                htmlstyleelement.update();
                node = htmlstyleelement;
            } else if (NodeType.equals("HTMLTableCellElement")) {
                HTMLTableCellElement htmltablecellelement = ngson.fromJson(NodeBody, HTMLTableCellElement.class);
                htmltablecellelement.update();
                node = htmltablecellelement;
            } else if (NodeType.equals("HTMLTableColElement")) {
                HTMLTableColElement htmltablecolelement = ngson.fromJson(NodeBody, HTMLTableColElement.class);
                htmltablecolelement.update();
                node = htmltablecolelement;
            } else if (NodeType.equals("HTMLTableElement")) {
                HTMLTableElement htmltableelement = ngson.fromJson(NodeBody, HTMLTableElement.class);
                htmltableelement.update();
                node = htmltableelement;
            } else if (NodeType.equals("HTMLTableRowElement")) {
                HTMLTableRowElement htmltablerowelement = ngson.fromJson(NodeBody, HTMLTableRowElement.class);
                htmltablerowelement.update();
                node = htmltablerowelement;
            } else if (NodeType.equals("HTMLTableSectionElement")) {
                HTMLTableSectionElement htmltablesectionelement = ngson.fromJson(NodeBody, HTMLTableSectionElement.class);
                htmltablesectionelement.update();
                node = htmltablesectionelement;
            } else if (NodeType.equals("HTMLTemplateElement")) {
                HTMLTemplateElement htmltemplateelement = ngson.fromJson(NodeBody, HTMLTemplateElement.class);
                htmltemplateelement.update();
                node = htmltemplateelement;
            } else if (NodeType.equals("HTMLTextAreaElement")) {
                HTMLTextAreaElement htmltextareaelement = ngson.fromJson(NodeBody, HTMLTextAreaElement.class);
                htmltextareaelement.update();
                node = htmltextareaelement;
            } else if (NodeType.equals("HTMLTimeElement")) {
                HTMLTimeElement htmltimeelement = ngson.fromJson(NodeBody, HTMLTimeElement.class);
                htmltimeelement.update();
                node = htmltimeelement;
            } else if (NodeType.equals("HTMLTitleElement")) {
                HTMLTitleElement htmltitleelement = ngson.fromJson(NodeBody, HTMLTitleElement.class);
                htmltitleelement.update();
                node = htmltitleelement;
            } else if (NodeType.equals("HTMLTrackElement")) {
                HTMLTrackElement htmltrackelement = ngson.fromJson(NodeBody, HTMLTrackElement.class);
                htmltrackelement.update();
                node = htmltrackelement;
            } else if (NodeType.equals("HTMLVideoElement")) {
                HTMLVideoElement htmlvideoelement = ngson.fromJson(NodeBody, HTMLVideoElement.class);
                htmlvideoelement.update();
                node = htmlvideoelement;
            }
            Log log = new Log(EventType, NodeType, event, node, userID);
            logs.add(log);

            System.out.println("--LogCreate--\nEventType(reception):" + EventType + "\nEventType(object):" + log.event+ "\nlog: " + log + "\nLogBody:" + LogBody + "\n--Fin(Logcreate)--");
            // キューの内容を表示
            System.out.println("Current logs in queue(LogCreate):");
            for (Log currentLog : logs) {
                System.out.println(currentLog);  // LogクラスのtoString()メソッドが呼び出されます
            }
            System.out.println("--End of logs--");

        } else {
            System.out.println("NULL");
        }
        //request.getRequestDispatcher("/standard_event.json").forward(request, response);
    }

    //HTTP GETリクエストを処理
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }


    //Cookieの設定
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String path, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(maxAge);
        cookie.setPath(path);
        //httpsで稼働している環境であればCookieが暗号化されるようSecure属性をつける
        if ("https".equals(request.getScheme())) {
            cookie.setSecure(true);
        }
        response.addCookie(cookie);
    }

    //MySQLデータベースにログデータを送信
    protected void sendDatabase() {
        //実行するSQL文
        try {
            //データベース接続情報
            String path = "jdbc:mysql://localhost:3306/test?autoReconnect=true&useSSL=false&rewriteBatchedStatements=true";  //接続パス
            String id = "root";    //ログインID
            String pw = "Usagi3.0807";  //ログインパスワード

            //JDBCドライバをロード
            Class.forName("com.mysql.jdbc.Driver");

            //データベースに接続
            conn = DriverManager.getConnection(path, id, pw);

            Log log;
            //ログキューがから出ない限りログをデータベースに送信(動いてる？？)
            while (logs.size() != 0) {
                log = logs.poll();
                //ログを挿入し，IDを取得
                int logID = log.Insert(conn, ps, rs);
                //イベント情報を挿入
                log.event.Insert(logID, conn, ps, rs);
                //ノード情報を挿入
                log.node.Insert(logID, conn, ps, rs);
                //ログキューサイズの表示
                System.out.println("size:" + logs.size());
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //接続クローズ
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //SQliteデータベースにログ情報を送信するメソッド
    synchronized protected void sendSQliteDatabase() {
        int logID = -1;
        try {
            //JDBCドライバをロード
            Class.forName("org.sqlite.JDBC");

            //SQLite設定
            SQLiteConfig sqLiteConfig = new SQLiteConfig();
            sqLiteConfig.setSynchronous(SQLiteConfig.SynchronousMode.NORMAL);
            sqLiteConfig.setJournalMode(SQLiteConfig.JournalMode.WAL);
            //try (Connection connection = DriverManager.getConnection("jdbc:sqlite:C:/Users/ikeda/Desktop/database/testSyudou.db", sqLiteConfig.toProperties())) //開発環境

            //データベース接続情報(Docker環境)
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:/usr/local/tomcat/db/test.db", sqLiteConfig.toProperties())) {
                connection.setAutoCommit(false);

                //ログキューが空でない限りログをSQLiteデータベースに送信
                while (logs.size() != 0) {
                    Log log = logs.poll();
                    log.sqliteInsert(connection);
                    //logs.remove(0);
                    System.out.println("SQLite size:" + logs.size());
                }
                
                connection.commit();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //初期化処理
    public void init() throws ServletException {
        //ログライタースレッドの開始
        LogWriter logwriter = new LogWriter(logs);
        logwriter.start();
    }
}

