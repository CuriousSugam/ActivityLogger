package com.lftechnology.activitylogger;

import android.app.Activity;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.CardView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by DevilDewzone on 8/12/2016.
 */
public class AllAppsDetailActivity extends Activity {

    private RawAppInfo rawAppInfo;


    @BindView(R.id.app_icon)
    ImageView appIcon;

    @BindView(R.id.installed_date)
    TextView installedDate;

    @BindView(R.id.total_data_used)
    TextView totalDataUsed;

    @BindView(R.id.total_time_used)
    TextView totalTimeUsed;

    @BindView(R.id.last_time_used)
    TextView lastTimeUsed;

    @BindView(R.id.app_name)
    TextView appName;


    Intent packageNameIntent = new Intent();

    String packageName = packageNameIntent.getData().getSchemeSpecificPart();

    // Drawable icon =(Drawable) getContext().getPackageManager().getApplicationIcon(packageIcon);

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.card_view_app_details_layout);
        ButterKnife.bind(this);
        try {
            Drawable icon = getPackageManager().getApplicationIcon(packageName);
            //appIcon.setImageDrawable(icon);
//            if(RawAppInfo.INSTALLED_APP.equals(appIcon)|| RawAppInfo.SYSTEM_APP.equals(appIcon)) {
                appIcon.setImageDrawable(icon);
            //}
        } catch (PackageManager.NameNotFoundException e) {
            return;
        }

        final PackageManager name = getApplicationContext().getPackageManager();
        ApplicationInfo info;
        try {
            info = name.getApplicationInfo(this.getPackageName(), 0);

        } catch (final PackageManager.NameNotFoundException e) {
            info = null;
        }
        final String applicationName = (String) (info != null ? name.getApplicationLabel(info) : "Unknown");


//        if (RawAppInfo.INSTALLED_APP.equals(appName) || RawAppInfo.SYSTEM_APP.equals(appName)){
            appName.setText(applicationName);




        //lastTimeUsed.setText();


        //to get installed date

        PackageManager date = getPackageManager();
        ApplicationInfo dateInfo = null;
        try {
            dateInfo = date.getApplicationInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String appFile = dateInfo.sourceDir;
        int appDate = (int) new File(appFile).lastModified();
        installedDate.setText(appDate);


    }

    //to get installed time another method

    public static long getAppFirstInstallTime(Context context) {
        PackageInfo packageInfo;
        try {
            if (Build.VERSION.SDK_INT > 10) {
                packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                return packageInfo.firstInstallTime;
            } else {
                ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 0);
                String appFile = appInfo.sourceDir;
                return new File(appFile).lastModified();
            }
        } catch (PackageManager.NameNotFoundException e) {
            return 0;
        }
    }



//    UsageStatsManager usageStatsManager = getUsageStatsManager(context);
//    Calendar calendar = Calendar.getInstance();
//    long endTime = calendar.getTimeInMillis();
//    calendar.add(Calendar.YEAR, -1);
//    long startTime = calendar.getTimeInMillis();
//    Log.d("LOG","Date Start:\t"+ DATE_FORMAT.format(startTime));//TODO remove
//    Log.d("LOG","Date End:\t"+ DATE_FORMAT.format(endTime));//TODO remove
//    List<UsageStats> usageStatsList =
//            usageStatsManager.queryUsageStats(interval,startTime,endTime);//UsageStats Queried here
//    return usageStatsList;


}



