package com.lftechnology.activitylogger.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lftechnology.activitylogger.Charts.BarChart;

/**
 * Created by sparsha on 7/21/2016.
 */
public class FragmentChartsActivityBarChart extends Fragment {
    BarChart barChart;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        barChart = new BarChart(getActivity());
        return barChart;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
