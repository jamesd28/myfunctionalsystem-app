package cmpt305.myfunctionalsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.GridView;

public class courseDescription extends MyMenu {

    private String[] courseInfo = {"Description\nTEXT",
                                   "Section\nTEXT",
                                   "Instructor\nTEXT",
                                   "Times\nTEXT"};

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
        Intent intent = new Intent(this, myClassSearch.class);
        startActivity(intent);
    }
}
