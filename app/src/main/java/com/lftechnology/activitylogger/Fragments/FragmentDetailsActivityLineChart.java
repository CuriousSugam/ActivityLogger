package com.lftechnology.activitylogger.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lftechnology.activitylogger.Charts.LineChart;

/**
 * Created by sparsha on 8/3/2016.
 */
public class FragmentDetailsActivityLineChart extends Fragment {
    LineChart lineChart;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        Intent intent = getActivity().getIntent();
//        String name = intent.getStringExtra("AppName");
        lineChart = new LineChart(getActivity());
        return lineChart;
    }

    @Override
    public void onPause() {
        super.onPause();
        lineChart.pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        lineChart.resume();
    }
}
