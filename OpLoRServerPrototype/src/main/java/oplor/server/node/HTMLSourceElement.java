package oplor.server.node;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HTMLSourceElement extends HTMLElement {
    public String keySystem;
    public String media;
    public String sizes;
    public String src;
    public String srcset;
    public String type;

    public void update() {
        super.update();
        getter.put("keySystem", keySystem);
        getter.put("media", media);
        getter.put("sizes", sizes);
        getter.put("src", src);
        getter.put("srcset", srcset);
        getter.put("type", type);
    }

    public int Insert(int logID, Connection conn, PreparedStatement ps, ResultSet rs) throws SQLException {
        int parentID = super.Insert(logID, conn, ps, rs);
        //INSERT
        String sql = "INSERT INTO htmlsourceelement(ref, keySystem, media, sizes, src, srcset, type)" +
                "VALUES(?, ?, ?, ?, ?, ?, ?)";
        ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setInt(1, parentID);
        ps.setString(2, keySystem);
        ps.setString(3, media);
        ps.setString(4, sizes);
        ps.setString(5, src);
        ps.setString(6, srcset);
        ps.setString(7, type);

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
        String sql = "insert into HTMLSourceElement(ref, keySystem, media, sizes, src, srcset, type)" +
                "values(?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, parentID);
            ps.setString(2, keySystem);
            ps.setString(3, media);
            ps.setString(4, sizes);
            ps.setString(5, src);
            ps.setString(6, srcset);
            ps.setString(7, type);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            while (rs.next()) {
                childID = rs.getInt(1);
            }
        }
        return childID;
    }
}
