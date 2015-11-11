package cmpt305.myfunctionalsystem;

import android.content.Intent;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
//import android.view.Menu;
//import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Semester extends MyMenu {

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
        ListView list = (ListView) findViewById(R.id.SemesterView);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {

                if (termNames[position].compareTo("Fall 2015") == 0) { launchDepartments(); }
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

}
