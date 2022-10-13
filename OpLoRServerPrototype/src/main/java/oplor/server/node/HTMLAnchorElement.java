package oplor.server.node;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HTMLAnchorElement extends HTMLElement {
    public String accessKey;
    public String download;
    public String hash;
    public String host;
    public String hostname;
    public String href;
    public String hreflang;
    public String media;
    public String password;
    public String origin;
    public String pathname;
    public String port;
    public String protocol;
    public String refferrerPolicy;
    public String rel;
    //public DOMTokenList relList;
    public String search;
    public long tabindex;
    public String target;
    public String text;
    public String type;
    public String username;

    public void update() {
        super.update();
        getter.put("accessKey", accessKey);
        getter.put("download", download);
        getter.put("hash", hash);
        getter.put("host", host);
        getter.put("hostname", hostname);
        getter.put("href", href);
        getter.put("hreflang", hreflang);
        getter.put("media", media);
        getter.put("password", password);
        getter.put("origin", origin);
        getter.put("pathname", pathname);
        getter.put("port", port);
        getter.put("protocol", protocol);
        getter.put("refferrerPolicy", refferrerPolicy);
        getter.put("rel", rel);
        getter.put("search", search);
        getter.put("tabindex", tabindex);
        getter.put("target", target);
        getter.put("text", text);
        getter.put("type", type);
        getter.put("username", username);
    }

    public int Insert(int logID, Connection conn, PreparedStatement ps, ResultSet rs) throws SQLException {
        int parentID = super.Insert(logID, conn, ps, rs);
        //INSERT
        String sql = "INSERT INTO htmlanchorelement(ref, accessKey, download, hash, host, hostname, href, hreflang, media, password, origin, pathname, port, protocol, refferrerPolicy, rel, search, tabindex, target, text, type, username)" +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setInt(1, parentID);
        ps.setString(2, accessKey);
        ps.setString(3, download);
        ps.setString(4, hash);
        ps.setString(5, host);
        ps.setString(6, hostname);
        ps.setString(7, href);
        ps.setString(8, hreflang);
        ps.setString(9, media);
        ps.setString(10, password);
        ps.setString(11, origin);
        ps.setString(12, pathname);
        ps.setString(13, port);
        ps.setString(14, protocol);
        ps.setString(15, refferrerPolicy);
        ps.setString(16, rel);
        ps.setString(17, search);
        ps.setLong(18, tabindex);
        ps.setString(19, target);
        ps.setString(20, text);
        ps.setString(21, type);
        ps.setString(22, username);

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
        String sql = "insert into HTMLAnchorElement(ref, accessKey, download, hash, host, hostname, href, hreflang, media, password, origin, pathname, port, protocol, refferrerPolicy, rel, search, tabindex, target, text, type, username)" +
                "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, parentID);
            ps.setString(2, accessKey);
            ps.setString(3, download);
            ps.setString(4, hash);
            ps.setString(5, host);
            ps.setString(6, hostname);
            ps.setString(7, href);
            ps.setString(8, hreflang);
            ps.setString(9, media);
            ps.setString(10, password);
            ps.setString(11, origin);
            ps.setString(12, pathname);
            ps.setString(13, port);
            ps.setString(14, protocol);
            ps.setString(15, refferrerPolicy);
            ps.setString(16, rel);
            ps.setString(17, search);
            ps.setLong(18, tabindex);
            ps.setString(19, target);
            ps.setString(20, text);
            ps.setString(21, type);
            ps.setString(22, username);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            while (rs.next()) {
                childID = rs.getInt(1);
            }
        }
        return childID;
    }
}
