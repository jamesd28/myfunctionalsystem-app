package cmpt305.myfunctionalsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class myClassSearch extends AppCompatActivity {

    private String[] termNames = {"Fall 2015",
                                  "Winter 2016",
                                  "Spring/Summer 2016",
                                  "Fall 2016",
                                  "Winter 2017"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_class_search);

        populateTerms();
        registerClickCallBack();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
                launchActivity(myClassSearch.class);
                break;
            case R.id.action_my_planner:
                launchActivity(MyPlanner.class);
                break;
            case R.id.action_my_agenda:
                launchActivity(MyAgenda.class);
                break;
            case R.id.action_my_cart:
                launchActivity(MyCart.class);
                break;
        }

        return super.onOptionsItemSelected(item);
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
        ListView list = (ListView) findViewById(R.id.myClassSearchView);
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

        ListView list = (ListView) findViewById(R.id.myClassSearchView);
        list.setAdapter(adapter);
    }

    public void launchDepartments() {
        Intent intent = new Intent(this, searchDepartments.class);
        startActivity(intent);
    }

    public void launchActivity(Class<?> className) {
        Intent intent = new Intent(this, className);
        startActivity(intent);
    }

}
