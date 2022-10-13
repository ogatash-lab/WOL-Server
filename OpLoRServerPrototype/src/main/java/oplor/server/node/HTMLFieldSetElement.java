package oplor.server.node;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HTMLFieldSetElement extends HTMLElement {
    public boolean disabled;
    public String element;
    //public HTMLCollection formSelector;
    public String name;
    public String type;
    public String validationMessage;
    //public ValidityState validity;
    public boolean willValidate;

    public void update() {
        super.update();
        getter.put("disabled", disabled);
        getter.put("element", element);
        getter.put("name", name);
        getter.put("type", type);
        getter.put("validationMessage", validationMessage);
        getter.put("willValidate", willValidate);
    }

    public int Insert(int logID, Connection conn, PreparedStatement ps, ResultSet rs) throws SQLException {
        int parentID = super.Insert(logID, conn, ps, rs);
        //INSERT
        String sql = "INSERT INTO htmlfieldelement(ref, disabled, element, name, type, validationMessage, willValidate)" +
                "VALUES(?, ?, ?, ?, ?, ?, ?)";
        ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setInt(1, parentID);
        ps.setBoolean(2, disabled);
        ps.setString(3, element);
        ps.setString(4, name);
        ps.setString(5, type);
        ps.setString(6, validationMessage);
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
        String sql = "insert into HTMLFieldElement(ref, disabled, element, name, type, validationMessage, willValidate)" +
                "values(?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, parentID);
            ps.setBoolean(2, disabled);
            ps.setString(3, element);
            ps.setString(4, name);
            ps.setString(5, type);
            ps.setString(6, validationMessage);
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
