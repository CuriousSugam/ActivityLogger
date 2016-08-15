package com.lftechnology.activitylogger.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lftechnology.activitylogger.R;
import com.lftechnology.activitylogger.adapter.CustomAdapterAppDetails;
import com.lftechnology.activitylogger.communicators.CommunicatorEachAppDetailsValues;

/**
 * Created by sparsha on 8/11/2016.
 */
public class FragmentChartDetails extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_fragment_chart_details, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        RecyclerView recyclerView = (RecyclerView)getActivity().findViewById(R.id.chartDetailsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new CustomAdapterAppDetails(getContext(),
                new CommunicatorEachAppDetailsValues().getEachAppDetailsList()));
    }
}
