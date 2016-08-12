package com.lftechnology.activitylogger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.lftechnology.activitylogger.services.UninstalledMonitoringService;

/**
 * @author  DevilDewzone on 8/12/2016.
 * Checks the broadcast of Uninstalled Apps
 */
public class UninstalledBroadcastReceiver extends BroadcastReceiver {
    protected Context context;

    /**
     * @param context  = uninstalled event context
     * @param uninstallIntent = intent for uninstalled apps
     */

    @Override
    public void onReceive(Context context, Intent uninstallIntent) {
        this.context = context;
        String uninstallPackageName = uninstallIntent.getData().getSchemeSpecificPart();

        if (uninstallIntent.getAction().equals(Intent.ACTION_PACKAGE_FULLY_REMOVED) ||
                uninstallIntent.getAction().equals(Intent.ACTION_PACKAGE_REMOVED)) {
            Toast.makeText(context, "Package Uninstalled By User", Toast.LENGTH_LONG).show();
            Intent newIntent = new Intent(context, UninstalledMonitoringService.class);
            newIntent.putExtra("RemovedAppKey", uninstallPackageName);
            newIntent.putExtra("RmAction", uninstallIntent.getAction());
            context.startService(newIntent);
        }
    }
}
