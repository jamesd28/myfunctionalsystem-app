package cmpt305.myfunctionalsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class Semester extends MyMenu {

    private final String TAG = "myFunctional System";
    private String[] termNames = {"Fall 2015",
            "Winter 2016",
            "Spring/Summer 2016",
            "Fall 2016",
            "Winter 2017"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semester);

        populateTerms();
        registerClickCallBack();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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

    private void registerClickCallBack() {
        ListView list = (ListView) findViewById(R.id.SemesterView);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                String term = termNames[position];
                launchCart(viewClicked, term);
                // do stuff
            }
        });
    }

    private void populateTerms() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.coursesearchview, termNames);

        ListView list = (ListView) findViewById(R.id.SemesterView);
        list.setAdapter(adapter);
    }

    public void launchDepartments() {
        Intent intent = new Intent(this, Department.class);
        startActivity(intent);
    }

    public void launchCart(View view, String term){

        Intent intent = new Intent(this, MyCart.class);
        //Log.d(TAG, term.split(" ")[0]);
        intent.putExtra("term", term.split(" ")[0]);
        startActivity(intent);
    }



}
