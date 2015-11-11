package cmpt305.myfunctionalsystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

/**
 *
 * Base abstract class for generic menu functionality. Extend this class
 * instead of AppCompatActivity.
 */
public abstract class MyMenu extends AppCompatActivity
{

    /**
     *
     * @param menu the physical menu to inflate into
     * @return always true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;

    }

    /**
     *
     * @param item the clicked item in the menu
     * @return true or false
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // handle menu actions by id. provide the corresponding clas for the id.
        switch (id)
        {

            case R.id.action_my_class_search:
                launchActivity(myClassSearch.class);
                break;
            case R.id.action_logout:
                launchActivity(MainActivity.class);
                break;
            case R.id.action_my_schedule:
                launchActivity(MySchedule.class);
                break;
            case R.id.action_my_planner:
                launchActivity(MyPlanner.class);
                break;
            case R.id.action_my_cart:
                launchActivity(MyCart.class);
                break;
            case R.id.action_my_agenda:
                launchActivity(MyAgenda.class);
                break;


        }

        //noinspection SimplifiableIfStatement
        return super.onOptionsItemSelected(item);

    }

    /**
     *
     * @param className the class of the activity to launch
     */
    public void launchActivity(Class<?> className)
    {

        Intent intent = new Intent(this, className);
        startActivity(intent);

    }

}
