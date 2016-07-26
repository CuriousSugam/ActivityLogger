package com.lftechnology.activitylogger;

import android.app.FragmentManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Parcel;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.lftechnology.activitylogger.Adapters.ViewPagerAdapter;
import com.lftechnology.activitylogger.Charts.PieChart;
import com.lftechnology.activitylogger.Fragments.FragmentTop5ActivityBarChart;
import com.lftechnology.activitylogger.Fragments.FragmentTop5ActivityPieChart;
import com.lftechnology.activitylogger.Interfaces.ChartsFragmentsCommunicator;

public class ChartsActivity extends AppCompatActivity{
    ViewPager viewPager;
    TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shows_top5_apps);

        viewPager = (ViewPager)findViewById(R.id.chartsViewPager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout)findViewById(R.id.fragmentTabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentTop5ActivityBarChart(),"Bar Chart");
        adapter.addFragment(new FragmentTop5ActivityPieChart(),"PieChart");
        viewPager.setAdapter(adapter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
