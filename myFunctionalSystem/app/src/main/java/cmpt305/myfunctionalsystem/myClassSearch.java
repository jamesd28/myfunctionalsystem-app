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

    private String[] courseNames = {"ACCT 111\nFinancial Accounting I",
                                    "ACCT 161\nFinancial Accounting II",
                                    "ACCT 164\nPractical Tax Applications",
                                    "ACCT 211\nFinancial Accounting III",
                                    "ACCT 214\nIntegrated Accounting Systems",
                                    "ACCT 215\nQuantitative Decision Support",
                                    "ACCT 218\nManagement Accounting I",
                                    "ACCT 261\nFinancial Accounting IV",
                                    "ACCT 268\nManagement Accounting II",
                                    "ACCT 275\nAccounting Information Systems"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_class_search);

        populateDepartments();
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
                launchActivity();
                break;

        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void registerClickCallBack() {
        ListView list = (ListView) findViewById(R.id.myClassSearchView);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                if (departmentNames[position].compareTo("Accounting") == 0) {
                    populateCourses("Accounting");
                }
                // launchActivity(viewClicked);
                // do stuff
            }
        });
    }

    private void populateDepartments() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list, departmentNames);

        ListView list = (ListView) findViewById(R.id.myClassSearchView);
        list.setAdapter(adapter);
    }

    private void populateCourses(String departmentName) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list, courseNames);

        ListView list = (ListView) findViewById(R.id.myClassSearchView);
        list.setAdapter(adapter);
    }

    public void launchActivity() {
        Intent intent = new Intent(this, myClassSearch.class);
        startActivity(intent);
    }
}
