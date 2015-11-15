package cmpt305.myfunctionalsystem;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MyCart extends MyMenu {

    private final String[] fallCourses1 = {"CMPT  361", "CMPT  305", "CMPT  310", "ECON  101"};
    private final String[] winterCourses1 = {"CMPT  399", "CMPT  491", "CMPT  315", "PHYS  124", "ECON  102"};
    private final String[] springSummerCourses1 = {"PHIL 125",  "POLS  101"};

    private List<String> fallCourses;
    private List<String> winterCourses;
    private List<String> springSummerCourses;

    HashMap<String, List<String>> terms;
    List<TableRow> tableRows;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        terms = new HashMap<>();
        fallCourses = new ArrayList<String>(Arrays.asList(fallCourses1));
        winterCourses = new ArrayList<String>(Arrays.asList(winterCourses1));
        springSummerCourses = new ArrayList<String>(Arrays.asList(springSummerCourses1));
        terms.put("Fall", fallCourses);
        terms.put("Winter", winterCourses);
        terms.put("Spring/Summer", springSummerCourses);

        String term = getIntent().getStringExtra("term");
        Log.d("display", term);
        addTableRows(terms.get(term));
        //addTableRows(winterCourses);
        //addTableRows(springFallCourses);
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

    public void addTableRows(List<String> courses) {
        TableLayout tl = (TableLayout) findViewById(R.id.TermTableLayout);

        for (int i = 0; i < courses.size(); i++) {

            TableRow tr = new TableRow(this);
            tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

            TextView cartCourse = new TextView(this);
            cartCourse.setText(courses.get(i) + "\t\t\t\t\t");
            cartCourse.setTextSize(18);
            cartCourse.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

            CheckBox selectForDelete = new CheckBox(this);
            selectForDelete.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

            Button deleteCourseFromCart = new Button(this);
            deleteCourseFromCart.setBackgroundColor(Color.WHITE);
            deleteCourseFromCart.setText("X");
            deleteCourseFromCart.setMaxWidth(20);
            deleteCourseFromCart.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));


            tr.addView(cartCourse);
            tr.addView(deleteCourseFromCart);
            tr.addView(selectForDelete);


            tl.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.FILL_PARENT));
        }
    }

}
