package oplor.server.event;

import java.sql.*;


public class WheelEvent extends MouseEvent {
    public double deltaX;
    public double deltaY;
    public double deltaZ;
    public long deltaMode;

    public void update() {
        super.update();
        getter.put("deltaX", deltaX);
        getter.put("deltaY", deltaY);
        getter.put("deltaZ", deltaZ);
        getter.put("deltaMode", deltaMode);
    }

    public int Insert(int logID, Connection conn, PreparedStatement ps, ResultSet rs) throws SQLException {
        int parentID = super.Insert(logID, conn, ps, rs);
        //INSERT
        String sql = "INSERT INTO wheelevent(ref, deltaX, deltaY, deltaZ, dataMode)"
                + "VALUES(?, ?, ?, ?, ?)";
        ps = conn.prepareStatement(sql);
        ps.setInt(1, parentID);
        ps.setDouble(2, deltaX);
        ps.setDouble(3, deltaY);
        ps.setDouble(4, deltaZ);
        ps.setLong(5, deltaMode);
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
        String sql = "insert into WheelEvent(ref, deltaX, deltaY, deltaZ, dataMode)"
                + "values(?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, parentID);
            ps.setDouble(2, deltaX);
            ps.setDouble(3, deltaY);
            ps.setDouble(4, deltaZ);
            ps.setLong(5, deltaMode);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            while (rs.next()) {
                childID = rs.getInt(1);
            }
        }
        return childID;
    }
}
