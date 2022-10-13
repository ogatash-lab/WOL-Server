package oplor.server.event;

import java.sql.*;

public class KeyboardEvent extends UIEvent {
    public boolean altKey;
    public String code;
    public boolean ctrlKey;
    public boolean isComposing;
    public String key;
    public String locate;
    public int location;
    public boolean metaKey;
    public boolean repeat;
    public boolean shiftKey;

    public void update() {
        super.update();
        getter.put("altKey", altKey);
        getter.put("code", code);
        getter.put("ctrlKey", ctrlKey);
        getter.put("isComposing", isComposing);
        getter.put("key", key);
        getter.put("locate", locate);
        getter.put("location", location);
        getter.put("metaKey", metaKey);
        getter.put("repeat", repeat);
        getter.put("shiftKey", shiftKey);
    }

    public int Insert(int logID, Connection conn, PreparedStatement ps, ResultSet rs) throws SQLException {
        int parentID = super.Insert(logID, conn, ps, rs);
        //INSERT
        String sql = "INSERT INTO keyboardevent(ref, altKey, code, ctrlKey, isComposing, key2, locate, location, metaKey, repeat2, shiftKey)VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setInt(1, parentID);
        ps.setBoolean(2, altKey);
        ps.setString(3, code);
        ps.setBoolean(4, ctrlKey);
        ps.setBoolean(5, isComposing);
        ps.setString(6, key);
        ps.setString(7, locate);
        ps.setDouble(8, location);
        ps.setBoolean(9, metaKey);
        ps.setBoolean(10, repeat);
        ps.setBoolean(11, shiftKey);
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
        String sql = "insert into KeyboardEvent(ref, altKey, code, ctrlKey, isComposing, key2, locate, location, metaKey, repeat2, shiftKey)" +
                "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, parentID);
            ps.setBoolean(2, altKey);
            ps.setString(3, code);
            ps.setBoolean(4, ctrlKey);
            ps.setBoolean(5, isComposing);
            ps.setString(6, key);
            ps.setString(7, locate);
            ps.setDouble(8, location);
            ps.setBoolean(9, metaKey);
            ps.setBoolean(10, repeat);
            ps.setBoolean(11, shiftKey);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            while (rs.next()) {
                childID = rs.getInt(1);
            }
        }
        return childID;
    }
}
