package oplor.server.node;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HTMLOptionElement extends HTMLElement {
    public boolean defaultSelected;
    public boolean disabled;
    public HTMLFormElement formSelector;
    public long index;
    public String label;
    public boolean selected;
    public String text;
    public String value;

    public void update() {
        super.update();
        getter.put("defaultSelected", defaultSelected);
        getter.put("disabled", disabled);
        getter.put("index", index);
        getter.put("label", label);
        getter.put("selected", selected);
        getter.put("text", text);
        getter.put("value", value);
    }

    public int Insert(int logID, Connection conn, PreparedStatement ps, ResultSet rs) throws SQLException {
        int parentID = super.Insert(logID, conn, ps, rs);
        //INSERT
        String sql = "INSERT INTO htmloptionelement(ref, defaultSelected, disabled, index, label, selected, text, value)" +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setInt(1, parentID);
        ps.setBoolean(2, defaultSelected);
        ps.setBoolean(3, disabled);
        ps.setLong(4, index);
        ps.setString(5, label);
        ps.setBoolean(6, selected);
        ps.setString(7, text);
        ps.setString(8, value);

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
        String sql = "insert into HTMLOptionElement(ref, defaultSelected, disabled, index, label, selected, text, value)" +
                "value(?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, parentID);
            ps.setBoolean(2, defaultSelected);
            ps.setBoolean(3, disabled);
            ps.setLong(4, index);
            ps.setString(5, label);
            ps.setBoolean(6, selected);
            ps.setString(7, text);
            ps.setString(8, value);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            while (rs.next()) {
                childID = rs.getInt(1);
            }
        }
        return childID;
    }
}
