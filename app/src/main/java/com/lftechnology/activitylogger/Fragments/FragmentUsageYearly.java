package com.lftechnology.activitylogger.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lftechnology.activitylogger.ChartsActivity;
import com.lftechnology.activitylogger.communicators.CommunicatorEachAppDetailsValues;
import com.lftechnology.activitylogger.Constants;
import com.lftechnology.activitylogger.model.EachAppDetails;
import com.lftechnology.activitylogger.R;
import com.lftechnology.activitylogger.utilities.SetFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Displays the usage stats of Apps used yearly In recycler view
 */
public class FragmentUsageYearly extends Fragment implements View.OnClickListener{
    private View view;
    private FloatingActionButton chartsButton;
    private RecyclerView recyclerView;
    SetFragment setFragment;
    List<EachAppDetails> eachAppDetailsList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_usage_tempelate, container, false);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setFragment = new SetFragment(getActivity(),Constants.DAILY.value);
//        setFragment.initialize();
//        setFragment.sort();
        setFragment = new SetFragment(getActivity());
        eachAppDetailsList = new CommunicatorEachAppDetailsValues().getEachAppDetailsListYearly();
    }

    @Override
    public void onResume() {
        super.onResume();
        chartsButton = (FloatingActionButton) view.findViewById(R.id.buttonShowsCharts);
        chartsButton.setOnClickListener(this);
        recyclerView = (RecyclerView) view.findViewById(R.id.customAppDetailsRecyclerView);
        setFragment.showInSortedList(recyclerView,eachAppDetailsList);
    }

    @Override
    public void onClick(View view) {
        setFragment.passListToCommunicator(eachAppDetailsList);
        Intent intent = new Intent(getActivity(), ChartsActivity.class);
        startActivity(intent);

    }
}
