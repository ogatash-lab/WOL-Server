if [ $# -ne 1 ]; then
    echo 引数エラー!マウント先を絶対パスで指定してください: $*
    exit 1
fi
docker run --name woltool -p 8080:8080 --mount type=bind,src=$1,dst=/usr/local/tomcat/db -d wol:latest
echo docker started, run on $1
