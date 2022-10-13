package oplor.server.event;

import java.sql.*;

public class InputEvent extends UIEvent {
    public String data;
    public String inputType;
    public boolean isComposing;

    public void update() {
        super.update();
        getter.put("data", data);
        getter.put("inputType", inputType);
        getter.put("isComposing", isComposing);
    }

    public int Insert(int logID, Connection conn, PreparedStatement ps, ResultSet rs) throws SQLException {
        int parentID = super.Insert(logID, conn, ps, rs);
        //INSERT
        String sql = "INSERT INTO inputevent(ref, data, inputType, isComposing)VALUES(?, ?, ?, ?)";
        ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, parentID);
        ps.setString(2, data);
        ps.setString(3, inputType);
        ps.setBoolean(4, isComposing);
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
        String sql = "insert into InputEvent(ref, data, inputType, isComposing)values(?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, parentID);
            ps.setString(2, data);
            ps.setString(3, inputType);
            ps.setBoolean(4, isComposing);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            while (rs.next()) {
                childID = rs.getInt(1);
            }
        }
        return childID;
    }
}
