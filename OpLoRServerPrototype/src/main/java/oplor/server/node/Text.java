package oplor.server.node;

import oplor.server.node.CharacterData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//HTMLテキストノードを表現するクラス
public class Text extends CharacterData {
    public String wholeText;
    //public HTMLSlotElement assignSlot;

    //Textの更新
    public void update() {
        //親クラスのアップデート
        super.update();
        //追加情報をgetterマップに追加
        getter.put("wholeText", wholeText);
    }

    //データベースにTextを挿入
    public int Insert(int logID, Connection conn, PreparedStatement ps, ResultSet rs) throws SQLException {
        //親イベントのIDを取得
        int parentID = super.Insert(logID, conn, ps, rs);
        //uieventテーブルにデータを挿入するSQL文
        String sql = "INSERT INTO uievent(wholeText)VALUES(?)";
        //準備されたステートメントを作成
        ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        //パラメータを設定
        ps.setString(1, wholeText);
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
}
