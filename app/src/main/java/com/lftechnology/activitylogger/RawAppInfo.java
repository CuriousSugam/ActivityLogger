package com.lftechnology.activitylogger;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.ResolveInfo;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * Created by sparsha on 6/29/2016.
 * Returns raw information of apps that are run within an Interval Provided.
 * Default is set to daily
 */
public class RawAppInfo {
    private static int interval=2;
    public static final String INSTALLED_APP = "installed";
    public static final String SYSTEM_APP = "system";

    /**
     * This methods provides us with all the installed apps
     *
     * @param context context of the calling
     * @return list of objects with installed apps
     */
    public static List<PackageInfo> getAllInstalledApps(Context context){
        List<PackageInfo> packageInfoList = context.getPackageManager().getInstalledPackages(0);
        Iterator iterator = packageInfoList.iterator();
        while(iterator.hasNext()){
            PackageInfo packageInfo = (PackageInfo)iterator.next();
            if((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0){
                iterator.remove();
            }
        }
        return packageInfoList;
    }

    /**
     * This methods provides us with all the system apps
     *
     * @param context context of the calling
     * @return List of ResolveInfo object containing the information about the systemapps
     *
     */
    public static List<ResolveInfo> getSystemApps(Context context){
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> resolveInfos = context.getPackageManager().queryIntentActivities(mainIntent, 0);
        Iterator iterator = resolveInfos.iterator();
        while(iterator.hasNext()){
            ResolveInfo resolveInfo = (ResolveInfo) iterator.next();
            if((resolveInfo.activityInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                iterator.remove();
            }
        }
        return resolveInfos;
    }

    /**
     * This method provides us with all the apps that are currently in the system
     *
     * @param context context of the calling
     * @return map with the information about all the apps
     */
    public static Map<String, List<PackageInfo>> getAllApps(Context context){
        Map<String, List<PackageInfo>> appMap = new HashMap<>();

        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        List<PackageInfo> packageInfoList = context.getPackageManager().getInstalledPackages(0);
        List<PackageInfo> installedPackageInfoList = new ArrayList<>();
        List<PackageInfo> systemPackageInfoList = new ArrayList<>();

        Iterator iterator = packageInfoList.iterator();
        while(iterator.hasNext()){
            PackageInfo packageInfo = (PackageInfo)iterator.next();
            if((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0){
                installedPackageInfoList.add(packageInfo);
            }else{
                systemPackageInfoList.add(packageInfo);
            }
        }
        appMap.put(INSTALLED_APP, installedPackageInfoList);
        appMap.put(SYSTEM_APP, systemPackageInfoList);
        return appMap;
    }



    /**
     * Returns the list of apps in a List to read
     */
    public static List<UsageStats> getUsageStatsAppList(Context context) {
        UsageStatsManager usageStatsManager = getUsageStatsManager(context);
        Calendar calendar = Calendar.getInstance();
        long endTime = calendar.getTimeInMillis();
        calendar.add(Calendar.YEAR, -1);
        long startTime = calendar.getTimeInMillis();
        return usageStatsManager.queryUsageStats(interval, startTime, endTime);
    }

    /**
     * Sets desired interval that the developer requires
     * Eg. RawAppInfo.printCurrentUsageStats(Context context, int mInterval)
     *
     * @param context   give context from the activity that you are calling (EG. "this")
     * @param mInterval Note: Integer Value = 0 For Daily
     *                  1 For Weekly
     *                  2 For Monthly
     *                  3 For Yearly
     *                  4 For From the Beginning
     */
    public static List<UsageStats> printCurrentUsageStats(Context context, int mInterval) {
        interval = mInterval;
        return getUsageStatsAppList(context);
    }


    @SuppressWarnings("ResourceType")
    private static UsageStatsManager getUsageStatsManager(Context context) {
        return (UsageStatsManager) context.getSystemService("usagestats");
    }
}