package oplor.server.node;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//HTML文書内のメタデータを表現するクラス
public class HTMLMetaElement extends HTMLElement {
    public String content;
    public String httpEquiv;
    public String name;

    //HTMLMetaElementの更新
    public void update() {
        //親クラスのアップデート
        super.update();
        //追加情報をgetterマップに追加
        getter.put("content", content);
        getter.put("httpEquiv", httpEquiv);
        getter.put("name", name);
    }

    //データベースにHTMLMetaElementを挿入
    public int Insert(int logID, Connection conn, PreparedStatement ps, ResultSet rs) throws SQLException {
        //親イベントのIDを取得
        int parentID = super.Insert(logID, conn, ps, rs);

        //htmlmetaelementテーブルにデータを挿入するSQL文
        String sql = "INSERT INTO htmlmetaelement(ref, content, httpEquiv, name)VALUES(?, ?, ?, ?)";

        //準備されたステートメントを作成
        ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

        //パラメータを設定
        ps.setInt(1, parentID);
        ps.setString(2, content);
        ps.setString(3, httpEquiv);
        ps.setString(4, name);

        //INSERT文を実行する
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

    //SQLiteデータベースにHTMLMetaElement情報を挿入
    public int sqliteInsert(int logID, Connection connection) throws SQLException {
        //親ノードのIDを取得
        int parentID = super.sqliteInsert(logID, connection);
        int childID = -1;

        //HTMLMetaElementテーブルにデータを挿入するSQL文
        String sql = "insert into HTMLMetaElement(ref, content, httpEquiv, name)values(?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            //パラメータの設定
            ps.setInt(1, parentID);
            ps.setString(2, content);
            ps.setString(3, httpEquiv);
            ps.setString(4, name);
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
