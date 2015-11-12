package cmpt305.myfunctionalsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;
import java.net.URL;
import java.util.Scanner;
import java.net.HttpURLConnection;

public class MainActivity extends MyMenu {

    //public static Intent intent;
    private final String TAG = "myFunctional System";
    private final String uname = "user";
    private final String password = "password";
    private List<Course> courses;

    private EditText usernameEditText;
    private EditText passwordEditText;

    Thread thread = new Thread(new Runnable() {
        public void run() {
            try {
                URL serverUrl = new URL("http://159.203.29.177/terms");
                HttpURLConnection urlConnection = (HttpURLConnection) serverUrl.openConnection();

                // Indicate that we want to write to the HTTP request body
                urlConnection.setRequestMethod("GET");

                // Reading from the HTTP response body
                Scanner httpResponseScanner = new Scanner(urlConnection.getInputStream());
                while (httpResponseScanner.hasNextLine()) {
                    System.out.println(httpResponseScanner.nextLine());
                }
                httpResponseScanner.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        courses = new ArrayList<>();
        Log.d(TAG, "onCreate() called");
    }

    public void launchLogin(View view){
        usernameEditText = (EditText) findViewById(R.id.unameText);
        passwordEditText = (EditText) findViewById(R.id.passwordText);

        thread.start();

        if (usernameEditText.getText().toString().compareTo(uname) == 0
                && passwordEditText.getText().toString().compareTo(password) == 0) {
            Intent intent = new Intent(this, MyAgenda.class);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_my_class_search:
                launchActivity(Department.class);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void getAllCourseInformation(){

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
