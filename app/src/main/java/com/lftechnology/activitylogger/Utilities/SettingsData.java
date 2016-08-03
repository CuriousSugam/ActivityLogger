package com.lftechnology.activitylogger.Utilities;

import android.content.Context;

/**
 * It provide the methods to persist the data of the settings specified by the user.
 * <p/>
 * Created by Sugam on 7/29/2016.
 */
public class SettingsData {

    private Context context;
    private static String SHARED_PREFERENCE_FILE = "activityLogger";
    private static String NOTIFICATION_STATUS = "notification_status";
    private static String NOTIFICATION_TIME = "notification_time";
    private static String ALERT_STATUS = "alert_status";
    private static String ALERT_DURATION = "alert_duration";


    public SettingsData(Context context) {
        this.context = context;
    }

    /**
     * set the status of notification
     *
     * @param notificationStatus true if the notification is to be set else false
     */
    public void setNotificationStatus(boolean notificationStatus) {
        context.getSharedPreferences(SHARED_PREFERENCE_FILE, Context.MODE_PRIVATE).edit().putBoolean(NOTIFICATION_STATUS, notificationStatus).apply();
    }

    /**
     * get the notification status
     *
     * @return notification status
     */
    public boolean getNotificationStatus() {
        return context.getSharedPreferences(SHARED_PREFERENCE_FILE, Context.MODE_PRIVATE).getBoolean(NOTIFICATION_STATUS, false);
    }

    /**
     * sets the time in milliseconds on which the user is to be notified
     *
     * @param timeInMillis notification time in millisecond
     */
    public void setNotificationTime(long timeInMillis) {
        context.getSharedPreferences(SHARED_PREFERENCE_FILE, Context.MODE_PRIVATE).edit().putLong(NOTIFICATION_TIME, timeInMillis).apply();
    }

    /**
     * get the notification time for when the user is to be notified at
     *
     * @return notification time in millisecond
     */
    public long getNotificationTime() {
        return context.getSharedPreferences(SHARED_PREFERENCE_FILE, Context.MODE_PRIVATE).getLong(NOTIFICATION_TIME, 0);
    }

    /**
     * get the status of the app usage alert
     *
     * @return true of the user has set the app usage alert
     */
    public boolean getUsageAlertStatus() {
        return context.getSharedPreferences(SHARED_PREFERENCE_FILE, Context.MODE_PRIVATE).getBoolean(ALERT_STATUS, false);
    }

    /**
     * set the app usage alert status
     *
     * @param usageAlertStatus status of the app usage alert ie. either true or false
     */
    public void setUsageAlertStatus(boolean usageAlertStatus) {
        context.getSharedPreferences(SHARED_PREFERENCE_FILE, Context.MODE_PRIVATE).edit().putBoolean(ALERT_STATUS, usageAlertStatus).apply();
    }

    /**
     * set the alert time duration after which the user is notified
     *
     * @param timeInMillis alert time duration in milliseconds
     */
    public void setAlertTimeDuration(long timeInMillis) {
        context.getSharedPreferences(SHARED_PREFERENCE_FILE, Context.MODE_PRIVATE).edit().putLong(ALERT_DURATION, timeInMillis).apply();
    }

    /**
     * get the alert time duration set by the user
     *
     * @return alert time duration in milliseconds
     */
    public long getAlertTimeDuration() {
        return context.getSharedPreferences(SHARED_PREFERENCE_FILE, Context.MODE_PRIVATE).getLong(ALERT_DURATION, 0);
    }
}
