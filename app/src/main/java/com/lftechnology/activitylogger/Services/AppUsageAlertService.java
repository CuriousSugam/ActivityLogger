package com.lftechnology.activitylogger.Services;

import android.app.Service;
import android.app.usage.UsageStats;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.lftechnology.activitylogger.Controller.SQLiteAccessLayer;
import com.lftechnology.activitylogger.RawAppInfo;
import com.lftechnology.activitylogger.SettingsActivity;

import java.util.List;

public class AppUsageAlertService extends Service {

    private static int count = 1;
    private boolean alertStatus;
    private long alertTimeInMillis;

    public AppUsageAlertService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        count++;
        alertStatus = intent.getBooleanExtra(SettingsActivity.ALERT_STATUS, false);
        alertTimeInMillis = intent.getLongExtra(SettingsActivity.ALERT_TIME_MILLIS, 0);
        Log.e("appUsageService", "alert status = "+alertStatus+" time : "+(alertTimeInMillis/(1000*60*60))%24 + "count: "+count);

        SQLiteAccessLayer sqLiteAccessLayer = new SQLiteAccessLayer(this);
        List<UsageStats> usageStatsList = RawAppInfo.getUsageStatsAppList(this);
        for(UsageStats usageStats : usageStatsList){
            sqLiteAccessLayer.insertIntoForegroundTable(usageStats.getPackageName(), usageStats.getTotalTimeInForeground());
        }
        sqLiteAccessLayer.closeDatabaseConnection();

        if(!alertStatus){
            stopSelf();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.e("appUsageService", "service destroyed");
        super.onDestroy();
    }
}
