package com.lftechnology.activitylogger;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.lftechnology.activitylogger.Services.ConnectivityChangeMonitoringIntentService;
import com.lftechnology.activitylogger.model.AppDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DevilDewzone on 7/12/2016.
 */
public class SecondMainFragment extends Fragment {
    private Context context;
    Button btnWifi, btnAllApps;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.second_main_fragment, container, false);
        btnWifi = (Button) view.findViewById(R.id.btn_wifi);
        btnWifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), WifiActivity.class));
            }
        });
        btnAllApps = (Button) view.findViewById(R.id.btn_all_apps);
        btnAllApps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    startActivity(new Intent(getActivity(), AllApps.class));
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
}
