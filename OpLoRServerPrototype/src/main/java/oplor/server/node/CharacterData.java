package oplor.server.node;

import oplor.server.Processor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//文字列に関するクラス???
public class CharacterData extends Node {
    public int data;
    public int length;

    CharacterData() {
        super();
        data = 0;
        length = 0;
    }

    //CharacterDateの更新
    public void update() {
        //親クラスのアップデート
        super.update();
        //追加情報をgetterマップに追加
        getter.put("data", data);
        getter.put("length", length);
    }
    /*
    public int Insert(int logID, Connection conn, PreparedStatement ps, ResultSet rs) throws SQLException {
        int parentID=super.Insert(logID, conn, ps, rs);
        //INSERT
        String sql="INSERT INTO characterdata(data, length)VALUES(?, ?)";
        ps=conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setInt(1, data);
        ps.setInt(2, length);
        //ISNERTを実行する
        ps.executeUpdate();
        //次の子クラスへと紐づけるためのID取得
        int childID=-1;
        rs=ps.getGeneratedKeys();
        while(rs.next()) {
            childID = rs.getInt(1);
        }
        return childID;
    }*/

    //データベースにCharacterDataを挿入
    public int sqliteInsert(Connection connection) throws SQLException {
        int childID = -1;
        //System.out.println("1");

        //CharacterDataテーブルにデータを挿入するSQL文
        String sql = "insert into CharacterData(data, length) values(?, ?)";

        //準備されたステートメントを作成
        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            //パラメータを設定
            ps.setInt(1, data);
            ps.setInt(2, length);

            //SQL文を実行
            ps.executeUpdate();

            //挿入されたレコードのキーを取得
            ResultSet rs = ps.getGeneratedKeys();
            while (rs.next()) {
                childID = rs.getInt(1);//挿入したレコードのIDを取得
            }
        }
        //取得したレコードIDを返す
        return childID;
    }

    //未実装
    public String accept(Processor pro) {
        return null;
    }
}
