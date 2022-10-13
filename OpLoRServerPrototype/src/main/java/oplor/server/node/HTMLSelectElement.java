package oplor.server.node;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HTMLSelectElement extends HTMLElement {
    public boolean autofocus;
    public boolean disabled;
    //public HTMLFormElement formSelector;
    //public NodeList labels;
    public long length;
    public boolean multiple;
    public String name;
    //public HTMLOptionCollection options;
    public boolean required;
    public long selectedIndex;
    //これをとれるようにする。
    //public HTMLCollection selectedOptions;
    public long size;
    public String type;
    public String validationMessage;
    //public ValidityState validity;
    public String value;
    public boolean willValidate;

    public void update() {
        super.update();
        getter.put("autofocus", autofocus);
        getter.put("disabled", disabled);
        getter.put("length", length);
        getter.put("multiple", multiple);
        getter.put("name", name);
        getter.put("required", required);
        getter.put("selectedIndex", selectedIndex);
        getter.put("size", size);
        getter.put("type", type);
        getter.put("validationMessage", validationMessage);
        getter.put("value", value);
        getter.put("willValidate", willValidate);
    }

    public int Insert(int logID, Connection conn, PreparedStatement ps, ResultSet rs) throws SQLException {
        int parentID = super.Insert(logID, conn, ps, rs);
        //INSERT
        String sql = "INSERT INTO htmlselectelement(ref, autofocus, disabled, length, multiple, name, required, selectedIndex, size, type, validationMessage, value, willValidate)" +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setInt(1, parentID);
        ps.setBoolean(2, autofocus);
        ps.setBoolean(3, disabled);
        ps.setLong(4, length);
        ps.setBoolean(5, multiple);
        ps.setString(6, name);
        ps.setBoolean(7, required);
        ps.setLong(8, selectedIndex);
        ps.setLong(9, size);
        ps.setString(10, type);
        ps.setString(11, validationMessage);
        ps.setString(12, value);
        ps.setBoolean(13, willValidate);

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
        String sql = "insert into HTMLSelectElement(ref, autofocus, disabled, length, multiple, name, required, selectedIndex, size, type, validationMessage, value, willValidate)" +
                "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, parentID);
            ps.setBoolean(2, autofocus);
            ps.setBoolean(3, disabled);
            ps.setLong(4, length);
            ps.setBoolean(5, multiple);
            ps.setString(6, name);
            ps.setBoolean(7, required);
            ps.setLong(8, selectedIndex);
            ps.setLong(9, size);
            ps.setString(10, type);
            ps.setString(11, validationMessage);
            ps.setString(12, value);
            ps.setBoolean(13, willValidate);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            while (rs.next()) {
                childID = rs.getInt(1);
            }
        }
        return childID;
    }
}
