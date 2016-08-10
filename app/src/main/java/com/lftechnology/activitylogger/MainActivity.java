
package com.lftechnology.activitylogger;

import android.app.Fragment;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.Toast;

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
    private boolean doubleBackButtonPressed = false;
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

    @Override
    public void onBackPressed() {
        if(doubleBackButtonPressed){
            super.onBackPressed();
            return;
        }
        this.doubleBackButtonPressed = true;
        Toast.makeText(this,"Press Again To Exit",Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackButtonPressed = false;
            }
        },2400);

    }
}

