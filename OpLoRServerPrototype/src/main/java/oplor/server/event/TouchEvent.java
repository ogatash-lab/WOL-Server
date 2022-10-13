package oplor.server.event;

import java.sql.*;

public class TouchEvent extends UIEvent {
    public boolean altKey;
    //public TouchList changedTouches;
    public boolean ctrlKey;
    public boolean metaKey;
    public boolean shiftKey;

    public void update() {
        super.update();
        getter.put("altKey", altKey);
        getter.put("ctrlKey", ctrlKey);
        getter.put("metaKey", metaKey);
        getter.put("shiftKey", shiftKey);
    }

    public int Insert(int logID, Connection conn, PreparedStatement ps, ResultSet rs) throws SQLException {
        int parentID = super.Insert(logID, conn, ps, rs);
        //INSERT
        String sql = "INSERT INTO touchevent(ref, altKey, ctrlKey, metaKey, shiftKey)VALUES(?, ?, ?, ?, ?, ?)";
        ps = conn.prepareStatement(sql);
        ps.setInt(1, parentID);
        ps.setBoolean(2, altKey);
        ps.setBoolean(3, ctrlKey);
        ps.setBoolean(4, metaKey);
        ps.setBoolean(5, shiftKey);
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
        String sql = "insert into TouchEvent(ref, altKey, ctrlKey, metaKey, shiftKey)values(?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, parentID);
            ps.setBoolean(2, altKey);
            ps.setBoolean(3, ctrlKey);
            ps.setBoolean(4, metaKey);
            ps.setBoolean(5, shiftKey);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            while (rs.next()) {
                childID = rs.getInt(1);
            }
        }
        return childID;
    }
}
