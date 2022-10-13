package oplor.server.event;

import java.sql.*;

public class CompositionEvent extends UIEvent {
    public String data;
    public String locate;

    CompositionEvent() {
        super();
        data = "?";
        locate = "?";
    }

    public void update() {
        super.update();
        getter.put("data", data);
        getter.put("locate", locate);
    }

    public int Insert(int logID, Connection conn, PreparedStatement ps, ResultSet rs) throws SQLException {
        int parentID = super.Insert(logID, conn, ps, rs);
        //INSERT
        String sql = "INSERT INTO compositionevent(ref, data, locate)VALUES(?, ?, ?, ?)";
        ps = conn.prepareStatement(sql);
        ps.setInt(1, parentID);
        ps.setString(2, data);
        ps.setString(3, locate);
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
        String sql = "insert into CompositionEvent(ref, data, locate) values(?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, parentID);
            ps.setString(2, data);
            ps.setString(3, locate);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            while (rs.next()) {
                childID = rs.getInt(1);
            }
        }
        return childID;
    }
}
