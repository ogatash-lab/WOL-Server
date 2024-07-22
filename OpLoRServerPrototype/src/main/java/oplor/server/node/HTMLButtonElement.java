package oplor.server.node;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//<button>要素に関するクラス
public class HTMLButtonElement extends HTMLElement {
    //public String accessKey; delete5/30
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
    //public long tabIndex; delete5/30
    public String type;
    public String validationMessage;
    //public ValidityState validity;
    public String value;
    public boolean willValidate;

    //HTMLButtonElementの更新
    public void update() {
        //親クラスのアップデート
        super.update();
        //追加情報をgetterマップに追加
        //getter.put("accessKey", accessKey);
        getter.put("autofocus", autofocus);
        getter.put("disabled", disabled);
        getter.put("formAction", formAction);
        getter.put("formEnctype", formEnctype);
        getter.put("formMethod", formMethod);
        getter.put("formNoValidate", formNoValidate);
        getter.put("formTarget", formTarget);
        getter.put("name", name);
        //getter.put("tabIndex", tabIndex);
        getter.put("type", type);
        getter.put("validationMessage", validationMessage);
        getter.put("value", value);
        getter.put("willValidate", willValidate);
    }

    //データベースにHTMLButtonElementを挿入
    public int Insert(int logID, Connection conn, PreparedStatement ps, ResultSet rs) throws SQLException {
        int childID = -1;
        //親イベントのIDを取得
        int parentID = super.Insert(logID, conn, ps, rs);
        //INSERT
        //Calendar
        /*
        Calendar cal=Calendar.getInstance();
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSSSSS");
        absTime = sdf.format(cal.getTime());
        */
        //System.out.println("absTime:"+absTime);

        //htmlbuttonelementテーブルにデータを挿入するSQL文
        String sql = "INSERT INTO htmlbuttonelement(ref, accessKey, autofocus, disabled, formAcion, formEnctype, formMethod, firnBiValidate, formTarget, name, tabIndex, type, validationMessage, value, willValidate)" +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        //準備されたステートメントを作成
        ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

        //パラメータを設定
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

        //INSERT文を実行
        ps.executeUpdate();

        //挿入したレコードのキーを取得
        rs = ps.getGeneratedKeys();
        while (rs.next()) {
            childID = rs.getInt(1); //挿入したレコードIDを取得
        }
        //取得したレコードIDを返す
        return childID;
    }

    //SQLiteデータベースにHTMLButtonElement情報を挿入
    public int sqliteInsert(int logID, Connection connection) throws SQLException {
        //親ノードのIDを取得
        int parentID = super.sqliteInsert(logID, connection);
        int childID = -1;

        //HTMLBaseElementテーブルにデータを挿入するSQL文
        String sql = "insert into HTMLButtonElement(ref, accessKey, autofocus, disabled, formAcion, formEnctype, formMethod, firnBiValidate, formTarget, name, tabIndex, type, validationMessage, value, willValidate)" +
                "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            //パラメータの設定
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
