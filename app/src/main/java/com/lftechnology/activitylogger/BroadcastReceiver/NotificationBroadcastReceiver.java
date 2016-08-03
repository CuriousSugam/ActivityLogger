package com.lftechnology.activitylogger.BroadcastReceiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Receives the broadcast when the alarm goes off in order to notify the user about the regular
 * monitoring of the app usage.
 *
 * It receives the broadcast
 * It display the notification which is in the pending intent.
 *
 * Created by Sugam on 7/29/2016.
 */
public class NotificationBroadcastReceiver extends BroadcastReceiver {

    public static String NOTIFICATION_ID = "notification_id";
    public static String NOTIFICATION = "notification";

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = intent.getParcelableExtra(NOTIFICATION);
        int id = intent.getIntExtra(NOTIFICATION_ID, 0);
        notificationManager.notify(id, notification);
    }
}
