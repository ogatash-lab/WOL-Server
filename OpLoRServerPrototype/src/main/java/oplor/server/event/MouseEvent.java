package oplor.server.event;

import java.sql.*;

//マウス操作に関するイベント
public class MouseEvent extends UIEvent {
    //イベントに関する情報
    public boolean altKey;
    public int button;
    public int buttons;
    public int clientX;
    public int clientY;
    public boolean ctrlKey;
    public boolean metaKey;
    public int movementX;
    public int movementY;
    public int offsetX;
    public int offsetY;
    public int pageX;
    public int pageY;
    //public String region;
    public int screenX;
    public int screenY;
    public boolean shiftKey;
    public int x;
    public int y;

    //MouseEventの更新
    public void update() {
        //親クラスのアップデート
        super.update();

        //Mouseイベントに関する追加情報をgetterマップに追加
        getter.put("altKey", altKey);
        getter.put("button", button);
        getter.put("buttons", buttons);
        getter.put("clientX", clientX);
        getter.put("clientY", clientY);
        getter.put("ctrlKey", ctrlKey);
        getter.put("metaKey", metaKey);
        getter.put("movementX", movementX);
        getter.put("movementY", movementY);
        getter.put("offsetX", offsetX);
        getter.put("offsetY", offsetY);
        getter.put("pageX", pageX);
        getter.put("pageY", pageY);
        //getter.put("region", region);
        getter.put("screenX", screenX);
        getter.put("screenY", screenY);
        getter.put("shiftKey", shiftKey);
        getter.put("x", x);
        getter.put("y", y);
    }

    //データベースにMouseEventを挿入
    public int Insert(int logID, Connection conn, PreparedStatement ps, ResultSet rs) throws SQLException {
        //親イベントのIDを取得
        int parentID = super.Insert(logID, conn, ps, rs);

        //MouseEventにデータを挿入するSQL文
        String sql = "INSERT INTO mouseevent(ref, altKey, button, buttons, client_X, client_Y, ctrlKey, metaKey, movementX, movementY, offsetX, offsetY, pageX, pageY, screenX, screenY, shiftKey, x, y)" +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        //準備されたステートメントを作成
        ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

        //SQL文のパラメータを設定
        ps.setInt(1, parentID);
        ps.setBoolean(2, altKey);
        ps.setInt(3, button);
        ps.setInt(4, buttons);
        ps.setInt(5, clientX);
        ps.setInt(6, clientY);
        ps.setBoolean(7, ctrlKey);
        ps.setBoolean(8, metaKey);
        ps.setInt(9, movementX);
        ps.setInt(10, movementY);
        ps.setInt(11, offsetX);
        ps.setInt(12, offsetY);
        ps.setInt(13, pageX);
        ps.setInt(14, pageY);
        ps.setInt(15, screenX);
        ps.setInt(16, screenY);
        ps.setBoolean(17, shiftKey);
        ps.setInt(18, x);
        ps.setInt(19, y);

        //INSERT文を実行
        ps.executeUpdate();

        //生成したキーの取得
        int childID = -1;
        rs = ps.getGeneratedKeys();

        //挿入したレコードのキーを取得
        while (rs.next()) {
            childID = rs.getInt(1); //挿入したレコードのIDを挿入
        }

        //挿入したレコードのIDを返す
        return childID;
    }

    //SQLiteデータベースにMouseEventを挿入
    public int sqliteInsert(int logID, Connection connection) throws SQLException {
        //親イベントのIDを取得
        int parentID = super.sqliteInsert(logID, connection);
        int childID = -1;

        //MouseEventにデータを挿入するSQL文
        String sql = "insert into MouseEvent(ref, altKey, button, buttons, client_X, client_Y, ctrlKey, metaKey, movementX, movementY, offsetX, offsetY, pageX, pageY, screenX, screenY, shiftKey, x, y)" +
                "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            //パラメータの設定
            ps.setInt(1, parentID);
            ps.setBoolean(2, altKey);
            ps.setInt(3, button);
            ps.setInt(4, buttons);
            ps.setInt(5, clientX);
            ps.setInt(6, clientY);
            ps.setBoolean(7, ctrlKey);
            ps.setBoolean(8, metaKey);
            ps.setInt(9, movementX);
            ps.setInt(10, movementY);
            ps.setInt(11, offsetX);
            ps.setInt(12, offsetY);
            ps.setInt(13, pageX);
            ps.setInt(14, pageY);
            ps.setInt(15, screenX);
            ps.setInt(16, screenY);
            ps.setBoolean(17, shiftKey);
            ps.setInt(18, x);
            ps.setInt(19, y);

            //SQL文の実行
            ps.executeUpdate();

            //生成したキーの取得
            ResultSet rs = ps.getGeneratedKeys();

            //挿入したレコードのキーを取得
            while (rs.next()) {
                childID = rs.getInt(1); //挿入したレコードのIDを取得
            }
        }

        //挿入したレコードIDを返す
        return childID;
    }
}
