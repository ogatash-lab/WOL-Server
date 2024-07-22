package oplor.server;

import org.sqlite.SQLiteConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.LinkedBlockingQueue;

//ログを非同期に書き込むクラス
public class LogWriter extends Thread {
    private final LinkedBlockingQueue<Log> logs;
    private final Properties properties;

    public LogWriter(LinkedBlockingQueue<Log> logs) {
        this.logs = logs;
        //SQLiteの設定
        SQLiteConfig sqLiteConfig = new SQLiteConfig();
        sqLiteConfig.setSynchronous(SQLiteConfig.SynchronousMode.NORMAL);
        sqLiteConfig.setJournalMode(SQLiteConfig.JournalMode.WAL);
        //SQLiteの設定をpropertiesオブジェクトに変換して保存
        this.properties = sqLiteConfig.toProperties();
    }

    //スレッドの実行(ログを書き込む)
    public void run() {
        //無限ループでログ処理を行う
        while (true) {
            //ログIDの初期化
            int logID = -1;
            try {
                //SQLite JDBCドライバのロード
                Class.forName("org.sqlite.JDBC");
                //try (Connection connection = DriverManager.getConnection("jdbc:sqlite:C:/Users/ikeda/Desktop/database/testSyudou.db", this.properties)) //開発環境

                //SQLiteデータベースの接続
                try (Connection connection = DriverManager.getConnection("jdbc:sqlite:/usr/local/tomcat/db/test.db", this.properties)) //Docker
                {
                    //自動コミットの無効化
                    connection.setAutoCommit(false);
                    //ログキューが空出ない限り，ログ処理を行う
                    while (logs.size() != 0) {
                        //キューからログを取り出す
                        Log log = logs.poll();
                        //ログをデータベースに挿入し，ログIDを取得
                        logID = log.sqliteInsert(connection);
                        //コンソールにログの内容を出力(デバック用)
                        System.out.println("EventType: " + log.EventType + " NodeType: " + log.NodeType);
                        //イベント情報とノード情報をデータベースに挿入
                        log.event.sqliteInsert(logID, connection);
                        log.node.sqliteInsert(logID, connection);
                        //ログキューのサイズを表示(デバック用)
                        System.out.println("SQLite size:" + logs.size());
                    }
                    //トランザクションをコミット
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
