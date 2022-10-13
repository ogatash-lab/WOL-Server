package oplor.server.node;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HTMLElement extends Element {
    public String accessKey;
    public String accessKeyLabel;
    public String contentEditable;
    public boolean isContentEditable;
    //public DOMStringMap dataset;
    public boolean draggable;
    //public DOMSettableTokenList dropzone;
    public boolean hidden;
    public boolean itemScope;
    //public DOMSettableTokenList itemType;
    public String itemId;
    //public DOMSettableTokenList itemRef;
    //public DOMSettableTokenList itemProp;
    public Object itemvalue;
    public String lang;
    public double offsetHeight;
    public double offsetLeft;
    public Element offsetParent;
    public double offsetTop;
    public double offsetWidth;
    //public HTMLPropertiesCollction properties;
    public boolean spellcheck;
    public String style;
    public long tabIndex;
    public String title;
    public boolean translate;

    public void update() {
        super.update();
        getter.put("accessKey", accessKey);
        getter.put("accessKeyLabel", accessKeyLabel);
        getter.put("contentEditable", contentEditable);
        getter.put("isContentEditable", isContentEditable);
        getter.put("draggable", draggable);
        getter.put("hidden", hidden);
        getter.put("itemScope", itemScope);
        getter.put("itemId", itemId);
        getter.put("lang", lang);
        getter.put("offsetHeight", offsetHeight);
        getter.put("offsetLeft", offsetLeft);
        getter.put("offsetTop", offsetTop);
        getter.put("offsetWidth", offsetWidth);
        getter.put("spellcheck", spellcheck);
        getter.put("style", style);
        getter.put("tabIndex", tabIndex);
        getter.put("title", title);
        getter.put("translate", translate);
    }

    public int Insert(int logID, Connection conn, PreparedStatement ps, ResultSet rs) throws SQLException {
        int parentID = super.Insert(logID, conn, ps, rs);
        //INSERT
        String sql = "INSERT INTO htmlelement(ref, accessKey, accessKeyLabel, contentEditable, isContentEditable, draggable, hidden, itemScope, itemId, lang, " +
                "offsetHeight, offsetLeft, offsetTop, offsetWidth, spellcheck, style, tabIndex, title, translate)" +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?," +
                " ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setInt(1, parentID);
        ps.setString(2, accessKey);
        ps.setString(3, accessKeyLabel);
        ps.setString(4, contentEditable);
        ps.setBoolean(5, isContentEditable);
        ps.setBoolean(6, draggable);
        ps.setBoolean(7, hidden);
        ps.setBoolean(8, itemScope);
        ps.setString(9, itemId);
        ps.setString(10, lang);
        ps.setDouble(11, offsetHeight);
        ps.setDouble(12, offsetLeft);
        ps.setDouble(13, offsetTop);
        ps.setDouble(14, offsetWidth);
        ps.setBoolean(15, spellcheck);
        ps.setString(16, style);
        ps.setLong(17, tabIndex);
        ps.setString(18, title);
        ps.setBoolean(19, translate);
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
        String sql = "insert into HTMLElement(ref, accessKey, accessKeyLabel, contentEditable, isContentEditable, draggable, hidden, itemScope, itemId, lang, " +
                "offsetHeight, offsetLeft, offsetTop, offsetWidth, spellcheck, style, tabIndex, title, translate)" +
                "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?," +
                " ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        ;
        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, parentID);
            ps.setString(2, accessKey);
            ps.setString(3, accessKeyLabel);
            ps.setString(4, contentEditable);
            ps.setBoolean(5, isContentEditable);
            ps.setBoolean(6, draggable);
            ps.setBoolean(7, hidden);
            ps.setBoolean(8, itemScope);
            ps.setString(9, itemId);
            ps.setString(10, lang);
            ps.setDouble(11, offsetHeight);
            ps.setDouble(12, offsetLeft);
            ps.setDouble(13, offsetTop);
            ps.setDouble(14, offsetWidth);
            ps.setBoolean(15, spellcheck);
            ps.setString(16, style);
            ps.setLong(17, tabIndex);
            ps.setString(18, title);
            ps.setBoolean(19, translate);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            while (rs.next()) {
                childID = rs.getInt(1);
            }
        }
        return childID;
    }
}
