package com.lftechnology.activitylogger.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.lftechnology.activitylogger.AllAppsActivity;
import com.lftechnology.activitylogger.MainActivity;
import com.lftechnology.activitylogger.MobileDataActivity;
import com.lftechnology.activitylogger.R;
import com.lftechnology.activitylogger.TimeActivity;
import com.lftechnology.activitylogger.WifiActivity;
import com.lftechnology.activitylogger.model.AppDetails;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * This Fragment displays the grid menu
 * Created by Sugam Shakya on 7/12/2016.
 */
public class SecondMainFragment extends Fragment {

    @BindView(R.id.ll_menu_wifi)
    LinearLayout menuWifi;

    @BindView(R.id.ll_menu_all_apps)
    LinearLayout menuAllApps;

    @BindView(R.id.ll_menu_mobile_data)
    LinearLayout menuMobileData;

    @BindView(R.id.ll_menu_time)
    LinearLayout menuTime;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.second_main_fragment, container, false);
        ButterKnife.bind(this, view);

        final List<AppDetails> appDetailsList = getArguments().getParcelableArrayList(MainActivity.APP_DETAILS);

        menuWifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), WifiActivity.class));
            }
        });

        menuAllApps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AllAppsActivity.class);
                intent.putParcelableArrayListExtra(MainActivity.APP_DETAILS, (ArrayList<? extends Parcelable>) appDetailsList);
                startActivity(intent);
            }
        });

        menuMobileData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MobileDataActivity.class));
            }
        });

        menuTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), TimeActivity.class));
            }
        });

        return view;
    }
}
