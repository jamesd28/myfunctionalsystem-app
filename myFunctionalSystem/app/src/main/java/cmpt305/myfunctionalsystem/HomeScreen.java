package cmpt305.myfunctionalsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class HomeScreen extends MenuToolbar {

    private String[] calendarTimes = {"8:00", "8:30", "9:00", "9:30", "10:00", "10:30", "11:00",
                                        "11:30", "12:00", "12:30", "1:00", "1:30", "2:00", "2:30",
                                        "3:00", "3:30", "4:00", "4:30", "5:00", "5:30", "6:00",
                                        "6:30", "7:00", "7:30", "8:00", "8:30", "9:00"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        populateListView();
        registerClickCallBack();
    }


    public void launchActivity(Class<?> className) {
        Intent intent = new Intent(this, className);
        startActivity(intent);
    }

    private void registerClickCallBack() {
        ListView list = (ListView) findViewById(R.id.listView);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                TextView textView = (TextView) viewClicked;
            }

        });
    }

    private void populateListView() {

        for (int i = 0; i < calendarTimes.length; i++){
            calendarTimes[i] = "\n" + calendarTimes[i] + "\n";
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.calendar_event, calendarTimes);

        ListView list = (ListView) findViewById(R.id.listView);
        list.setAdapter(adapter);
    }
}
