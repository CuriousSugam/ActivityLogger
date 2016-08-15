package com.lftechnology.activitylogger.services;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.app.usage.UsageStats;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.util.Log;

import com.lftechnology.activitylogger.MainActivity;
import com.lftechnology.activitylogger.R;
import com.lftechnology.activitylogger.TimeActivity;
import com.lftechnology.activitylogger.broadcastReceiver.NotificationBroadcastReceiver;
import com.lftechnology.activitylogger.controller.SQLiteAccessLayer;
import com.lftechnology.activitylogger.RawAppInfo;
import com.lftechnology.activitylogger.SettingsActivity;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
//        count++;
        alertStatus = intent.getBooleanExtra(SettingsActivity.ALERT_STATUS, false);
        SQLiteAccessLayer sqLiteAccessLayer = new SQLiteAccessLayer(this);
        if(!alertStatus){
            sqLiteAccessLayer.flushForegroundTable();
            stopSelf();
        }else{
            alertTimeInMillis = intent.getLongExtra(SettingsActivity.ALERT_TIME_MILLIS, 0);

            List<UsageStats> usageStatsList = RawAppInfo.getUsageStatsAppList(this);
            Map<String, UsageStats> usageStatsMap = new HashMap<>();

            if(sqLiteAccessLayer.isForegroundTableEmpty()){ // the service has recently been started
                for(UsageStats usageStats : usageStatsList){
                    sqLiteAccessLayer.insertIntoForegroundTable(usageStats.getPackageName(), usageStats.getTotalTimeInForeground());
                    usageStatsMap.put(usageStats.getPackageName(), usageStats);
                }
            }else{      // the service was started already and is running
                // get the current foreground time of every app
                Map<String, Long> currentForegroundTimeMap = new HashMap<>();
                for(UsageStats usageStats : usageStatsList){
                    currentForegroundTimeMap.put(usageStats.getPackageName(), usageStats.getTotalTimeInForeground());
                }

                // foreground time from db
                Map<String, Long> foregroundTimeFromDb = sqLiteAccessLayer.queryForegroundTable();
                // compare the foreground time
                for(Map.Entry<String, Long> dbEntry : foregroundTimeFromDb.entrySet()){
                    String packagename = dbEntry.getKey();
                    // check if the app has not been used for more than 5 mins
                    long netForegroundTime = currentForegroundTimeMap.get(packagename) - dbEntry.getValue();
                    long timeOfInactivity = currentForegroundTimeMap.get(packagename) -
                            usageStatsMap.get(packagename).getLastTimeUsed();
                    if(timeOfInactivity > (3*60*1000)) { // 3mins
                        sqLiteAccessLayer.updateForegroundTableRecord(packagename,
                                currentForegroundTimeMap.get(packagename));
                    }else{
                        if(netForegroundTime >= alertTimeInMillis){
                            ApplicationInfo applicationInfo = null;
                            try {
                               applicationInfo = getPackageManager().getApplicationInfo(packagename, PackageManager.GET_META_DATA);
                            } catch (PackageManager.NameNotFoundException e) {
                                e.printStackTrace();
                            }
                            assert applicationInfo != null;
                            String applicationName = String.valueOf(applicationInfo.loadLabel(getPackageManager()));
                            String message = "You have been using "+applicationName+" for "+netForegroundTime/(1000*60)+" mins";
                            // generate a notification
                            showNotification("message for the notificaiton goes here like : you have been using fb for 30 mins");
                            sqLiteAccessLayer.updateForegroundTableRecord(packagename,
                                    currentForegroundTimeMap.get(packagename));
                        }
                    }
                }

//                Calendar currentTime = Calendar.getInstance();
//                Calendar notificationCalendarObj;
//                notificationCalendarObj = Calendar.getInstance();
//                notificationCalendarObj.set(currentTime.get(Calendar.YEAR),currentTime.get(Calendar.MONTH),
//                        currentTime.get(Calendar.DATE),
//                        selectedHour, selectedMinute);
//
//                // Set the alarm to go off when at the selected time
//                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//                long interval = 24 * 60 * 60 * 1000;      // convert 24hours to milliseconds
//                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, notificationCalendarObj.getTimeInMillis(), interval, pendingIntent);

                // calculate the net foreground time of every app
                // reset the initial foreground time of the app which has not been active for more than 5mins
                // if any app usage exceeds or is equal to the specified time period the notify users
                // set alarm for next wake up ==>> interval being 2 mins
            }


        }
        sqLiteAccessLayer.closeDatabaseConnection();

//        Log.e("appUsageService", "alert status = "+alertStatus+" time : "+(alertTimeInMillis/(1000*60*60))%24 + "count: "+count);
//

//
        return START_STICKY;
    }


    /**
     * prepare the heads up notification on the notification panel
     *
     * @return a Notification
     */
    private void showNotification(String message){
        // Build a notification to be display with appropriate message
        Notification.Builder notificationBuilder = new Notification.Builder(this);
        notificationBuilder.setContentTitle(message);
//                notificationBuilder.setContentText("");
        notificationBuilder.setSmallIcon(R.drawable.applogo).setDefaults(Notification.DEFAULT_ALL);
        notificationBuilder.setPriority(Notification.PRIORITY_HIGH);

        // Prepare a pending intent to be initiated when the notification is clicked
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


    @Override
    public void onDestroy() {
        Log.e("appUsageService", "service destroyed");
        super.onDestroy();
    }
}
