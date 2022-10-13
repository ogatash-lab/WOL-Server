package oplor.server.node;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HTMLMeterElement extends HTMLElement {
    public double high;
    public double low;
    public double max;
    public double min;
    public double optimum;
    //public NodeList labels;

    public void update() {
        super.update();
        getter.put("high", high);
        getter.put("low", low);
        getter.put("max", max);
        getter.put("min", min);
    }

    public int Insert(int logID, Connection conn, PreparedStatement ps, ResultSet rs) throws SQLException {
        int parentID = super.Insert(logID, conn, ps, rs);
        //INSERT
        String sql = "INSERT INTO htmlmeterelement(ref, high, low, max, min, optimum)VALUES(?, ?, ?, ?, ?, ?)";
        ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setInt(1, parentID);
        ps.setDouble(2, high);
        ps.setDouble(3, low);
        ps.setDouble(4, max);
        ps.setDouble(5, min);
        ps.setDouble(6, optimum);

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
        String sql = "insert into HTMLMeterElement(ref, high, low, max, min, optimum)values(?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, parentID);
            ps.setDouble(2, high);
            ps.setDouble(3, low);
            ps.setDouble(4, max);
            ps.setDouble(5, min);
            ps.setDouble(6, optimum);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            while (rs.next()) {
                childID = rs.getInt(1);
            }
        }
        return childID;
    }
}
