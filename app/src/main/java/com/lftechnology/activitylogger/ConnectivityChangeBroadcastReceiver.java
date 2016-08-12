package com.lftechnology.activitylogger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.lftechnology.activitylogger.Services.ConnectivityChangeMonitoringIntentService;

/**
 * ConnectivityChangeBroadcastReceiver is a broadcast receiver. It receives the broadcast from the
 * android system when the network connectivity changes.
 *
 * Created by Sugam on 7/11/2016.
 */
public class ConnectivityChangeBroadcastReceiver extends BroadcastReceiver {
    private final static String ACTION_CONNECTIVITY_CHANGE = "android.net.conn.CONNECTIVITY_CHANGE";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null && intent.getAction().equals(ACTION_CONNECTIVITY_CHANGE)) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            Intent serviceIntent = new Intent(context, ConnectivityChangeMonitoringIntentService.class);
            if (networkInfo != null) {
                if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI && networkInfo.isConnected()) {
                    serviceIntent.putExtra("networkType", ConnectivityChangeMonitoringIntentService.WIFI_NETWORK);
                    context.startService(serviceIntent);
                } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE && networkInfo.isConnected()) {
                    serviceIntent.putExtra("networkType", ConnectivityChangeMonitoringIntentService.MOBILE_NETWORK);
                    context.startService(serviceIntent);
                }
            } else {
                serviceIntent.putExtra("networkType", ConnectivityChangeMonitoringIntentService.OFFLINE);
                context.startService(serviceIntent);
            }
        }
    }
}
