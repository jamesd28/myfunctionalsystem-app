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
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class CartScreen extends MenuToolbar {
    private List<String> currentlyViewedTerm = new ArrayList<>();
    private String term;
    private Integer termId;

    private HashMap<String, List<String>> terms;

    private HashMap<String, View[]> tableRowContents;
    private List<TableRow> tableRows;
    private List<Integer> classIds = new ArrayList<>();
    private List<String> courseNames = new ArrayList<>();

    Thread resultsThread = new Thread(new Runnable() {
        public void run() {
            try {
                URL serverUrl = new URL("http://159.203.29.177/cart/" + termId);
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
                        String sectionNo = jsonQueryResult.getJSONObject(i).get("sectionNumber").toString();

                        currentlyViewedTerm.add(courseCode + "  " + courseNo + " " + sectionNo);
                        classIds.add((Integer) jsonQueryResult.getJSONObject(i).get("ClassId"));
                        courseNames.add(courseCode);
                        System.out.println("CURRENTLY VIEWED TERM: " + jsonQueryResult.getJSONObject(i));
                        System.out.println("COURSE VIEWED TERM: " + currentlyViewedTerm);

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
        setContentView(R.layout.activity_cart_screen);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        currentlyViewedTerm = new ArrayList<>();
        tableRowContents = new HashMap<>();//
        tableRows = new ArrayList<>();
        terms = new HashMap<>();

        term = getIntent().getStringExtra("term");
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            termId = extras.getInt("termId");
        }

        resultsThread.start();

        /* Waits until Thread is Done */
        while(resultsThread.isAlive()) {};
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

            tl.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.FILL_PARENT));
        }
    }
    public void browseCourseCatalog(View view){
        launchActivity(DepartmentScreen.class);
    }

    public void deleteCourseFromCart(View view, final String course){

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Are you sure you want to delete "+ course + " from your shopping cart?");
        builder1.setCancelable(true);
        builder1.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Thread thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    JSONObject json = new JSONObject().put("", "");
                                    String post = json.toString();
                                    URL myFunctionalServer = new URL("http://159.203.29.177/cart/delete/"+ classIds.get(currentlyViewedTerm.indexOf(course)));
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

                        thread.start();
			/* Waits until Thread is Done */
                        while(thread.isAlive()) {};

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

        Intent intent = new Intent(this, DescriptionScreen.class);
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

    public String getCourseName(Button button){
        for (String key : tableRowContents.keySet()){
            //Log.d("this", key);
            if (tableRowContents.get(key)[0].equals(button) /*||
                    tableRowContents.get(key)[1].equals(button)*/){
                Log.d("this", key);
                return key;
            }
        }
        return null;
    }

}
