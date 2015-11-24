package cmpt305.myfunctionalsystem;

import android.content.Intent;
import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
//import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
//import android.view.Menu;
//import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Department extends MyMenu {

    private String[] departmentNames= {"Accounting",
            "Acupuncture",
            "Advanced Cardiac Life Support",
            "Anthropology",
            "Art",
            "Arts and Cultural Management",
            "Astronomy",
            "Audiovisual Communication",
            "Basic Life Support",
            "Biochemistry",
            "Biology",
            "Botany",
            "Business",
            "Chemistry"};

    private ArrayList<String> deptNames = new ArrayList<>();

    Thread resultsThread = new Thread(new Runnable() {
        public void run() {
            try {
                URL serverUrl = new URL("http://159.203.29.177/coursecodes");
                HttpURLConnection urlConnection = (HttpURLConnection) serverUrl.openConnection();

                // Indicate that we want to write to the HTTP request body
                urlConnection.setRequestMethod("GET");

                // Reading from the HTTP response body
                Scanner httpResponseScanner = new Scanner(urlConnection.getInputStream());
                while (httpResponseScanner.hasNextLine()) {
                    JSONArray jsonQueryResult = new JSONArray(httpResponseScanner.nextLine());
                    for(int i = 0; i < jsonQueryResult.length(); i++)
                        deptNames.add(jsonQueryResult.getJSONObject(i).get("code").toString());
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
        setContentView(R.layout.activity_department);
        //deptNames = new ArrayList<>();
        resultsThread.start();
        /* Waits until Thread is Done */
        while(resultsThread.isAlive()) {};
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        populateDepartments();
        registerClickCallBack();
    }

    private void populateDepartments() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.coursesearchview, deptNames);

        ListView list = (ListView) findViewById(R.id.DepartmentListView);
        list.setAdapter(adapter);
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
        ListView list = (ListView) findViewById(R.id.DepartmentListView);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {

                launchCourses(position);
                //if (departmentNames[position].compareTo("Accounting") == 0) { launchCourses(); }

                // do stuff
            }
        });
    }

    public void launchCourses(int i) {
        Intent intent = new Intent(this, CourseScreen.class);
        intent.putExtra("course", deptNames.get(i));
        startActivity(intent);
    }

}
