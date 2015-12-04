package cmpt305.myfunctionalsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import org.json.JSONObject;

import java.io.DataOutputStream;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.List;

import java.net.URL;
import java.net.HttpURLConnection;
import java.net.CookieManager;
import java.net.CookieHandler;
import java.util.Map;

public class LoginScreen extends MenuToolbar {
    private final String TAG = "myFunctional System";
    public static String uname = "";
    public static String password = "";

    private EditText usernameEditText;
    private EditText passwordEditText;

    static final String COOKIES_HEADER = "Set-Cookie";
    Map<String, List<String>> headerFields;
    List<String> cookiesHeader;

    public static boolean validated = false;

    private void login()
    {
        if(validated) {
            Intent intent = new Intent(this, HomeScreen.class);
            startActivity(intent);
        }
    }

    private Thread thread = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Log.d(TAG, "onCreate() called");

        findViewById(R.id.unameText).requestFocus();

        CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
    }

    private void makeThread() {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject json = new JSONObject().put("password", passwordEditText.getText().toString()).put("netid", usernameEditText.getText().toString());
                    String post = json.toString();
                    Log.d(TAG, post);
                    URL myFunctionalServer = new URL("http://159.203.29.177/auth/login/");
                    HttpURLConnection connection = (HttpURLConnection) myFunctionalServer.openConnection();

//                    CookieManager cookieManager = new CookieManager();
//                    headerFields = connection.getHeaderFields();
//                    cookiesHeader = headerFields.get(COOKIES_HEADER);
//                    if(cookiesHeader != null)
//                    {
//                        for (String cookie : cookiesHeader)
//                        {
//                            cookieManager.getCookieStore().add(null, HttpCookie.parse(cookie).get(0));
//                        }
//                    }

                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);
                    connection.setFixedLengthStreamingMode(post.getBytes().length);
                    connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
                    connection.connect();

                    DataOutputStream reqStream = new DataOutputStream(connection.getOutputStream());
                    reqStream.writeBytes(post);
                    reqStream.flush();

                    try {
                        if (connection.getResponseCode() == 200) {
                            validated = true;
                            uname = usernameEditText.getText().toString();
                            password = passwordEditText.getText().toString();
                        } else {
                            Log.d(TAG, String.format("%d\n", connection.getResponseCode()));
                        }
                        reqStream.close();

                    }
                    catch (java.io.IOException e) {
                        return;
                    }

                    login();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void launchLogin(View view){
        usernameEditText = (EditText) findViewById(R.id.unameText);
        passwordEditText = (EditText) findViewById(R.id.passwordText);

        if(thread == null)
            makeThread();
        try {
            thread.start();
        }
        catch (IllegalThreadStateException e) {
            e.printStackTrace();
            thread.interrupt();
            makeThread();
            thread.start();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_my_class_search:
                launchActivity(DepartmentScreen.class);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    @Override
    protected void onPause(){
        super.onPause();
    }

    @Override
    protected void onStop(){
        super.onStop();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }
}
