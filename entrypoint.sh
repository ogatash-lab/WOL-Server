# DB名（拡張子なし）を環境変数で指定、デフォルトは "test"
DB_BASE_NAME="${DB_FILE_BASE:-test}"
DB_PATH="/usr/local/tomcat/db/${DB_BASE_NAME}.db"

# DB_PATHを環境変数として設定
export DB_PATH

/usr/bin/sqlite3 "$DB_PATH" < /usr/local/tomcat/dbfile/CreateLogTable.sql
/usr/local/tomcat/bin/catalina.sh run