package cmpt305.myfunctionalsystem;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
    private List<String> currentlyViewedTerm;

    private HashMap<String, List<String>> terms;

    private HashMap<String, View[]> tableRowContents;
    private List<TableRow> tableRows;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tableRowContents = new HashMap<>();
        tableRows = new ArrayList<>();
        terms = new HashMap<>();
        fallCourses = new ArrayList<String>(Arrays.asList(fallCourses1));
        winterCourses = new ArrayList<String>(Arrays.asList(winterCourses1));
        springSummerCourses = new ArrayList<String>(Arrays.asList(springSummerCourses1));
        terms.put("Fall", fallCourses);
        terms.put("Winter", winterCourses);
        terms.put("Spring/Summer", springSummerCourses);

        String term = getIntent().getStringExtra("term");
        currentlyViewedTerm = new ArrayList<>(terms.get(term));
        //Log.d("display", term);
        addTableRows();

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

    public void addTableRows() {
        TableLayout tl = (TableLayout) findViewById(R.id.TermTableLayout);

        for (int i = 0; i < currentlyViewedTerm.size(); i++) {

            TableRow tr = new TableRow(this);
            tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

            TextView cartCourse = new TextView(this);
            cartCourse.setText(currentlyViewedTerm.get(i) + "\t\t\t\t\t");
            cartCourse.setTextSize(18);
            cartCourse.setGravity(10);
            cartCourse.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

            CheckBox selectForDelete = new CheckBox(this);
            selectForDelete.setGravity(1);
            selectForDelete.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

            final Button deleteCourseFromCart = new Button(this);
            deleteCourseFromCart.setBackgroundColor(Color.WHITE);
            deleteCourseFromCart.setGravity(1);
            deleteCourseFromCart.setText("X");
            deleteCourseFromCart.setMaxWidth(20);
            deleteCourseFromCart.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            deleteCourseFromCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String course = getCourseName((Button) v);
                    deleteCourseFromCart(v, course);
                }
            });

            tr.addView(cartCourse);
            tr.addView(deleteCourseFromCart);
            tr.addView(selectForDelete);
            tableRows.add(tr);
            tableRowContents.put(currentlyViewedTerm.get(i), new TextView[]{deleteCourseFromCart, selectForDelete});
            /*tr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String course = getCourseName((TableRow) v);
                    Log.d("System", course);
                    viewClassDescription(v, course);
                }
            });*/
            tl.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.FILL_PARENT));
        }
    }
    public void browseCourseCatalog(View view){
        launchActivity(Department.class);
    }

    public void deleteCourseFromCart(View view, final String course){

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Are you sure you want to delete "+ course + " from your shopping cart?");
        builder1.setCancelable(true);
        builder1.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        currentlyViewedTerm.remove(course);
                        removeTableRows();
                        addTableRows();
                        dialog.cancel();
                    }
                });
        builder1.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();

        //notifyStudentModel
    }

    public void viewClassDescription(View view, String course){

        Intent intent = new Intent(this, courseDescription.class);
        intent.putExtra("course", course);
        intent.putExtra("description", "descriptions.get(courseNames.get(i))");
        intent.putExtra("id", 1);
        startActivity(intent);
    }
    public void deleteSelectedCartCourses(View view){

        
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Are you sure you want to delete the selected courses from your shopping cart?");
        builder1.setCancelable(true);
        builder1.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        List<String> selectedCourses = new ArrayList<>();
                        for (String key : tableRowContents.keySet()){
                            CheckBox checkBox = (CheckBox) tableRowContents.get(key)[1];
                            if (checkBox.isChecked()){
                                currentlyViewedTerm.remove(key);
                            }
                        }
                        removeTableRows();
                        addTableRows();
                        dialog.cancel();
                    }
                });
        builder1.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();

    }

    public void removeTableRows(){
        TableLayout tl = (TableLayout) findViewById(R.id.TermTableLayout);
        tl.removeAllViews();
    }

    public String getCourseName(View button){
        for (String key : tableRowContents.keySet()){
            if (tableRowContents.get(key)[0].equals(button) ||
                    tableRowContents.get(key)[1].equals(button)){
                return key;
            }
        }
        return null;
    }

}
