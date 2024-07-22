package oplor.server.event;

import java.sql.*;

//テキスト入力に関するクラス
public class CompositionEvent extends UIEvent {
    //イベントに関する追加情報
    public String data;
    public String locate;

    CompositionEvent() {
        super();
        data = "?";
        locate = "?";
    }

    //CompositionEventの更新
    public void update() {
        //親クラスのアップデート
        super.update();
        //deta, locateの値をgetterマップに追加
        getter.put("data", data);
        getter.put("locate", locate);
    }

    //データベースにCompositionEventを挿入
    public int Insert(int logID, Connection conn, PreparedStatement ps, ResultSet rs) throws SQLException {
        //親イベントのIDを取得
        int parentID = super.Insert(logID, conn, ps, rs);

        //CompositionEventテーブルにデータを挿入するSQL文
        String sql = "INSERT INTO compositionevent(ref, data, locate)VALUES(?, ?, ?)";

        //準備されたステートメントを作成
        ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        //SQL文のパラメータを設定
        ps.setInt(1, parentID);
        ps.setString(2, data);
        ps.setString(3, locate);

        //INSERT文の実行
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

    //SQLiteデータベースにCompositionEventを挿入
    public int sqliteInsert(int logID, Connection connection) throws SQLException {
        //親イベントのIDを取得
        int parentID = super.sqliteInsert(logID, connection);
        int childID = -1;

        //CompositionEventにデータを挿入するSQL文
        String sql = "insert into CompositionEvent(ref, data, locate) values(?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            //パラメータの設定
            ps.setInt(1, parentID);
            ps.setString(2, data);
            ps.setString(3, locate);

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
