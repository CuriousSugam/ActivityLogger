package com.lftechnology.activitylogger;

import android.content.Intent;
import android.os.Parcelable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lftechnology.activitylogger.Adapter.AllAppsViewPagerAdapter;
import com.lftechnology.activitylogger.Fragments.InstalledAppsFragment;
import com.lftechnology.activitylogger.Fragments.SystemAppsFragment;
import com.lftechnology.activitylogger.model.AppDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * It is an activity that displays all the installed and system applications of the system.
 */
public class AllAppsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_apps);

        ViewPager viewPager;
        TabLayout tabLayout;
        List<AppDetails> installedAppDetailsList = new ArrayList<>();
        List<AppDetails> systemAppDetailsList = new ArrayList<>();
        Fragment installedAppsFragment, systemAppsFragment;

        Intent intent = getIntent();
        List<AppDetails> appDetailsList = intent.getParcelableArrayListExtra("appDetails");
        if (appDetailsList == null) {
            appDetailsList = savedInstanceState.getParcelableArrayList("appDetails");
        }

        for (AppDetails a : appDetailsList) {
            if (a.getApplicationType().equals(RawAppInfo.INSTALLED_APP)) {
                installedAppDetailsList.add(a);
            } else {
                systemAppDetailsList.add(a);
            }
        }

        // get the reference to the viewpager
        viewPager = (ViewPager) findViewById(R.id.viewpager_all_apps);
        // setup the viewpager
        AllAppsViewPagerAdapter viewPagerAdapter = new AllAppsViewPagerAdapter(getSupportFragmentManager());
        installedAppsFragment = new InstalledAppsFragment();
        systemAppsFragment = new SystemAppsFragment();

        // create a bundle to pass the arraylist to the fragments
        Bundle installedAppBundle = new Bundle();
        installedAppBundle.putParcelableArrayList(RawAppInfo.INSTALLED_APP, (ArrayList<? extends Parcelable>) installedAppDetailsList);
        installedAppsFragment.setArguments(installedAppBundle);

        Bundle systemAppBundle = new Bundle();
        systemAppBundle.putParcelableArrayList(RawAppInfo.SYSTEM_APP, (ArrayList<? extends Parcelable>) systemAppDetailsList);
        systemAppsFragment.setArguments(systemAppBundle);

        viewPagerAdapter.addFragment(installedAppsFragment, "Installed Apps");
        viewPagerAdapter.addFragment(systemAppsFragment, "System Apps");

        viewPager.setAdapter(viewPagerAdapter);
        // get the reference to the tablayout and setup it with viewpager
        tabLayout = (TabLayout) findViewById(R.id.tab_container);
        tabLayout.setupWithViewPager(viewPager);

    }
}
