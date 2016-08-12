package com.lftechnology.activitylogger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.lftechnology.activitylogger.services.InstalledMonitoringService;

/**
 * @author DevilDewzone
 * This class receives the broadcast of the installed apps and its package name
 */

public class InstalledBroadcastReceiver extends BroadcastReceiver {

    protected Context context;

    /**
     * @param context   = on receive context
     * @param installedIntent = onReceive InstallApp Intent
     */

    @Override
    public void onReceive(Context context, Intent installedIntent) {
        this.context = context;

        /**
         * newPackage = gets the name of installed package
         */

        String newPackage = installedIntent.getData().getSchemeSpecificPart();

        if (installedIntent.getAction().equals("android.intent.action.PACKAGE_ADDED")) {
            Toast.makeText(context, "Package Added By User", Toast.LENGTH_LONG).show();
            Intent newIntent = new Intent(context, InstalledMonitoringService.class);
            newIntent.putExtra("AppAddKey", newPackage);
            newIntent.putExtra("AddAction", installedIntent.getAction());
            context.startService(newIntent);

        }
    }
}






