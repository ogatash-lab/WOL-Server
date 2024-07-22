package oplor.server.node;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//<textarea>要素に関するクラス
public class HTMLTextAreaElement extends HTMLElement {
    //public parent_pointer formSelector;
    public String type;
    public String value;
    public long textLength;
    public String defaultValue;
    public String placeholder;
    public long rows;
    public long cols;
    public boolean autofocus;
    public String name;
    public boolean disabled;
    //public NodeList labels;
    public long maxLength;
    //public String accessKey;
    public boolean readOnly;
    public boolean required;
    //public long tabIndex;delete5/30
    public long selectionStart;
    public long selectionEnd;
    public String selectionDirection;
    //public ValidityState validity;
    public boolean willValidate;
    public String validationMessage;
    public String autocomplete;
    public String autocapitalize;
    public String inputMode;
    public String wrap;

    //HTMLTextAreaElementの更新
    public void update() {
        //親クラスのアップデート
        super.update();
        //追加情報をgetterマップに追加
        getter.put("type", type);
        getter.put("value", value);
        getter.put("textLength", textLength);
        getter.put("defaultValue", defaultValue);
        getter.put("placeholder", placeholder);
        getter.put("rows", rows);
        getter.put("cols", cols);
        getter.put("autofocus", autofocus);
        getter.put("name", name);
        getter.put("disabled", disabled);
        getter.put("maxLength", maxLength);
        //getter.put("accessKey", accessKey);
        getter.put("readOnly", readOnly);
        getter.put("required", required);
        //getter.put("tabIndex", tabIndex);
        getter.put("selectionStart", selectionStart);
        getter.put("selectionEnd", selectionEnd);
        getter.put("selectionDirection", selectionDirection);
        getter.put("willValidate", willValidate);
        getter.put("validationMessage", validationMessage);
        getter.put("autocomplete", autocomplete);
        getter.put("autocapitalize", autocapitalize);
        getter.put("inputMode", inputMode);
        getter.put("wrap", wrap);
    }

    //データベースにHTMLTextAreaElementを挿入
    public int Insert(int logID, Connection conn, PreparedStatement ps, ResultSet rs) throws SQLException {
        //親イベントのIDを取得
        int parentID = super.Insert(logID, conn, ps, rs);
        //htmltextareaelementテーブルにデータを挿入するSQL文
        String sql = "INSERT INTO htmltextareaelement(ref, type, value, textLength, defaultValue, placeholder, rows, cols, autofocus, name, disabled, maxLength, accessKey, readOnly, required, tabIndex, selectionStart, selectionEnd, selectionDirection, willValidate, validationMessage, autocomplete, autocapitalize, inputMode, wrap)"
                + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        //準備されたステートメントを作成
        ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        //パラメータを設定
        ps.setInt(1, parentID);
        ps.setString(2, type);
        ps.setString(3, value);
        ps.setLong(4, textLength);
        ps.setString(5, defaultValue);
        ps.setString(6, placeholder);
        ps.setLong(7, rows);
        ps.setLong(8, cols);
        ps.setBoolean(9, autofocus);
        ps.setString(10, name);
        ps.setBoolean(11, disabled);
        ps.setLong(12, maxLength);
        ps.setString(13, accessKey);
        ps.setBoolean(14, readOnly);
        ps.setBoolean(15, required);
        ps.setLong(16, tabIndex);
        ps.setLong(17, selectionStart);
        ps.setLong(18, selectionEnd);
        ps.setString(19, selectionDirection);
        ps.setBoolean(20, willValidate);
        ps.setString(21, validationMessage);
        ps.setString(22, autocomplete);
        ps.setString(23, autocapitalize);
        ps.setString(24, inputMode);
        ps.setString(25, wrap);

        //INSERT文を実行
        ps.executeUpdate();
        //挿入したレコードのキーを取得
        int childID = -1;
        rs = ps.getGeneratedKeys();
        while (rs.next()) {
            childID = rs.getInt(1); //挿入したレコードIDを取得
        }
        //取得したレコードIDを返す
        return childID;
    }

    //SQLiteデータベースにHTMLTextAreaElement情報を挿入
    public int sqliteInsert(int logID, Connection connection) throws SQLException {
        //親ノードのIDを取得
        int parentID = super.sqliteInsert(logID, connection);
        int childID = -1;
        //HTMLTextAreaElementテーブルにデータを挿入するSQL文
        String sql = "insert into HTMLTextAreaElement(ref, type, value, textLength, defaultValue, placeholder, rows, cols, autofocus, name, disabled, maxLength, accessKey, readOnly, required, tabIndex, selectionStart, selectionEnd, selectionDirection, willValidate, validationMessage, autocomplete, autocapitalize, inputMode, wrap)"
                + "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            //パラメータの設定
            ps.setInt(1, parentID);
            ps.setString(2, type);
            ps.setString(3, value);
            ps.setLong(4, textLength);
            ps.setString(5, defaultValue);
            ps.setString(6, placeholder);
            ps.setLong(7, rows);
            ps.setLong(8, cols);
            ps.setBoolean(9, autofocus);
            ps.setString(10, name);
            ps.setBoolean(11, disabled);
            ps.setLong(12, maxLength);
            ps.setString(13, accessKey);
            ps.setBoolean(14, readOnly);
            ps.setBoolean(15, required);
            ps.setLong(16, tabIndex);
            ps.setLong(17, selectionStart);
            ps.setLong(18, selectionEnd);
            ps.setString(19, selectionDirection);
            ps.setBoolean(20, willValidate);
            ps.setString(21, validationMessage);
            ps.setString(22, autocomplete);
            ps.setString(23, autocapitalize);
            ps.setString(24, inputMode);
            ps.setString(25, wrap);
            //SQL文の実行
            ps.executeUpdate();
            //挿入したレコードのキーを取得
            ResultSet rs = ps.getGeneratedKeys();
            while (rs.next()) {
                childID = rs.getInt(1); //挿入したレコードのIDを取得
            }
        }
        //挿入したレコードIDを返す
        return childID;
    }
}
