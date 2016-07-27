
package com.lftechnology.activitylogger;

import android.app.Fragment;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;

import com.lftechnology.activitylogger.Fragments.SecondMainFragment;
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

    public static String APP_DETAILS = "appDetails";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fragment lowerMainFragment = new SecondMainFragment();
        Intent intent = getIntent();
        List<AppDetails> appDetailsFromDatabase = intent.getParcelableArrayListExtra(APP_DETAILS);
        Bundle fragmentArguments = new Bundle();
        fragmentArguments.putParcelableArrayList(APP_DETAILS, (ArrayList<? extends Parcelable>) appDetailsFromDatabase);
        lowerMainFragment.setArguments(fragmentArguments);
        getFragmentManager().beginTransaction().add(R.id.lower_fragment_container, lowerMainFragment, "lowerFragment").commit();
    }
}

