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

//Commented codes are to be deleted.
public class InstalledBroadcastReceiver extends BroadcastReceiver {


    Context context;

    @Override
    public void onReceive(Context context, Intent aboutAppInfo) {
        //Log.v(TAG, "Installed BroadCast");

        //get app status
        this.context = context;

        // install uninstall display
        if (aboutAppInfo.getAction().equals("android.intent.action.PACKAGE_ADDED")) {
            // Log.e("BroadcastReceiver", "onReceive called" + "PACKAGE_INSTALLED");
            //Toast.makeText(context, "onReceive !!! PACKAGE_INSTALLED", Toast.LENGTH_LONG).show();
            //Intent insIntent = new Intent(this, InstalledMonitoringService.class);

            Intent addIntent = new Intent(context, InstalledMonitoringService.class);
            addIntent.putExtra("AddKey", "android.intent.action.PACKAGE_ADDED");
            context.startService(addIntent);

        } else if (aboutAppInfo.getAction().equals("android.intent.action.PACKAGE_REPLACED")) {
            //Log.e("BroadcastReceiver", "onReceive called" + "PACKAGE_ADDED");
            //Toast.makeText(context, "onReceive!!! PACKAGE_ADDED", Toast.LENGTH_LONG).show();

            Intent rpIntent = new Intent(context, InstalledMonitoringService.class);
            rpIntent.putExtra("RpKey", "android.intent.action.PACKAGE_REPLACED");
            context.startService(rpIntent);

        } else if (aboutAppInfo.getAction().equals("android.intent.action.PACKAGE_REMOVED")) {
            //Log.e("BroadcastReceiver", "onReceive !!! PACKAGE_REMOVED");
            //Toast.makeText(context, "onReceive  !!! PACKAGE_REMOVED", Toast.LENGTH_LONG).show();

            Intent rmIntent = new Intent(context, InstalledMonitoringService.class);
            rmIntent.putExtra("RmKey", "android.intent.action.PACKAGE_REMOVED");
            context.startService(rmIntent);

        } else {
            Log.i("Message", "Nothing Done");
        }

//        if (toastMessage != null) {
//            Toast.makeText(context, toastMessage, Toast.LENGTH_LONG).show();
//        }
    }
}


