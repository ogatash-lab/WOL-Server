package oplor.server.node;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//<video>要素に関するクラス
public class HTMLVideoElement extends HTMLMediaElement {
    public String poster;
    public long videoHeight;
    public long videoWidth;
    public String width;

    //HTMLVideoElementの更新
    public void update() {
        //親クラスのアップデート
        super.update();
        //追加情報をgetterマップに追加
        getter.put("poster", poster);
        getter.put("videoHeight", videoHeight);
        getter.put("videoWidth", videoWidth);
        getter.put("width", width);
    }

    //データベースにHTMLVideoElementを挿入
    public int Insert(int logID, Connection conn, PreparedStatement ps, ResultSet rs) throws SQLException {
        //親イベントのIDを取得
        int parentID = super.Insert(logID, conn, ps, rs);
        //htmlvideoelementテーブルにデータを挿入するSQL文
        String sql = "INSERT INTO htmlvideoelement(ref, poster, videoHeight, videoWidth, width)"
                + "VALUES(?, ?, ?, ?, ?)";
        //準備されたステートメントを作成
        ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        //パラメータを設定
        ps.setInt(1, parentID);
        ps.setString(2, poster);
        ps.setLong(3, videoHeight);
        ps.setLong(4, videoWidth);
        ps.setString(5, width);

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

    //SQLiteデータベースにHTMLVideoElement情報を挿入
    public int sqliteInsert(int logID, Connection connection) throws SQLException {
        //親ノードのIDを取得
        int parentID = super.sqliteInsert(logID, connection);
        int childID = -1;
        //HTMLVideoElementテーブルにデータを挿入するSQL文
        String sql = "insert into HTMLVideoElement(ref, poster, videoHeight, videoWidth, width)"
                + "values(?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            //パラメータの設定
            ps.setInt(1, parentID);
            ps.setString(2, poster);
            ps.setLong(3, videoHeight);
            ps.setLong(4, videoWidth);
            ps.setString(5, width);
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
