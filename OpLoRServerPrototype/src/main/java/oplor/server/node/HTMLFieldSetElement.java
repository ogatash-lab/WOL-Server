package oplor.server.node;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//<fieldset>要素に関するクラス
public class HTMLFieldSetElement extends HTMLElement {
    public boolean disabled;
    public String element;
    //public HTMLCollection formSelector;
    public String name;
    public String type;
    public String validationMessage;
    //public ValidityState validity;
    public boolean willValidate;

    //HTMLFieldSetElementの更新
    public void update() {
        //親クラスのアップデート
        super.update();
        //追加情報をgetterマップに追加
        getter.put("disabled", disabled);
        getter.put("element", element);
        getter.put("name", name);
        getter.put("type", type);
        getter.put("validationMessage", validationMessage);
        getter.put("willValidate", willValidate);
    }

    //データベースにHTMLFieldSetElementを挿入
    public int Insert(int logID, Connection conn, PreparedStatement ps, ResultSet rs) throws SQLException {
        //親イベントのIDを取得
        int parentID = super.Insert(logID, conn, ps, rs);

        //htmlfieldelementテーブルにデータを挿入するSQL文
        String sql = "INSERT INTO htmlfieldelement(ref, disabled, element, name, type, validationMessage, willValidate)" +
                "VALUES(?, ?, ?, ?, ?, ?, ?)";

        //準備されたステートメントを作成
        ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

        //パラメータを設定
        ps.setInt(1, parentID);
        ps.setBoolean(2, disabled);
        ps.setString(3, element);
        ps.setString(4, name);
        ps.setString(5, type);
        ps.setString(6, validationMessage);
        ps.setBoolean(7, willValidate);

        //INSERT文を実行
        ps.executeUpdate();

        //挿入されたレコードのキーを取得
        int childID = -1;
        rs = ps.getGeneratedKeys();
        while (rs.next()) {
            childID = rs.getInt(1); //挿入したレコードのIDを取得
        }
        //取得したレコードIDを返す
        return childID;
    }

    //SQLiteデータベースにHTMLFieldSetElement情報を挿入
    public int sqliteInsert(int logID, Connection connection) throws SQLException {
        //親ノードのIDを取得
        int parentID = super.sqliteInsert(logID, connection);
        int childID = -1;

        //HTMLFieldSetElementテーブルにデータを挿入するSQL文
        String sql = "insert into HTMLFieldElement(ref, disabled, element, name, type, validationMessage, willValidate)" +
                "values(?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            //パラメータの設定
            ps.setInt(1, parentID);
            ps.setBoolean(2, disabled);
            ps.setString(3, element);
            ps.setString(4, name);
            ps.setString(5, type);
            ps.setString(6, validationMessage);
            ps.setBoolean(7, willValidate);
            //SQL文の実行
            ps.executeUpdate();
            //挿入したレコードのキーを取得
            ResultSet rs = ps.getGeneratedKeys();
            while (rs.next()) {
                childID = rs.getInt(1); //挿入したレコードIDを取得
            }
        }
        //挿入されたレコードIDを返す
        return childID;
    }
}
