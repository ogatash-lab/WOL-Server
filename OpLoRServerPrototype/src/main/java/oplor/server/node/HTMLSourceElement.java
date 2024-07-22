package oplor.server.node;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//<source>要素に関するクラス
public class HTMLSourceElement extends HTMLElement {
    public String keySystem;
    public String media;
    public String sizes;
    public String src;
    public String srcset;
    public String type;

    //HTMLSourceElementの更新
    public void update() {
        //親クラスのアップデート
        super.update();
        //追加情報をgetterマップに追加
        getter.put("keySystem", keySystem);
        getter.put("media", media);
        getter.put("sizes", sizes);
        getter.put("src", src);
        getter.put("srcset", srcset);
        getter.put("type", type);
    }

    //データベースにHTMLSourceElementを挿入
    public int Insert(int logID, Connection conn, PreparedStatement ps, ResultSet rs) throws SQLException {
        //親イベントのIDを取得
        int parentID = super.Insert(logID, conn, ps, rs);
        //htmlsourceelementテーブルにデータを挿入するSQL文
        String sql = "INSERT INTO htmlsourceelement(ref, keySystem, media, sizes, src, srcset, type)" +
                "VALUES(?, ?, ?, ?, ?, ?, ?)";
        //準備されたステートメントを作成
        ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        //パラメータを設定
        ps.setInt(1, parentID);
        ps.setString(2, keySystem);
        ps.setString(3, media);
        ps.setString(4, sizes);
        ps.setString(5, src);
        ps.setString(6, srcset);
        ps.setString(7, type);

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

    //SQLiteデータベースにHTMLSourceElement情報を挿入
    public int sqliteInsert(int logID, Connection connection) throws SQLException {
        //親ノードのIDを取得
        int parentID = super.sqliteInsert(logID, connection);
        int childID = -1;
        //HTMLSourceElementテーブルにデータを挿入するSQL文
        String sql = "insert into HTMLSourceElement(ref, keySystem, media, sizes, src, srcset, type)" +
                "values(?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            //パラメータを設定
            ps.setInt(1, parentID);
            ps.setString(2, keySystem);
            ps.setString(3, media);
            ps.setString(4, sizes);
            ps.setString(5, src);
            ps.setString(6, srcset);
            ps.setString(7, type);
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
