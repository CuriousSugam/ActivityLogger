package com.lftechnology.activitylogger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.nfc.Tag;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.lftechnology.activitylogger.Services.InstalledMonitoringService;

/**
 * Created by DevilDewzone on 7/29/2016.
 */
public class InstalledBroadcastReceiver extends BroadcastReceiver {

    // PackageManager packageMngr;


    Context context;

    @Override
    public void onReceive(Context context, Intent aboutAppInfo) {
        //Log.v(TAG, "Installed BroadCast");

        //get app status
        this.context = context;
        //boolean appNotify = aboutAppInfo.getBooleanExtra(Intent.EXTRA_REPLACING, false);
        //String toastMessage = null;

        // install uninstall display
        if (aboutAppInfo.getAction().equals("android.intent.action.PACKAGE_INSTALL")) {
            // Log.e("BroadcastReceiver", "onReceive called" + "PACKAGE_INSTALLED");
            //Toast.makeText(context, "onReceive !!! PACKAGE_INSTALLED", Toast.LENGTH_LONG).show();
            //Intent insIntent = new Intent(this, InstalledMonitoringService.class);

            Intent insIntent = new Intent(context, InstalledMonitoringService.class);
            insIntent.putExtra("InsKey","android.intent.action.PACKAGE_INSTALLED");
            context.startService(insIntent);

            //packageMngr = (PackageManager)context.getPackageManager().queryIntentActivities(insIntent, 0);


            // toastMessage = "PACKAGE_INSTALL: " + aboutAppInfo.getData().toString() + (context, aboutAppInfo.getData().toString(), PackageManager.MATCH_UNINSTALLED_PACKAGES);
        } else if (aboutAppInfo.getAction().equals("android.intent.action.PACKAGE_ADDED")) {
            //Log.e("BroadcastReceiver", "onReceive called" + "PACKAGE_ADDED");
            //Toast.makeText(context, "onReceive!!! PACKAGE_ADDED", Toast.LENGTH_LONG).show();
            //Intent addIntent = new Intent(Intent.ACTION_PACKAGE_ADDED, null);

            Intent addIntent = new Intent(context, InstalledMonitoringService.class);
            addIntent.putExtra("AddKey","android.intent.action.PACKAGE_INSTALLED");
            context.startService(addIntent);

            //packageMngr=(PackageManager)context.getPackageManager().queryIntentActivities(addIntent, 0);
            //toastMessage = "PACKAGE_ADDED: " + aboutAppInfo.getData().toString() + getApplicationName(context, aboutAppInfo.getData().toString(), PackageManager.MATCH_UNINSTALLED_PACKAGES);

        } else if (aboutAppInfo.getAction().equals("android.intent.action.PACKAGE_REMOVED")) {
            //Log.e("BroadcastReceiver", "onReceive !!! PACKAGE_REMOVED");
            //Toast.makeText(context, "onReceive  !!! PACKAGE_REMOVED", Toast.LENGTH_LONG).show();
            //Intent rmIntent = new Intent(Intent.ACTION_PACKAGE_REMOVED, null);

            Intent rmIntent = new Intent(context, InstalledMonitoringService.class);
            rmIntent.putExtra("KEY","android.intent.action.PACKAGE_INSTALLED");
            rmIntent.getStringExtra("KEY");
            context.startService(rmIntent);

            //packageMngr = (PackageManager)context.getPackageManager().queryIntentActivities(rmIntent,0);
            // toastMessage = "PACKAGE_REMOVED:" + aboutAppInfo.getData().toString() + getApplicationName(context, aboutAppInfo.getData().toString(), PackageManager.MATCH_UNINSTALLED_PACKAGES);

        } else {
            Log.i("Message", "Nothing Done");
        }

//        if (toastMessage != null) {
//            Toast.makeText(context, toastMessage, Toast.LENGTH_LONG).show();
//        }
    }
}


