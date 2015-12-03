package cmpt305.myfunctionalsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class SemesterScreen extends MenuToolbar {

    private final String TAG = "myFunctional System";
    private ArrayList<String> termNames = new ArrayList<>();
    private ArrayList<Integer> termIds = new ArrayList<>();

    Thread resultsThread = new Thread(new Runnable() {
        public void run() {
            try {
                URL serverUrl = new URL("http://159.203.29.177/terms");
                HttpURLConnection urlConnection = (HttpURLConnection) serverUrl.openConnection();

                // Indicate that we want to write to the HTTP request body
                urlConnection.setRequestMethod("GET");

                // Reading from the HTTP response body
                Scanner httpResponseScanner = new Scanner(urlConnection.getInputStream());
                while (httpResponseScanner.hasNextLine()) {
                    JSONArray jsonQueryResult = new JSONArray(httpResponseScanner.nextLine());
                    for(int i = 0; i < jsonQueryResult.length(); i++) {
                        termNames.add(jsonQueryResult.getJSONObject(i).get("termName").toString());
                        termIds.add((Integer) jsonQueryResult.getJSONObject(i).get("id"));
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
        setContentView(R.layout.activity_semester_screen);

        resultsThread.start();

        /* Waits until Thread is Done */
        while(resultsThread.isAlive()) {};
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
                String term = termNames.get(position);
                launchCart(viewClicked, term);
            }
        });
    }

    private void populateTerms() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.coursesearchview, termNames);

        ListView list = (ListView) findViewById(R.id.SemesterView);
        list.setAdapter(adapter);
    }

    public void launchDepartments() {
        Intent intent = new Intent(this, DepartmentScreen.class);
        startActivity(intent);
    }

    public void launchCart(View view, String term){

        Intent intent = new Intent(this, CartScreen.class);
        intent.putExtra("term", term.split(" ")[0]);
        intent.putExtra("termId", termIds.get(termNames.indexOf(term)));
        startActivity(intent);
    }
}
