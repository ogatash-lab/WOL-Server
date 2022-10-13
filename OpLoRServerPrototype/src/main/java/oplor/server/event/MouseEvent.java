package oplor.server.event;

import java.sql.*;

public class MouseEvent extends UIEvent {
    public boolean altKey;
    public int button;
    public int buttons;
    public int clientX;
    public int clientY;
    public boolean ctrlKey;
    public boolean metaKey;
    public int movementX;
    public int movementY;
    public int offsetX;
    public int offsetY;
    public int pageX;
    public int pageY;
    //public String region;
    public int screenX;
    public int screenY;
    public boolean shiftKey;
    public int x;
    public int y;

    public void update() {
        super.update();
        getter.put("altKey", altKey);
        getter.put("button", button);
        getter.put("buttons", buttons);
        getter.put("clientX", clientX);
        getter.put("clientY", clientY);
        getter.put("ctrlKey", ctrlKey);
        getter.put("metaKey", metaKey);
        getter.put("movementX", movementX);
        getter.put("movementY", movementY);
        getter.put("offsetX", offsetX);
        getter.put("offsetY", offsetY);
        getter.put("pageX", pageX);
        getter.put("pageY", pageY);
        //getter.put("region", region);
        getter.put("screenX", screenX);
        getter.put("screenY", screenY);
        getter.put("shiftKey", shiftKey);
        getter.put("x", x);
        getter.put("y", y);
    }

    public int Insert(int logID, Connection conn, PreparedStatement ps, ResultSet rs) throws SQLException {
        int parentID = super.Insert(logID, conn, ps, rs);
        //INSERT
        String sql = "INSERT INTO mouseevent(ref, altKey, button, buttons, client_X, client_Y, ctrlKey, metaKey, movementX, movementY, offsetX, offsetY, pageX, pageY, screenX, screenY, shiftKey, x, y)" +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setInt(1, parentID);
        ps.setBoolean(2, altKey);
        ps.setInt(3, button);
        ps.setInt(4, buttons);
        ps.setInt(5, clientX);
        ps.setInt(6, clientY);
        ps.setBoolean(7, ctrlKey);
        ps.setBoolean(8, metaKey);
        ps.setInt(9, movementX);
        ps.setInt(10, movementY);
        ps.setInt(11, offsetX);
        ps.setInt(12, offsetY);
        ps.setInt(13, pageX);
        ps.setInt(14, pageY);
        ps.setInt(15, screenX);
        ps.setInt(16, screenY);
        ps.setBoolean(17, shiftKey);
        ps.setInt(18, x);
        ps.setInt(19, y);
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
        String sql = "insert into MouseEvent(ref, altKey, button, buttons, client_X, client_Y, ctrlKey, metaKey, movementX, movementY, offsetX, offsetY, pageX, pageY, screenX, screenY, shiftKey, x, y)" +
                "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, parentID);
            ps.setBoolean(2, altKey);
            ps.setInt(3, button);
            ps.setInt(4, buttons);
            ps.setInt(5, clientX);
            ps.setInt(6, clientY);
            ps.setBoolean(7, ctrlKey);
            ps.setBoolean(8, metaKey);
            ps.setInt(9, movementX);
            ps.setInt(10, movementY);
            ps.setInt(11, offsetX);
            ps.setInt(12, offsetY);
            ps.setInt(13, pageX);
            ps.setInt(14, pageY);
            ps.setInt(15, screenX);
            ps.setInt(16, screenY);
            ps.setBoolean(17, shiftKey);
            ps.setInt(18, x);
            ps.setInt(19, y);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            while (rs.next()) {
                childID = rs.getInt(1);
            }
        }
        return childID;
    }
}
