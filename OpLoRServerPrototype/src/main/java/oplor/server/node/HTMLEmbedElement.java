package oplor.server.node;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HTMLEmbedElement extends HTMLElement {
    public String height;
    public String src;
    public String type;
    public String width;

    public void update() {
        super.update();
        getter.put("height", height);
        getter.put("src", src);
        getter.put("type", type);
        getter.put("width", width);
    }

    public int Insert(int logID, Connection conn, PreparedStatement ps, ResultSet rs) throws SQLException {
        int parentID = super.Insert(logID, conn, ps, rs);
        //INSERT
        String sql = "INSERT INTO htmlembedelement(ref, height, src, type, width)VALUES(?, ?, ?, ?, ?)";
        ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setInt(1, parentID);
        ps.setString(2, height);
        ps.setString(3, src);
        ps.setString(4, type);
        ps.setString(5, width);
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
        String sql = "insert into HTMLEmbedElement(ref, height, src, type, width)values(?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, parentID);
            ps.setString(2, height);
            ps.setString(3, src);
            ps.setString(4, type);
            ps.setString(5, width);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            while (rs.next()) {
                childID = rs.getInt(1);
            }
        }
        return childID;
    }
}
