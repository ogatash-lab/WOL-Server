package oplor.server.node;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HTMLTableCellElement extends HTMLElement {
    public String addr;
    public long cellIndex;
    public long colSpan;
    public long rowSpan;
    public String scope;

    public void update() {
        super.update();
        getter.put("addr", addr);
        getter.put("cellIndex", cellIndex);
        getter.put("colSpan", colSpan);
        getter.put("rowSpan", rowSpan);
        getter.put("scope", scope);
    }

    public int Insert(int logID, Connection conn, PreparedStatement ps, ResultSet rs) throws SQLException {
        int parentID = super.Insert(logID, conn, ps, rs);
        //INSERT
        String sql = "INSERT INTO htmltablecellelement(ref, addr, cellIndex, colSpan, rowSpan, scope)" +
                "VALUES(?, ?, ?, ?, ?, ?)";
        ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setInt(1, parentID);
        ps.setString(2, addr);
        ps.setLong(3, cellIndex);
        ps.setLong(4, colSpan);
        ps.setLong(5, rowSpan);
        ps.setString(6, scope);

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
        String sql = "insert into HTMLTableCellElement(ref, addr, cellIndex, colSpan, rowSpan, scope)" +
                "values(?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, parentID);
            ps.setString(2, addr);
            ps.setLong(3, cellIndex);
            ps.setLong(4, colSpan);
            ps.setLong(5, rowSpan);
            ps.setString(6, scope);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            while (rs.next()) {
                childID = rs.getInt(1);
            }
        }
        return childID;
    }
}
