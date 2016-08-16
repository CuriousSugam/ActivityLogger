package com.lftechnology.activitylogger;

import android.content.Intent;
import android.os.Parcelable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lftechnology.activitylogger.adapter.AllAppsViewPagerAdapter;
import com.lftechnology.activitylogger.fragments.InstalledAppsFragment;
import com.lftechnology.activitylogger.fragments.SystemAppsFragment;
import com.lftechnology.activitylogger.model.AppDetails;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * It is an activity that displays all the installed and system applications of the system.
 */
public class AllAppsActivity extends AppCompatActivity {

    @BindView(R.id.viewpager_all_apps)
    ViewPager viewPager;

    @BindView(R.id.tab_container)
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_apps);
        ButterKnife.bind(this);

        ActionBar actionbar = getSupportActionBar();
        if(actionbar != null){
            actionbar.setElevation(0);
        }
        List<AppDetails> installedAppDetailsList = new ArrayList<>();
        List<AppDetails> systemAppDetailsList = new ArrayList<>();
        Fragment installedAppsFragment, systemAppsFragment;

        Intent intent = getIntent();
        List<AppDetails> appDetailsList = intent.getParcelableArrayListExtra(MainActivity.APP_DETAILS);
        if (appDetailsList == null) {
            appDetailsList = savedInstanceState.getParcelableArrayList(MainActivity.APP_DETAILS);
        }

        for (AppDetails a : appDetailsList) {
            if (a.getApplicationType().equals(RawAppInfo.INSTALLED_APP)) {
                installedAppDetailsList.add(a);
            } else {
                systemAppDetailsList.add(a);
            }
        }

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
        tabLayout.setupWithViewPager(viewPager);

    }
}
