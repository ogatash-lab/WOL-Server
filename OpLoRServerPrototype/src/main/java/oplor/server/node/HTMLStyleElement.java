package oplor.server.node;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//<style>要素に関するクラス
public class HTMLStyleElement extends HTMLElement {
    public String media;
    public String type;
    public boolean disabled;
    //public StyleSheet sheet;

    //HTMLStyleElementの更新
    public void update() {
        //親クラスのアップデート
        super.update();
        //追加情報をgetterマップに追加
        getter.put("media", media);
        getter.put("type", type);
        getter.put("disabled", disabled);
    }

    //データベースにHTMLStyleElementを挿入
    public int Insert(int logID, Connection conn, PreparedStatement ps, ResultSet rs) throws SQLException {
        //親イベントのIDを取得
        int parentID = super.Insert(logID, conn, ps, rs);
        //htmlstyleelementテーブルにデータを挿入するSQL文
        String sql = "INSERT INTO htmlstyleelement(ref, media, type, disabled)" +
                "VALUES(?, ?, ?, ?)";
        //準備されたステートメントを作成
        ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        //パラメータを作成
        ps.setInt(1, parentID);
        ps.setString(2, media);
        ps.setString(3, type);
        ps.setBoolean(4, disabled);

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

    //SQLiteデータベースにHTMLStyleElement情報を挿入
    public int sqliteInsert(int logID, Connection connection) throws SQLException {
        //親ノードのIDを取得
        int parentID = super.sqliteInsert(logID, connection);
        int childID = -1;
        //HTMLStyleElementテーブルにデータを挿入するSQL文
        String sql = "insert into HTMLStyleElement(ref, media, type, disabled)" +
                "values(?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            //パラメータの設定
            ps.setInt(1, parentID);
            ps.setString(2, media);
            ps.setString(3, type);
            ps.setBoolean(4, disabled);
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
