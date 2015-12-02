package cmpt305.myfunctionalsystem;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import java.util.List;
import java.util.Scanner;

public class ClassSections extends AppCompatActivity {

    private ArrayList<String> classSections, sections;
    private ArrayList<Integer> classIDs;
    private ArrayList<Boolean> clickedViews;
    private int courseID;

    Thread resultsThread = new Thread(new Runnable() {
        public void run() {
            try {
                URL serverUrl = new URL("http://159.203.29.177/classes/" + courseID);
                HttpURLConnection urlConnection = (HttpURLConnection) serverUrl.openConnection();

                // Indicate that we want to write to the HTTP request body
                urlConnection.setRequestMethod("GET");

                // Reading from the HTTP response body
                Scanner httpResponseScanner = new Scanner(urlConnection.getInputStream());
                while (httpResponseScanner.hasNextLine()) {
                    JSONArray jsonQueryResult = new JSONArray(httpResponseScanner.nextLine());
                    for(int i = 0; i < jsonQueryResult.length(); i++) {
                        String day = jsonQueryResult.getJSONObject(i).get("meetDates").toString();
                        String time = jsonQueryResult.getJSONObject(i).get("startTime").toString().split("T")[1].substring(0, 5)
                                + " - " +
                                jsonQueryResult.getJSONObject(i).get("endTime").toString().split("T")[1].substring(0, 5);
                        String section = jsonQueryResult.getJSONObject(i).get("sectionNumber").toString();
                        Log.d("ClassSections", section + "  " + day + "  " + time);
                        classSections.add(section + "\t\t" + day + "\n" + time);
                        clickedViews.add(false);
                        //classIDs.add(section);
                        //profs.add(jsonQueryResult.getJSONObject(i).get("instructor").toString());


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
        setContentView(R.layout.activity_class_sections);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        classSections = new ArrayList<>();
        classIDs = new ArrayList<>();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            courseID = extras.getInt("id");
        }

        resultsThread.start();
        /* Waits until Thread is Done */
        while(resultsThread.isAlive()) {};

	populateClassSections();
        registerClickCallBack();

        addToCalendar();
    }

    private void populateClassSections() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.coursesearchview, classSections);

        ListView list = (ListView) findViewById(R.id.classSections);
        list.setAdapter(adapter);
    }

    private void registerClickCallBack() {
        ListView list = (ListView) findViewById(R.id.classSections);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {

                if (clickedViews.get(position)) {

                } else {
                    clickedViews.set(position, true);


                }
            }
        });
    }

    /* Source: http://code.tutsplus.com/tutorials/android-essentials-adding-events-to-the-users-calendar--mobile-8363 */
    public void addToCalendar() {

        Intent calIntent = new Intent(Intent.ACTION_INSERT);
        calIntent.setType("vnd.android.cursor.item/event");
        calIntent.putExtra(CalendarContract.Events.TITLE, "Title");
        calIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, "Location");
        calIntent.putExtra(CalendarContract.Events.DESCRIPTION, "Description");

        GregorianCalendar startTime = new GregorianCalendar(2015, 7, 15, 13, 00);
        GregorianCalendar endTime = new GregorianCalendar(2015, 7, 15, 15, 00);

        calIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startTime.getTimeInMillis());
        calIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis());

        
        calIntent.putExtra(CalendarContract.Events.RRULE, "FREQ=WEEKLY;WKST=SU;BYDAY=TU,TH;UNTIL=20151202T000000Z");

        startActivity(calIntent);


    }

    /*private void addToCart(){
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject json = new JSONObject().put("password", passwordEditText.getText().toString()).put("netid", usernameEditText.getText().toString());
                    String post = json.toString();
                    Log.d(TAG, post);
                    URL myFunctionalServer = new URL("http://159.203.29.177/auth/login/");
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
    }*/
}

