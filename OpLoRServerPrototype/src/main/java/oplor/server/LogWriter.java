package oplor.server;

import oplor.server.event.MouseEvent;
import org.sqlite.SQLiteConfig;

import java.sql.*;
import java.util.Properties;
import java.util.concurrent.LinkedBlockingQueue;

import oplor.server.event.*;
import oplor.server.node.*;

// ログを非同期に書き込むクラス(スレッドとして動作)
public class LogWriter extends Thread {
    private final LinkedBlockingQueue<Log> logs;    // 非同期処理のためのログを保持するキュー
    private final Properties properties;        // SQLiteの設定をプロパティ

    // コンストラクタ：ログキューとSQLiteの設定を初期化
    public LogWriter(LinkedBlockingQueue<Log> logs) {
        this.logs = logs;
        // SQLiteの設定を構成
        SQLiteConfig sqLiteConfig = new SQLiteConfig();
        sqLiteConfig.setSynchronous(SQLiteConfig.SynchronousMode.NORMAL);   // 同期モードを設定
        sqLiteConfig.setJournalMode(SQLiteConfig.JournalMode.WAL);  // Write-Ahead Loggingモードを設定
        // SQLiteの設定をpropertiesオブジェクトに変換して保存
        this.properties = sqLiteConfig.toProperties();
    }

    // スレッドのメイン処理：ログを書き込む
    public void run() {
        // 無限ループでログ処理を行う
        while (true) {
            // ログIDの初期化
            int logID = -1;
            try {
                // SQLite JDBCドライバのロード
                Class.forName("org.sqlite.JDBC");

                // SQLiteデータベースに接続
                try (Connection connection = DriverManager.getConnection("jdbc:sqlite:/usr/local/tomcat/db/test.db", this.properties)){
                    // 自動コミットの無効化
                    connection.setAutoCommit(false);

                    // ログキューが空でない限り，ログを処理
                    while (logs.size() != 0) {
                        Log log = logs.poll();  // キューからログを取り出す
                        logID = log.sqliteInsert(connection);   // ログをデータベースに挿入し，ログIDを取得

                        // イベント情報とノード情報を取得
                        Event event = log.getEvent();
                        Node node = log.getNode();

                        // デバック：コンソールにログの内容を出力
                        System.out.println("--LogWriter--");
                        System.out.println(log);

                        // MouseEventの場合，座標を出力(pageX, pageY)
                        if (event instanceof MouseEvent) {
                            MouseEvent mouseEvent = (MouseEvent) event;
                            System.out.println("logID: " + logID + " pageX: " + mouseEvent.getPageX() + ", pageY: " + mouseEvent.getPageY());
                        }

                        // デバック：ログキューのサイズを表示
                        System.out.println("SQLite size:" + logs.size());
                        System.out.println("--Fin(LogWriter)--");
                        
                        // イベント情報とノード情報をデータベースに挿入
                        event.sqliteInsert(logID, connection);
                        node.sqliteInsert(logID, connection);
                    }
                    // トランザクションをコミット
                    connection.commit();
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();    // JDBCドライバが見つからない場合のエラーを出力
            } catch (SQLException e) {
                e.printStackTrace();    // SQLite接続やクエリ実行中のエラーを出力
            }
        }
    }
}
