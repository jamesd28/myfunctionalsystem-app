package cmpt305.myfunctionalsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;

public class courseDescription extends MyMenu {

    private String[] courseInfo = {
            "",
            "Description\nA very insightful description of the course goes here",
    };
    //CourseScreen course;
    private long courseID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_description);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        courseInfo[0] = getIntent().getStringExtra("course");
        courseInfo[1] = "Description: \n\n\t\t" + getIntent().getStringExtra("description");
        courseID = getIntent().getLongExtra("id", -1);
        populateDescription();
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
    protected void onStop(){ super.onStop(); }

    @Override
    protected void onDestroy(){ super.onDestroy(); }

    private void populateDescription() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.coursedescriptionview, courseInfo);

        ListView list = (ListView) findViewById(R.id.classDescription);
        list.setAdapter(adapter);
    }



    public void viewClassSections(View view){
        Intent intent = new Intent(this, ClassSections.class);
        intent.putExtra("id", courseID);
        startActivity(intent);
    }

    public void addToPlanner(View view){
        // Person.addToPlanner
    }
}
