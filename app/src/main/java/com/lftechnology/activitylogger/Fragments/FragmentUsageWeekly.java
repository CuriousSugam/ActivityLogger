package com.lftechnology.activitylogger.fragments;

import android.app.usage.UsageStats;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lftechnology.activitylogger.adapter.CustomAdapterAppDetails;
import com.lftechnology.activitylogger.ChartsActivity;
import com.lftechnology.activitylogger.communicators.CommunicatorEachAppDetailsValues;
import com.lftechnology.activitylogger.ConstantIntervals;
import com.lftechnology.activitylogger.model.EachAppDetails;
import com.lftechnology.activitylogger.R;
import com.lftechnology.activitylogger.RawAppInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Displays the usage stats of Apps used weekly In recycler view
 */
public class FragmentUsageWeekly extends Fragment implements View.OnClickListener {
    View view;
    FloatingActionButton chartsButton;
    RecyclerView recyclerView;
    String[] namesOfApp;
    Long[] runTimeOfApp;
    List<EachAppDetails> eachAppDetailsList = new ArrayList<>();
    List<UsageStats> usageStatses;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_usage_tempelate, container, false);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
        sort();
    }

    @Override
    public void onResume() {
        super.onResume();
        chartsButton = (FloatingActionButton) view.findViewById(R.id.buttonShowsCharts);
        chartsButton.setOnClickListener(this);
        recyclerView = (RecyclerView) view.findViewById(R.id.customAppDetailsRecyclerView);
        showInSortedList();

    }

    /**
     * Get the list of usageStats object and separate the package name and runtime of app
     */
    public void initialize() {
        int i = 0;
        RawAppInfo rawAppInfo = new RawAppInfo();
        usageStatses = rawAppInfo.printCurrentUsageStats(getActivity(), ConstantIntervals.WEEKLY.value);
        namesOfApp = new String[usageStatses.size()];
        runTimeOfApp = new Long[usageStatses.size()];

        for (UsageStats stats : usageStatses) {
            namesOfApp[i] = stats.getPackageName();
            runTimeOfApp[i] = stats.getTotalTimeInForeground();
            i++;
        }
    }

    /**
     * Sort the list of usage of apps with time
     */
    public void sort() {
        for (int i = 0; i < namesOfApp.length && i < runTimeOfApp.length; i++) {
            for (int j = 0; j < i; j++) {
                if (runTimeOfApp[j] < runTimeOfApp[i]) {
                    String tempName = namesOfApp[i];
                    long tempRunTime = runTimeOfApp[i];
                    namesOfApp[i] = namesOfApp[j];
                    runTimeOfApp[i] = runTimeOfApp[j];
                    namesOfApp[j] = tempName;
                    runTimeOfApp[j] = tempRunTime;
                }
            }
        }
    }

    /**
     * Show sorted apps in recycler view
     */
    public void showInSortedList() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new CustomAdapterAppDetails(getContext(), getData()));
    }

    public boolean PackageExists(String mPackageName) {
        PackageManager pm;
        pm = getContext().getPackageManager();
        try {
            ApplicationInfo applicationInfo = pm.getApplicationInfo(mPackageName, 0);
            if (((applicationInfo.flags & ApplicationInfo.FLAG_INSTALLED) != 1) &&
                    (applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 1) {
                //i.e. if the application is installed and is not a system app
                return true;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return false;

    }

    /**
     * Show sorted apps in recycler view
     */
    public List<EachAppDetails> getData() {
        if(!eachAppDetailsList.isEmpty())
            return eachAppDetailsList;

        try {

            for (int i = 0; i < namesOfApp.length && i < runTimeOfApp.length; i++) {

                EachAppDetails current = new EachAppDetails();
                if (PackageExists(namesOfApp[i])) {
                    ApplicationInfo applicationInfo = getActivity().getPackageManager().getApplicationInfo(namesOfApp[i], 0);
                    current.eachAppName = String.valueOf(getActivity().getPackageManager().getApplicationLabel(applicationInfo));
                    current.eachAppUsageDuration = runTimeOfApp[i];
                    Drawable icon = getActivity().getPackageManager().getApplicationIcon(applicationInfo);
                    current.eachAppIcon = icon;
                    boolean skip = false;
                    for (EachAppDetails eachAppDetails : eachAppDetailsList) {
                        if (current.eachAppName.equals(eachAppDetails.eachAppName))
                            skip = true;
                    }
                    if(skip)
                        continue;
                    eachAppDetailsList.add(current);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return eachAppDetailsList;
    }

    @Override
    public void onClick(View view) {
        passListToCommunicator();
        Intent intent = new Intent(getActivity(), ChartsActivity.class);
        startActivity(intent);

    }

    /**
     * Pass list of EachAppDetails object to a communicator class
     */
    private void passListToCommunicator() {
        CommunicatorEachAppDetailsValues values = new CommunicatorEachAppDetailsValues();
        values.setDetailsList(eachAppDetailsList);
    }
}
