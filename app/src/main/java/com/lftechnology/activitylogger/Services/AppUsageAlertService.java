package com.lftechnology.activitylogger.services;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;

import android.app.TaskStackBuilder;
import android.app.usage.UsageStats;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.lftechnology.activitylogger.MainActivity;
import com.lftechnology.activitylogger.R;
import com.lftechnology.activitylogger.TimeActivity;
import com.lftechnology.activitylogger.broadcastReceiver.RegularAppUsageCheckBroadcastReceiver;
import com.lftechnology.activitylogger.controller.SQLiteAccessLayer;
import com.lftechnology.activitylogger.RawAppInfo;
import com.lftechnology.activitylogger.SettingsActivity;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * It is a service which runs in background and check the foreground usage of the application and
 * notifiy the user when the specified time is reached.
 */
public class AppUsageAlertService extends IntentService {

    private long alertTimeInMillis;
    private final static int REQUEST_CODE = 2;
    private final static int ALARM_INTERVAL = 60*1000;  // 1 minute
    private static int count = 1;

    public AppUsageAlertService() {
        super("AppUsageAlertService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        Log.e("appUsageService", "app usage service started");
        boolean alertStatus = false;
        if(intent.hasExtra(SettingsActivity.ALERT_STATUS)){
            alertStatus = intent.getBooleanExtra(SettingsActivity.ALERT_STATUS, false);
        }

        if(!alertStatus){
            stopAlertService();
        }else{
            alertTimeInMillis = intent.getLongExtra(SettingsActivity.ALERT_TIME_MILLIS, 0);
            if(alertTimeInMillis == 0){
                stopAlertService();
            }else{
                List<UsageStats> usageStatsList = RawAppInfo.getUsageStatsAppList(this);
                Map<String, UsageStats> currentUsageStatsMap = new HashMap<>();
                SQLiteAccessLayer sqLiteAccessLayer = new SQLiteAccessLayer(this);

                if(sqLiteAccessLayer.isForegroundTableEmpty()){ // the service has recently been started
                    Log.e("foregroundTable", "empty");
                    List<PackageInfo> installedApps = RawAppInfo.getAllInstalledApps(this);
                    Log.e("installedAppsSize", "siize:"+installedApps.size());
                    Log.e("usageStats", "Size: "+usageStatsList.size());
                    for(UsageStats usageStats : usageStatsList){
                        for (PackageInfo info : installedApps){
                            ApplicationInfo applicationInfo = info.applicationInfo;
                            if(applicationInfo.packageName.equals(usageStats.getPackageName())){
                                sqLiteAccessLayer.insertIntoForegroundTable(usageStats.getPackageName(), usageStats.getTotalTimeInForeground());
                                currentUsageStatsMap.put(usageStats.getPackageName(), usageStats);
                                Log.e("packageInserted", usageStats.getPackageName()+" "+usageStats.getTotalTimeInForeground());
                            }
                        }
                    }
                }else{      // the service was started already and is running
                    for(UsageStats usageStats : usageStatsList){
                        currentUsageStatsMap.put(usageStats.getPackageName(), usageStats);
                    }
                    Map<String, Long> foregroundTimeFromDb = sqLiteAccessLayer.queryForegroundTable();
                    compareForegroundTime(currentUsageStatsMap, foregroundTimeFromDb);
                }
                sqLiteAccessLayer.closeDatabaseConnection();
                setAlarmForNextWakeUp();
            }

        }
    }

    /**
     * prepare the heads up notification on the notification panel
     *
     * @return a Notification
     */
    private void showNotification(String message){
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
        notificationBuilder.setContentTitle("Hold on a minute!!");
        notificationBuilder.setContentText(message)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message));
        notificationBuilder.setSmallIcon(R.drawable.applogo).setDefaults(Notification.DEFAULT_ALL);
        notificationBuilder.setPriority(Notification.PRIORITY_HIGH);
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setDefaults(Notification.DEFAULT_ALL);

        Intent intentForNotification = new Intent(this, TimeActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(intentForNotification);
        PendingIntent pendingIntentForNotification = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        notificationBuilder.setContentIntent(pendingIntentForNotification);
        Notification notification = notificationBuilder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(2, notification);
    }

    /**
     * This method empties the record in the foreground table and stops this service
     */
    private void stopAlertService(){
        SQLiteAccessLayer sqLiteAccessLayer = new SQLiteAccessLayer(this);
        sqLiteAccessLayer.flushForegroundTable();
        sqLiteAccessLayer.closeDatabaseConnection();
    }

    /**
     * This method checks the time of inactivity of an app and the net foreground time of an app
     * and update the corresponding record in the database table
     * @param currentUsageStatsMap map containing the current usagestats. <packagename, UsageStats object>
     * @param foregroundTimeFromDb map containing the foreground usagestats from the database
     */
    private void compareForegroundTime(Map<String, UsageStats> currentUsageStatsMap, Map<String, Long> foregroundTimeFromDb){
        SQLiteAccessLayer sqLiteAccessLayer = new SQLiteAccessLayer(this);
        for(Map.Entry<String, Long> dbEntry : foregroundTimeFromDb.entrySet()){
            String packageName = dbEntry.getKey();
            long timeOfInactivity = System.currentTimeMillis() -
                    currentUsageStatsMap.get(packageName).getLastTimeUsed();
            if(timeOfInactivity > (3*60*1000)) { // 3mins
                sqLiteAccessLayer.updateForegroundTableRecord(packageName,
                        currentUsageStatsMap.get(packageName).getTotalTimeInForeground());
            }

            long netForegroundTime = currentUsageStatsMap.get(packageName).getTotalTimeInForeground() - dbEntry.getValue();
            if(netForegroundTime >= alertTimeInMillis){
                ApplicationInfo applicationInfo = null;
                try {
                    applicationInfo = getPackageManager().getApplicationInfo(packageName, PackageManager.GET_META_DATA);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                String applicationName = String.valueOf(applicationInfo.loadLabel(getPackageManager()));
                String message = "You have been using "+applicationName+" for "+netForegroundTime/(1000*60)+" mins";
                // generate a notification
                showNotification(message);
                sqLiteAccessLayer.updateForegroundTableRecord(packageName,
                        currentUsageStatsMap.get(packageName).getTotalTimeInForeground());
            }
        }
    }

    /**
     * This method set the alarm manager time for next wakeup. It schedules the time for re-running
     * this service in the future
     */
    private void setAlarmForNextWakeUp(){
        Calendar currentTime = Calendar.getInstance();
        Intent intentForBroadcast = new Intent(this, RegularAppUsageCheckBroadcastReceiver.class);
        intentForBroadcast.putExtra(SettingsActivity.ALERT_STATUS, true);
        intentForBroadcast.putExtra(SettingsActivity.ALERT_TIME_MILLIS, alertTimeInMillis);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, REQUEST_CODE,
                intentForBroadcast, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, currentTime.getTimeInMillis() + ALARM_INTERVAL
                , ALARM_INTERVAL, pendingIntent);
    }
}
