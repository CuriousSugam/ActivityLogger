package com.lftechnology.activitylogger.Fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.lftechnology.activitylogger.AllAppsActivity;
import com.lftechnology.activitylogger.MainActivity;
import com.lftechnology.activitylogger.MobileDataActivity;
import com.lftechnology.activitylogger.R;
import com.lftechnology.activitylogger.TimeActivity;
import com.lftechnology.activitylogger.WifiActivity;
import com.lftechnology.activitylogger.Model.AppDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * This Fragment displays the grid menu
 * Created by Sugam Shakya on 7/12/2016.
 */
public class SecondMainFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.second_main_fragment, container, false);
        final List<AppDetails> appDetailsList = getArguments().getParcelableArrayList(MainActivity.APP_DETAILS);
        TextView txtWifi = (TextView) view.findViewById(R.id.menu_wifi);
        txtWifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), WifiActivity.class));
            }
        });
        TextView txtAllApps = (TextView) view.findViewById(R.id.menu_all_apps);
        txtAllApps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AllAppsActivity.class);
                intent.putParcelableArrayListExtra(MainActivity.APP_DETAILS, (ArrayList<? extends Parcelable>) appDetailsList);
                startActivity(intent);
            }
        });
        TextView  txtMobileData= (TextView) view.findViewById(R.id.menu_mobile_data);
        txtMobileData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MobileDataActivity.class));
            }
        });
        TextView txtTime = (TextView) view.findViewById(R.id.menu_time);
        txtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), TimeActivity.class));
            }
        });
        return view;
    }
}
