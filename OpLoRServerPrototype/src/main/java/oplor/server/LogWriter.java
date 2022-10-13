package oplor.server;

import org.sqlite.SQLiteConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.LinkedBlockingQueue;

public class LogWriter extends Thread {
    private final LinkedBlockingQueue<Log> logs;
    private final Properties properties;

    public LogWriter(LinkedBlockingQueue<Log> logs) {
        this.logs = logs;
        SQLiteConfig sqLiteConfig = new SQLiteConfig();
        sqLiteConfig.setSynchronous(SQLiteConfig.SynchronousMode.NORMAL);
        sqLiteConfig.setJournalMode(SQLiteConfig.JournalMode.WAL);
        this.properties = sqLiteConfig.toProperties();
    }

    public void run() {
        while (true) {
            int logID = -1;
            try {
                Class.forName("org.sqlite.JDBC");
                //try (Connection connection = DriverManager.getConnection("jdbc:sqlite:C:/Users/ikeda/Desktop/database/testSyudou.db", this.properties)) //開発環境
                try (Connection connection = DriverManager.getConnection("jdbc:sqlite:/usr/local/tomcat/db/test.db", this.properties)) //Docker
                {
                    connection.setAutoCommit(false);
                    while (logs.size() != 0) {
                        Log log = logs.poll();
                        logID = log.sqliteInsert(connection);
                        System.out.println("EventType: " + log.EventType + " NodeType: " + log.NodeType);
                        log.event.sqliteInsert(logID, connection);
                        log.node.sqliteInsert(logID, connection);
                        System.out.println("SQLite size:" + logs.size());
                    }
                    connection.commit();
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
