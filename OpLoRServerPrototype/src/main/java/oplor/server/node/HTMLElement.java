package oplor.server.node;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//<html>要素に関するクラス
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

    //HTMLElementの更新
    public void update() {
        //親クラスのアップデート
        super.update();
        //追加情報をgetterマップに追加
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

    //データベースにHTMLElementを挿入
    public int Insert(int logID, Connection conn, PreparedStatement ps, ResultSet rs) throws SQLException {
        //親イベントのID
        int parentID = super.Insert(logID, conn, ps, rs);

        //htmlelementテーブルにデータを挿入するSQL文
        String sql = "INSERT INTO htmlelement(ref, accessKey, accessKeyLabel, contentEditable, isContentEditable, draggable, hidden, itemScope, itemId, lang, " +
                "offsetHeight, offsetLeft, offsetTop, offsetWidth, spellcheck, style, tabIndex, title, translate)" +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?," +
                " ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        //準備されたステートメントを作成
        ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

        //パラメータを設定
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

        //INSERT文を実行
        ps.executeUpdate();

        //挿入されたレコードのキーを取得
        int childID = -1;
        rs = ps.getGeneratedKeys();
        while (rs.next()) {
            childID = rs.getInt(1); //挿入したレコードIDを取得
        }

        //取得したレコードIDを返す
        return childID;
    }

    //SQLiteデータベースにHTMLElement情報を挿入
    public int sqliteInsert(int logID, Connection connection) throws SQLException {
        //親ノードのIDを取得
        int parentID = super.sqliteInsert(logID, connection);
        int childID = -1;

        //HTMLElementテーブルにデータを挿入するSQL文
        String sql = "insert into HTMLElement(ref, accessKey, accessKeyLabel, contentEditable, isContentEditable, draggable, hidden, itemScope, itemId, lang, " +
                "offsetHeight, offsetLeft, offsetTop, offsetWidth, spellcheck, style, tabIndex, title, translate)" +
                "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?," +
                " ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        ;
        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            //パラメータの設定
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

            //SQL文の実行
            ps.executeUpdate();
            //挿入したレコードのキーを取得
            ResultSet rs = ps.getGeneratedKeys();
            while (rs.next()) {
                childID = rs.getInt(1); //挿入したレコードのIDを取得
            }
        }
        //挿入されたレコードIDを返す
        return childID;
    }
}
