package oplor.server.node;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//<meter>要素に関するクラス
public class HTMLMeterElement extends HTMLElement {
    public double high;
    public double low;
    public double max;
    public double min;
    public double optimum;
    //public NodeList labels;

    //HTMLMeterElementの更新
    public void update() {
        //親クラスのアップデート
        super.update();
        //追加情報をgetterマップに追加
        getter.put("high", high);
        getter.put("low", low);
        getter.put("max", max);
        getter.put("min", min);
    }

    //データベースにHTMLMeterElementを挿入
    public int Insert(int logID, Connection conn, PreparedStatement ps, ResultSet rs) throws SQLException {
        int parentID = super.Insert(logID, conn, ps, rs);
        //親イベントのIDを取得
        String sql = "INSERT INTO htmlmeterelement(ref, high, low, max, min, optimum)VALUES(?, ?, ?, ?, ?, ?)";
        //準備されたステートメントを作成
        ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        //パラメータを設定
        ps.setInt(1, parentID);
        ps.setDouble(2, high);
        ps.setDouble(3, low);
        ps.setDouble(4, max);
        ps.setDouble(5, min);
        ps.setDouble(6, optimum);

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

    //SQLiteデータベースにHTMLMeterElement情報を挿入するSQL文
    public int sqliteInsert(int logID, Connection connection) throws SQLException {
        //親ノードのIDを取得
        int parentID = super.sqliteInsert(logID, connection);
        int childID = -1;
        //HTMLMeterElementテーブルにデータを挿入するSQL文
        String sql = "insert into HTMLMeterElement(ref, high, low, max, min, optimum)values(?, ?, ?, ?, ?, ?)";
        //パラメータの設定
        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, parentID);
            ps.setDouble(2, high);
            ps.setDouble(3, low);
            ps.setDouble(4, max);
            ps.setDouble(5, min);
            ps.setDouble(6, optimum);
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
