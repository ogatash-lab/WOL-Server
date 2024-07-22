package oplor.server.node;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//<object>要素に関するクラス
public class HTMLObjectElement extends HTMLElement {
    public String data;
    public HTMLFormElement formSelector;
    public String height;
    public String name;
    public long tabindex;
    public boolean typeMustMatch;
    public String useMap;
    public String validationMessage;
    //public ValidityState validity;
    public String width;
    public boolean willValidate;

    //HTMLObjectElementの更新
    public void update() {
        //親クラスのアップデート
        super.update();
        //追加情報をgetterマップに追加
        getter.put("data", data);
        getter.put("height", height);
        getter.put("name", name);
        getter.put("tabindex", tabindex);
        getter.put("typeMustMatch", typeMustMatch);
        getter.put("useMap", useMap);
        getter.put("validationMessage", validationMessage);
        getter.put("width", width);
        getter.put("willValidate", willValidate);
    }

    //データベースにHTMLObjectElementを挿入
    public int Insert(int logID, Connection conn, PreparedStatement ps, ResultSet rs) throws SQLException {
        //親イベントのIDを取得
        int parentID = super.Insert(logID, conn, ps, rs);
        //htmlobjectelementテーブルにデータを挿入するSQL文
        String sql = "INSERT INTO htmlobjectelement(ref, data, height, name, tabindex, typeMustMatch, useMap, validationMessage, width, willValidate)" +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        //準備されたステートメントを作成
        ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        //パラメータを設定
        ps.setInt(1, parentID);
        ps.setString(2, data);
        ps.setString(3, height);
        ps.setString(4, name);
        ps.setLong(5, tabindex);
        ps.setBoolean(6, typeMustMatch);
        ps.setString(7, useMap);
        ps.setString(8, validationMessage);
        ps.setString(9, width);
        ps.setBoolean(10, willValidate);

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

    //SQLiteデータベースにHTMLObjectElement情報を挿入
    public int sqliteInsert(int logID, Connection connection) throws SQLException {
        //親ノードのIDを取得
        int parentID = super.sqliteInsert(logID, connection);
        int childID = -1;
        //HTMLObjectElementテーブルにデータを挿入するSQL文
        String sql = "insert into HTMLObjectElement(ref, data, height, name, tabindex, typeMustMatch, useMap, validationMessage, width, willValidate)" +
                "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            //パラメータの設定
            ps.setInt(1, parentID);
            ps.setString(2, data);
            ps.setString(3, height);
            ps.setString(4, name);
            ps.setLong(5, tabindex);
            ps.setBoolean(6, typeMustMatch);
            ps.setString(7, useMap);
            ps.setString(8, validationMessage);
            ps.setString(9, width);
            ps.setBoolean(10, willValidate);
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
