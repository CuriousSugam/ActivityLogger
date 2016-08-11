package com.lftechnology.activitylogger.fragments;

//import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lftechnology.activitylogger.R;
import com.lftechnology.activitylogger.adapter.MainMenuViewPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by DevilDewzone on 7/11/2016.
 */
public class FirstMainFragment extends Fragment {

    @BindView(R.id.viewpager_main_activity)
    ViewPager informationViewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.first_main_fragment_layout,container,false);
        ButterKnife.bind(this, view);

        Fragment fragment1 = new InformationSliderFragment();
        Fragment fragment2 = new InformationSliderFragment();
        Fragment fragment3 = new InformationSliderFragment();
        Fragment fragment4 = new InformationSliderFragment();

        MainMenuViewPagerAdapter adapter = new MainMenuViewPagerAdapter(getFragmentManager());
        adapter.addFragment(fragment1);
        adapter.addFragment(fragment2);
        adapter.addFragment(fragment3);
        adapter.addFragment(fragment4);

        informationViewPager.setAdapter(adapter);
        return view;
    }

}
