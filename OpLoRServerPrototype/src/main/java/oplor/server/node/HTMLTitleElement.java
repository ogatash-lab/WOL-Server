package oplor.server.node;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//<title>要素に関するクラス
public class HTMLTitleElement extends HTMLElement {
    public String text;

    //データベースにHTMLTitleElementを挿入
    public int Insert(String userID, Connection conn, PreparedStatement ps, ResultSet rs) throws SQLException {
        //int parentID=super.Insert(conn, ps, rs);
        //htmltitleelementテーブルにデータを挿入するSQL文
        String sql = "INSERT INTO htmltitleelement(ref, text)"
                + "VALUES(?, ?)";
        //準備されたステートメントを作成
        ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        //パラメータを設定
        //ps.setInt(1, parentID);
        ps.setString(2, text);

        //INSERT文を実行
        ps.executeUpdate();
        //挿入したレコードのキーを取得
        int childID = -1;
        rs = ps.getGeneratedKeys();
        while (rs.next()) {
            childID = rs.getInt(1); //挿入したレコードのIDを取得
        }
        //取得したレコードIDを返す
        return childID;
    }

    //SQLiteデータベースにHTMLTitleElement情報を挿入
    public int sqliteInsert(int logID, Connection connection) throws SQLException {
        //親ノードのIDを取得
        int parentID = super.sqliteInsert(logID, connection);
        int childID = -1;
        //HTMLTitleElementテーブルにデータを挿入するSQL文
        String sql = "insert into HTMLTitleElement(ref, text)"
                + "values(?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            //パラメータの設定
            ps.setInt(1, parentID);
            ps.setString(2, text);
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
