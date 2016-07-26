package com.lftechnology.activitylogger.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lftechnology.activitylogger.Adapter.AllSystemAppsRecyclerViewAdapter;
import com.lftechnology.activitylogger.R;
import com.lftechnology.activitylogger.RawAppInfo;
import com.lftechnology.activitylogger.model.AppDetails;

import java.util.List;


public class SystemAppsFragment extends Fragment {

    private Context context;

    public SystemAppsFragment(){}

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        List<AppDetails> appDetailsList = getArguments().getParcelableArrayList(RawAppInfo.SYSTEM_APP);
        View v = inflater.inflate(R.layout.layout_all_apps_fragment, container, false);

        RecyclerView recyclerView = (RecyclerView)v.findViewById(R.id.recycler_view_all_apps);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new GridLayoutManager(context, 4));
        recyclerView.setAdapter(new AllSystemAppsRecyclerViewAdapter(context, appDetailsList));
        return v;
    }
}
