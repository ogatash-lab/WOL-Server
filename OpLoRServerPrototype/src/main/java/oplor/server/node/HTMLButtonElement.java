package oplor.server.node;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HTMLButtonElement extends HTMLElement {
    public String accessKey;
    public boolean autofocus;
    public boolean disabled;
    //public HTMLFormElement formSelector;
    public String formAction;
    public String formEnctype;
    public String formMethod;
    public boolean formNoValidate;
    public String formTarget;
    //public NodeList labels;
    //public HTMLMenuElement menu;
    public String name;
    public long tabIndex;
    public String type;
    public String validationMessage;
    //public ValidityState validity;
    public String value;
    public boolean willValidate;

    public void update() {
        super.update();
        getter.put("accessKey", accessKey);
        getter.put("autofocus", autofocus);
        getter.put("disabled", disabled);
        getter.put("formAction", formAction);
        getter.put("formEnctype", formEnctype);
        getter.put("formMethod", formMethod);
        getter.put("formNoValidate", formNoValidate);
        getter.put("formTarget", formTarget);
        getter.put("name", name);
        getter.put("tabIndex", tabIndex);
        getter.put("type", type);
        getter.put("validationMessage", validationMessage);
        getter.put("value", value);
        getter.put("willValidate", willValidate);
    }

    public int Insert(int logID, Connection conn, PreparedStatement ps, ResultSet rs) throws SQLException {
        int childID = -1;
        int parentID = super.Insert(logID, conn, ps, rs);
        //INSERT
        //Calendar
        /*
        Calendar cal=Calendar.getInstance();
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSSSSS");
        absTime = sdf.format(cal.getTime());
        */
        //System.out.println("absTime:"+absTime);
        String sql = "INSERT INTO htmlbuttonelement(ref, accessKey, autofocus, disabled, formAcion, formEnctype, formMethod, firnBiValidate, formTarget, name, tabIndex, type, validationMessage, value, willValidate)" +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setInt(1, parentID);
        ps.setString(2, accessKey);
        ps.setBoolean(3, autofocus);
        ps.setBoolean(4, disabled);
        ps.setString(5, formAction);
        ps.setString(6, formEnctype);
        ps.setString(7, formMethod);
        ps.setBoolean(8, formNoValidate);
        ps.setString(9, formTarget);
        ps.setString(10, name);
        ps.setLong(11, tabIndex);
        ps.setString(12, type);
        ps.setString(13, validationMessage);
        ps.setString(14, value);
        ps.setBoolean(15, willValidate);
        //ISNERTを実行する
        ps.executeUpdate();
        //次の子クラスへと紐づけるためのID取得
        rs = ps.getGeneratedKeys();
        while (rs.next()) {
            childID = rs.getInt(1);
        }
        return childID;
    }

    public int sqliteInsert(int logID, Connection connection) throws SQLException {
        int parentID = super.sqliteInsert(logID, connection);
        int childID = -1;
        String sql = "insert into HTMLButtonElement(ref, accessKey, autofocus, disabled, formAcion, formEnctype, formMethod, firnBiValidate, formTarget, name, tabIndex, type, validationMessage, value, willValidate)" +
                "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, parentID);
            ps.setString(2, accessKey);
            ps.setBoolean(3, autofocus);
            ps.setBoolean(4, disabled);
            ps.setString(5, formAction);
            ps.setString(6, formEnctype);
            ps.setString(7, formMethod);
            ps.setBoolean(8, formNoValidate);
            ps.setString(9, formTarget);
            ps.setString(10, name);
            ps.setLong(11, tabIndex);
            ps.setString(12, type);
            ps.setString(13, validationMessage);
            ps.setString(14, value);
            ps.setBoolean(15, willValidate);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            while (rs.next()) {
                childID = rs.getInt(1);
            }
        }
        return childID;
    }
}
