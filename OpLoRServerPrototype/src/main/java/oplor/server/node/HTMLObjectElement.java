package oplor.server.node;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HTMLObjectElement extends HTMLElement {
    public String data;
    public HTMLFormElement formSelector;
    public String height;
    public String name;
    public long tabindex;
    public boolean typeMustMatch;
    public String useMap;
    public String validationMessage;
    //public ValidityState validity;
    public String width;
    public boolean willValidate;

    public void update() {
        super.update();
        getter.put("data", data);
        getter.put("height", height);
        getter.put("name", name);
        getter.put("tabindex", tabindex);
        getter.put("typeMustMatch", typeMustMatch);
        getter.put("useMap", useMap);
        getter.put("validationMessage", validationMessage);
        getter.put("width", width);
        getter.put("willValidate", willValidate);
    }

    public int Insert(int logID, Connection conn, PreparedStatement ps, ResultSet rs) throws SQLException {
        int parentID = super.Insert(logID, conn, ps, rs);
        //INSERT
        String sql = "INSERT INTO htmlobjectelement(ref, data, height, name, tabindex, typeMustMatch, useMap, validationMessage, width, willValidate)" +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setInt(1, parentID);
        ps.setString(2, data);
        ps.setString(3, height);
        ps.setString(4, name);
        ps.setLong(5, tabindex);
        ps.setBoolean(6, typeMustMatch);
        ps.setString(7, useMap);
        ps.setString(8, validationMessage);
        ps.setString(9, width);
        ps.setBoolean(10, willValidate);

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
        String sql = "insert into HTMLObjectElement(ref, data, height, name, tabindex, typeMustMatch, useMap, validationMessage, width, willValidate)" +
                "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, parentID);
            ps.setString(2, data);
            ps.setString(3, height);
            ps.setString(4, name);
            ps.setLong(5, tabindex);
            ps.setBoolean(6, typeMustMatch);
            ps.setString(7, useMap);
            ps.setString(8, validationMessage);
            ps.setString(9, width);
            ps.setBoolean(10, willValidate);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            while (rs.next()) {
                childID = rs.getInt(1);
            }
        }
        return childID;
    }
}
