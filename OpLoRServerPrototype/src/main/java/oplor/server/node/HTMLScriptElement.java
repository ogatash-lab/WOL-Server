package oplor.server.node;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//<script>要素に関するクラス
public class HTMLScriptElement extends HTMLElement {
    public String type;
    public String src;
    public String charset;
    public boolean async;
    public boolean defer;
    public String crossOrigin;
    public String text;
    public boolean noModule;

    //HTMLScriptElementの更新
    public void update() {
        //親クラスのアップデート
        super.update();
        //追加情報をgetterマップに追加
        getter.put("type", type);
        getter.put("src", src);
        getter.put("charset", charset);
        getter.put("async", async);
        getter.put("defer", defer);
        getter.put("crossOrigin", crossOrigin);
        getter.put("text", text);
        getter.put("noModule", noModule);
    }

    //データベースにHTMLScriptElementを挿入
    public int Insert(int logID, Connection conn, PreparedStatement ps, ResultSet rs) throws SQLException {
        //親イベントのIDを取得
        int parentID = super.Insert(logID, conn, ps, rs);
        //htmlscriptelementテーブルにデータを挿入するSQL文
        String sql = "INSERT INTO htmlscriptelement(ref, type, src, charset, async, defer, crossOrigin, text, noModule)" +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        //準備されたステートメントを作成
        ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        //パラメータを設定
        ps.setInt(1, parentID);
        ps.setString(2, type);
        ps.setString(3, src);
        ps.setString(4, charset);
        ps.setBoolean(5, async);
        ps.setBoolean(6, defer);
        ps.setString(7, crossOrigin);
        ps.setString(8, text);
        ps.setBoolean(9, noModule);

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

    //SQLiteデータベースにHTMLScriptElement情報を挿入
    public int sqliteInsert(int logID, Connection connection) throws SQLException {
        //親ノードのIDを取得
        int parentID = super.sqliteInsert(logID, connection);
        int childID = -1;
        //HTMLScriptElementテーブルにデータを挿入するSQL文
        String sql = "insert into HTMLScriptElement(ref, type, src, charset, async, defer, crossOrigin, text, noModule)" +
                "values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            //パラメータの設定
            ps.setInt(1, parentID);
            ps.setString(2, type);
            ps.setString(3, src);
            ps.setString(4, charset);
            ps.setBoolean(5, async);
            ps.setBoolean(6, defer);
            ps.setString(7, crossOrigin);
            ps.setString(8, text);
            ps.setBoolean(9, noModule);
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
