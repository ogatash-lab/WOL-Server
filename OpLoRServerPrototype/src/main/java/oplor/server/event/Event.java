package oplor.server.event;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class Event {
    public Map<String, Object> getter = new HashMap<>();

    public boolean bubbles;
    public boolean cancelable;
    public boolean composed;
    public boolean defaultPrevented;
    public int eventPhase;
    public double timeStamp;
    public String type;
    public boolean isTrusted;

    public String absTime;

    public Event() {
        this.bubbles = false;
        this.cancelable = false;
        this.composed = false;
        this.defaultPrevented = false;
        this.eventPhase = 0;
        this.timeStamp = 0.0;
        this.type = "?";
        this.isTrusted = false;
        this.absTime = "?";
    }

    //Map内のオブジェクトをアップデートする
    public void update() {
        getter.put("bubbles", bubbles);
        getter.put("cancelable", cancelable);
        getter.put("composed", composed);
        getter.put("defaultPrevented", defaultPrevented);
        getter.put("eventPhase", eventPhase);
        getter.put("timeStamp", timeStamp);
        getter.put("type", type);
        getter.put("isTrusted", isTrusted);
    }

    public void send(int logID) {
        System.out.println("MySQLTEST---START--------------------------------------");
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
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        absTime = ts.toString(); //2014-02-21 15:33:15.123456789
        //System.out.println("absTime:"+absTime);
        String sql = "INSERT INTO event(ref, bubbles, cancelable, composed, defaultPrevented, eventPhase, timeStamp, type, isTrusted, absTime)VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setInt(1, logID);
        ps.setBoolean(2, bubbles);
        ps.setBoolean(3, cancelable);
        ps.setBoolean(4, composed);
        ps.setBoolean(5, defaultPrevented);
        ps.setInt(6, eventPhase);
        ps.setDouble(7, timeStamp);
        ps.setString(8, type);
        ps.setBoolean(9, isTrusted);
        ps.setString(10, absTime);
        //ISNERTを実行する
        ps.executeUpdate();
        //次の子クラスへと紐づけるためのID取得
        rs = ps.getGeneratedKeys();
        while (rs.next()) {
            childID = rs.getInt(1);
        }
        return childID;
    }

    public int sqliteInsert(int logID, Connection connection) throws SQLException {
        int childID = -1;
        //System.out.println("1");
        String sql = "insert into Event(ref, bubbles, cancelable, composed, defaultPrevented, eventPhase, timeStamp, type, isTrusted, absTime)values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, logID);
            ps.setBoolean(2, bubbles);
            ps.setBoolean(3, cancelable);
            ps.setBoolean(4, composed);
            ps.setBoolean(5, defaultPrevented);
            ps.setInt(6, eventPhase);
            ps.setDouble(7, timeStamp);
            ps.setString(8, type);
            ps.setBoolean(9, isTrusted);
            ps.setString(10, absTime);
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
}
