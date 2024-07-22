package oplor.server.node;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//<form>要素に関するクラス
public class HTMLFormElement extends HTMLElement {
    public long length;
    public String name;
    public String method;
    public String target;
    public String action;
    public String encoding;
    public String enctype;
    public String acceptCharset;
    public String autocomplete;
    public boolean noValidate;

    //HTMLFormElementの更新
    public void update() {
        //親クラスのアップデート
        super.update();
        //追加情報をgetterマップに追加
        getter.put("length", length);
        getter.put("name", name);
        getter.put("method", method);
        getter.put("target", target);
        getter.put("action", action);
        getter.put("encoding", encoding);
        getter.put("enctype", enctype);
        getter.put("acceptCharset", acceptCharset);
        getter.put("autocomplete", autocomplete);
        getter.put("noValidate", noValidate);
    }

    //データベースにHTMLFormElementを挿入
    public int Insert(int logID, Connection conn, PreparedStatement ps, ResultSet rs) throws SQLException {
        //親イベントのIDを取得
        int parentID = super.Insert(logID, conn, ps, rs);

        //htmlformelementテーブルにデータを挿入するSQL文
        String sql = "INSERT INTO htmlformelement(ref, length, name, method, target, action, encoding, enctype, acceptCharset, autocomplete, noValidate)" +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        //準備されたステートメントを作成
        ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

        //パラメータを設定
        ps.setInt(1, parentID);
        ps.setLong(2, length);
        ps.setString(3, name);
        ps.setString(4, method);
        ps.setString(5, target);
        ps.setString(6, action);
        ps.setString(7, encoding);
        ps.setString(8, enctype);
        ps.setString(9, acceptCharset);
        ps.setString(10, autocomplete);
        ps.setBoolean(11, noValidate);

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

    //SQLiteデータベースにHTMLFormElement情報を挿入
    public int sqliteInsert(int logID, Connection connection) throws SQLException {
        //親ノードのIDを取得
        int parentID = super.sqliteInsert(logID, connection);
        int childID = -1;

        //HTMLFormElementテーブルにデータを挿入するSQL文
        String sql = "insert into HTMLFormElement(ref, length, name, method, target, action, encoding, enctype, acceptCharset, autocomplete, noValidate)" +
                "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            //パラメータの設定
            ps.setInt(1, parentID);
            ps.setLong(2, length);
            ps.setString(3, name);
            ps.setString(4, method);
            ps.setString(5, target);
            ps.setString(6, action);
            ps.setString(7, encoding);
            ps.setString(8, enctype);
            ps.setString(9, acceptCharset);
            ps.setString(10, autocomplete);
            ps.setBoolean(11, noValidate);
            //SQL文の実行
            ps.executeUpdate();
            //挿入したレコードのキーを取得
            ResultSet rs = ps.getGeneratedKeys();
            while (rs.next()) {
                childID = rs.getInt(1); //挿入したレコードIDを取得
            }
        }
        //挿入したレコードIDを取得
        return childID;
    }
}
