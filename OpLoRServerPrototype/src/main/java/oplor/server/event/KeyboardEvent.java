package oplor.server.event;

import java.sql.*;

//キーボード入力に関するクラス
public class KeyboardEvent extends UIEvent {
    //イベントに関する情報
    public boolean altKey;
    public String code;
    public boolean ctrlKey;
    public boolean isComposing;
    public String key;
    public String locate;
    public int location;
    public boolean metaKey;
    public boolean repeat;
    public boolean shiftKey;

    //KeyboardEventの更新
    public void update() {
        //親クラスのアップデート
        super.update();

        //Keyboardイベントに関する追加情報をgetterマップに追加
        getter.put("altKey", altKey);
        getter.put("code", code);
        getter.put("ctrlKey", ctrlKey);
        getter.put("isComposing", isComposing);
        getter.put("key", key);
        getter.put("locate", locate);
        getter.put("location", location);
        getter.put("metaKey", metaKey);
        getter.put("repeat", repeat);
        getter.put("shiftKey", shiftKey);
    }

    //データベースにKeyboardEventを挿入
    public int Insert(int logID, Connection conn, PreparedStatement ps, ResultSet rs) throws SQLException {
        //親イベントのIDを取得
        int parentID = super.Insert(logID, conn, ps, rs);

        //KeyboardEventにデータを挿入するSQL文
        String sql = "INSERT INTO keyboardevent(ref, altKey, code, ctrlKey, isComposing, key2, locate, location, metaKey, repeat2, shiftKey)VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        //準備されたステートメントを作成
        ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

        //SQL文のパラメータを設定
        ps.setInt(1, parentID);
        ps.setBoolean(2, altKey);
        ps.setString(3, code);
        ps.setBoolean(4, ctrlKey);
        ps.setBoolean(5, isComposing);
        ps.setString(6, key);
        ps.setString(7, locate);
        ps.setDouble(8, location);
        ps.setBoolean(9, metaKey);
        ps.setBoolean(10, repeat);
        ps.setBoolean(11, shiftKey);

        //INSERT文を実行
        ps.executeUpdate();

        //生成したキーの取得
        int childID = -1;
        rs = ps.getGeneratedKeys();

        //挿入したレコードのキーを取得
        while (rs.next()) {
            childID = rs.getInt(1); //挿入したレコードのIDを取得
        }

        //挿入したレコードのIDを返す
        return childID;
    }

    //SQLiteデータベースにKeyboardEventを挿入
    public int sqliteInsert(int logID, Connection connection) throws SQLException {
        //親イベントのIDを取得
        int parentID = super.sqliteInsert(logID, connection);
        int childID = -1;

        //KeyboardEventにデータを挿入するSQL文
        String sql = "insert into KeyboardEvent(ref, altKey, code, ctrlKey, isComposing, key2, locate, location, metaKey, repeat2, shiftKey)" +
                "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            //パラメータの設定
            ps.setInt(1, parentID);
            ps.setBoolean(2, altKey);
            ps.setString(3, code);
            ps.setBoolean(4, ctrlKey);
            ps.setBoolean(5, isComposing);
            ps.setString(6, key);
            ps.setString(7, locate);
            ps.setDouble(8, location);
            ps.setBoolean(9, metaKey);
            ps.setBoolean(10, repeat);
            ps.setBoolean(11, shiftKey);

            //SQL文の実行
            ps.executeUpdate();

            //挿入したレコードのキーを取得
            ResultSet rs = ps.getGeneratedKeys();
            while (rs.next()) {
                childID = rs.getInt(1); //挿入したレコードのIDを取得
            }
        }

        //挿入されたレコードIDを返す
        return childID;
    }
}
