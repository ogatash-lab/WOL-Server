package oplor.server.node;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//<slot>要素に関するクラス
public class HTMLSlotElement {
    public String name;

    //データベースにHTMLSlotElementを挿入
    public int Insert(String logID, Connection conn, PreparedStatement ps, ResultSet rs) throws SQLException {
        //int parentID=super.Insert(logID, conn, ps, rs);
        //INSERT
        //htmlslotelementテーブルにデータを挿入するSQL文
        String sql = "INSERT INTO htmlslotelement(name)VALUES(?)";
        //準備されたステートメントを作成
        ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        //パラメータを設定
        ps.setString(1, name);
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

    //SQLiteデータベースにHTMLSlotElement情報を挿入
    public int sqliteInsert(int logID, Connection connection) throws SQLException {
        //int parentID=super.sqliteInsert(connection);
        int childID = -1;
        //HTMLSlotElementテーブルにデータを挿入するSQL文
        String sql = "insert into HTMLSlotElement(name)value(?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            //パラメータの設定
            ps.setString(1, name);
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
