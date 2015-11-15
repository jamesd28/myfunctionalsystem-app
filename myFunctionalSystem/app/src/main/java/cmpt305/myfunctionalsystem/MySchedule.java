package cmpt305.myfunctionalsystem;

import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

public class MySchedule extends MyMenu {

    private final String[] classTimes = {" ", "8:00", "8:30", "9:00", "9:30", "10:00", "10:30", "11:00",
            "11:30", "12:00", "12:30", "1:00", "1:30", "2:00", "2:30",
            "3:00", "3:30", "4:00", "4:30", "5:00", "5:30", "6:00",
            "6:30", "7:00", "7:30", "8:00", "8:30", "9:00", " "};

    private final String[] daysOfTheWeeek = {" ", "Mon", "Tue", "Wed", "Thu",
                                            "Fri", "Sat"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_schedule);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        populateGridView();

    }

    public void populateGridView(){

        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        int screenWidth = size.x;
        int screenHeight = size.y;
        int columnWidths = (int)(screenWidth / 7);
        int rowHeights = (int)(screenHeight / 14);

        GridLayout gridLayout = (GridLayout) findViewById(R.id.classScheduleView);
        int columns = gridLayout.getColumnCount();
        int rows = gridLayout.getRowCount();
        GridLayout.Spec col = GridLayout.spec(0);
        GridLayout.Spec row;

        for (int i = 1; i < rows; i++){
            row = GridLayout.spec(i);
            TextView time = new TextView(this);
            GridLayout.LayoutParams timeBox = new GridLayout.LayoutParams(row, col);
            //timeBox.width = columnWidths;
            timeBox.height = rowHeights;
            time.setText(classTimes[i]);
            gridLayout.addView(time, timeBox);
        }

        row = GridLayout.spec(0);
        for (int i = 1; i < columns; i++){
            col = GridLayout.spec(i);
            TextView day = new TextView(this);
            GridLayout.LayoutParams dayBox = new GridLayout.LayoutParams(row, col);
            dayBox.width = columnWidths;
            dayBox.height = rowHeights;
            day.setText(daysOfTheWeeek[i]);
            gridLayout.addView(day, dayBox);
        }
    }
}
