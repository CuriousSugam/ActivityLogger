package com.lftechnology.activitylogger;

import android.app.usage.UsageStats;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;

import com.lftechnology.activitylogger.communicators.CommunicatorEachAppDetailsValues;
import com.lftechnology.activitylogger.controller.SQLiteAccessLayer;
import com.lftechnology.activitylogger.model.AppDetails;
import com.lftechnology.activitylogger.model.EachAppDetails;
import com.lftechnology.activitylogger.model.NetworkUsageSummary;
import com.lftechnology.activitylogger.utilities.CheckPermissions;

import java.util.ArrayList;
import java.util.List;

/**
 * This activity shows the splash screen containing the logo of the applications to cover up the delay
 * while the necessary data are being fetched from the system.
 *
 * Created by Sugam on 7/5/2016.
 */
public class SplashScreenActivity extends AppCompatActivity {
    private String[] namesOfApp;
    private Long[] runTimeOfApp;
    private List<EachAppDetails> eachAppDetailsListDaily,eachAppDetailsListWeekly,eachAppDetailsListMonthly,eachAppDetailsListYearly;
    private List<UsageStats> usageStatsListDaily,usageStatsListWeekly,usageStatsListMonthly,usageStatsListYearly;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!CheckPermissions.isPermissionForAccessSet(this)) {
            // TODO Add the code that informs the user that the permission needed for this app is to be set using a popup window
            finish();
            startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
        } else {
            List<AppDetails> appDetailsFromDatabase = getAppDetailsFromDatabase();
            NetworkUsageSummary wifiUsageSummary = new NetworkUsageSummary(this, Constants.WIFI_NETWORK);
            NetworkUsageSummary mobileDataUsageSummary = new NetworkUsageSummary(this, Constants.MOBILE_NETWORK);

            usageStatsListDaily = RawAppInfo.printCurrentUsageStats(this, Constants.DAILY.value);
            usageStatsListWeekly = RawAppInfo.printCurrentUsageStats(this, Constants.WEEKLY.value);
            usageStatsListMonthly = RawAppInfo.printCurrentUsageStats(this,Constants.MONTHLY.value);
            usageStatsListYearly = RawAppInfo.printCurrentUsageStats(this,Constants.YEARLY.value);

            Intent intent = new Intent(this, MainActivity.class);

            initialize(usageStatsListDaily);
            sort();
            eachAppDetailsListDaily = getData();

            initialize(usageStatsListWeekly);
            sort();
            eachAppDetailsListWeekly = getData();

            initialize(usageStatsListMonthly);
            sort();
            eachAppDetailsListMonthly = getData();

            initialize(usageStatsListYearly);
            sort();
            eachAppDetailsListYearly = getData();

            CommunicatorEachAppDetailsValues values = new CommunicatorEachAppDetailsValues();
            values.setEachAppDetailsListEveryInterval(eachAppDetailsListDaily,eachAppDetailsListWeekly
                    ,eachAppDetailsListMonthly,eachAppDetailsListYearly);

            if(!wifiUsageSummary.getNetworkUsageDetailsList().isEmpty()){
                intent.putParcelableArrayListExtra(
                        MainActivity.MOST_WIFI_USED_APP,
                        (ArrayList<? extends Parcelable>) wifiUsageSummary.getNetworkUsageDetailsList());
                intent.putExtra(MainActivity.TOTAL_WIFI_DATA, wifiUsageSummary.getTotal());
            }
            if(!mobileDataUsageSummary.getNetworkUsageDetailsList().isEmpty()){
                intent.putParcelableArrayListExtra(MainActivity.MOST_DATA_USED_APP,
                        (ArrayList<? extends Parcelable>) mobileDataUsageSummary.getNetworkUsageDetailsList());
                intent.putExtra(MainActivity.TOTAL_MOBILE_DATA, mobileDataUsageSummary.getTotal());
            }

            startActivity(intent);
            finish();
        }
    }


    /**
     * This method first checks if the app details has been added to database. If yes, the data from
     * the database if fetched and returned. If the data has not been added to database, it first adds
     * the data to the database and then returns it.
     *
     * @return a List of AppDetails objects containing the data of applications i.e List<AppDetails>
     */
    private List<AppDetails> getAppDetailsFromDatabase() {
        SQLiteAccessLayer sqLiteAccessLayer = new SQLiteAccessLayer(this);
        List<AppDetails> appDetailsList = new ArrayList<>();
        PackageManager packageManager = getPackageManager();

        if (sqLiteAccessLayer.isDatabaseEmpty()) { // database empty
            List<PackageInfo> installedApps = RawAppInfo.getAllInstalledApps(this);
            List<ResolveInfo> systemApps = RawAppInfo.getSystemApps(this);

            for (PackageInfo packageInfo : installedApps) {
                AppDetails appDetails = new AppDetails(
                        packageInfo.applicationInfo.uid,
                        packageInfo.applicationInfo.packageName,
                        String.valueOf(packageInfo.applicationInfo.loadLabel(packageManager)),
                        RawAppInfo.INSTALLED_APP
                );

                SQLiteAccessLayer sqLiteAccessLayerForInsert = new SQLiteAccessLayer(this, appDetails);
                sqLiteAccessLayerForInsert.insertIntoAppDetails();
                appDetailsList.add(appDetails);
            }
            for (ResolveInfo resolveInfo : systemApps) {
                ApplicationInfo applicationInfo = resolveInfo.activityInfo.applicationInfo;
                AppDetails appDetails = new AppDetails(
                        applicationInfo.uid,                                         //uid
                        applicationInfo.packageName,                                 //packagename
                        String.valueOf(applicationInfo.loadLabel(packageManager)),  // applicationName
                        RawAppInfo.SYSTEM_APP                                       //applicationType
                );
                SQLiteAccessLayer sqLiteAccessLayerForInsert = new SQLiteAccessLayer(this, appDetails);
                sqLiteAccessLayerForInsert.insertIntoAppDetails();
                appDetailsList.add(appDetails);
            }
        } else {
            appDetailsList = new ArrayList<>(new SQLiteAccessLayer(this).queryAppDetails());
        }
        sqLiteAccessLayer.closeDatabaseConnection();
        return appDetailsList;
    }

    /**
     * Get the list of usageStats object and separate the package name and runtime of app
     */
    public void initialize(List<UsageStats>usageStatses) {
        int i = 0;
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

    public List<EachAppDetails> getData() {
        List<EachAppDetails>mEachAppDetailsList = new ArrayList<>();
        try {

            for (int i = 0; i < namesOfApp.length && i < runTimeOfApp.length; i++) {

                EachAppDetails current = new EachAppDetails();
                if (PackageExists(namesOfApp[i])) {
                    ApplicationInfo applicationInfo = getPackageManager().getApplicationInfo(namesOfApp[i], 0);
                    current.eachAppName = String.valueOf(getPackageManager().getApplicationLabel(applicationInfo));
                    current.eachAppUsageDuration = runTimeOfApp[i];
                    Drawable icon = getPackageManager().getApplicationIcon(applicationInfo);
                    current.eachAppIcon = icon;
                    boolean skip = false;
                    for (EachAppDetails eachAppDetails : mEachAppDetailsList) {
                        if (current.eachAppName.equals(eachAppDetails.eachAppName))
                            skip = true;
                    }
                    if (skip)
                        continue;
                    mEachAppDetailsList.add(current);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mEachAppDetailsList;
    }

    public boolean PackageExists(String mPackageName) {
        PackageManager pm;
        pm = getPackageManager();
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



}
