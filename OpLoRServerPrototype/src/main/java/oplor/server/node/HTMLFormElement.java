package oplor.server.node;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HTMLFormElement extends HTMLElement {
    public long length;
    public String name;
    public String method;
    public String target;
    public String action;
    public String encoding;
    public String enctype;
    public String acceptCharset;
    public String autocomplete;
    public boolean noValidate;

    public void update() {
        super.update();
        getter.put("length", length);
        getter.put("name", name);
        getter.put("method", method);
        getter.put("target", target);
        getter.put("action", action);
        getter.put("encoding", encoding);
        getter.put("enctype", enctype);
        getter.put("acceptCharset", acceptCharset);
        getter.put("autocomplete", autocomplete);
        getter.put("noValidate", noValidate);
    }

    public int Insert(int logID, Connection conn, PreparedStatement ps, ResultSet rs) throws SQLException {
        int parentID = super.Insert(logID, conn, ps, rs);
        //INSERT
        String sql = "INSERT INTO htmlformelement(ref, length, name, method, target, action, encoding, enctype, acceptCharset, autocomplete, noValidate)" +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setInt(1, parentID);
        ps.setLong(2, length);
        ps.setString(3, name);
        ps.setString(4, method);
        ps.setString(5, target);
        ps.setString(6, action);
        ps.setString(7, encoding);
        ps.setString(8, enctype);
        ps.setString(9, acceptCharset);
        ps.setString(10, autocomplete);
        ps.setBoolean(11, noValidate);
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
        String sql = "insert into HTMLFormElement(ref, length, name, method, target, action, encoding, enctype, acceptCharset, autocomplete, noValidate)" +
                "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, parentID);
            ps.setLong(2, length);
            ps.setString(3, name);
            ps.setString(4, method);
            ps.setString(5, target);
            ps.setString(6, action);
            ps.setString(7, encoding);
            ps.setString(8, enctype);
            ps.setString(9, acceptCharset);
            ps.setString(10, autocomplete);
            ps.setBoolean(11, noValidate);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            while (rs.next()) {
                childID = rs.getInt(1);
            }
        }
        return childID;
    }
}
