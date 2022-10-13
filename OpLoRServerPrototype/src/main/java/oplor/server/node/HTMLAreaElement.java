package oplor.server.node;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HTMLAreaElement extends HTMLElement {
    public String accessKey;
    public String alt;
    public String coords;
    public String download;
    public String hash;
    public String host;
    public String hostname;
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
    public String shape;
    public String tabindex;
    public String target;
    public String type;
    public String username;

    public void update() {
        super.update();
        getter.put("accessKey", accessKey);
        getter.put("alt", alt);
        getter.put("coords", coords);
        getter.put("download", download);
        getter.put("hash", hash);
        getter.put("host", host);
        getter.put("hostname", hostname);
        getter.put("media", media);
        getter.put("password", password);
        getter.put("origin", origin);
        getter.put("pathname", pathname);
        getter.put("port", port);
        getter.put("protocol", protocol);
        getter.put("refferrerPolicy", refferrerPolicy);
        getter.put("rel", rel);
        getter.put("search", search);
        getter.put("shape", shape);
        getter.put("tabindex", tabindex);
        getter.put("target", target);
        getter.put("type", type);
        getter.put("username", username);
    }

    public int Insert(int logID, Connection conn, PreparedStatement ps, ResultSet rs) throws SQLException {
        int childID = -1;
        int parentID = super.Insert(logID, conn, ps, rs);
        //INSERT
        //Calendar
        /*
        Calendar cal=Calendar.getInstance();
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSSSSS");
        absTime = sdf.format(cal.getTime());
        */
        //System.out.println("absTime:"+absTime);
        String sql = "INSERT INTO htmlareaelement(ref, accessKey, alt, coords, download, hash, host, hostname, media, password, origin, pathname, port, protocol, refferrerPolicy, rel, search, shape, tabindex, target, type, username)" +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setInt(1, parentID);
        ps.setString(2, accessKey);
        ps.setString(3, alt);
        ps.setString(4, coords);
        ps.setString(5, download);
        ps.setString(6, hash);
        ps.setString(7, host);
        ps.setString(8, media);
        ps.setString(9, password);
        ps.setString(10, origin);
        ps.setString(11, pathname);
        ps.setString(12, port);
        ps.setString(13, protocol);
        ps.setString(14, refferrerPolicy);
        ps.setString(15, rel);
        ps.setString(16, search);
        ps.setString(17, shape);
        ps.setString(18, tabindex);
        ps.setString(19, target);
        ps.setString(20, type);
        ps.setString(21, username);
        //ISNERTを実行する
        ps.executeUpdate();
        //次の子クラスへと紐づけるためのID取得
        rs = ps.getGeneratedKeys();
        while (rs.next()) {
            childID = rs.getInt(1);
        }
        return childID;
    }

    public int sqliteInsert(int logID, Connection connection) throws SQLException {
        int parentID = super.sqliteInsert(logID, connection);
        int childID = -1;
        String sql = "insert into HTMLAreaElement(ref, accessKey, alt, coords, download, hash, host, hostname, media, password, origin, pathname, port, protocol, refferrerPolicy, rel, search, shape, tabindex, target, type, username)" +
                "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, parentID);
            ps.setString(2, accessKey);
            ps.setString(3, alt);
            ps.setString(4, coords);
            ps.setString(5, download);
            ps.setString(6, hash);
            ps.setString(7, host);
            ps.setString(8, media);
            ps.setString(9, password);
            ps.setString(10, origin);
            ps.setString(11, pathname);
            ps.setString(12, port);
            ps.setString(13, protocol);
            ps.setString(14, refferrerPolicy);
            ps.setString(15, rel);
            ps.setString(16, search);
            ps.setString(17, shape);
            ps.setString(18, tabindex);
            ps.setString(19, target);
            ps.setString(20, type);
            ps.setString(21, username);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            while (rs.next()) {
                childID = rs.getInt(1);
            }
        }
        return childID;
    }
}
