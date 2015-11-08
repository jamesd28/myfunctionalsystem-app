package cmpt305.myfunctionalsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Space;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MyPlanner extends AppCompatActivity {

    private final String[] plannedCourses = {"CMPT  491", "PHYS  124", "CMPT  315", "ECON  101", "CMPT  360", "CMPT  464",
            "PHIL  125", "MATH  200", "ECON  102", "CMPT  399", "CHEM  263", "CHEM  291", "POLS  101" };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_planner);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Space space;
        addTableRows();
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
    public void addTableRows() {
        TableLayout tl = (TableLayout) findViewById(R.id.coursePlannerView);


        for (int i = 0; i < plannedCourses.length; i++) {

            TableRow tr = new TableRow(this);
            tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.FILL_PARENT));

            TextView plannerCourse = new TextView(this);
            plannerCourse.setText(plannedCourses[i] + "\t\t\t\t\t\t\t\t\t\t\t\t");
            plannerCourse.setTextSize(18);
            plannerCourse.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.FILL_PARENT));

            CheckBox selectForDelete = new CheckBox(this);
            selectForDelete.setText("\t\t");
            selectForDelete.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.FILL_PARENT));

            TextView deleteCourseFromPlanner = new TextView(this);
            deleteCourseFromPlanner.setText("X\t");
            deleteCourseFromPlanner.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.FILL_PARENT));

            tr.addView(plannerCourse);
            tr.addView(deleteCourseFromPlanner);
            tr.addView(selectForDelete);

            tl.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        }
    }

    public void launchActivity(Class<?> className) {
        Intent intent = new Intent(this, className);
        startActivity(intent);
    }
}