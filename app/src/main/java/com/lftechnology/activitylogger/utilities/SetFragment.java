package com.lftechnology.activitylogger.utilities;

import android.app.usage.UsageStats;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.lftechnology.activitylogger.RawAppInfo;
import com.lftechnology.activitylogger.adapter.CustomAdapterAppDetails;
import com.lftechnology.activitylogger.communicators.CommunicatorEachAppDetailsValues;
import com.lftechnology.activitylogger.model.EachAppDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to setup fragments' time usage in recycler view
 * @author sparsha
 */
public class SetFragment {

    private Context context;

    public SetFragment(Context mContext){
        context = mContext;
    }
    /**
     * Show sorted apps in recycler view
     */
    public void showInSortedList(RecyclerView recyclerView,List<EachAppDetails> eachAppDetails) {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new CustomAdapterAppDetails(context, eachAppDetails));
    }
    /**
     * Pass list of EachAppDetails object to a communicator class
     */
    public void passListToCommunicator(List<EachAppDetails> eachAppDetailsList) {
        CommunicatorEachAppDetailsValues values = new CommunicatorEachAppDetailsValues();
        values.setDetailsList(eachAppDetailsList);
    }

}
