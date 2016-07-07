package com.lftechnology.activitylogger;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.lftechnology.activitylogger.Controller.SQLiteAccessLayer;
import com.lftechnology.activitylogger.model.AppDetails;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayAdapter<String> adapter;
    String[] namesOfApp,mostUsedApps, details;
    Long[] runTimeOfApp;
    //static int intervals;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.nameOfApp);
        checkPermissionForAccess();
    }

    public void getStatsDaily(View view) {
        RawAppInfo.printCurrentUsageStats(this, ConstantIntervals.DAILY.value);
        initialize();
        sort();
        showInSortedList();
    }

    public void getStatsWeekly(View view) {
        RawAppInfo.printCurrentUsageStats(this, ConstantIntervals.WEEKLY.value);
        initialize();
        sort();
        showInSortedList();
    }

    public void getStatsFromBeginning(View view) {
        RawAppInfo.printCurrentUsageStats(this, ConstantIntervals.BEST.value);
        initialize();
        sort();
        showInSortedList();
    }

    public void getStatsMonthly(View view){
        RawAppInfo.printCurrentUsageStats(this, ConstantIntervals.MONTHLY.value);
        initialize();
        sort();
        showInSortedList();
    }
    public void getStatsYearly(View view){
        RawAppInfo.printCurrentUsageStats(this,ConstantIntervals.YEARLY.value);
        initialize();
        sort();
        showInSortedList();
    }

    /**
     * unecessary to use this use showInSortedList
     */
    public void showInList() {
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, namesOfApp);
        listView.setAdapter(adapter);

    }
    public void showInSortedList(){
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,details);
        listView.setAdapter(adapter);
    }

    public void sort(){
        SharedPreferences sharedPreferences = getSharedPreferences("appName", Context.MODE_PRIVATE);
        for(int i =0; i< sharedPreferences.getInt("count",1);i++){
            namesOfApp[i] = sharedPreferences.getString("packageName"+i,"N/A");
            runTimeOfApp[i] = sharedPreferences.getLong("runtime"+i,0);
        }
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
    public void initialize(){
        SharedPreferences sharedPreferences = getSharedPreferences("appName", Context.MODE_PRIVATE);
        namesOfApp = new String[sharedPreferences.getInt("count",1)];
        runTimeOfApp = new Long[sharedPreferences.getInt("count",1)];
        details = new String[sharedPreferences.getInt("count", 1)];
        //Set the name of app and its corresponding foregroundRunning time
        for(int i =0; i< sharedPreferences.getInt("count",1);i++){
            namesOfApp[i] = sharedPreferences.getString("packageName"+i,"N/A");
            runTimeOfApp[i] = sharedPreferences.getLong("runtime"+i,0);

        }
    }

    @Override
    protected void onDestroy() {
        SharedPreferences sharedPreferences = getSharedPreferences("appName", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        super.onDestroy();
    }

    /**
     * if doesnot you havent enabled permission, settings opens for granting permission access with this
     */
    private void checkPermissionForAccess(){
        if(RawAppInfo.getUsageStatsAppList(this).isEmpty())
            startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
    }
}