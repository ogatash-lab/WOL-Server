package oplor.server.node;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

    public void update() {
        super.update();
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

    public int Insert(int logID, Connection conn, PreparedStatement ps, ResultSet rs) throws SQLException {
        int parentID = super.Insert(logID, conn, ps, rs);
        //INSERT
        String sql = "INSERT INTO htmllinkelement(ref, as, crossOrigin, disabled, href, hrefkang, media, referrerPolicy, rel, type)" +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setInt(1, parentID);
        ps.setString(2, as);
        ps.setString(3, crossOrigin);
        ps.setBoolean(4, disabled);
        ps.setString(5, hrefkang);
        ps.setString(6, media);
        ps.setString(7, referrerPolicy);
        ps.setString(8, rel);
        ps.setString(9, type);

        //ISNERTを実行する
        ps.executeUpdate();
        //次の子クラスへと紐づけるためのID取得
        int childID = -1;
        rs = ps.getGeneratedKeys();
        while (rs.next()) {
            childID = rs.getInt(1);
        }
        return childID;
    }

    public int sqliteInsert(int logID, Connection connection) throws SQLException {
        int parentID = super.sqliteInsert(logID, connection);
        int childID = -1;
        String sql = "insert into HTMLLinkElement(ref, as, crossOrigin, disabled, href, hrefkang, media, referrerPolicy, rel, type)" +
                "values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, parentID);
            ps.setString(2, as);
            ps.setString(3, crossOrigin);
            ps.setBoolean(4, disabled);
            ps.setString(5, hrefkang);
            ps.setString(6, media);
            ps.setString(7, referrerPolicy);
            ps.setString(8, rel);
            ps.setString(9, type);

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            while (rs.next()) {
                childID = rs.getInt(1);
            }
        }
        return childID;
    }
}
