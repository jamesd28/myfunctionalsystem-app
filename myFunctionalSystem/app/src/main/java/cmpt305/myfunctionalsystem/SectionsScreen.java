package cmpt305.myfunctionalsystem;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

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
import java.util.GregorianCalendar;

import java.util.HashMap;
import java.util.Scanner;

public class SectionsScreen extends MenuToolbar {

    private ArrayList<String> classSections;
    private ArrayList<HashMap<String, String>> sectionObjects;
    private int courseID;
    private String courseName;

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

                        String startTime = jsonQueryResult.getJSONObject(i).get("startTime").toString().split("T")[1].substring(0, 5);
                        String endTime = jsonQueryResult.getJSONObject(i).get("endTime").toString().split("T")[1].substring(0, 5);
                        String timeString = startTime + " - " + endTime;

                        String sectionID = jsonQueryResult.getJSONObject(i).get("id").toString();
                        String section = jsonQueryResult.getJSONObject(i).get("sectionNumber").toString();
                        Log.d("ClassSections", section + "  " + day + "  " + timeString);
                        classSections.add(section + "\t\t" + day + "\n" + timeString);

                        /* Adds for Calendar to use */
                        HashMap<String, String> courseObject = new HashMap<>();

                        courseObject.put("sectionID", sectionID);
                        courseObject.put("section", section);
                        courseObject.put("days", day);
                        courseObject.put("startTime", startTime);
                        courseObject.put("endTime", endTime);

                        sectionObjects.add(courseObject);
                        /* End Calendar */
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
        setContentView(R.layout.activity_sections_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        classSections = new ArrayList<>();
        sectionObjects = new ArrayList<>();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            courseID = extras.getInt("id");
            courseName = extras.getString("name");
        }

        resultsThread.start();
        /* Waits until Thread is Done */
        while(resultsThread.isAlive()) {};

	    populateSections();
        registerClickCallBack();

        //addToCalendar();
    }

    private void populateSections() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.coursesearchview, classSections);

        ListView list = (ListView) findViewById(R.id.classSections);
        list.setAdapter(adapter);
    }

    private void registerClickCallBack() {
        ListView list = (ListView) findViewById(R.id.classSections);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {


                /* Begin -- Calendar */
                String section = sectionObjects.get(position).get("section");
                String location = "CCC - Bldg 5 - 232";
                String days = getDays(sectionObjects.get(position).get("days"));

                /* Gets starting date to put first calendar event on */
                int year = 2015;
                int month = 9;
                int day = 1;
                while(!days.contains(convertDatetoDay(day, month, year))) { day++; }

                /* Calendars for start/end times */
                GregorianCalendar startCal =  new GregorianCalendar(year, month - 1, day,
                        Integer.valueOf(sectionObjects.get(position).get("startTime").split(":")[0]),
                        Integer.valueOf(sectionObjects.get(position).get("startTime").split(":")[1]));

                GregorianCalendar endCal = new GregorianCalendar(year, month - 1, day,
                        Integer.valueOf(sectionObjects.get(position).get("endTime").split(":")[0]),
                        Integer.valueOf(sectionObjects.get(position).get("endTime").split(":")[1]));

                /* Convoluted String / Specifies where to end recurrence (YYYYMMDD????????)
                                                Best Leave the question marks as: T000000Z */
                String untilDate = "20151202T000000Z";

                /* Add to Calendar */
                addToCalendar(courseName, section, location, days, startCal, endCal, untilDate);
                /* End -- Calendar */
            }

        });
    }

    /* Source http://stason.org/TULARC/society/calendars/2-5-What-day-of-the-week-was-2-August-1953.html#.Vl6CO9-rS3U */
    public String convertDatetoDay(int day, int month, int year) {
        String[] days = {"SU", "MO", "TU", "WE", "TH", "FR", "SA"};

        int offset = (14 - month) / 12;
        year = year - offset;
        month = month + 12 * offset - 2;

        int dayIdx = (day + year + (year / 4) - (year / 100) + (year / 400) + (31 * month) / 12) % 7;
        return days[dayIdx];
    }

    public String getDays(String dayString) {
        String newDayString = "";
        char[] allDays;

        allDays = dayString.toCharArray();

        for(int i = 0; i < allDays.length; i++) {
            if(allDays[i] == 'U') { newDayString += "SU"; }
            if(allDays[i] == 'M') { newDayString += "MO"; }
            if(allDays[i] == 'T') { newDayString += "TU"; }
            if(allDays[i] == 'W') { newDayString += "WE"; }
            if(allDays[i] == 'R') { newDayString += "TH"; }
            if(allDays[i] == 'F') { newDayString += "FR"; }
            if(allDays[i] == 'S') { newDayString += "SA"; }
            if(i != (allDays.length - 1)) newDayString += ",";
        }
        return newDayString;
    }

    /* Source: http://code.tutsplus.com/tutorials/android-essentials-adding-events-to-the-users-calendar--mobile-8363 */
    public void addToCalendar(String title,
                              String sectionNum,
                              String location,
                              String days,
                              GregorianCalendar startTime,
                              GregorianCalendar endTime,
                              String untilDate) {

        Intent calIntent = new Intent(Intent.ACTION_INSERT);
        calIntent.setType("vnd.android.cursor.item/event");
        calIntent.putExtra(CalendarContract.Events.TITLE, title + " - " + sectionNum);
        calIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, location);

        calIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startTime.getTimeInMillis());
        calIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis());

        calIntent.putExtra(CalendarContract.Events.RRULE, "FREQ=WEEKLY;BYDAY=" + days + ";UNTIL=" + untilDate);

        startActivity(calIntent);

    }

    private void addToCart(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject json = new JSONObject().put("", "");
                    String post = json.toString();
                    URL myFunctionalServer = new URL("http://159.203.29.177/cart/add"+ courseID);
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
    }
}

