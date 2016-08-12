package com.lftechnology.activitylogger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.lftechnology.activitylogger.services.UninstalledMonitoringService;

/**
 * Created by DevilDewzone on 8/12/2016.
 * Checks the broadcast of Uninstalled Apps
 */
public class UninstalledBroadcastReceiver extends BroadcastReceiver {
    protected Context context;

    /**
     * @param context  = uninstalled event context
     * @param rmIntent = intent for uninstalled apps
     */

    @Override
    public void onReceive(Context context, Intent rmIntent) {
        this.context = context;
        String uninstallPackageName = rmIntent.getData().getSchemeSpecificPart();

        if (rmIntent.getAction().equals(Intent.ACTION_PACKAGE_FULLY_REMOVED) ||
                rmIntent.getAction().equals(Intent.ACTION_PACKAGE_REMOVED)) {
            Toast.makeText(context, "Package Uninstalled By User", Toast.LENGTH_LONG).show();
            Intent newIntent = new Intent(context, UninstalledMonitoringService.class);
            newIntent.putExtra("RemovedAppKey", uninstallPackageName);
            newIntent.putExtra("RmAction", rmIntent.getAction());
            context.startService(newIntent);
        }
    }
}
