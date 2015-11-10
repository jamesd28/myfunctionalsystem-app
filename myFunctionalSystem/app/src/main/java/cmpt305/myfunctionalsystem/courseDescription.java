package cmpt305.myfunctionalsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.GridView;

public class courseDescription extends AppCompatActivity {

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
    protected void onStop(){
        super.onStop();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_my_class_search:
                launchActivity();
                break;
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) { return true; }

        return super.onOptionsItemSelected(item);
    }


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
