package oplor.server.node;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//<iframe>要素に関するクラス
public class HTMLFrameElement extends HTMLElement {
    public String allow;
    public boolean allowfullscreen;
    public boolean allowPaymentRequest;
    public String height;
    public String name;
    public String referrerPolicy;
    //public DOMSettableTokenList sandbox;
    public String src;
    public String srcdoc;
    public String width;

    //HTMLFrameElementの更新
    public void update() {
        //親クラスのアップデート
        super.update();
        //追加情報をgetterマップに追加
        getter.put("allow", allow);
        getter.put("allowfullscreen", allowfullscreen);
        getter.put("allowPaymentRequest", allowPaymentRequest);
        getter.put("height", height);
        getter.put("name", name);
        getter.put("referrerPolicy", referrerPolicy);
        getter.put("src", src);
        getter.put("srcdoc", srcdoc);
        getter.put("width", width);
    }

    //データベースにHTMLFrameElementを挿入
    public int Insert(int logID, Connection conn, PreparedStatement ps, ResultSet rs) throws SQLException {
        //親イベントのIDを取得
        int parentID = super.Insert(logID, conn, ps, rs);
        //htmlframeelementテーブルにデータを挿入するSQL文
        String sql = "INSERT INTO htmlframeelement(ref, allow, allowfullscreen, allowPaymentRequest, height, name, referrerPolicy, src, srcdoc, width)" +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        //準備されたステートメントを作成
        ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

        //パラメータを設定
        ps.setInt(1, parentID);
        ps.setString(2, allow);
        ps.setBoolean(3, allowfullscreen);
        ps.setBoolean(4, allowPaymentRequest);
        ps.setString(5, height);
        ps.setString(6, name);
        ps.setString(7, referrerPolicy);
        ps.setString(8, src);
        ps.setString(9, srcdoc);
        ps.setString(10, width);

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

        //HTMLFrameElementテーブルにデータを挿入するSQL文
        String sql = "insert into HTMLFrameElement(ref, allow, allowfullscreen, allowPaymentRequest, height, name, referrerPolicy, src, srcdoc, width)" +
                "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            //パラメータの設定
            ps.setInt(1, parentID);
            ps.setString(2, allow);
            ps.setBoolean(3, allowfullscreen);
            ps.setBoolean(4, allowPaymentRequest);
            ps.setString(5, height);
            ps.setString(6, name);
            ps.setString(7, referrerPolicy);
            ps.setString(8, src);
            ps.setString(9, srcdoc);
            ps.setString(10, width);
            //SQL文の実行
            ps.executeUpdate();
            //挿入したレコードのキーを取得
            ResultSet rs = ps.getGeneratedKeys();
            while (rs.next()) {
                childID = rs.getInt(1); //挿入したレコードIDを取得
            }
        }
        //挿入されたレコードIDを取得
        return childID;
    }
}
