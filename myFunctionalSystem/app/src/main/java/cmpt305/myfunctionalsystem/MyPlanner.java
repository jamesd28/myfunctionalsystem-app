package cmpt305.myfunctionalsystem;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Space;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class MyPlanner extends MyMenu {

    private final String[] plannedCourses = {"CMPT  491", "PHYS  124", "CMPT  315", "ECON  101", "CMPT  360", "CMPT  464",
            "PHIL  125", "MATH  200", "ECON  102", "CMPT  399", "CHEM  263", "CHEM  291", "POLS  101", "CMPT  101" };
    private ArrayList<String> coursesInPlanner = new ArrayList<>();
    private HashMap<String, View[]> tableRowContents;
    private List<TableRow> tableRows;
    private List<Integer> classIDs;
    private HashMap<String, String> descriptions;


    Thread resultsThread = new Thread(new Runnable() {
        public void run() {
            try {
                URL serverUrl = new URL("http://159.203.29.177/planner");
                HttpURLConnection urlConnection = (HttpURLConnection) serverUrl.openConnection();

                // Indicate that we want to write to the HTTP request body
                urlConnection.setRequestMethod("GET");

                // Reading from the HTTP response body
                Scanner httpResponseScanner = new Scanner(urlConnection.getInputStream());
                while (httpResponseScanner.hasNextLine()) {
                    JSONArray jsonQueryResult = new JSONArray(httpResponseScanner.nextLine());
                    for(int i = 0; i < jsonQueryResult.length(); i++){
                        String courseCode = jsonQueryResult.getJSONObject(i).get("code").toString();
                        String courseNo = jsonQueryResult.getJSONObject(i).get("number").toString();
                        coursesInPlanner.add(courseCode + "  " + courseNo);
                        Log.d("Planner ", courseCode + " " + courseNo);
                    }
                }
                httpResponseScanner.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_planner);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        coursesInPlanner = new ArrayList<String>(Arrays.asList(plannedCourses));
        tableRowContents = new HashMap<>();
        resultsThread.start();
        /* Waits until Thread is Done */
        while(resultsThread.isAlive()) {};

        addTableRows();
    }

    public void addTableRows() {
        TableLayout tl = (TableLayout) findViewById(R.id.coursePlannerView);
        tableRows = new ArrayList<>();

        for (int i = 0; i < coursesInPlanner.size(); i++) {

            TableRow tr = new TableRow(this);
            tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

            TextView plannerCourse = new TextView(this);
            plannerCourse.setText(coursesInPlanner.get(i) + "\t\t\t\t\t\t\t\t");
            plannerCourse.setTextSize(18);
            plannerCourse.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

            CheckBox selectForDelete = new CheckBox(this);
            //selectForDelete.setText("");
            selectForDelete.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

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
            deleteCourseFromPlanner.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

            tr.addView(plannerCourse);
            tr.addView(deleteCourseFromPlanner);
            tr.addView(selectForDelete);
            tableRows.add(tr);
            tableRowContents.put(coursesInPlanner.get(i), new TextView[]{deleteCourseFromPlanner, selectForDelete});
            tl.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
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

    public void deleteCourseFromPlanner(View view, final String course){

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Are you sure you want to delete "+ course + " from your planner?");
        builder1.setCancelable(true);
        builder1.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Thread thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    JSONObject json = new JSONObject();
                                    String post = json.toString();
                                    URL myFunctionalServer = new URL("http://159.203.29.177/planner/delete/");
                                    HttpURLConnection connection = (HttpURLConnection) myFunctionalServer.openConnection();
                                    connection.setRequestMethod("POST");
                                    connection.setDoOutput(true);
                                    connection.setFixedLengthStreamingMode(post.getBytes().length);
                                    connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
                                    connection.connect();

                                    DataOutputStream reqStream = new DataOutputStream(connection.getOutputStream());
                                    reqStream.writeBytes(post);
                                    reqStream.flush();

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        coursesInPlanner.remove(course);
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

    public void deleteSelectedCourses(View view){

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Are you sure you want to delete the selected courses from your planner?");
        builder1.setCancelable(true);
        builder1.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        List<String> selectedCourses = new ArrayList<>();
                        for (String key : tableRowContents.keySet()){
                            CheckBox checkBox = (CheckBox) tableRowContents.get(key)[1];
                            if (checkBox.isChecked()){
                                coursesInPlanner.remove(key);
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
}