package oplor.server.node;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//<link>要素に関するクラス
public class HTMLLinkElement extends HTMLElement {
    public String as;
    public String crossOrigin;
    public boolean disabled;
    public String href;
    public String hrefkang;
    public String media;
    public String referrerPolicy;
    public String rel;
    //public DOMTokenList relList;
    //public DOMSettableTokenList sizes;
    //public StyleSheet sheet;
    public String type;

    //HTMLLinkElementの更新
    public void update() {
        //親クラスのアップデート
        super.update();
        //追加情報をgetterマップに追加
        getter.put("as", as);
        getter.put("crossOrigin", crossOrigin);
        getter.put("disabled", disabled);
        getter.put("href", href);
        getter.put("hrefkang", hrefkang);
        getter.put("media", media);
        getter.put("referrerPolicy", referrerPolicy);
        getter.put("rel", rel);
        getter.put("type", type);
    }

    //データベースにHTMLLinkElementを挿入
    public int Insert(int logID, Connection conn, PreparedStatement ps, ResultSet rs) throws SQLException {
        //親イベントのIDを取得
        int parentID = super.Insert(logID, conn, ps, rs);
        //htmllinkelementテーブルにデータを挿入するSQL文
        String sql = "INSERT INTO htmllinkelement(ref, as, crossOrigin, disabled, href, hrefkang, media, referrerPolicy, rel, type)" +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        //準備されたステートメントを作成
        ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

        //パラメータを設定
        ps.setInt(1, parentID);
        ps.setString(2, as);
        ps.setString(3, crossOrigin);
        ps.setBoolean(4, disabled);
        ps.setString(5, hrefkang);
        ps.setString(6, media);
        ps.setString(7, referrerPolicy);
        ps.setString(8, rel);
        ps.setString(9, type);

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

    //SQLiteデータベースにHTMLLinkElement情報を挿入
    public int sqliteInsert(int logID, Connection connection) throws SQLException {
        //親ノードのIDを取得
        int parentID = super.sqliteInsert(logID, connection);
        int childID = -1;
        //HTMLLinkElementテーブルにデータを挿入するSQL文
        String sql = "insert into HTMLLinkElement(ref, as, crossOrigin, disabled, href, hrefkang, media, referrerPolicy, rel, type)" +
                "values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            //パラメータの設定
            ps.setInt(1, parentID);
            ps.setString(2, as);
            ps.setString(3, crossOrigin);
            ps.setBoolean(4, disabled);
            ps.setString(5, hrefkang);
            ps.setString(6, media);
            ps.setString(7, referrerPolicy);
            ps.setString(8, rel);
            ps.setString(9, type);
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
