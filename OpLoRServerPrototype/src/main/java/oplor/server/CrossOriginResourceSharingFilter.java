package oplor.server;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// CORSを処理するためのフィルター
@WebFilter(filterName = "CrossOriginResourceSharingFilter", urlPatterns = {"/*"})
public class CrossOriginResourceSharingFilter implements Filter {
    // 定数としてCORS設定値を管理
    private static final String ALLOWED_ORIGIN = "http://localhost:5000";   // 許可するオリジン
    private static final String ALLOWED_METHODS = "GET, HEAD, OPTIONS, POST, PUT";  // 許可するHTTPメソッド
    private static final String ALLOWED_HEADERS = "Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers";  // 許可するリクエストヘッダー
    private static final String ALLOW_CREDENTIALS = "true"; // 認証情報の共有を許可するか
    private static final String MAX_AGE = "-1"; // プリフライトリクエストのキャッシュ時間

    // フィルター処理
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // レスポンスがHTTPの場合にのみCORSヘッダーを設定
        if (response instanceof HttpServletResponse) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            setCORSHeaders(httpResponse);   // ヘッダーを設定するカスタムメソッドを呼び出し
        }
        // 次のフィルターまたはリソースに処理を移譲
        chain.doFilter(request, response);
    }

    // フィルター初期化(必須)
    @Override
    public void init(FilterConfig config) throws ServletException {
    }

    // フィルター破棄(必須)
    @Override
    public void destroy() {
    }

    // CORSヘッダーを設定するメソッド
    private void setCORSHeaders(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", ALLOWED_ORIGIN);
        response.setHeader("Access-Control-Allow-Credentials", ALLOW_CREDENTIALS);
        response.setHeader("Access-Control-Allow-Methods", ALLOWED_METHODS);
        response.setHeader("Access-Control-Allow-Headers", ALLOWED_HEADERS);
        response.setHeader("Access-Control-Max-Age", MAX_AGE);
    }
}
