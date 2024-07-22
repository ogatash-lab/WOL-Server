package oplor.server.event;

import java.sql.*;

//入力操作に関するクラス
public class InputEvent extends UIEvent {
    //イベントに関する追加情報
    public String data;
    public String inputType;
    public boolean isComposing;

    //InputEventの更新
    public void update() {
        //親クラスのアップデート
        super.update();
        //data, inputType, isComposingの値をgetterマップに追加
        getter.put("data", data);
        getter.put("inputType", inputType);
        getter.put("isComposing", isComposing);
    }

    //データベースにInputEventを挿入
    public int Insert(int logID, Connection conn, PreparedStatement ps, ResultSet rs) throws SQLException {
        //親イベントのIDを取得
        int parentID = super.Insert(logID, conn, ps, rs);

        //inputEventテーブルにデータを挿入するSQL文
        String sql = "INSERT INTO inputevent(ref, data, inputType, isComposing)VALUES(?, ?, ?, ?)";

        //準備されたステートメントを作成
        ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        //SQL文のパラメータを設定
        ps.setInt(1, parentID);
        ps.setString(2, data);
        ps.setString(3, inputType);
        ps.setBoolean(4, isComposing);

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

    //SQLiteデータベースにInputEventを挿入
    public int sqliteInsert(int logID, Connection connection) throws SQLException {
        //親イベントのIDを取得
        int parentID = super.sqliteInsert(logID, connection);
        int childID = -1;

        //InputEventにデータを挿入するSQL文
        String sql = "insert into InputEvent(ref, data, inputType, isComposing)values(?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            //パラメータの設定
            ps.setInt(1, parentID);
            ps.setString(2, data);
            ps.setString(3, inputType);
            ps.setBoolean(4, isComposing);

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
