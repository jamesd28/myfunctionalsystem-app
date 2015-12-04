package cmpt305.myfunctionalsystem;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DescriptionScreen extends MenuToolbar {

    private String[] courseInfo = {
            "course name goes here",
            "Description\nA very insightful description of the course goes here",
    };
    private int courseID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        courseInfo[0] = getIntent().getStringExtra("course");
        courseInfo[1] = "Description: \n\n\t\t" + getIntent().getStringExtra("description");

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            courseID = extras.getInt("id");
        }

        populateDescription();
    }


    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void populateDescription() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.coursedescriptionview, courseInfo);

        ListView list = (ListView) findViewById(R.id.classDescription);
        list.setAdapter(adapter);
    }


    public void viewClassSections(View view) {
        Intent intent = new Intent(this, SectionsScreen.class);
        intent.putExtra("id", courseID);
        intent.putExtra("name", courseInfo[0]);
        startActivity(intent);
    }

    public void addToPlanner(View view) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject json = new JSONObject();
                    String post = json.toString();
                    URL myFunctionalServer = new URL("http://159.203.29.177/planner/add/"+courseID);
                    HttpURLConnection connection = (HttpURLConnection) myFunctionalServer.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);
                    connection.setFixedLengthStreamingMode(post.getBytes().length);
                    connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
                    connection.connect();

                    DataOutputStream reqStream = new DataOutputStream(connection.getOutputStream());
                    reqStream.writeBytes(post);
                    reqStream.flush();

                    Toast.makeText(getApplicationContext(), courseInfo[0] + " has been added to your planner", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
        /* Waits until Thread is Done */
        while(thread.isAlive()) {};
    }
}
