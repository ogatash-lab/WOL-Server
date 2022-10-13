package oplor.server.node;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HTMLOutputElement extends HTMLElement {
    public String defaultValue;
    public HTMLFormElement formSelector;
    //public DOMTokenList htmlFor;
    //public NodeList labels;
    public String name;
    public String type;
    public String validationMessage;
    //public ValidityState validity;
    public String value;
    public boolean willValidate;

    public void update() {
        super.update();
        getter.put("defaultValue", defaultValue);
        getter.put("name", name);
        getter.put("type", type);
        getter.put("validationMessage", validationMessage);
        getter.put("value", value);
        getter.put("willValidate", willValidate);
    }

    public int Insert(int logID, Connection conn, PreparedStatement ps, ResultSet rs) throws SQLException {
        int parentID = super.Insert(logID, conn, ps, rs);
        //INSER
        String sql = "INSERT INTO htmloutputelement(ref, defaultValue, name, type, validationMessage, value, willValidate)" +
                "VALUES(?, ?, ?, ?, ?, ?, ?)";
        ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setInt(1, parentID);
        ps.setString(2, defaultValue);
        ps.setString(3, name);
        ps.setString(4, type);
        ps.setString(5, validationMessage);
        ps.setString(6, value);
        ps.setBoolean(7, willValidate);

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
        String sql = "insert into HTMLOutputElement(ref, defaultValue, name, type, validationMessage, value, willValidate)" +
                "values(?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, parentID);
            ps.setString(2, defaultValue);
            ps.setString(3, name);
            ps.setString(4, type);
            ps.setString(5, validationMessage);
            ps.setString(6, value);
            ps.setBoolean(7, willValidate);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            while (rs.next()) {
                childID = rs.getInt(1);
            }
        }
        return childID;
    }
}
