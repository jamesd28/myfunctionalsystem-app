package cmpt305.myfunctionalsystem;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

/**
 *
 * Base abstract class for generic menu functionality. Extend this class
 * instead of AppCompatActivity.
 */
public abstract class MenuToolbar extends AppCompatActivity {

    /**
     *
     * @param menu the physical menu to inflate into
     * @return always true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {

        // Inflate the menu; this adds items to the action bar if it is present.
        if(LoginScreen.validated)
            getMenuInflater().inflate(R.menu.menu_main, menu);
        else
            getMenuInflater().inflate(R.menu.login_menu, menu);
        return true;

    }

    @Override
    public void onBackPressed()
    {

        if(!LoginScreen.validated)
            launchActivity(LoginScreen.class);
        else
            super.onBackPressed();

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
                launchActivity(DepartmentScreen.class);
                break;
            case R.id.action_logout:
                LoginScreen.validated = false;
                LoginScreen.uname = "";
                LoginScreen.password = "";
                launchActivity(LoginScreen.class);
                break;
            case R.id.action_my_schedule:
                if(LoginScreen.validated)
                    launchCalendar();
                break;
            case R.id.action_my_planner:
                if(LoginScreen.validated)
                    launchActivity(PlannerScreen.class);
                else
                    launchActivity(LoginScreen.class);
                break;
            case R.id.action_my_cart:
                if(LoginScreen.validated)
                    launchActivity(SemesterScreen.class);
                else
                    launchActivity(LoginScreen.class);
                break;
            case R.id.action_home_screen:
                if(LoginScreen.validated)
                    launchActivity(HomeScreen.class);
                else
                    launchActivity(LoginScreen.class);
                break;
            case R.id.action_contact:
                launchContactActivity();
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

    /**
     * Handles a "contact" click, by launching an email application with the appropriate fields set.
     */
    public void launchContactActivity(){
        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("plain/text");
            i.putExtra(Intent.EXTRA_EMAIL, new String[]{"helpdesk@macewan.ca"});
            startActivity(i.createChooser(i, "Send email using:"));
        } catch (ActivityNotFoundException e){
            new AlertDialog.Builder(this)
                    .setTitle("Contact")
                    .setMessage("Please email helpdesk@macewan.ca")
                    .setCancelable(false)
                    .setIcon(R.mipmap.ic_launcher)
                    .show();
        }
    }

    public void launchCalendar() {
        Intent i = new Intent();

        ComponentName cn = new ComponentName("com.android.calendar", "com.android.calendar.LaunchActivity");

        i.setComponent(cn);
        startActivity(i);
    }

}
