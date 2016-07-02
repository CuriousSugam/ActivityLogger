package com.lftechnology.activitylogger;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by sparsha on 6/29/2016.
 */
public class MyUsageStats {
    public static final SimpleDateFormat date = new SimpleDateFormat();
    public static final String LOG = MyUsageStats.class.getSimpleName();
    public static int i;
    public static int interval;

    public static List<UsageStats> getUsageStatsAppList(Context context){

        UsageStatsManager usageStatsManager = getUsageStatsManager(context);
        Calendar calendar = Calendar.getInstance();
        long endTime = calendar.getTimeInMillis();
        calendar.add(Calendar.YEAR, -1);
        long startTime = calendar.getTimeInMillis();
        Log.d(LOG,"Date Start:\t"+ date.format(startTime));
        Log.d(LOG,"Date End:\t"+ date.format(endTime));
        List<UsageStats> usageStatsList =
                usageStatsManager.queryUsageStats(interval,startTime,endTime);
        return usageStatsList;
    }


    /**
     * This Method Should be called for the proper implementation of the class
     *
     * Eg. MyUsageStats.printCurrentUsageStats(Give Context, Give an Integer Value)
     *
     * @param context give context from the activity that you are calling (EG. "this")
     * @param mInterval Note: Integer Value = 0 For Daily
     *                                        1 For Weekly
     *
     *                                        2 For Monthly
     *
     *                                        3 For Yearly
     *
     *                                        4 For From the Beginning
     */
    public static void printCurrentUsageStats(Context context, int mInterval){
        interval = mInterval;
        printUsageStats(getUsageStatsAppList(context),context);
    }

    public static void printUsageStats(List<UsageStats> usageStatsList,Context context){
        //TODO
///////////////////////////////// \/\/\/\/\/\BAD CODE if Have a better way, make better\/\/\/\/\/\/\/ //////////////
        {
            SharedPreferences sharedPreferences = context.getSharedPreferences("appName",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();/////////////////////////////////// TO CLEAR THE FILE
            editor.apply();
            i = 0;
        }
////////////////////////////////^^^^^^^^^^^^^^BAD CODE^^^^^^^/////////////////////////////////////////////////
        SharedPreferences sharedPreferences = context.getSharedPreferences("appName",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for(UsageStats u : usageStatsList){
            String mNameOfPackage = u.getPackageName();
            long totalTimeInForeground = u.getTotalTimeInForeground();
            Log.d(LOG,"Package Name = "+mNameOfPackage+"\tForeground Time: "+totalTimeInForeground);
            editor.putString("packageName"+i,mNameOfPackage);
            i++;
        }
        editor.putInt("count",i);
        editor.apply();
    }


    @SuppressWarnings("ResourceType")
    private static UsageStatsManager getUsageStatsManager(Context context) {
        return (UsageStatsManager) context.getSystemService("usagestats");
    }
}
