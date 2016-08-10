
package com.lftechnology.activitylogger;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lftechnology.activitylogger.Adapter.ViewPagerAdapter;
import com.lftechnology.activitylogger.Fragments.FragmentUsageDaily;
import com.lftechnology.activitylogger.Fragments.FragmentUsageMonthly;
import com.lftechnology.activitylogger.Fragments.FragmentUsageWeekly;
import com.lftechnology.activitylogger.Fragments.FragmentUsageYearly;

public class TimeActivity extends AppCompatActivity {
    ViewPager viewPager;
    TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        checkPermissionForAccess();
        setContentView(R.layout.activity_time);
        viewPager = (ViewPager)findViewById(R.id.chartsViewPager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout)findViewById(R.id.fragmentTabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentUsageDaily(),"Today");
        adapter.addFragment(new FragmentUsageWeekly(),"Last Week");
        adapter.addFragment(new FragmentUsageMonthly(),"Last Month");
        adapter.addFragment(new FragmentUsageYearly(),"Last Year");
        viewPager.setAdapter(adapter);
    }

//    private void checkPermissionForAccess(){
//        if(new RawAppInfo().getUsageStatsAppList(this).isEmpty()){
//            startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
//            finish();
//        }
//    }
    @Override
    protected void onDestroy() {
        SharedPreferences sharedPreferences = getSharedPreferences("appName", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        super.onDestroy();
    }
}
