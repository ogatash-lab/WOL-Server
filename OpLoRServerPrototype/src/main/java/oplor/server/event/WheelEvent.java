package oplor.server.event;

import java.sql.*;

//ホイール操作に関するイベント
public class WheelEvent extends MouseEvent {
    //イベントに関する情報
    public double deltaX;
    public double deltaY;
    public double deltaZ;
    public long deltaMode;

    //WheelEventの更新
    public void update() {
        //親クラスのアップデート
        super.update();

        //Wheelイベントに関する追加情報をgetterマップに追加
        getter.put("deltaX", deltaX);
        getter.put("deltaY", deltaY);
        getter.put("deltaZ", deltaZ);
        getter.put("deltaMode", deltaMode);
    }

    //データベースにWheelEventを挿入
    public int Insert(int logID, Connection conn, PreparedStatement ps, ResultSet rs) throws SQLException {
        //親イベントのIDを取得
        int parentID = super.Insert(logID, conn, ps, rs);

        //WheelEventにデータを挿入するSQL文
        String sql = "INSERT INTO wheelevent(ref, deltaX, deltaY, deltaZ, dataMode)"
                + "VALUES(?, ?, ?, ?, ?)";

        //準備されたステートメントを作成
        ps = conn.prepareStatement(sql);

        //SQL文のパラメータを設定
        ps.setInt(1, parentID);
        ps.setDouble(2, deltaX);
        ps.setDouble(3, deltaY);
        ps.setDouble(4, deltaZ);
        ps.setLong(5, deltaMode);

        //INSERT文を実行
        ps.executeUpdate();

        //生成したキーの取得
        int childID = -1;
        rs = ps.getGeneratedKeys();

        //挿入したレコードのキーを取得
        while (rs.next()) {
            childID = rs.getInt(1); //挿入したレコードのIDを挿入
        }

        //挿入したレコードのIDを返す
        return childID;
    }

    //SQLiteデータベースにWheelEventを挿入
    public int sqliteInsert(int logID, Connection connection) throws SQLException {
        //親イベントのIDを取得
        int parentID = super.sqliteInsert(logID, connection);
        int childID = -1;

        //WheelEventにデータを挿入するSQL文
        String sql = "insert into WheelEvent(ref, deltaX, deltaY, deltaZ, dataMode)"
                + "values(?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            //パラメータの設定
            ps.setInt(1, parentID);
            ps.setDouble(2, deltaX);
            ps.setDouble(3, deltaY);
            ps.setDouble(4, deltaZ);
            ps.setLong(5, deltaMode);

            //SQL文の実行
            ps.executeUpdate();

            //生成したキーの取得
            ResultSet rs = ps.getGeneratedKeys();

            //挿入したレコードのキーを取得
            while (rs.next()) {
                childID = rs.getInt(1); //挿入したレコードのIDを取得
            }
        }

        //挿入したレコードIDを返す
        return childID;
    }
}
