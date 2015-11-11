package cmpt305.myfunctionalsystem;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Space;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

public class MyPlanner extends MyMenu {

    private final String[] plannedCourses = {"CMPT  491", "PHYS  124", "CMPT  315", "ECON  101", "CMPT  360", "CMPT  464",
            "PHIL  125", "MATH  200", "ECON  102", "CMPT  399", "CHEM  263", "CHEM  291", "POLS  101" };
    private List<String> coursesInPlanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_planner);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Space space;
        addTableRows();
    }

    public void addTableRows() {
        TableLayout tl = (TableLayout) findViewById(R.id.coursePlannerView);


        for (int i = 0; i < plannedCourses.length; i++) {

            TableRow tr = new TableRow(this);
            tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.FILL_PARENT));

            TextView plannerCourse = new TextView(this);
            plannerCourse.setText(plannedCourses[i] + "\t\t\t\t\t\t\t\t");
            plannerCourse.setTextSize(18);
            plannerCourse.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.FILL_PARENT));

            CheckBox selectForDelete = new CheckBox(this);
            //selectForDelete.setText("");
            selectForDelete.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.FILL_PARENT));

            Button deleteCourseFromPlanner = new Button(this);
            deleteCourseFromPlanner.setBackgroundColor(Color.WHITE);
            deleteCourseFromPlanner.setText("X");
            deleteCourseFromPlanner.setMaxWidth(20);
            deleteCourseFromPlanner.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

            tr.addView(plannerCourse);
            tr.addView(deleteCourseFromPlanner);
            tr.addView(selectForDelete);

            tl.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.FILL_PARENT));
        }
    }

    public void launchActivity(Class<?> className) {
        Intent intent = new Intent(this, className);
        startActivity(intent);
    }

    public void browseCourseCatalog(View view){
        launchActivity(Semester.class);
    }

}