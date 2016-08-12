package com.lftechnology.activitylogger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;


import com.lftechnology.activitylogger.services.InstalledMonitoringService;

/**
 * This class receives the broadcast of the installed apps and its package name
 */

public class InstalledBroadcastReceiver extends BroadcastReceiver {

    protected Context context;

    /**
     * @param context   = on receive context
     * @param insIntent = onReceive InstallApp Intent
     */

    @Override
    public void onReceive(Context context, Intent insIntent) {
        this.context = context;

        /**
         * newPackage = gets the name of installed package
         */

        String newPackage = insIntent.getData().getSchemeSpecificPart();
        Log.e("InstallCheckReceiver", newPackage);

        if (insIntent.getAction().equals("android.intent.action.PACKAGE_ADDED")) {
            Toast.makeText(context, "Package Added By User", Toast.LENGTH_LONG).show();
            Intent newIntent = new Intent(context, InstalledMonitoringService.class);
            newIntent.putExtra("AppAddKey", newPackage);
            newIntent.putExtra("AddAction", insIntent.getAction());
            context.startService(newIntent);

        } else {
            Log.e("InstallPackage", "InstallError");

        }

    }

}






