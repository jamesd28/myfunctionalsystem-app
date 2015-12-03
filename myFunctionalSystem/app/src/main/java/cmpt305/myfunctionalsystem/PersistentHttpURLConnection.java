package cmpt305.myfunctionalsystem;

import java.net.HttpURLConnection;
import java.net.URL;

public class PersistentHttpURLConnection {
    private HttpURLConnection singleton;

    public HttpURLConnection getHttpContext(){

        if (singleton == null){
            try {
                URL myFunctionalServer = new URL("http://159.203.29.177/auth/login/");
                singleton = (HttpURLConnection) myFunctionalServer.openConnection();
            } catch (Exception e){
                return null;
            }
        }

        return singleton;
    }
}
