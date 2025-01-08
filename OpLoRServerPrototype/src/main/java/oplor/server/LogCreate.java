package oplor.server;

import com.google.gson.Gson;    // JSONのデシリアライズ用
import oplor.server.event.*;    // Eventに関するクラス
import oplor.server.node.*;     // Nodeに関するクラス
import org.sqlite.SQLiteConfig; // SQLiteの設定クラス

// Servlet関係
import javax.servlet.ServletException;      // 例外処理用
import javax.servlet.annotation.WebServlet; // URLマッピングを行うアノテーション(@WebServlet)用
import javax.servlet.http.*;                // httpリクエスト・レスポンス関連クラス

import java.io.BufferedReader;                      // リクエストボディ処理用バッファ付きリーダ
import java.io.IOException;                         // 入出力例外処理
import java.util.concurrent.LinkedBlockingQueue;    // スレッド間でデータを共有するためのスレッドセーフなキュー

// MySQLデータベースに保存するクラス
@WebServlet(name = "LogCreate", urlPatterns = {"/HW"})  // @WebServletアノテーションでServletのURLパターンを指定
public class LogCreate extends HttpServlet {
    private final LinkedBlockingQueue<Log> logs = new LinkedBlockingQueue<>();   // ログデータを保存するためのキュー

    // HTTP POSTリクエストを処理
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=ASCII");  // レスポンスのコンテンツタイプをJSONに設定

        // リクエストボディを読み込む
        BufferedReader reader = request.getReader();    // リクエストボディを読み取るためのBufferedReader

        // リクエストボディを分割(区切り文字：@@)
        String body = "";
        String line = null;
        while ((line = reader.readLine()) != null) {
            body += line;   // リクエストボディを1行ずつ読み込み、bodyに追加
        }
        String[] strs = body.split("@@", 0);    // 受け取ったデータを区切り文字'@@'で分割

        if (strs.length == 3) { // リクエストデータが正しく分割されている場合
            // 分割したデータからイベントタイプ，ノードタイプ，ログボディを解析
            String EventType = strs[0]; // イベントタイプを取得
            String NodeType = strs[1];  // ノードタイプを取得
            String LogBody = strs[2];   // ログボディを取得

            // ログボディをイベントボディとノードボディに分割(例. LogBody = [{イベント情報}{ノード情報}])
            String[] logbody = LogBody.split("},", 2);  // イベント部分とノード部分に分割

            // イベントボディの作成
            String EventBody = logbody[0];
            EventBody += "}";
            EventBody = EventBody.replace("[", "");

            // ノードボディの作成
            String NodeBody = logbody[1];
            NodeBody = NodeBody.replace("}]", "}");

            // JSONデシリアライズ用のGsonオブジェクトを初期化(JSON->Object)
            Gson gson = new Gson();

            Event event = new Event();
            Node node = new Node();
            

            // EventTypeに基づいてイベントをデシリアライズ
            try {
                if (EventType.equals("Event")) {
                    event = gson.fromJson(EventBody, Event.class);
                } else if (EventType.equals("UIEvent")) {
                    UIEvent uievent = gson.fromJson(EventBody, UIEvent.class);
                    event = uievent;
                } else if (EventType.equals("FocusEvent")) {
                    FocusEvent focusevent = gson.fromJson(EventBody, FocusEvent.class);
                    event = focusevent;
                } else if (EventType.equals("MouseEvent")) {
                    MouseEvent mouseevent = gson.fromJson(EventBody, MouseEvent.class);
                    event = mouseevent;
                } else if (EventType.equals("TouchEvent")) {
                    TouchEvent touchevent = gson.fromJson(EventBody, TouchEvent.class);
                    event = touchevent;
                } else if (EventType.equals("KeyboardEvent")) {
                    KeyboardEvent keyboardevent = gson.fromJson(EventBody, KeyboardEvent.class);
                    event = keyboardevent;
                } else if (EventType.equals("InputEvent")) {
                    InputEvent inputevent = gson.fromJson(EventBody, InputEvent.class);
                    event = inputevent;
                } else if (EventType.equals("CompositionEvent")) {
                    CompositionEvent compositionevent = gson.fromJson(EventBody, CompositionEvent.class);
                    event = compositionevent;
                } else if (EventType.equals("WheelEvent")) {
                    WheelEvent wheelevent = gson.fromJson(EventBody, WheelEvent.class);
                    event = wheelevent;
                } else if (EventType.equals("DragEvent")) {
                    DragEvent dragevent = gson.fromJson(EventBody, DragEvent.class);
                    event = dragevent;
                }
            } catch (Exception e) {
                System.err.println("Error deserializing event: " + EventType);
                System.err.println("Error message: " + e.getMessage());
                e.printStackTrace();
            }

            // NodeTypeに基づいてノードをデシリアライズ
            try {
                if (NodeType.equals("node")) {
                    node = gson.fromJson(NodeBody, Node.class);
                } else if (NodeType.equals("DocumentType")) {
                    DocumentType documenttype = gson.fromJson(NodeBody, DocumentType.class);
                    node = documenttype;
                } else if (NodeType.equals("Document")) {
                    Document document = gson.fromJson(NodeBody, Document.class);
                    node = document;
                } else if (NodeType.equals("Text")) {
                    Text text = gson.fromJson(NodeBody, Text.class);
                    node = text;
                } else if (NodeType.equals("DocumentFragment")) {
                    DocumentFragment documentfragment = gson.fromJson(NodeBody, DocumentFragment.class);
                    node = documentfragment;
                } else if (NodeType.equals("Element")) {
                    Element element = gson.fromJson(NodeBody, Element.class);
                    node = element;
                } else if (NodeType.equals("HTMLElement")) {
                    HTMLElement htmlelement = gson.fromJson(NodeBody, HTMLElement.class);
                    node = htmlelement;
                } else if (NodeType.equals("HTMLAnchorElement")) {
                    HTMLAnchorElement htmlanchorelement = gson.fromJson(NodeBody, HTMLAnchorElement.class);
                    node = htmlanchorelement;
                } else if (NodeType.equals("HTMLAreaElement")) {
                    HTMLAreaElement htmlareaelement = gson.fromJson(NodeBody, HTMLAreaElement.class);
                    node = htmlareaelement;
                } else if (NodeType.equals("HTMLBaseElement")) {
                    HTMLBaseElement htmlbaseelement = gson.fromJson(NodeBody, HTMLBaseElement.class);
                    node = htmlbaseelement;
                } else if (NodeType.equals("HTMLButtonElement")) {
                    HTMLButtonElement htmlbuttonelement = gson.fromJson(NodeBody, HTMLButtonElement.class);
                    node = htmlbuttonelement;
                } else if (NodeType.equals("HTMLCanvasElement")) {
                    HTMLCanvasElement htmlcanvaselement = gson.fromJson(NodeBody, HTMLCanvasElement.class);
                    node = htmlcanvaselement;
                } else if (NodeType.equals("HTMLDataElement")) {
                    HTMLDataElement htmldataelement = gson.fromJson(NodeBody, HTMLDataElement.class);
                    node = htmldataelement;
                } else if (NodeType.equals("HTMLDataListElement")) {
                    HTMLDataListElement htmldatalistelement = gson.fromJson(NodeBody, HTMLDataListElement.class);
                    node = htmldatalistelement;
                } else if (NodeType.equals("HTMLDialogElement")) {
                    HTMLDialogElement htmldialogelement = gson.fromJson(NodeBody, HTMLDialogElement.class);
                    node = htmldialogelement;
                } else if (NodeType.equals("HTMLEmbedElement")) {
                    HTMLEmbedElement htmlembedelement = gson.fromJson(NodeBody, HTMLEmbedElement.class);
                    node = htmlembedelement;
                } else if (NodeType.equals("HTMLFieldSetElement")) {
                    HTMLFieldSetElement htmlfieldsetelement = gson.fromJson(NodeBody, HTMLFieldSetElement.class);
                    node = htmlfieldsetelement;
                } else if (NodeType.equals("HTMLFormElement")) {
                    HTMLFormElement htmlformelement = gson.fromJson(NodeBody, HTMLFormElement.class);
                    node = htmlformelement;
                } else if (NodeType.equals("HTMLIFrameElement")) {
                    HTMLIFrameElement htmliframeelement = gson.fromJson(NodeBody, HTMLIFrameElement.class);
                    node = htmliframeelement;
                } else if (NodeType.equals("HTMLInputElement")) {
                    HTMLInputElement htmlinputelement = gson.fromJson(NodeBody, HTMLInputElement.class);
                    node = htmlinputelement;
                } else if (NodeType.equals("HTMLLabelElement")) {
                    HTMLLabelElement htmllabelelement = gson.fromJson(NodeBody, HTMLLabelElement.class);
                    node = htmllabelelement;
                } else if (NodeType.equals("HTMLLegendElement")) {
                    HTMLLegendElement htmllegendelement = gson.fromJson(NodeBody, HTMLLegendElement.class);
                    node = htmllegendelement;
                } else if (NodeType.equals("HTMLLIElement")) {
                    HTMLLIElement htmllielement = gson.fromJson(NodeBody, HTMLLIElement.class);
                    node = htmllielement;
                } else if (NodeType.equals("HTMLLinkElement")) {
                    HTMLLinkElement htmllinkelement = gson.fromJson(NodeBody, HTMLLinkElement.class);
                    node = htmllinkelement;
                } else if (NodeType.equals("HTMLMapElement")) {
                    HTMLMapElement htmlmapelement = gson.fromJson(NodeBody, HTMLMapElement.class);
                    node = htmlmapelement;
                } else if (NodeType.equals("HTMLMetaElement")) {
                    HTMLMetaElement htmlmetaelement = gson.fromJson(NodeBody, HTMLMetaElement.class);
                    node = htmlmetaelement;
                } else if (NodeType.equals("HTMLMediaElement")) {
                    HTMLMediaElement htmlmediaelement = gson.fromJson(NodeBody, HTMLMediaElement.class);
                    node = htmlmediaelement;
                } else if (NodeType.equals("HTMLMeterElement")) {
                    HTMLMeterElement htmlmeterelement = gson.fromJson(NodeBody, HTMLMeterElement.class);
                    node = htmlmeterelement;
                } else if (NodeType.equals("HTMLOListElement")) {
                    HTMLOListElement htmlolistelement = gson.fromJson(NodeBody, HTMLOListElement.class);
                    node = htmlolistelement;
                } else if (NodeType.equals("HTMLObjectElement")) {
                    HTMLObjectElement htmlobjectelement = gson.fromJson(NodeBody, HTMLObjectElement.class);
                    node = htmlobjectelement;
                } else if (NodeType.equals("HTMLOptGroupElement")) {
                    HTMLOptGroupElement htmloptgroupelement = gson.fromJson(NodeBody, HTMLOptGroupElement.class);
                    node = htmloptgroupelement;
                } else if (NodeType.equals("HTMLParamElement")) {
                    HTMLParamElement htmlparamelement = gson.fromJson(NodeBody, HTMLParamElement.class);
                    node = htmlparamelement;
                } else if (NodeType.equals("HTMLProgressElement")) {
                    HTMLProgressElement htmlprogresselement = gson.fromJson(NodeBody, HTMLProgressElement.class);
                    node = htmlprogresselement;
                } else if (NodeType.equals("HTMLQuoteElement")) {
                    HTMLQuoteElement htmlquoteelement = gson.fromJson(NodeBody, HTMLQuoteElement.class);
                    node = htmlquoteelement;
                } else if (NodeType.equals("HTMLScriptElement")) {
                    HTMLScriptElement htmlscriptelement = gson.fromJson(NodeBody, HTMLScriptElement.class);
                    node = htmlscriptelement;
                } else if (NodeType.equals("HTMLSelectElement")) {
                    HTMLSelectElement htmlselectelement = gson.fromJson(NodeBody, HTMLSelectElement.class);
                    node = htmlselectelement;
                } else if (NodeType.equals("HTMLSourceElement")) {
                    HTMLSourceElement htmlsourceelement = gson.fromJson(NodeBody, HTMLSourceElement.class);
                    node = htmlsourceelement;
                } else if (NodeType.equals("HTMLStyleElement")) {
                    HTMLStyleElement htmlstyleelement = gson.fromJson(NodeBody, HTMLStyleElement.class);
                    node = htmlstyleelement;
                } else if (NodeType.equals("HTMLTableCellElement")) {
                    HTMLTableCellElement htmltablecellelement = gson.fromJson(NodeBody, HTMLTableCellElement.class);
                    node = htmltablecellelement;
                } else if (NodeType.equals("HTMLTableColElement")) {
                    HTMLTableColElement htmltablecolelement = gson.fromJson(NodeBody, HTMLTableColElement.class);
                    node = htmltablecolelement;
                } else if (NodeType.equals("HTMLTableElement")) {
                    HTMLTableElement htmltableelement = gson.fromJson(NodeBody, HTMLTableElement.class);
                    node = htmltableelement;
                } else if (NodeType.equals("HTMLTableRowElement")) {
                    HTMLTableRowElement htmltablerowelement = gson.fromJson(NodeBody, HTMLTableRowElement.class);
                    node = htmltablerowelement;
                } else if (NodeType.equals("HTMLTableSectionElement")) {
                    HTMLTableSectionElement htmltablesectionelement = gson.fromJson(NodeBody, HTMLTableSectionElement.class);
                    node = htmltablesectionelement;
                } else if (NodeType.equals("HTMLTemplateElement")) {
                    HTMLTemplateElement htmltemplateelement = gson.fromJson(NodeBody, HTMLTemplateElement.class);
                    node = htmltemplateelement;
                } else if (NodeType.equals("HTMLTextAreaElement")) {
                    HTMLTextAreaElement htmltextareaelement = gson.fromJson(NodeBody, HTMLTextAreaElement.class);
                    node = htmltextareaelement;
                } else if (NodeType.equals("HTMLTimeElement")) {
                    HTMLTimeElement htmltimeelement = gson.fromJson(NodeBody, HTMLTimeElement.class);
                    node = htmltimeelement;
                } else if (NodeType.equals("HTMLTitleElement")) {
                    HTMLTitleElement htmltitleelement = gson.fromJson(NodeBody, HTMLTitleElement.class);
                    node = htmltitleelement;
                } else if (NodeType.equals("HTMLTrackElement")) {
                    HTMLTrackElement htmltrackelement = gson.fromJson(NodeBody, HTMLTrackElement.class);
                    node = htmltrackelement;
                } else if (NodeType.equals("HTMLVideoElement")) {
                    HTMLVideoElement htmlvideoelement = gson.fromJson(NodeBody, HTMLVideoElement.class);
                    node = htmlvideoelement;
                }
            } catch (Exception e) {
                System.err.println("Error deserializing node: " + NodeType);
                System.err.println("Error message: " + e.getMessage());
                e.printStackTrace();
            }

            // Logオブジェクトを作成し，キューに追加
            Log log = new Log(EventType, NodeType, event, node);
            logs.add(log);

            // ログ作成処理のデバック出力
            System.out.println("--LogCreate--\n" +  "log: " + log + "\nLogBody:" + LogBody + "\n--Fin(Logcreate)--");

        } else {    // リクエストデータが正しく分割されていない場合
            // エラーメッセージを表示
            System.out.println("The request body cannot be properly split.");
        }
    }

    // HTTP GETリクエストを処理
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    // 初期化処理
    public void init() throws ServletException {
        // ログライタースレッドの開始
        LogWriter logwriter = new LogWriter(logs);
        logwriter.start();
    }
}
