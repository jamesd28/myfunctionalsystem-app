package cmpt305.myfunctionalsystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

/**
 *
 */
public abstract class MyMenu extends AppCompatActivity
{

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id)
        {

            case R.id.action_my_class_search:
                launchActivity(myClassSearch.class);
                break;
            case R.id.action_logout:
                launchActivity(MainActivity.class);
                break;

        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {

            return true;

        }
        return super.onOptionsItemSelected(item);

    }

    public void launchActivity(Class<?> className)
    {

        Intent intent = new Intent(this, className);
        startActivity(intent);

    }

}
