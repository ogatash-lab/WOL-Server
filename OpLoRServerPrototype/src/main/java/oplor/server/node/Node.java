package oplor.server.node;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

//HTML DOM ノードを表す基底クラス
public /*abstract*/ class Node {
    //イベント情報を格納するMap
    public Map<String, Object> getter = new HashMap<>();

    //Nodeのプロパティ(内容は以下のwebサイト参照：https://developer.mozilla.org/ja/docs/Web/API/Node/baseURI(access:2024/6/3))
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

    //ノード情報を更新
    public void update() {
        getter.put("baseURI", baseURI);
        getter.put("innerText", innerText);
        getter.put("nodeName", nodeName);
        getter.put("nodeValue", nodeValue);
        getter.put("textContent", textContent);
    }

    //ノード情報をMySQLに送信
    public void send(int logID) {
        //System.out.println("MySQLTEST---START--------------------------------------");
        //データベースに接続
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String path = "jdbc:mysql://localhost:3306/test?autoReconnect=true&useSSL=false";  //接続パス
            String id = "root";    //ログインID
            String pw = "Usagi3.0807";  //ログインパスワード
            Class.forName("com.mysql.jdbc.Driver");//JDBCドライバをロード
            conn = DriverManager.getConnection(path, id, pw);//コネクションの作成
            //NodeデータをMySQLに挿入
            Insert(logID, conn, ps, rs);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //接続をクローズ
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //MySQLデータベースにノード情報を挿入
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

        //SQL文を作成
        String sql = "INSERT INTO node(ref, baseURI, innerText, nodeName, nodeValue)VALUES(?, ?, ?, ?, ?)";

        //準備されたステートメントを作成
        ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

        //パラメータの設定
        ps.setInt(1, logID);
        ps.setString(2, baseURI);
        ps.setString(3, innerText);
        ps.setString(4, nodeName);
        ps.setString(5, nodeValue);

        //INSERTを実行する
        ps.executeUpdate();

        //挿入されたレコードのキーを取得
        rs = ps.getGeneratedKeys();
        while (rs.next()) {
            childID = rs.getInt(1); //挿入されたレコードのIDを取得
        }

        //textContentを格納
        String sql2 = "INSERT INTO textcontent(node_id, textContent) VALUES(?, ?)";
        ps = conn.prepareStatement(sql2, PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setInt(1, childID);
        ps.setString(2, textContent);
        ps.executeUpdate();
        return childID;
    }

    //ノード情報をSQLiteに挿入
    public int sqliteInsert(int logID, Connection connection) throws SQLException {
        int childID = -1;
        //System.out.println("1");

        //SQL文を作成
        String sql = "insert into Node(ref, baseURI, innerText, nodeName, nodeValue)values(?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, logID);
            ps.setString(2, baseURI);
            ps.setString(3, innerText);
            ps.setString(4, nodeName);
            ps.setString(5, nodeValue);

            //SQL文の実行
            ps.executeUpdate();

            //挿入されたレコードのキーを取得
            ResultSet rs = ps.getGeneratedKeys();
            while (rs.next()) {
                childID = rs.getInt(1); //挿入したレコードのIDを取得
            }
        }

        //取得したレコードIDを返す
        return childID;
    }

    //指定したキーに対するオブジェクトを返す
    public Object get(String str) {
        return getter.get(str);
    }

    //public abstract String accept(Processor pro);
}
