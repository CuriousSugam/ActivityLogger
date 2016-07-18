package com.lftechnology.activitylogger;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.graphics.drawable.Drawable;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.content.pm.PackageManager;
import java.util.ArrayList;
import java.util.List;

public class LoggerActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Spinner spinner;
    ArrayAdapter<CharSequence> intervalAdapter;
    String[] namesOfApp,mostUsedApps, details;
    Long[] runTimeOfApp;
    List<EachAppDetails> eachAppDetailsList = new ArrayList<>();
    List<String> dummyList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logger);
        checkPermissionForAccess();
        spinner = (Spinner) findViewById(R.id.spinnerInterval);
        intervalAdapter = ArrayAdapter.createFromResource(this,R.array.interval,android.R.layout.simple_spinner_item);
        intervalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(intervalAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        getStatsDaily(view);
                        break;
                    case 1:
                        getStatsWeekly(view);
                        break;
                    case 2:
                        getStatsMonthly(view);
                        break;
                    case 3:
                        getStatsYearly(view);
                        break;
                    case 4:
                        getStatsFromBeginning(view);
                        break;
                    case 5:
                        startActivity(new Intent(LoggerActivity.this,ShowsTop5AppsActivity.class));
                    default:
                        Toast.makeText(LoggerActivity.this,"What just happened?",Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public void getStatsDaily(View view) {
        RawAppInfo.printCurrentUsageStats(this, ConstantIntervals.DAILY.value);
        initialize();
        sort();
        putTopFiveInDB();
        showInSortedList();
    }

    public void getStatsWeekly(View view) {
        RawAppInfo.printCurrentUsageStats(this, ConstantIntervals.WEEKLY.value);
        initialize();
        sort();
        putTopFiveInDB();
        showInSortedList();
    }

    public void getStatsMonthly(View view){
        RawAppInfo.printCurrentUsageStats(this, ConstantIntervals.MONTHLY.value);
        initialize();
        sort();
        putTopFiveInDB();
        showInSortedList();
    }
    public void getStatsYearly(View view){
        RawAppInfo.printCurrentUsageStats(this,ConstantIntervals.YEARLY.value);
        initialize();
        sort();
        putTopFiveInDB();
        showInSortedList();
    }

    public void getStatsFromBeginning(View view) {
        RawAppInfo.printCurrentUsageStats(this, ConstantIntervals.BEST.value);
        initialize();
        sort();
        putTopFiveInDB();
        showInSortedList();
    }



    public  List<EachAppDetails> getData(){
        eachAppDetailsList.clear();

        try{

            for(int i=0;i< namesOfApp.length && i< runTimeOfApp.length;i++){

                EachAppDetails current = new EachAppDetails();
                if(PackageExists(namesOfApp[i])){
                    ApplicationInfo applicationInfo = getPackageManager().getApplicationInfo(namesOfApp[i],0);
                    current.eachAppName = String.valueOf(getPackageManager().getApplicationLabel(applicationInfo));
                    current.eachAppUsageDuration = Long.toString(runTimeOfApp[i]/1000/3600)+":"+Long.toString(((runTimeOfApp[i]/1000)%3600)/60)+":"
                            +Long.toString((runTimeOfApp[i]/1000)%60);
                    Drawable icon =getPackageManager().getApplicationIcon(applicationInfo);
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
        recyclerView = (RecyclerView) findViewById(R.id.customAppDetailsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new CustomAdapterAppDetails(this,getData()));
    }
    public boolean PackageExists(String mPackageName){
        PackageManager pm;
        pm = getPackageManager();
        if(mPackageName=="com.sonyericsson.music" || mPackageName =="com.android.chrome"){
            return true;
        }
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
        SharedPreferences sharedPreferences = getSharedPreferences("Top5Apps", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for (int i = 0; i < 5; i++) {
            editor.putLong("App" + i, runTimeOfApp[i]);
        }
        editor.apply();
    }
    @Override
    protected void onDestroy() {
        SharedPreferences sharedPreferences = getSharedPreferences("appName", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        super.onDestroy();
    }

    private void checkPermissionForAccess(){
        if(RawAppInfo.getUsageStatsAppList(this).isEmpty())
            startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
    }


}
