package oplor.server.node;

import oplor.server.Processor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//DOCTYPEのクラス
public class DocumentType extends Node {
    public String name;
    public String publicId;
    public String systemId;

    //DocumentTypeの更新
    public void update() {
        //親クラスのアップデート
        super.update();
        //追加情報をgetterマップに追加
        getter.put("name", name);
        getter.put("publicId", publicId);
        getter.put("systemId", systemId);
    }

    //データベースにDocumentTypeを挿入
    public int Insert(int logID, Connection conn, PreparedStatement ps, ResultSet rs) throws SQLException {
        //親イベントのIDを取得
        int parentID = super.Insert(logID, conn, ps, rs);

        //documenttypeテーブルにデータを挿入するSQL文
        String sql = "INSERT INTO documenttype(ref, name, publicId, systemId)VALUES(?, ?, ?, ?)";

        //準備されたステートメントを作成
        ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

        //パラメータを設定
        ps.setInt(1, parentID);
        ps.setString(2, name);
        ps.setString(3, publicId);
        ps.setString(4, systemId);

        //INSERTを実行
        ps.executeUpdate();

        //挿入されたレコードのキーを取得
        int childID = -1;
        rs = ps.getGeneratedKeys();
        while (rs.next()) {
            childID = rs.getInt(1); //挿入したレコードのIDを取得
        }
        //取得したレコードIDを返す
        return childID;
    }

    //SQLiteデータベースにDocumentType情報を挿入
    public int sqliteInsert(int logID, Connection connection) throws SQLException {
        //親ノードのIDを取得
        int parentID = super.sqliteInsert(logID, connection);
        int childID = -1;
        //System.out.println("1");

        //DocumentTypeテーブルにデータを挿入するSQL文
        String sql = "insert into DocumentType(ref, name, publicId, systemId)values(?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            //パラメータの設定
            ps.setInt(1, parentID);
            ps.setString(2, name);
            ps.setString(3, publicId);
            ps.setString(4, systemId);

            //SQL文の実行
            ps.executeUpdate();

            //挿入したレコードのキーを取得
            ResultSet rs = ps.getGeneratedKeys();
            while (rs.next()) {
                childID = rs.getInt(1); //挿入したレコードのIDを取得
            }
        }
        //挿入されたレコードIDを返す
        return childID;
    }

    //???
    public String accept(Processor pro) {
        return null;
    }
}
