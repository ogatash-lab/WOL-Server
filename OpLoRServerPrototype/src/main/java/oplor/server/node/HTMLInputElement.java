package oplor.server.node;

import oplor.server.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HTMLInputElement extends HTMLElement {
    //public HTMLFormElement formSelector;
    public String formAction;
    public String formEncType;
    public String formMethod;
    public String formNoValidate;
    public String formTarget;
    public String name;
    public String type;
    public boolean disabled;
    public boolean autofocus;
    public boolean required;
    public String value;
    public ValidityState validity;
    public String validationMessage;
    public boolean willValidate;
    public boolean checked;
    public boolean defaultChecked;
    public boolean indeterminate;
    public String alt;
    public int height;//String?
    public String src;
    public int width;//String?
    public String accept;
    public FileList files;
    public String autocomplete;
    public long maxLength;
    public long size;
    public String pattern;
    public String placeholder;
    public boolean readyOnly;
    public String min;
    public String max;
    public long selectionStart;
    public long selectionEnd;
    public String selectionDirection;
    public String defaultValue;
    public String dirName;
    //public String accessKey;//fromJsonできない原因
    //public HTMLElement list;//fromJsonできない原因
    public boolean multiple;
    public NodeList labels;//すごく怪しい
    public String step;
    public Date valueAsDate;
    public double valueAsNumber;
    public String autocapitalize;

    public void update() {
        super.update();
        getter.put("formAction", formAction);
        getter.put("formEncType", formEncType);
        getter.put("formMethod", formMethod);
        getter.put("formNoValidate", formNoValidate);
        getter.put("formTarget", formTarget);
        getter.put("name", name);
        getter.put("type", type);
        getter.put("disabled", disabled);
        getter.put("autofocus", autofocus);
        getter.put("required", required);
        getter.put("value", value);
        getter.put("validationMessage", validationMessage);
        getter.put("willValidate", willValidate);
        getter.put("checked", checked);
        getter.put("defaultChecked", defaultChecked);
        getter.put("indeterminate", indeterminate);
        getter.put("alt", alt);
        getter.put("height", height);
        getter.put("src", src);
        getter.put("width", width);
        getter.put("accept", accept);
        getter.put("autocomplete", autocomplete);
        getter.put("maxLength", maxLength);
        getter.put("size", size);
        getter.put("pattern", pattern);
        getter.put("placeholder", placeholder);
        getter.put("readyOnly", readyOnly);
        getter.put("min", min);
        getter.put("max", max);
        getter.put("selectionStart", selectionStart);
        getter.put("selectionEnd", selectionEnd);
        getter.put("selectionDirection", selectionDirection);
        getter.put("defaultValue", defaultValue);
        getter.put("dirName", dirName);
        getter.put("multiple", multiple);
        getter.put("step", step);
        getter.put("valueAsNumber", valueAsNumber);
        getter.put("autocapitalize", autocapitalize);
    }

    public int Insert(int logID, Connection conn, PreparedStatement ps, ResultSet rs) throws SQLException {
        int parentID = super.Insert(logID, conn, ps, rs);
        //INSERT
        String sql = "INSERT INTO htmlinputelement(ref, formAction, formEnctype, formMethod, formNoValidate, formTarget, name, type, disabled, autofocus," +
                " required, value, validationMessage, willValidate, checked, defaultChecked, indeterminate, alt, height, src," +
                " width, accept, autocomplete, maxLength, size, pattern, placeholder, readyOnly, min, max, " +
                "selectionStart, selectionEnd, selectionDirection, defaultValue, dirName, multiple, step, valueAsNumber, autocapitalize)" +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?," +
                " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?," +
                " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
                "?, ?, ?, ?, ?, ?, ?, ?, ?)";
        ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setInt(1, parentID);
        ps.setString(2, formAction);
        ps.setString(3, formEncType);
        ps.setString(4, formMethod);
        ps.setString(5, formNoValidate);
        ps.setString(6, formTarget);
        ps.setString(7, name);
        ps.setString(8, type);
        ps.setBoolean(9, disabled);
        ps.setBoolean(10, autofocus);
        ps.setBoolean(11, required);
        ps.setString(12, value);
        ps.setString(13, validationMessage);
        ps.setBoolean(14, willValidate);
        ps.setBoolean(15, checked);
        ps.setBoolean(16, defaultChecked);
        ps.setBoolean(17, indeterminate);
        ps.setString(18, alt);
        ps.setInt(19, height);
        ps.setString(20, src);
        ps.setInt(21, width);
        ps.setString(22, accept);
        ps.setString(23, autocomplete);
        ps.setLong(24, maxLength);
        ps.setLong(25, size);
        ps.setString(26, pattern);
        ps.setString(27, placeholder);
        ps.setBoolean(28, readyOnly);
        ps.setString(29, min);
        ps.setString(30, max);
        ps.setLong(31, selectionStart);
        ps.setLong(32, selectionEnd);
        ps.setString(33, selectionDirection);
        ps.setString(34, defaultValue);
        ps.setString(35, dirName);
        ps.setBoolean(36, multiple);
        ps.setString(37, step);
        ps.setDouble(38, valueAsNumber);
        ps.setString(39, autocapitalize);
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
        String sql = "insert into HTMLInputElement(ref, formAction, formEnctype, formMethod, formNoValidate, formTarget, name, type, disabled, autofocus," +
                " required, value, validationMessage, willValidate, checked, defaultChecked, indeterminate, alt, height, src," +
                " width, accept, autocomplete, maxLength, size, pattern, placeholder, readyOnly, min, max, " +
                "selectionStart, selectionEnd, selectionDirection, defaultValue, dirName, multiple, step, valueAsNumber, autocapitalize)" +
                "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?," +
                " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?," +
                " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
                "?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, parentID);
            ps.setString(2, formAction);
            ps.setString(3, formEncType);
            ps.setString(4, formMethod);
            ps.setString(5, formNoValidate);
            ps.setString(6, formTarget);
            ps.setString(7, name);
            ps.setString(8, type);
            ps.setBoolean(9, disabled);
            ps.setBoolean(10, autofocus);
            ps.setBoolean(11, required);
            ps.setString(12, value);
            ps.setString(13, validationMessage);
            ps.setBoolean(14, willValidate);
            ps.setBoolean(15, checked);
            ps.setBoolean(16, defaultChecked);
            ps.setBoolean(17, indeterminate);
            ps.setString(18, alt);
            ps.setInt(19, height);
            ps.setString(20, src);
            ps.setInt(21, width);
            ps.setString(22, accept);
            ps.setString(23, autocomplete);
            ps.setLong(24, maxLength);
            ps.setLong(25, size);
            ps.setString(26, pattern);
            ps.setString(27, placeholder);
            ps.setBoolean(28, readyOnly);
            ps.setString(29, min);
            ps.setString(30, max);
            ps.setLong(31, selectionStart);
            ps.setLong(32, selectionEnd);
            ps.setString(33, selectionDirection);
            ps.setString(34, defaultValue);
            ps.setString(35, dirName);
            ps.setBoolean(36, multiple);
            ps.setString(37, step);
            ps.setDouble(38, valueAsNumber);
            ps.setString(39, autocapitalize);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            while (rs.next()) {
                childID = rs.getInt(1);
            }
        }
        return childID;
    }

    public String accept(Processor pro) {
        pro.process(this);
        return null;
    }
}
