package oplor.server.node;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//<li>要素に関するクラス
public class HTMLLIElement extends HTMLElement {
    public long value;

    //HTMLLIElementの更新
    public void update() {
        //親クラスのアップデート
        super.update();
        //追加情報をgetterマップに追加
        getter.put("value", value);
    }

    //データベースにHTMLLIElementを挿入
    public int Insert(int logID, Connection conn, PreparedStatement ps, ResultSet rs) throws SQLException {
        //親イベントのIDを取得
        int parentID = super.Insert(logID, conn, ps, rs);

        //htmllielementテーブルにデータを挿入するSQL文
        String sql = "INSERT INTO htmllielement(ref, value)VALUES(?, ?)";

        //準備されたステートメントを作成
        ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

        //パラメータを設定
        ps.setInt(1, parentID);
        ps.setLong(2, value);

        //INSERT文を実行
        ps.executeUpdate();

        //挿入されたレコードのキーを取得
        int childID = -1;
        rs = ps.getGeneratedKeys();
        while (rs.next()) {
            childID = rs.getInt(1); //挿入したレコードIDを取得
        }
        //取得したレコードIDを返す
        return childID;
    }

    //SQLiteデータベースにHTMLLIElement情報を挿入
    public int sqliteInsert(int logID, Connection connection) throws SQLException {
        //親ノードIDを取得
        int parentID = super.sqliteInsert(logID, connection);
        int childID = -1;

        //HTMLLIElementテーブルに挿入するSQL文
        String sql = "insert into HTMLLIElement(ref, value)values(?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            //パラメータの設定
            ps.setInt(1, parentID);
            ps.setLong(2, value);
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
