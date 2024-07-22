package oplor.server.node;

import oplor.server.DOMImplementation;
import oplor.server.Processor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//ドキュメントに関するクラス
public class Document extends Node {
    public String characterSet;
    public String compatMode;
    public String contentType;
    public String doctype;
    public int documentURI;
    public boolean hidden;
    public DOMImplementation implementation;
    public String selectedStyleSheetSet;
    //public DocumentTimeline timeline;
    //public int undoManager;
    public String visibilityState;
    public String[] activeElementSelector;//Element?
    public String cookies;
    public String dir;
    public boolean designMode;
    public String domain;
    public String lastModified;
    public String location;//fromJsonできない原因
    public HTMLCollection plugins;
    public String readyState;
    public String referrer;
    public String title;
    public String URL;

    Document() {
        super();
    }

    //Document情報の更新
    public void update() {
        //親クラスのアップデート
        super.update();
        //追加情報をgetterマップに追加
        getter.put("characterSet", characterSet);
        getter.put("compatMode", compatMode);
        getter.put("contentType", contentType);
        getter.put("doctype", doctype);
        getter.put("documentURI", documentURI);
        getter.put("hidden", hidden);
        //DOMImplementation implementation;
        getter.put("selectedStyleSheetSet", selectedStyleSheetSet);
        //DocumentTimeline timeline;
        //int undoManager;
        getter.put("visibilityState", visibilityState);
        //String[] activeElementSelector;
        getter.put("cookies", cookies);
        getter.put("dir", dir);
        getter.put("designMode", designMode);
        getter.put("domain", domain);
        getter.put("lastModified", lastModified);
        getter.put("location", location);
        //HTMLCollection plugins;
        getter.put("readyState", readyState);
        getter.put("referrer", referrer);
        getter.put("title", title);
        getter.put("URL", URL);
    }

    //Documentと表示???
    public String accept(Processor pro) {
        pro.process(this);
        return null;
    }

    //MySQLデータベースにDocument情報を挿入
    public int Insert(int logID, Connection conn, PreparedStatement ps, ResultSet rs) throws SQLException {
        //親イベントのIDを取得
        int parentID = super.Insert(logID, conn, ps, rs);
        //SQL文を作成
        String sql = "INSERT INTO document(ref, characterSet, compatMode, contentType, doctype, documentURI, hidden, selectedStyleSheetSet, visibilityState, cookies, dir, designMode, domain, lastModified, location, readyState, referrer, title, URL)" +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        //準備されたステートメントを作成
        ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

        //パラメータの設定
        ps.setInt(1, parentID);
        ps.setString(2, characterSet);
        ps.setString(3, compatMode);
        ps.setString(4, contentType);
        ps.setString(5, doctype);
        ps.setInt(6, documentURI);
        ps.setBoolean(7, hidden);
        ps.setString(8, selectedStyleSheetSet);
        ps.setString(9, visibilityState);
        ps.setString(10, cookies);
        ps.setString(11, dir);
        ps.setBoolean(12, designMode);
        ps.setString(13, domain);
        ps.setString(14, lastModified);
        ps.setString(15, location);
        ps.setString(16, readyState);
        ps.setString(17, referrer);
        ps.setString(18, title);
        ps.setString(19, URL);

        //INSERTを実行
        ps.executeUpdate();

        //挿入されたレコードのキーを取得
        int childID = -1;
        rs = ps.getGeneratedKeys();
        while (rs.next()) {
            childID = rs.getInt(1); //挿入されたレコードのIDを取得
        }

        //取得したレコードIDを返す
        return childID;
    }

    //SQLiteデータベースにDocument情報を挿入
    public int sqliteInsert(int logID, Connection connection) throws SQLException {
        //親ノードのIDを取得
        int parentID = super.sqliteInsert(logID, connection);
        int childID = -1;
        //System.out.println("1");

        //Documentテーブルにデータを挿入するSQL文
        String sql = "insert into Document(ref, characterSet, compatMode, contentType, doctype, documentURI, hidden, selectedStyleSheetSet, visibilityState, cookies, dir, designMode, domain, lastModified, location, readyState, referrer, title, URL) "
                + "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            //パラメータの設定
            ps.setInt(1, parentID);
            ps.setString(2, characterSet);
            ps.setString(3, compatMode);
            ps.setString(4, contentType);
            ps.setString(5, doctype);
            ps.setInt(6, documentURI);
            ps.setBoolean(7, hidden);
            ps.setString(8, selectedStyleSheetSet);
            ps.setString(9, visibilityState);
            ps.setString(10, cookies);
            ps.setString(11, dir);
            ps.setBoolean(12, designMode);
            ps.setString(13, domain);
            ps.setString(14, lastModified);
            ps.setString(15, location);
            ps.setString(16, readyState);
            ps.setString(17, referrer);
            ps.setString(18, title);
            ps.setString(19, URL);

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
}
