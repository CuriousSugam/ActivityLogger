package com.lftechnology.activitylogger.fragments;

//import android.app.Fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lftechnology.activitylogger.R;
import com.lftechnology.activitylogger.adapter.MainMenuViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.LogRecord;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * This fragment shows the slider of the information like:
 * - time of usage of most used app in the device
 * - application tha used most of the network data

 * This fragment displays the slider of the important information like the most used app of the day,
 * application that used the most network data and other random productivity guidelines.
>>>>>>> dev:app/src/main/java/com/lftechnology/activitylogger/fragments/FirstMainFragment.java
 * <p/>
 * Created by DevilDewzone on 7/11/2016.
 */
public class FirstMainFragment extends Fragment {


    @BindView(R.id.viewpager_main_activity)
    ViewPager informationViewPager;

    @BindView(R.id.first_indicator)
    ImageView first_indicator;

    @BindView(R.id.second_indicator)
    ImageView second_indicator;

    private static final int FIRST_PAGE = 0;
    private static final int SECOND_PAGE = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.first_main_fragment_layout, container, false);
        ButterKnife.bind(this, view);

        Fragment networkDataInfoFragment = new InformationSliderFragment();
        Bundle infoToNetworkInfoFragment = new Bundle();
        infoToNetworkInfoFragment.putString("info", "You have used instagram for 4 hours today");
        networkDataInfoFragment.setArguments(infoToNetworkInfoFragment);

        Fragment timeInfoFragment = new InformationSliderFragment();
        Bundle infoToTimeInfoFragment = new Bundle();
        infoToTimeInfoFragment.putString("info", "Facebook has consumed 500MB of your wifi data");
        timeInfoFragment.setArguments(infoToTimeInfoFragment);

        final MainMenuViewPagerAdapter adapter = new MainMenuViewPagerAdapter(getFragmentManager());
        adapter.addFragment(networkDataInfoFragment);
        adapter.addFragment(timeInfoFragment);

        informationViewPager.setAdapter(adapter);

        final Handler handler = new Handler();
        final Runnable updateInfoThread = new Runnable() {
            @Override
            public void run() {
                int currentItem = informationViewPager.getCurrentItem();
                Log.e("pagerPosition", currentItem + "th item");
                currentItem = currentItem + 1;
                if (currentItem > SECOND_PAGE) {
                    currentItem = FIRST_PAGE;
                }
                informationViewPager.setCurrentItem(currentItem, true);
                switch (informationViewPager.getCurrentItem()){
                    case FIRST_PAGE : first_indicator.setImageResource(R.drawable.current_page_circular_indicator);
                        second_indicator.setImageResource(R.drawable.page_circular_indicator);
                        break;
                    case SECOND_PAGE : first_indicator.setImageResource(R.drawable.page_circular_indicator);
                        second_indicator.setImageResource(R.drawable.current_page_circular_indicator);
                        break;
                }
            }
        };
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(updateInfoThread);
            }
        }, 2000, 4000);
        
        return view;
    }
}
