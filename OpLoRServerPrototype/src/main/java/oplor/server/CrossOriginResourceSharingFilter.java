package oplor.server;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//CORSを処理するためのフィルターに関するクラス
//フィルター設定
@WebFilter(filterName = "CrossOriginResourceSharingFilter", urlPatterns = {"/*"})
public class CrossOriginResourceSharingFilter implements Filter {
    //フィルター破棄
    public void destroy() {
    }

    //フィルター処理
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (response instanceof HttpServletResponse) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;

            //CORSポリシーを設定するヘッダーを追加

            //httpResponse.addHeader("Access-Control-Allow-Origin", "http://localhost:63342");
            //httpResponse.addHeader("Access-Control-Allow-Origin", "https://www.rakuten.co.jp");
            //httpResponse.addHeader("Access-Control-Allow-Origin", "https://www.dnp.co.jp");
            //httpResponse.addHeader("Access-Control-Allow-Origin", "https://anet.akita-u.ac.jp");
            //httpResponse.addHeader("Access-Control-Allow-Origin", "http://localhost:8080/standard_event.json");
            //httpResponse.addHeader("Access-Control-Allow-Origin", "https://campus-3.shinshu-u.ac.jp");
            //httpResponse.addHeader("Access-Control-Allow-Origin", "https://www.amazon.co.jp");
            httpResponse.addHeader("Access-Control-Allow-Origin", "http://127.0.0.1:5000"); //許可するオリジンを設定
            //httpResponse.addHeader("Access-Control-Allow-Origin", "http://127.0.0.1:3000");
            //httpResponse.addHeader("Access-Control-Allow-Origin", "http://192.168.56.1:5000/");

            httpResponse.addHeader("Access-Control-Allow-Credentials", "true"); //cookie等を許可
            httpResponse.addHeader("Access-Control-Allow-Methods", "GET, HEAD, OPTIONS, POST, PUT");    //許可するHTTPメソッドを設定
            httpResponse.addHeader("Access-Control-Allow-Headers", "Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");    //許可するヘッダーを設定
            httpResponse.setHeader("Access-Control-Max-Age", "-1"); //プリフライトリクエストのキャッシュ時間を設定
        }
        chain.doFilter(request, response);  //次のフィルターまたはリソースに処理を移譲
    }

    //フィルター初期化
    public void init(FilterConfig config) throws ServletException {
    }

}
