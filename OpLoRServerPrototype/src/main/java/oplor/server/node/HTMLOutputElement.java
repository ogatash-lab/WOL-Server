package oplor.server.node;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//<output>要素に関するクラス
public class HTMLOutputElement extends HTMLElement {
    public String defaultValue;
    public HTMLFormElement formSelector;
    //public DOMTokenList htmlFor;
    //public NodeList labels;
    public String name;
    public String type;
    public String validationMessage;
    //public ValidityState validity;
    public String value;
    public boolean willValidate;

    //HTMLOutputElementの更新
    public void update() {
        //親クラスのアップデート
        super.update();
        //追加情報をgetterマップに追加
        getter.put("defaultValue", defaultValue);
        getter.put("name", name);
        getter.put("type", type);
        getter.put("validationMessage", validationMessage);
        getter.put("value", value);
        getter.put("willValidate", willValidate);
    }

    //データベースにHTMLOutputElementを挿入
    public int Insert(int logID, Connection conn, PreparedStatement ps, ResultSet rs) throws SQLException {
        //親イベントのIDを取得
        int parentID = super.Insert(logID, conn, ps, rs);
        //htmloutputelementテーブルにデータを挿入するSQL文
        String sql = "INSERT INTO htmloutputelement(ref, defaultValue, name, type, validationMessage, value, willValidate)" +
                "VALUES(?, ?, ?, ?, ?, ?, ?)";
        //準備されたステートメントを作成
        ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        //パラメータを設定
        ps.setInt(1, parentID);
        ps.setString(2, defaultValue);
        ps.setString(3, name);
        ps.setString(4, type);
        ps.setString(5, validationMessage);
        ps.setString(6, value);
        ps.setBoolean(7, willValidate);

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

    //SQLiteデータベースにHTMLOutputElement情報を挿入
    public int sqliteInsert(int logID, Connection connection) throws SQLException {
        //親ノードのIDを取得
        int parentID = super.sqliteInsert(logID, connection);
        int childID = -1;
        //HTMLOutputElementテーブルにデータを挿入するSQL文
        String sql = "insert into HTMLOutputElement(ref, defaultValue, name, type, validationMessage, value, willValidate)" +
                "values(?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            //パラメータの設定
            ps.setInt(1, parentID);
            ps.setString(2, defaultValue);
            ps.setString(3, name);
            ps.setString(4, type);
            ps.setString(5, validationMessage);
            ps.setString(6, value);
            ps.setBoolean(7, willValidate);
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
