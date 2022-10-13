package oplor.server.node;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HTMLFrameElement extends HTMLElement {
    public String allow;
    public boolean allowfullscreen;
    public boolean allowPaymentRequest;
    public String height;
    public String name;
    public String referrerPolicy;
    //public DOMSettableTokenList sandbox;
    public String src;
    public String srcdoc;
    public String width;

    public void update() {
        super.update();
        getter.put("allow", allow);
        getter.put("allowfullscreen", allowfullscreen);
        getter.put("allowPaymentRequest", allowPaymentRequest);
        getter.put("height", height);
        getter.put("name", name);
        getter.put("referrerPolicy", referrerPolicy);
        getter.put("src", src);
        getter.put("srcdoc", srcdoc);
        getter.put("width", width);
    }

    public int Insert(int logID, Connection conn, PreparedStatement ps, ResultSet rs) throws SQLException {
        int parentID = super.Insert(logID, conn, ps, rs);
        //INSERT
        String sql = "INSERT INTO htmlframeelement(ref, allow, allowfullscreen, allowPaymentRequest, height, name, referrerPolicy, src, srcdoc, width)" +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setInt(1, parentID);
        ps.setString(2, allow);
        ps.setBoolean(3, allowfullscreen);
        ps.setBoolean(4, allowPaymentRequest);
        ps.setString(5, height);
        ps.setString(6, name);
        ps.setString(7, referrerPolicy);
        ps.setString(8, src);
        ps.setString(9, srcdoc);
        ps.setString(10, width);
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
        String sql = "insert into HTMLFrameElement(ref, allow, allowfullscreen, allowPaymentRequest, height, name, referrerPolicy, src, srcdoc, width)" +
                "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, parentID);
            ps.setString(2, allow);
            ps.setBoolean(3, allowfullscreen);
            ps.setBoolean(4, allowPaymentRequest);
            ps.setString(5, height);
            ps.setString(6, name);
            ps.setString(7, referrerPolicy);
            ps.setString(8, src);
            ps.setString(9, srcdoc);
            ps.setString(10, width);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            while (rs.next()) {
                childID = rs.getInt(1);
            }
        }
        return childID;
    }
}
