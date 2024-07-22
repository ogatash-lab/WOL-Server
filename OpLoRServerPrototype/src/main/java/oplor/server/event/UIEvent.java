package oplor.server.event;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.*;

public class UIEvent extends Event {
    //UIEventの詳細情報を保持
    public long detail;

    //UIEventの更新
    public void update() {
        //親クラスのアップデート
        super.update();
        //detailをgetterマップに追加
        getter.put("detail", detail);
    }

    //データベースにUIEventを挿入
    public int Insert(int logID, Connection conn, PreparedStatement ps, ResultSet rs) throws SQLException {
        //親イベントのIDを取得
        int parentID = super.Insert(logID, conn, ps, rs);

        //UIEventテーブルにデータを挿入するSQL文
        String sql = "INSERT INTO uievent(ref, detail)VALUES(?, ?)";

        //準備されたステートメントを作成
        ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

        //SQL文のパラメータを設定
        ps.setInt(1, parentID);
        ps.setLong(2, detail);

        //INSERT文の実行
        ps.executeUpdate();

        //生成されたキーの取得
        int childID = -1;
        rs = ps.getGeneratedKeys();

        //挿入されたレコードのキーを取得
        while (rs.next()) {
            childID = rs.getInt(1); //挿入されたレコードのIDを取得
        }

        //挿入されたレコードのIDを返す
        return childID;
    }

    //SQLiteデータベースにUIEventを挿入
    public int sqliteInsert(int logID, Connection connection) throws SQLException {
        //親イベントのIDを取得
        int parentID = super.sqliteInsert(logID, connection);
        int childID = -1;

        //UIEventテーブルにデータを挿入するSQL文
        String sql = "insert into UIEvent(ref, detail)values(?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            //パラメータの設定
            ps.setInt(1, parentID);
            ps.setLong(2, detail);

            //SQL文の実行
            ps.executeUpdate();

            //挿入されたレコードのキーを取得
            ResultSet rs = ps.getGeneratedKeys();
            while (rs.next()) {
                childID = rs.getInt(1); //挿入されたレコードのIDを取得
            }
        }

        //挿入されたレコードIDを返す
        return childID;
    }
};
