package com.lftechnology.activitylogger;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lftechnology.activitylogger.adapter.ViewPagerAdapter;
import com.lftechnology.activitylogger.fragments.FragmentChartsActivityBarChart;
import com.lftechnology.activitylogger.fragments.FragmentChartsActivityPieChart;

public class ChartsActivity extends AppCompatActivity{
    ViewPager viewPager;
    TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charts);

        viewPager = (ViewPager)findViewById(R.id.upperChartsViewPagerFragment);
        setupViewPager(viewPager);

        tabLayout = (TabLayout)findViewById(R.id.fragmentChartTabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentChartsActivityBarChart(),"Bar Chart");
        adapter.addFragment(new FragmentChartsActivityPieChart(),"PieChart");
        viewPager.setAdapter(adapter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
