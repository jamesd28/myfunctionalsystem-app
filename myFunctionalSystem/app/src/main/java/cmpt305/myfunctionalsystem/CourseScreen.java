package cmpt305.myfunctionalsystem;

import android.content.Intent;
import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
//import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
//import android.view.Menu;
//import android.view.MenuItem;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class CourseScreen extends MyMenu {

    /*private String[] courseNames = {"ACCT 111\nFinancial Accounting I",
                                    "ACCT 161\nFinancial Accounting II",
                                    "ACCT 164\nPractical Tax Applications",
                                    "ACCT 211\nFinancial Accounting III",
                                    "ACCT 214\nIntegrated Accounting Systems",
                                    "ACCT 215\nQuantitative Decision Support",
                                    "ACCT 218\nManagement Accounting I",
                                    "ACCT 261\nFinancial Accounting IV",
                                    "ACCT 268\nManagement Accounting II",
                                    "ACCT 275\nAccounting Information Systems"}; */

    private String course;
    private ArrayList<String> courseNames = new ArrayList<>();
    private ArrayList<Long> classIDs = new ArrayList<>();
    private HashMap<String, String> descriptions = new HashMap<>();

    Thread resultsThread = new Thread(new Runnable() {
        public void run() {
            try {
                URL serverUrl = new URL("http://159.203.29.177/courses/"+course);
                HttpURLConnection urlConnection = (HttpURLConnection) serverUrl.openConnection();

                // Indicate that we want to write to the HTTP request body
                urlConnection.setRequestMethod("GET");

                // Reading from the HTTP response body
                Scanner httpResponseScanner = new Scanner(urlConnection.getInputStream());
                while (httpResponseScanner.hasNextLine()) {
                    JSONArray jsonQueryResult = new JSONArray(httpResponseScanner.nextLine());
                    for(int i = 0; i < jsonQueryResult.length(); i++){
                        String courseNo = jsonQueryResult.getJSONObject(i).get("number").toString();
                        String courseDesc = jsonQueryResult.getJSONObject(i).get("description").toString();
                        int classID = (int) jsonQueryResult.getJSONObject(i).get("id");
                        //Log.d("system", courseNo);
                        courseNames.add(course + " " + courseNo);
                        descriptions.put(course + " " + courseNo, courseDesc);
                    }
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
        setContentView(R.layout.activity_course_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        course = getIntent().getStringExtra("course");

        resultsThread.start();
        /* Waits until Thread is Done */
        while(resultsThread.isAlive()) {};
        populateCourses();
        registerClickCallBack();
    }

    @Override
    protected void onRestart(){
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

    private void registerClickCallBack() {
        ListView list = (ListView) findViewById(R.id.CourseListView);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                launchCourseDescription(position);
            }
        });
    }

    private void populateCourses() {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.coursesearchview, courseNames);

        ListView list = (ListView) findViewById(R.id.CourseListView);
        list.setAdapter(adapter);
    }

    public void launchCourseDescription(int i) {
        Intent intent = new Intent(this, courseDescription.class);
        intent.putExtra("course", courseNames.get(i));
        intent.putExtra("description", descriptions.get(courseNames.get(i)));
        intent.putExtra("id", classIDs.get(i));
        startActivity(intent);
    }
}
