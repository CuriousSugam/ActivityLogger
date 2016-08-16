package com.lftechnology.activitylogger;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.lftechnology.activitylogger.fragments.FirstMainFragment;
import com.lftechnology.activitylogger.fragments.SecondMainFragment;
import com.lftechnology.activitylogger.model.AppDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * This activity shows the very first UI of the application containing the slider at the top and
 * the grid menu at the bottom.
 *
 * @author Sugam Shakya
 */
public class MainActivity extends AppCompatActivity {

    private final static String LOWER_FRAGMENT = "lowerFragment";
    private final static String UPPER_FRAGMENT = "upperFragment";

    public static String APP_DETAILS = "appDetails";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fragment upperMainFragment = new FirstMainFragment();
        Fragment lowerMainFragment = new SecondMainFragment();
        Intent intent = getIntent();



        List<AppDetails> appDetailsFromDatabase = intent.getParcelableArrayListExtra(APP_DETAILS);
        Bundle fragmentArguments = new Bundle();
        fragmentArguments.putParcelableArrayList(APP_DETAILS, (ArrayList<? extends Parcelable>) appDetailsFromDatabase);
        lowerMainFragment.setArguments(fragmentArguments);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.lower_fragment_container, lowerMainFragment, LOWER_FRAGMENT)
                .add(R.id.upper_fragment_container, upperMainFragment, UPPER_FRAGMENT)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menu_info:
                startActivity(new Intent(MainActivity.this, AppInfoActivity.class));
                return true;

            case R.id.menu_settings:
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }
}

