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

public class Department extends MyMenu {

    private String[] departmentNames = {"Accounting",
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        populateDepartments();
        registerClickCallBack();
    }

    private void populateDepartments() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.coursesearchview, departmentNames);

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

                if (departmentNames[position].compareTo("Accounting") == 0) { launchCourses(); }

                // do stuff
            }
        });
    }

    public void launchCourses() {
        Intent intent = new Intent(this, CourseCodes.class);
        startActivity(intent);
    }

}
