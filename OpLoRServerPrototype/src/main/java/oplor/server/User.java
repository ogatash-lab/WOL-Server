package oplor.server;

import javax.net.ssl.HandshakeCompletedEvent;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;
import java.util.UUID;

public class User {
    public HttpSession ses;

    // 現行スレッドの初期値を取得
    /*
    public static ThreadLocal<String> ID = new ThreadLocal<String>(){
        protected String initialValue() {
            UUID uuid = UUID.randomUUID();//uuid.toString
            return uuid.toString();
        }
    };
    */

    User(ServletRequest req) {
    }
}
