package com.example.OpLoRServerPrototype;

import java.io.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

// サーブレットのアノテーションを使用して、/hello-servlet URLパターンにマッピング
@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message; // 表示するメッセージを格納する変数

    // サーブレットの初期化メソッド
    // サーブレットが最初にインスタンス化されたときに呼ばれる
    public void init() {
        // メッセージを初期化
        message = "hello World!";
    }

    // HTTP GETリクエストが来たときに呼ばれるメソッド
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // レスポンスのコンテンツタイプをHTMLに設定
        response.setContentType("text/html");

        // レスポンスの出力ストリームを取得
        PrintWriter out = response.getWriter();

        // HTMLコンテンツを出力
        out.println("<html><body>");
        // メッセージを表示
        out.println("<h1>" + message + "</h1>");
        out.println("</body></html>");
    }

    // サーブレットが破棄されるときに呼ばれるメソッド
    public void destroy() {
    }
}