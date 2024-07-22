package oplor.server.node;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//<track>要素に関するクラス
public class HTMLTrackElement extends HTMLElement {
    public String kind;
    public String src;
    public String srclang;
    public String label;
    public boolean m_default;
    public String readyState;
    //public TextTrack text;

    //HTMLTrackElementの更新
    public void update() {
        //親クラスのアップデート
        super.update();
        //追加情報をgetterマップに追加
        getter.put("kind", kind);
        getter.put("src", src);
        getter.put("srclang", srclang);
        getter.put("label", label);
        getter.put("m_default", m_default);
        getter.put("readyState", readyState);
    }

    //データベースにHTMLTrackElementを挿入
    public int Insert(int logID, Connection conn, PreparedStatement ps, ResultSet rs) throws SQLException {
        //親イベントのIDを取得
        int parentID = super.Insert(logID, conn, ps, rs);
        //htmltrackelementテーブルにデータを挿入するSQL文
        String sql = "INSERT INTO htmltrackelement(ref, kind, src, srclang, label, m_default, readyState)"
                + "VALUES(?, ?, ?, ?, ?, ?, ?)";
        //準備されたステートメントを作成
        ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        //パラメータを設定
        ps.setInt(1, parentID);
        ps.setString(2, kind);
        ps.setString(3, src);
        ps.setString(4, srclang);
        ps.setString(5, label);
        ps.setBoolean(6, m_default);
        ps.setString(7, readyState);

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

    //SQLiteデータベースにHTMLTrackElement情報を挿入
    public int sqliteInsert(int logID, Connection connection) throws SQLException {
        //親ノードのIDを取得
        int parentID = super.sqliteInsert(logID, connection);
        int childID = -1;
        //HTMLTrackElementテーブルにデータを挿入するSQL文
        String sql = "insert into HTMLTrackElement(ref, kind, src, srclang, label, m_default, readyState)"
                + "values(?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            //パラメータの設定
            ps.setInt(1, parentID);
            ps.setString(2, kind);
            ps.setString(3, src);
            ps.setString(4, srclang);
            ps.setString(5, label);
            ps.setBoolean(6, m_default);
            ps.setString(7, readyState);
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
