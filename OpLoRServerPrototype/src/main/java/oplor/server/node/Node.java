package oplor.server.node;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public /*abstract*/ class Node {
    public Map<String, Object> getter = new HashMap<>();

    public String baseURI;
    public String innerText;
    public String nodeName;
    public String nodeValue;
    public String textContent;

    public Node() {
        //this.baseURI="?";
        //this.innerText="?";
        this.nodeName = "?";
        this.nodeValue = "?";
        //this.textContent="?";
    }

    public void update() {
        getter.put("baseURI", baseURI);
        getter.put("innerText", innerText);
        getter.put("nodeName", nodeName);
        getter.put("nodeValue", nodeValue);
        getter.put("textContent", textContent);
    }

    public void send(int logID) {
        //System.out.println("MySQLTEST---START--------------------------------------");
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        //実行するSQL文
        try {
            String path = "jdbc:mysql://localhost:3306/test?autoReconnect=true&useSSL=false";  //接続パス
            String id = "root";    //ログインID
            String pw = "Usagi3.0807";  //ログインパスワード
            Class.forName("com.mysql.jdbc.Driver");//JDBCドライバをロード
            conn = DriverManager.getConnection(path, id, pw);//コネクションの作成
            //INSERT
            Insert(logID, conn, ps, rs);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public int Insert(int logID, Connection conn, PreparedStatement ps, ResultSet rs) throws SQLException {
        int childID = -1;
        //INSERT
        //Calendar
        /*
        Calendar cal=Calendar.getInstance();
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSSSSS");
        absTime = sdf.format(cal.getTime());
        */
        //System.out.println("absTime:"+absTime);
        String sql = "INSERT INTO node(ref, baseURI, innerText, nodeName, nodeValue)VALUES(?, ?, ?, ?, ?)";
        ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setInt(1, logID);
        ps.setString(2, baseURI);
        ps.setString(3, innerText);
        ps.setString(4, nodeName);
        ps.setString(5, nodeValue);
        //ISNERTを実行する
        ps.executeUpdate();
        //次の子クラスへと紐づけるためのID取得
        rs = ps.getGeneratedKeys();
        while (rs.next()) {
            childID = rs.getInt(1);
        }
        //textContent格納
        String sql2 = "INSERT INTO textcontent(node_id, textContent) VALUES(?, ?)";
        ps = conn.prepareStatement(sql2, PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setInt(1, childID);
        ps.setString(2, textContent);
        ps.executeUpdate();
        return childID;
    }

    public int sqliteInsert(int logID, Connection connection) throws SQLException {
        int childID = -1;
        //System.out.println("1");
        String sql = "insert into Node(ref, baseURI, innerText, nodeName, nodeValue)values(?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, logID);
            ps.setString(2, baseURI);
            ps.setString(3, innerText);
            ps.setString(4, nodeName);
            ps.setString(5, nodeValue);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            while (rs.next()) {
                childID = rs.getInt(1);
            }
        }
        return childID;
    }

    public Object get(String str) {
        return getter.get(str);
    }

    //public abstract String accept(Processor pro);
}
