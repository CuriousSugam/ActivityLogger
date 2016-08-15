
package com.lftechnology.activitylogger;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lftechnology.activitylogger.adapter.ViewPagerAdapter;
import com.lftechnology.activitylogger.fragments.FragmentUsageDaily;
import com.lftechnology.activitylogger.fragments.FragmentUsageMonthly;
import com.lftechnology.activitylogger.fragments.FragmentUsageWeekly;
import com.lftechnology.activitylogger.fragments.FragmentUsageYearly;

public class TimeActivity extends AppCompatActivity {
    ViewPager viewPager;
    TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkPermissionForAccess();
        setContentView(R.layout.activity_time);
        viewPager = (ViewPager)findViewById(R.id.chartsViewPager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout)findViewById(R.id.fragmentTabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentUsageDaily(),"Daily");
        adapter.addFragment(new FragmentUsageWeekly(),"Weekly");
        adapter.addFragment(new FragmentUsageMonthly(),"Monthly");
        adapter.addFragment(new FragmentUsageYearly(),"Yearly");
        viewPager.setAdapter(adapter);
    }

    private void checkPermissionForAccess(){
        if(RawAppInfo.getUsageStatsAppList(this).isEmpty()){
            startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
            finish();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
