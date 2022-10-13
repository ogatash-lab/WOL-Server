package oplor.server;

import oplor.server.event.Event;
import oplor.server.node.Node;

import java.sql.*;

public class Log {
    public Event event;
    public Node node;

    String EventType;
    String NodeType;
    String userID;

    public static final String baseURI = "baseURI";

    Log(String eventType, String nodeType, Event vevent, Node vnode, String vuserID) {
        EventType = eventType;
        NodeType = nodeType;
        event = vevent;
        node = vnode;
        userID = vuserID;
    }
    /*
    public void send() {
        Connection conn = null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        //実行するSQL文
        try {
            String path="jdbc:mysql://localhost:3306/test?autoReconnect=true&useSSL=false";  //接続パス
            String id="root";    //ログインID
            String pw="Usagi3.0807";  //ログインパスワード
            Class.forName("com.mysql.jdbc.Driver");//JDBCドライバをロード
            conn = DriverManager.getConnection(path, id, pw);//コネクションの作成

            //INSERT MySQL
            int logID=Insert(conn, ps, rs);
            event.Insert(logID, conn, ps, rs);
            node.Insert(logID, conn, ps, rs);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally{
            try{
                if (conn != null){
                    conn.close();
                }
            }
            catch (SQLException e){
                e.printStackTrace();
            }
        }
    }*/

    //MySQL
    public int Insert(Connection conn, PreparedStatement ps, ResultSet rs) throws SQLException {
        int childID = -1;

        String sql = "INSERT INTO Log(userID, EventType, NodeType)VALUES(?, ?, ?)";
        ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setString(1, userID);
        ps.setString(2, EventType);
        ps.setString(3, NodeType);
        //ISNERTを実行する
        ps.executeUpdate();
        //event, Nodeクラスへと紐づけるためのID取得
        rs = ps.getGeneratedKeys();
        while (rs.next()) {
            childID = rs.getInt(1);
        }
        return childID;
    }

    //SQLite

    public void sqliteSend() {
        Connection connection = null;
        Statement statement = null;
        PreparedStatement ps = null;
        int logID = -1;
        try {
            Class.forName("org.sqlite.JDBC");
            //connection = DriverManager.getConnection("jdbc:sqlite:C:/Users/ikeda/Desktop/database/testSyudou.db"); //開発環境
            connection = DriverManager.getConnection("jdbc:sqlite:/usr/local/tomcat/db/test.db"); //Docker
            statement = connection.createStatement();
            logID = sqliteInsert(connection);
            System.out.println("logID:" + logID);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public int sqliteInsert(Connection connection) throws SQLException {
        int logID = -1;
        //System.out.println("1");
        String sql = "insert into Log(userID, EventType, NodeType) values(?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, userID);
            ps.setString(2, EventType);
            ps.setString(3, NodeType);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            while (rs.next()) {
                logID = rs.getInt(1);
            }
        }
        return logID;
    }

    public Object getEvent(String str) {
        return event.getter.get(str);
    }

    public Object getNode(String str) {
        return node.getter.get(str);
    }
}