package cmpt305.myfunctionalsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;

public class courseDescription extends MyMenu {

    private String[] courseInfo = {"Description\n ljh ;lrejr;lh je;lhjwr; h;wlrwrj; hwj;lr jl;wrjh;l wjr;hw hjrth oerjh rlhj ;erljh;er j;lhejr;lh jlwrj hsr ogej rpojgrepoj oerj hoj wpoerjg pworjg jrwowro grjo jwogj erojg rj fpqjgj e ewog wqe;o gjpworejgpowjrgpojrwp",
                                   "Section\nTEXT",
                                   "Instructor\nTEXT",
                                   "Times\nTEXT"};
    CourseCodes course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_description);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

        GridView grid = (GridView) findViewById(R.id.courseGrid);
        grid.setAdapter(adapter);
    }

    public void launchActivity() {
        Intent intent = new Intent(this, Semester.class);
        startActivity(intent);
    }

    public void viewClassSections(View view){
        Intent intent = new Intent(this, Semester.class);
        startActivity(intent);
    }

    public void addToPlanner(View view){
        // Person.addToPlanner
    }
}
