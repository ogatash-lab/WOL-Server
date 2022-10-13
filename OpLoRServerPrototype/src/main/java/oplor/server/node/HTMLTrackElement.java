package oplor.server.node;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HTMLTrackElement extends HTMLElement {
    public String kind;
    public String src;
    public String srclang;
    public String label;
    public boolean m_default;
    public String readyState;
    //public TextTrack text;

    public void update() {
        super.update();
        getter.put("kind", kind);
        getter.put("src", src);
        getter.put("srclang", srclang);
        getter.put("label", label);
        getter.put("m_default", m_default);
        getter.put("readyState", readyState);
    }

    public int Insert(int logID, Connection conn, PreparedStatement ps, ResultSet rs) throws SQLException {
        int parentID = super.Insert(logID, conn, ps, rs);
        //INSERT
        String sql = "INSERT INTO htmltrackelement(ref, kind, src, srclang, label, m_default, readyState)"
                + "VALUES(?, ?, ?, ?, ?, ?, ?)";
        ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setInt(1, parentID);
        ps.setString(2, kind);
        ps.setString(3, src);
        ps.setString(4, srclang);
        ps.setString(5, label);
        ps.setBoolean(6, m_default);
        ps.setString(7, readyState);

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
        String sql = "insert into HTMLTrackElement(ref, kind, src, srclang, label, m_default, readyState)"
                + "values(?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, parentID);
            ps.setString(2, kind);
            ps.setString(3, src);
            ps.setString(4, srclang);
            ps.setString(5, label);
            ps.setBoolean(6, m_default);
            ps.setString(7, readyState);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            while (rs.next()) {
                childID = rs.getInt(1);
            }
        }
        return childID;
    }
}
