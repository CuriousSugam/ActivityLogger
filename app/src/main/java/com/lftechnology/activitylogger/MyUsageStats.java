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
    private static final SimpleDateFormat date = new SimpleDateFormat();
    private static int interval;

    public enum Interval{
        DAILY(0),WEEKLY(1),MONTHLY(2),YEARLY(3),BEST(4);
        public int value;
        Interval(int mValue){
            value = mValue;
        }
    }

    public static List<UsageStats> getUsageStatsAppList(Context context){
        UsageStatsManager usageStatsManager = getUsageStatsManager(context);
        Calendar calendar = Calendar.getInstance();
        long endTime = calendar.getTimeInMillis();
        calendar.add(Calendar.YEAR, -1);
        long startTime = calendar.getTimeInMillis();
        Log.d("LOG","Date Start:\t"+ date.format(startTime));//TODO remove
        Log.d("LOG","Date End:\t"+ date.format(endTime));//TODO remove
        List<UsageStats> usageStatsList =
                usageStatsManager.queryUsageStats(interval,startTime,endTime);
        return usageStatsList;
    }


    /**
     * This Method Should be called for the proper implementation of the class
     *
     * Eg. MyUsageStats.printCurrentUsageStats(Context context, MyUsageStats.Interval.Daily.interval)
     *
     * @param context give context from the activity that you are calling (EG. "this")
     * @param mInterval Note: Integer Value = 0 For Daily
     *                                        1 For Weekly
     *                                        2 For Monthly
     *                                        3 For Yearly
     *                                        4 For From the Beginning
     */
    public static void printCurrentUsageStats(Context context, int mInterval){
        interval = mInterval;
        printUsageStats(getUsageStatsAppList(context),context);
    }

    static void printUsageStats(List<UsageStats> usageStatsList,Context context){
       
        for(UsageStats u : usageStatsList){
            String mNameOfPackage = u.getPackageName();
            long totalTimeInForeground = u.getTotalTimeInForeground();
            Log.d("LOG","Package Name = "+mNameOfPackage+"\tForeground Time: "+totalTimeInForeground);//TODO remove
        }
    }


    @SuppressWarnings("ResourceType")
    private static UsageStatsManager getUsageStatsManager(Context context) {
        return (UsageStatsManager) context.getSystemService("usagestats");
    }
}
