package cmpt305.myfunctionalsystem;

import android.content.Intent;
import android.graphics.Color;
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
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MyCart extends MyMenu {

    private final String[] fallCourses = {"CMPT  361", "CMPT  305", "CMPT  310", "ECON  101"};
    private final String[] winterCourses = {"CMPT  399", "CMPT  491", "CMPT  315", "PHYS  124", "ECON  102"};
    private final String[] springFallCourses = {"PHIL 125",  "POLS  101"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        addTableRows(fallCourses);
        addTableRows(winterCourses);
        addTableRows(springFallCourses);
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


    public void launchActivity(Class<?> className) {
        Intent intent = new Intent(this, className);
        startActivity(intent);
    }

    public void addTableRows(String[] courses) {
        TableLayout tl = (TableLayout) findViewById(R.id.TermTableLayout);

        for (int i = 0; i < courses.length; i++) {

            TableRow tr = new TableRow(this);
            tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.FILL_PARENT));

            TextView plannerCourse = new TextView(this);
            plannerCourse.setText(courses[i] + "\t\t\t\t\t");
            plannerCourse.setTextSize(18);
            plannerCourse.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.FILL_PARENT));

            CheckBox selectForDelete = new CheckBox(this);
            //selectForDelete.setText("");
            selectForDelete.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.FILL_PARENT));

            Button deleteCourseFromCart = new Button(this);
            deleteCourseFromCart.setBackgroundColor(Color.WHITE);
            deleteCourseFromCart.setText("X");
            deleteCourseFromCart.setMaxWidth(20);
            deleteCourseFromCart.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

            tr.addView(plannerCourse);
            tr.addView(deleteCourseFromCart);
            tr.addView(selectForDelete);

            tl.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.FILL_PARENT));
        }
    }

}
