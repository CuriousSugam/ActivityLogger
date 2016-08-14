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
 * <p/>
 * Created by DevilDewzone on 7/11/2016.
 */
public class FirstMainFragment extends Fragment {

    private Context context;

    @BindView(R.id.viewpager_main_activity)
    ViewPager informationViewPager;

//    @BindView(R.id.indicator_container)
//    LinearLayout indicator_container;

    @BindView(R.id.first_indicator)
    ImageView first_indicator;

    @BindView(R.id.second_indicator)
    ImageView second_indicator;

    @BindView(R.id.third_indicator)
    ImageView third_indicator;

    @BindView(R.id.forth_indicator)
    ImageView forth_indicator;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.first_main_fragment_layout, container, false);
        ButterKnife.bind(this, view);

        Fragment fragment1 = new InformationSliderFragment();
        Bundle infoToFragment1 = new Bundle();
        infoToFragment1.putString("info", "You have used instagram for 4 hours today");
        fragment1.setArguments(infoToFragment1);

        Fragment fragment2 = new InformationSliderFragment();
        Bundle infoToFragment2 = new Bundle();
        infoToFragment2.putString("info", "Facebook has consumed 500MB of your wifi data");
        fragment2.setArguments(infoToFragment2);

        Fragment fragment3 = new InformationSliderFragment();
        Bundle infoToFragment3 = new Bundle();
        infoToFragment3.putString("info", "Facebook and viber has consumed 50MB of your mobile data");
        fragment3.setArguments(infoToFragment3);

        Fragment fragment4 = new InformationSliderFragment();
        Bundle infoToFragment4 = new Bundle();
        infoToFragment4.putString("info", "Facebook and insta has consumed 50MB of your mobile data");
        fragment4.setArguments(infoToFragment4);

        final MainMenuViewPagerAdapter adapter = new MainMenuViewPagerAdapter(getFragmentManager());
        adapter.addFragment(fragment1);
        adapter.addFragment(fragment2);
        adapter.addFragment(fragment3);
        adapter.addFragment(fragment4);

        informationViewPager.setAdapter(adapter);

//        final List<ImageView> imageViewList = new ArrayList<>();
//        for (int i = 0; i < adapter.getCount(); i++) {
//            imageViewList.add(new ImageView(context));
//        }

        final Handler handler = new Handler();
        final Runnable updateInfoThread = new Runnable() {
            @Override
            public void run() {
                int currentItem = informationViewPager.getCurrentItem();
                Log.e("pagerPosition", currentItem + "th item");
                currentItem = currentItem + 1;
                if (currentItem > 3) {
                    currentItem = 0;
                }
                informationViewPager.setCurrentItem(currentItem, true);
                switch (informationViewPager.getCurrentItem()){
                    case 0 : first_indicator.setImageResource(R.drawable.current_page_circular_indicator);
                        second_indicator.setImageResource(R.drawable.page_circular_indicator);
                        third_indicator.setImageResource(R.drawable.page_circular_indicator);
                        forth_indicator.setImageResource(R.drawable.page_circular_indicator);
                        break;
                    case 1 : first_indicator.setImageResource(R.drawable.page_circular_indicator);
                        second_indicator.setImageResource(R.drawable.current_page_circular_indicator);
                        third_indicator.setImageResource(R.drawable.page_circular_indicator);
                        forth_indicator.setImageResource(R.drawable.page_circular_indicator);
                        break;
                    case 2 : first_indicator.setImageResource(R.drawable.page_circular_indicator);
                        second_indicator.setImageResource(R.drawable.page_circular_indicator);
                        third_indicator.setImageResource(R.drawable.current_page_circular_indicator);
                        forth_indicator.setImageResource(R.drawable.page_circular_indicator);
                        break;
                    case 3 : first_indicator.setImageResource(R.drawable.page_circular_indicator);
                        second_indicator.setImageResource(R.drawable.page_circular_indicator);
                        third_indicator.setImageResource(R.drawable.page_circular_indicator);
                        forth_indicator.setImageResource(R.drawable.current_page_circular_indicator);
                        break;
                }

//                ViewGroup viewGroup;
//                ImageView imageView;
//                for (int i = 0; i < imageViewList.size(); i++) {
//                    imageView = imageViewList.get(i);
//                    if(i == informationViewPager.getCurrentItem()){
//                        imageView.setImageResource(R.drawable.current_page_circular_indicator);
//                    }else{
//                        imageView.setImageResource(R.drawable.page_circular_indicator);
//                    }
//                    indicator_container.addView(imageView);
//                }

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

    @Override
    public void onAttach(Context context) {
        this.context = context;
        super.onAttach(context);
    }
}
