package oplor.server.node;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//<table>要素に関するクラス
public class HTMLTableElement extends HTMLElement {
    //public HTMLTableCaptionElement caption;
    public boolean sortable;

    //HTMLTableElementの更新
    public void update() {
        //親クラスのアップデート
        super.update();
        //追加情報をgetterマップに追加
        getter.put("sortable", sortable);
    }

    //データベースにHTMLTableElementを挿入
    public int Insert(int logID, Connection conn, PreparedStatement ps, ResultSet rs) throws SQLException {
        //親イベントのIDを取得
        int parentID = super.Insert(logID, conn, ps, rs);
        //htmltableelementテーブルにデータを挿入するSQL文
        String sql = "INSERT INTO htmltableelement(ref, sortable)" +
                "VALUES(?, ?)";
        //準備されたステートメントを作成
        ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        //パラメータを設定
        ps.setInt(1, parentID);
        ps.setBoolean(2, sortable);

        //INSERT文を実行
        ps.executeUpdate();
        //挿入したレコードのキーを取得
        int childID = -1;
        rs = ps.getGeneratedKeys();
        while (rs.next()) {
            childID = rs.getInt(1); //挿入したレコードIDを取得
        }
        //取得したレコードIDを返す
        return childID;
    }

    //SQLiteデータベースにHTMLTableElement情報を挿入
    public int sqliteInsert(int logID, Connection connection) throws SQLException {
        //親ノードのIDを取得
        int parentID = super.sqliteInsert(logID, connection);
        int childID = -1;
        //HTMLTableElementテーブルにデータを挿入するSQL文
        String sql = "insert into HTMLTableElement(ref, sortable)" +
                "values(?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            //パラメータの設定
            ps.setInt(1, parentID);
            ps.setBoolean(2, sortable);
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
