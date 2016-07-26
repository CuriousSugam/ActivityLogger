package com.lftechnology.activitylogger.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import com.lftechnology.activitylogger.Adapters.CustomAdapterAppDetails;
import com.lftechnology.activitylogger.ChartsActivity;
import com.lftechnology.activitylogger.ConstantIntervals;
import com.lftechnology.activitylogger.EachAppDetails;
import com.lftechnology.activitylogger.R;
import com.lftechnology.activitylogger.RawAppInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sparsha on 7/26/2016.
 */
public class FragmentUsageMonthly extends Fragment implements View.OnClickListener {
    View view;
    Button chartsButton;
    RecyclerView recyclerView;
    String[] namesOfApp,mostUsedApps, details;
    Long[] runTimeOfApp;
    List<EachAppDetails> eachAppDetailsList = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_usage_tempelate,container,false);
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        chartsButton = (Button) view.findViewById(R.id.buttonShowsCharts);
        chartsButton.setOnClickListener(this);
        recyclerView = (RecyclerView)view.findViewById(R.id.customAppDetailsRecyclerView);
        initialize();
        sort();
        showInSortedList();
        putTopFiveInDB();
    }
    public void initialize()
    {
        RawAppInfo.printCurrentUsageStats(getActivity(), ConstantIntervals.MONTHLY.value);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("appName", Context.MODE_PRIVATE);
        namesOfApp = new String[sharedPreferences.getInt("count",1)];
        runTimeOfApp = new Long[sharedPreferences.getInt("count",1)];
        details = new String[sharedPreferences.getInt("count", 1)];
        //Set the name of app and its corresponding foregroundRunning time
        for(int i =0; i< sharedPreferences.getInt("count",1);i++){
            namesOfApp[i] = sharedPreferences.getString("packageName"+i,"N/A");
            runTimeOfApp[i] = sharedPreferences.getLong("runtime"+i,0);

        }
    }
    public void sort(){
        for(int i=0;i<namesOfApp.length && i< runTimeOfApp.length;i++){
            for(int j=0; j<i;j++){
                if(runTimeOfApp[j]<runTimeOfApp[i]){
                    String tempName = namesOfApp[i];
                    long tempRunTime = runTimeOfApp[i];
                    namesOfApp[i] = namesOfApp[j];
                    runTimeOfApp[i] = runTimeOfApp[j];
                    namesOfApp[j] = tempName;
                    runTimeOfApp[j] = tempRunTime;
                }
            }
        }
        for (int i = 0; i<namesOfApp.length && i<runTimeOfApp.length;i++){
            details[i] = namesOfApp[i] + " Runtime: "+
                    Long.toString(runTimeOfApp[i]/1000/3600)+":"+Long.toString(((runTimeOfApp[i]/1000)%3600)/60)+":"
                    +Long.toString((runTimeOfApp[i]/1000)%60);
        }
    }
    public void showInSortedList(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new CustomAdapterAppDetails(getContext(),getData()));
    }
    public boolean PackageExists(String mPackageName){
        PackageManager pm;
        pm = getContext().getPackageManager();
        try {
            ApplicationInfo applicationInfo = pm.getApplicationInfo(mPackageName,0);
            if(((applicationInfo.flags & ApplicationInfo.FLAG_INSTALLED)!=1)&&
                    (applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM)!=1){
                return true;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return false;

    }

    private void putTopFiveInDB() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Top5Apps", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int count = 0;
        for (int i = 0; i < namesOfApp.length; i++) {
            if(PackageExists(namesOfApp[i])){
                editor.putLong("AppDuration" + count, runTimeOfApp[i]);
                editor.putString("AppName"+count,namesOfApp[i]);
                count++;
            }
            if (count == 5)
                break;
        }
        editor.apply();
    }
    public  List<EachAppDetails> getData(){
        eachAppDetailsList.clear();

        try{

            for(int i=0;i< namesOfApp.length && i< runTimeOfApp.length;i++){

                EachAppDetails current = new EachAppDetails();
                if(PackageExists(namesOfApp[i])){
                    ApplicationInfo applicationInfo = getActivity().getPackageManager().getApplicationInfo(namesOfApp[i],0);
                    current.eachAppName = String.valueOf(getActivity().getPackageManager().getApplicationLabel(applicationInfo));
                    current.eachAppUsageDuration = Long.toString(runTimeOfApp[i]/1000/3600)+":"+Long.toString(((runTimeOfApp[i]/1000)%3600)/60)+":"
                            +Long.toString((runTimeOfApp[i]/1000)%60);
                    Drawable icon =getActivity().getPackageManager().getApplicationIcon(applicationInfo);
                    current.eachAppIcon = icon;
                    eachAppDetailsList.add(current);
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return eachAppDetailsList;
    }
    @Override
    public void onClick(View view) {
        startActivity(new Intent(getActivity(),ChartsActivity.class));

    }

}
