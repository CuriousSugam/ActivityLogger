package com.lftechnology.activitylogger;

//import android.content.Intent;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentTransaction;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
//import android.view.View;

public class MainActivity extends AppCompatActivity {
//    ViewPager viewPager;
//    TopSlider topSlider;
   // LinearLayout l;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("check", "welcome to main activity");

//        FirstMainFragment frag = new FirstMainFragment();
//        SecondMainFragment frag2 = new SecondMainFragment();
//
//
//        FragmentManager fragmentManager = getFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//
//        fragmentTransaction.add(R.id.topfragment, frag);
//        fragmentTransaction.commit();
//
//        //l = (LinearLayout)findViewById(R.id.bottom_grid);
//
//        FragmentManager fragmentManager1 = getFragmentManager();
//        FragmentTransaction fragmentTransaction1 = fragmentManager1.beginTransaction();
//
//        fragmentTransaction1.add(R.id.bottomfragment, frag2);
//        fragmentTransaction1.commit();
//
//        viewPager = (ViewPager)findViewById(R.id.slide_pager);
//        topSlider= new TopSlider(this);
//        viewPager.setAdapter(topSlider);

    }

}

