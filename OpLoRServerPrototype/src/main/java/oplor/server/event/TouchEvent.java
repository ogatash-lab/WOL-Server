package oplor.server.event;

import java.sql.*;

//タッチイベントに関するイベント
public class TouchEvent extends UIEvent {
    //イベントに関する情報
    public boolean altKey;
    //public TouchList changedTouches;
    public boolean ctrlKey;
    public boolean metaKey;
    public boolean shiftKey;

    //TouchEventの更新
    public void update() {
        //親クラスのアップデート
        super.update();

        //Touchイベントに関する追加情報をgetterマップに追加
        getter.put("altKey", altKey);
        getter.put("ctrlKey", ctrlKey);
        getter.put("metaKey", metaKey);
        getter.put("shiftKey", shiftKey);
    }

    //データベースにTouchEventを挿入
    public int Insert(int logID, Connection conn, PreparedStatement ps, ResultSet rs) throws SQLException {
        //親イベントのIDを取得
        int parentID = super.Insert(logID, conn, ps, rs);

        //TouchEventにデータを挿入するSQL文
        String sql = "INSERT INTO touchevent(ref, altKey, ctrlKey, metaKey, shiftKey)VALUES(?, ?, ?, ?, ?, ?)";

        //準備されたステートメントを作成
        ps = conn.prepareStatement(sql);

        //SQL文のパラメータを設定
        ps.setInt(1, parentID);
        ps.setBoolean(2, altKey);
        ps.setBoolean(3, ctrlKey);
        ps.setBoolean(4, metaKey);
        ps.setBoolean(5, shiftKey);

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

    //SQLiteデータベースにTouchEventを挿入
    public int sqliteInsert(int logID, Connection connection) throws SQLException {
        //親イベントのIDを取得
        int parentID = super.sqliteInsert(logID, connection);
        int childID = -1;

        //TouchEventにデータを挿入するSQL文
        String sql = "insert into TouchEvent(ref, altKey, ctrlKey, metaKey, shiftKey)values(?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            //パラメータの設定
            ps.setInt(1, parentID);
            ps.setBoolean(2, altKey);
            ps.setBoolean(3, ctrlKey);
            ps.setBoolean(4, metaKey);
            ps.setBoolean(5, shiftKey);

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
