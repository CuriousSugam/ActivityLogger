package com.lftechnology.activitylogger.broadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.lftechnology.activitylogger.SettingsActivity;
import com.lftechnology.activitylogger.services.AppUsageAlertService;

/**
 * It is a broadcast receiver which receives the broadcast set in order to start the service to check
 * the foreground running application
 * Created by Sugam on 8/15/2016.
 */
public class RegularAppUsageCheckBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent serviceIntent = new Intent(context, AppUsageAlertService.class);
        serviceIntent.putExtra(SettingsActivity.ALERT_STATUS, intent.getBooleanExtra(SettingsActivity.ALERT_STATUS, false));
        serviceIntent.putExtra(SettingsActivity.ALERT_TIME_MILLIS,
                intent.getLongExtra(SettingsActivity.ALERT_TIME_MILLIS, 0));
        context.startService(serviceIntent);

    }
}
