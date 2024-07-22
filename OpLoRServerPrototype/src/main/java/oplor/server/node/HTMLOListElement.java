package oplor.server.node;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//<ol>要素に関するクラス
public class HTMLOListElement extends HTMLElement {
    public boolean reversed;
    public long start;
    public String type;

    //HTMLOlistElementの更新
    public void update() {
        //親クラスのアップデート
        super.update();
        //追加情報をgetterマップに追加
        getter.put("reversed", reversed);
        getter.put("start", start);
        getter.put("type", type);
    }

    //データベースにHTMLOListElementを挿入
    public int Insert(int logID, Connection conn, PreparedStatement ps, ResultSet rs) throws SQLException {
        //親イベントのIDを取得
        int parentID = super.Insert(logID, conn, ps, rs);
        //htmlolistelementテーブルにデータを挿入するSQL文
        String sql = "INSERT INTO htmlolistelement(ref, reversed, start, type)VALUES(?, ?, ?, ?)";
        //準備されたステート作成
        ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        //パラメータを設定
        ps.setInt(1, parentID);
        ps.setBoolean(2, reversed);
        ps.setLong(3, start);
        ps.setString(4, type);

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

    //SQLiteデータベースにHTMLOLIstElement情報を挿入
    public int sqliteInsert(int logID, Connection connection) throws SQLException {
        //親ノードのIDを取得
        int parentID = super.sqliteInsert(logID, connection);
        int childID = -1;
        //HTMLOListElementテーブルにデータを挿入するSQL文
        String sql = "insert into HTMLOListElement(ref, reversed, start, type)values(?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            //パラメータの設定
            ps.setInt(1, parentID);
            ps.setBoolean(2, reversed);
            ps.setLong(3, start);
            ps.setString(4, type);
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
