package oplor.server.node;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//<option>要素に関するクラス
public class HTMLOptionElement extends HTMLElement {
    public boolean defaultSelected;
    public boolean disabled;
    public HTMLFormElement formSelector;
    public long index;
    public String label;
    public boolean selected;
    public String text;
    public String value;

    //HTMLOptionElementの更新
    public void update() {
        //親クラスのアップデート
        super.update();
        //追加情報をgetterマップに追加
        getter.put("defaultSelected", defaultSelected);
        getter.put("disabled", disabled);
        getter.put("index", index);
        getter.put("label", label);
        getter.put("selected", selected);
        getter.put("text", text);
        getter.put("value", value);
    }

    //データベースにHTMLOptionElementを挿入
    public int Insert(int logID, Connection conn, PreparedStatement ps, ResultSet rs) throws SQLException {
        //親イベントのIDを取得
        int parentID = super.Insert(logID, conn, ps, rs);
        //htmloptionelementテーブルにデータを挿入するSQL文
        String sql = "INSERT INTO htmloptionelement(ref, defaultSelected, disabled, index, label, selected, text, value)" +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        //準備されたステートメントを作成
        ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        //パラメータを設定
        ps.setInt(1, parentID);
        ps.setBoolean(2, defaultSelected);
        ps.setBoolean(3, disabled);
        ps.setLong(4, index);
        ps.setString(5, label);
        ps.setBoolean(6, selected);
        ps.setString(7, text);
        ps.setString(8, value);

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

    //SQLiteデータベースにHTMLOptionElement情報を挿入
    public int sqliteInsert(int logID, Connection connection) throws SQLException {
        //親ノードのIDを取得
        int parentID = super.sqliteInsert(logID, connection);
        int childID = -1;

        //HTMLOptionElementテーブルにデータを挿入するSQL文
        String sql = "insert into HTMLOptionElement(ref, defaultSelected, disabled, index, label, selected, text, value)" +
                "value(?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            //パラメータの設定
            ps.setInt(1, parentID);
            ps.setBoolean(2, defaultSelected);
            ps.setBoolean(3, disabled);
            ps.setLong(4, index);
            ps.setString(5, label);
            ps.setBoolean(6, selected);
            ps.setString(7, text);
            ps.setString(8, value);
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
