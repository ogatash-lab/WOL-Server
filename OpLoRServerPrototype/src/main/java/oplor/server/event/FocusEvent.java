package oplor.server.event;

import java.sql.*;

//フォームやリンクを選択した際のイベントに関するクラス
public class FocusEvent extends UIEvent {

    //FocusEventの更新
    public void update() {
        //親クラスのアップデート
        super.update();
    }

    //データベースにFocusEventを挿入
    public int Insert(int logID, Connection conn, PreparedStatement ps, ResultSet rs) throws SQLException {
        //親イベントのIDを取得
        int parentID = super.Insert(logID, conn, ps, rs);

        //FocusEventテーブルにデータを挿入するSQL文
        String sql = "INSERT INTO focusevent(ref)VALUES(?)";

        //準備されたステートステートメントを作成
        ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

        //SQL文のパラメータを設定
        ps.setInt(1, parentID);

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

    //SQLiteデータベースにFocusEventを挿入
    public int sqliteInsert(int logID, Connection connection) throws SQLException {
        //親イベントのIDを取得
        int parentID = super.sqliteInsert(logID, connection);
        int childID = -1;

        //FocusEventにデータを挿入するSQL文
        String sql = "insert into FocusEvent(ref)values(?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            //パラメータの設定
            ps.setInt(1, parentID);

            //SQL文の実行
            ps.executeUpdate();

            //挿入したレコードのキーを取得
            ResultSet rs = ps.getGeneratedKeys();
            while (rs.next()) {
                childID = rs.getInt(1); //挿入したレコードのIDを取得
            }
        }

        //挿入したレコードIDを返す
        return childID;
    }
}
