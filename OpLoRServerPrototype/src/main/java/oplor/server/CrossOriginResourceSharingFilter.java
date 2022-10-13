package oplor.server;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "CrossOriginResourceSharingFilter", urlPatterns = {"/*"})
public class CrossOriginResourceSharingFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (response instanceof HttpServletResponse) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;

            httpResponse.addHeader("Access-Control-Allow-Origin", "http://localhost:63342");

            //httpResponse.addHeader("Access-Control-Allow-Origin", "https://www.rakuten.co.jp");
            //httpResponse.addHeader("Access-Control-Allow-Origin", "https://www.dnp.co.jp");
            //httpResponse.addHeader("Access-Control-Allow-Origin", "https://anet.akita-u.ac.jp");
            //httpResponse.addHeader("Access-Control-Allow-Origin", "http://localhost:8080/standard_event.json");
            //httpResponse.addHeader("Access-Control-Allow-Origin", "https://campus-3.shinshu-u.ac.jp");
            //httpResponse.addHeader("Access-Control-Allow-Origin", "https://www.amazon.co.jp");

            httpResponse.addHeader("Access-Control-Allow-Credentials", "true");
            httpResponse.addHeader("Access-Control-Allow-Methods", "GET, HEAD, OPTIONS, POST, PUT");
            httpResponse.addHeader("Access-Control-Allow-Headers", "Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
            httpResponse.setHeader("Access-Control-Max-Age", "-1");

        }
        chain.doFilter(request, response);
    }

    public void init(FilterConfig config) throws ServletException {
    }

}
