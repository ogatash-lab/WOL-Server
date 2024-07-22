package oplor.server.node;

import oplor.server.DOMTokenList;
import oplor.server.NameNodeMap;
import oplor.server.Processor;
import oplor.server.ShadowRoot;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//HTML要素を表すクラス
public class Element extends Node {
    public String selector;//型はElementかもしれない。ログを見る限りStringで格納できそうであるけれど
    public HTMLSlotElement assignedSlot;
    public NameNodeMap attributes;
    public DOMTokenList classList;
    public String className;
    public String clientHeight;
    public int clientLeft;
    public int clientTop;
    public String computedName;
    public String computedRole;
    public String id;
    public String innerHTML;
    public String localName;
    public String namespaceURI;
    public String outerHTML;
    public String prefix;
    public int scrollHeight;
    public int scrollWidth;
    public ShadowRoot shadowRoot;
    public String slot;
    public String tagName;
    //public int undoManager;
    public boolean undoScope;

    //Elementの更新
    public void update() {
        //親クラスのアップデート
        super.update();
        //追加情報をgetterマップに追加
        getter.put("selector", selector);
        //HTMLSlotElement assignedSlot;
        //DOMTokenList classList;
        getter.put("className", className);
        getter.put("classHeight", clientHeight);
        getter.put("clientLeft", clientLeft);
        getter.put("clientTop", clientTop);
        getter.put("computedName", computedName);
        getter.put("computedRole", computedRole);
        getter.put("id", id);
        getter.put("innerHTML", innerHTML);
        getter.put("localName", localName);
        getter.put("namespaceURI", namespaceURI);
        getter.put("outerHTML", outerHTML);
        getter.put("prefix", prefix);
        getter.put("scrollHeight", scrollHeight);
        getter.put("scrollWidth", scrollWidth);
        getter.put("slot", slot);
        getter.put("tagName", tagName);
        //getter.put("undoManager", undoManager);
        getter.put("undoScope", undoScope);
    }
    /*
    public int Insert(int logID, Connection conn, PreparedStatement ps, ResultSet rs) throws SQLException {
        int parentID=super.Insert(logID, conn, ps, rs);
        //INSERT
        String sql="INSERT INTO element(ref, clientHeight, clientLeft, clientTop, computedName, computedRole, id, localName, namespaceURI, prefix, scrollHeight, scrollWidth, slot, tagName, undoScope)" +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        ps=conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setInt(1, parentID);
        ps.setString(2, clientHeight);
        ps.setInt(3, clientLeft);
        ps.setInt(4, clientTop);
        ps.setString(5, computedName);
        ps.setString(6, computedRole);
        ps.setString(7, id);
        ps.setString(8, localName);
        ps.setString(9, namespaceURI);
        ps.setString(10, prefix);
        ps.setInt(11, scrollHeight);
        ps.setInt(12, scrollWidth);
        ps.setString(13, slot);
        ps.setString(14, tagName);
        ps.setBoolean(15, undoScope);
        //ISNERTを実行する
        ps.executeUpdate();
        //次の子クラスへと紐づけるためのID取得
        int childID=-1;
        rs=ps.getGeneratedKeys();
        while(rs.next()) {
            childID = rs.getInt(1);
        }
        //innerHTMLに格納
        String sql2="INSERT INTO innerhtml(element_id, innerHTML) VALUES(?, ?)";
        ps=conn.prepareStatement(sql2, PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setInt(1, childID);
        ps.setString(2, innerHTML);
        ps.executeUpdate();
        //outerHTMLに格納
        String sql3="INSERT INTO outerhtml(element_id, outerHTML) VALUES(?, ?)";
        ps=conn.prepareStatement(sql3, PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setInt(1, childID);
        ps.setString(2, outerHTML);
        ps.executeUpdate();
        //classNameに格納
        String sql4="INSERT INTO classname(element_id, className) VALUES(?, ?)";
        ps=conn.prepareStatement(sql4, PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setInt(1, childID);
        ps.setString(2, className);
        ps.executeUpdate();
        return childID;
    }*/

    //SQLiteデータベースにElement情報を挿入
    public int sqliteInsert(int logID, Connection connection) throws SQLException {
        //親ノードのIDを取得
        int parentID = super.sqliteInsert(logID, connection);
        int childID = -1;
        //System.out.println("1");

        //Elementテーブルにデータを挿入するSQL文
        String sql = "insert into Element(ref, className, clientHeight, clientLeft, clientTop, computedName, computedRole, id, innerHTML, localName, namespaceURI, outerHTML, prefix, scrollHeight, scrollWidth, slot, tagName, undoScope)" +
                "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            //パラメータの設定
            ps.setInt(1, parentID);
            ps.setString(2, className);
            ps.setString(3, clientHeight);
            ps.setInt(4, clientLeft);
            ps.setInt(5, clientTop);
            ps.setString(6, computedName);
            ps.setString(7, computedRole);
            ps.setString(8, id);
            ps.setString(9, innerHTML);
            ps.setString(10, localName);
            ps.setString(11, namespaceURI);
            ps.setString(12, outerHTML);
            ps.setString(13, prefix);
            ps.setInt(14, scrollHeight);
            ps.setInt(15, scrollWidth);
            ps.setString(16, slot);
            ps.setString(17, tagName);
            ps.setBoolean(18, undoScope);

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
