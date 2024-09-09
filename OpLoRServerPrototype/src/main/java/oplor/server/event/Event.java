package oplor.server.event;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class Event {
    //イベント情報を格納するMap
    public Map<String, Object> getter = new HashMap<>();

    //イベントのプロパティ(内容は以下webサイト参照：https://developer.mozilla.org/ja/docs/Web/API/Event(access:2024/5/31))
    public boolean bubbles;
    public boolean cancelable;
    public boolean composed;
    public boolean defaultPrevented;
    public int eventPhase;
    public double timeStamp;
    public String type;
    public boolean isTrusted;

    //イベントの絶対時間
    public String absTime;

    public Event() {
        this.bubbles = false;
        this.cancelable = false;
        this.composed = false;
        this.defaultPrevented = false;
        this.eventPhase = 0;
        this.timeStamp = 0.0;
        this.type = "?";
        this.isTrusted = false;
        this.absTime = "?";
    }

    //getterマップにイベント情報を更新
    public void update() {
        getter.put("bubbles", bubbles);
        getter.put("cancelable", cancelable);
        getter.put("composed", composed);
        getter.put("defaultPrevented", defaultPrevented);
        getter.put("eventPhase", eventPhase);
        getter.put("timeStamp", timeStamp);
        getter.put("type", type);
        getter.put("isTrusted", isTrusted);
    }

    //データベース接続+イベント情報をデータベースに挿入
    public void send(int logID) {
        //データベース接続の開始をログ出力
        System.out.println("MySQLTEST---START--------------------------------------");
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        //SQL文の実行
        try {
            String path = "jdbc:mysql://localhost:3306/test?autoReconnect=true&useSSL=false";  //接続パス
            String id = "root";    //ログインID
            String pw = "Usagi3.0807";  //ログインパスワード

            //JDBCドライバのロードとデータベースへの接続
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(path, id, pw);

            //データベースへイベント情報を挿入
            Insert(logID, conn, ps, rs);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //データベース接続のクローズ
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //イベント情報をデータベースに挿入
    public int Insert(int logID, Connection conn, PreparedStatement ps, ResultSet rs) throws SQLException {
        int childID = -1;

        //イベントのタイムスタンプを取得
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        absTime = ts.toString(); //2014-02-21 15:33:15.123456789

        //SQL文の作成
        String sql = "INSERT INTO event(ref, bubbles, cancelable, composed, defaultPrevented, eventPhase, timeStamp, type, isTrusted, absTime)VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        //準備されたステートメントを作成
        ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

        //パラメータの設定
        ps.setInt(1, logID);
        ps.setBoolean(2, bubbles);
        ps.setBoolean(3, cancelable);
        ps.setBoolean(4, composed);
        ps.setBoolean(5, defaultPrevented);
        ps.setInt(6, eventPhase);
        ps.setDouble(7, timeStamp);
        ps.setString(8, type);
        ps.setBoolean(9, isTrusted);
        ps.setString(10, absTime);

        //INSERT文の実行
        ps.executeUpdate();

        //挿入されたレコードのキーを取得
        rs = ps.getGeneratedKeys();
        while (rs.next()) {
            childID = rs.getInt(1); //挿入されたレコードのIDを取得
        }

        //挿入されたレコードのIDを返す
        return childID;
    }

    //SQLiteデータベースに新しいイベントを挿入
    public int sqliteInsert(int logID, Connection connection) throws SQLException {
        int childID = -1;
        //INSERT文のSQL文
        String sql = "insert into Event(ref, bubbles, cancelable, composed, defaultPrevented, eventPhase, timeStamp, type, isTrusted, absTime)values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            //パラメータの設定
            ps.setInt(1, logID);
            ps.setString(2, bubbles ? "true" : "false"); // BOOLEANをTEXTに変換
            ps.setString(3, cancelable ? "true" : "false"); // BOOLEANをTEXTに変換
            ps.setString(4, composed ? "true" : "false"); // BOOLEANをTEXTに変換
            ps.setString(5, defaultPrevented ? "true" : "false"); // BOOLEANをTEXTに変換
            ps.setString(6, String.valueOf(eventPhase)); // INTEGERをTEXTに変換
            ps.setString(7, String.valueOf(timeStamp)); // REALをTEXTに変換
            ps.setString(8, type);
            ps.setString(9, isTrusted ? "true" : "false"); // BOOLEANをTEXTに変換
            ps.setString(10, absTime);

            //SQL文の実行
            ps.executeUpdate();

            //挿入されたレコードのキーを取得
            ResultSet rs = ps.getGeneratedKeys();
            while (rs.next()) {
                childID = rs.getInt(1); //挿入されたレコードのIDを取得
            }
        }
        return childID; //挿入されたレコードのIDを返す
    }

    //指定されたキーに対するgetterのオブジェクトを返す
    public Object get(String str) {
        return getter.get(str);
    }
}
