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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MyPlanner extends MyMenu {

    private final String[] plannedCourses = {"CMPT  491", "PHYS  124", "CMPT  315", "ECON  101", "CMPT  360", "CMPT  464",
            "PHIL  125", "MATH  200", "ECON  102", "CMPT  399", "CHEM  263", "CHEM  291", "POLS  101" };
    private List<String> coursesInPlanner;
    HashMap<String, View[]> tableRowContents;
    List<TableRow> tableRows;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_planner);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        coursesInPlanner = new ArrayList<String>(Arrays.asList(plannedCourses));
        tableRowContents = new HashMap<>();

        addTableRows();
    }

    public void addTableRows() {
        TableLayout tl = (TableLayout) findViewById(R.id.coursePlannerView);
        tableRows = new ArrayList<>();

        for (int i = 0; i < coursesInPlanner.size(); i++) {

            TableRow tr = new TableRow(this);
            tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.FILL_PARENT));

            TextView plannerCourse = new TextView(this);
            plannerCourse.setText(coursesInPlanner.get(i) + "\t\t\t\t\t\t\t\t");
            plannerCourse.setTextSize(18);
            plannerCourse.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.FILL_PARENT));

            CheckBox selectForDelete = new CheckBox(this);
            selectForDelete.setText("");
            selectForDelete.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.FILL_PARENT));

            Button deleteCourseFromPlanner = new Button(this);

            deleteCourseFromPlanner.setBackgroundColor(Color.WHITE);
            deleteCourseFromPlanner.setText("X");
            deleteCourseFromPlanner.setMaxWidth(20);
            deleteCourseFromPlanner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String course = getCourseName((Button) v);

                    deleteCourseFromPlanner(v, course);
                }
            });
            deleteCourseFromPlanner.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

            tr.addView(plannerCourse);
            tr.addView(deleteCourseFromPlanner);
            tr.addView(selectForDelete);
            tableRows.add(tr);
            tableRowContents.put(coursesInPlanner.get(i), new TextView[]{deleteCourseFromPlanner, selectForDelete});
            tl.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.FILL_PARENT));
        }
    }

    public void removeTableRows(){
        TableLayout tl = (TableLayout) findViewById(R.id.coursePlannerView);
        tl.removeAllViews();
    }
    public String getCourseName(Button button){
        for (String key : tableRowContents.keySet()){
            if (tableRowContents.get(key)[0].equals(button)){
                return key;
            }
        }
        return null;
    }

    public void launchActivity(Class<?> className) {
        Intent intent = new Intent(this, className);
        startActivity(intent);
    }

    public void browseCourseCatalog(View view){
        launchActivity(Department.class);
    }

    public void deleteCourseFromPlanner(View view, String course){
        coursesInPlanner.remove(course);
        removeTableRows();
        addTableRows();
        //notifyStudentModel
    }

    public void deleteSelectedCourses(View view){
        List<String> selectedCourses = new ArrayList<>();
        for (String key : tableRowContents.keySet()){
            CheckBox checkBox = (CheckBox) tableRowContents.get(key)[1];
            if (checkBox.isChecked()){
                coursesInPlanner.remove(key);
            }
        }
        removeTableRows();
        addTableRows();
    }
}