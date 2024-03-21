# WOL-Server
Web Operation Logger for Server (Maven + Tomcat)
## 導入
### 準備
事前にインストールするツール
- Docker
- sqlite3
### Githubリポジトリをクローン
githubリポジトリをクローン
```
git clone https://github.com/ogatash-lab/WOL-Server.git
```
### ツールを構築
`/WOL-Server`ディレクトリに移動し，以下のコマンドでDockerファイルでDockerイメージを構築
```
docker build ./ -t 「イメージの名前(イメージ名)」

例
docker build ./ -t woltool
```
その後，作成したDockerイメージを用いてコンテナを作成
```
docker run --name 「任意の名前(コンテナ名)」 -p 8080:8080 --mount type=bind,src=「ホストのデータベースファイル保存先(※1)」,dst=/usr/local/tomcat/db -d 「イメージ名」:latest

例
docker run --name woltool -p 8080:8080 --mount type=bind,src=C:\Users\name\Desktop\workspace\testserv,dst=/usr/local/tomcat/db -d woltool:latest
```
(※1)保存先のディレクトリの中身は空にしておくこと
