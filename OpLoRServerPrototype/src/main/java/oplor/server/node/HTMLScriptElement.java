package oplor.server.node;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HTMLScriptElement extends HTMLElement {
    public String type;
    public String src;
    public String charset;
    public boolean async;
    public boolean defer;
    public String crossOrigin;
    public String text;
    public boolean noModule;

    public void update() {
        super.update();
        getter.put("type", type);
        getter.put("src", src);
        getter.put("charset", charset);
        getter.put("async", async);
        getter.put("defer", defer);
        getter.put("crossOrigin", crossOrigin);
        getter.put("text", text);
        getter.put("noModule", noModule);
    }

    public int Insert(int logID, Connection conn, PreparedStatement ps, ResultSet rs) throws SQLException {
        int parentID = super.Insert(logID, conn, ps, rs);
        //INSERT
        String sql = "INSERT INTO htmlscriptelement(ref, type, src, charset, async, defer, crossOrigin, text, noModule)" +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setInt(1, parentID);
        ps.setString(2, type);
        ps.setString(3, src);
        ps.setString(4, charset);
        ps.setBoolean(5, async);
        ps.setBoolean(6, defer);
        ps.setString(7, crossOrigin);
        ps.setString(8, text);
        ps.setBoolean(9, noModule);

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
        String sql = "insert into HTMLScriptElement(ref, type, src, charset, async, defer, crossOrigin, text, noModule)" +
                "values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, parentID);
            ps.setInt(1, parentID);
            ps.setString(2, type);
            ps.setString(3, src);
            ps.setString(4, charset);
            ps.setBoolean(5, async);
            ps.setBoolean(6, defer);
            ps.setString(7, crossOrigin);
            ps.setString(8, text);
            ps.setBoolean(9, noModule);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            while (rs.next()) {
                childID = rs.getInt(1);
            }
        }
        return childID;
    }
}
