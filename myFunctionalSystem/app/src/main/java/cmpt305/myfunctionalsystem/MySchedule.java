package cmpt305.myfunctionalsystem;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

public class MySchedule extends AppCompatActivity {

    private String[] calendarTimes = {"8:00", "8:30", "9:00", "9:30", "10:00", "10:30", "11:00",
            "11:30", "12:00", "12:30", "1:00", "1:30", "2:00", "2:30",
            "3:00", "3:30", "4:00", "4:30", "5:00", "5:30", "6:00",
            "6:30", "7:00", "7:30", "8:00", "8:30", "9:00"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_schedule);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }

    public void populateGridView(){

        GridLayout gridLayout = (GridLayout) findViewById(R.id.classScheduleView);
        int columns = gridLayout.getColumnCount();
        int rows = gridLayout.getRowCount();

        for (int i = 0; i < rows; i++){
            TextView time = new TextView(this);
            GridLayout.LayoutParams params;
                   // (GridLayout.LayoutParams)myViews[yPos*numOfCol + xPos].getLayoutParams();


        }
    }
}
