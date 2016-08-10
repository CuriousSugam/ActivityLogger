package com.lftechnology.activitylogger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;


import com.lftechnology.activitylogger.Controller.SQLiteAccessLayer;
import com.lftechnology.activitylogger.Services.InstalledMonitoringService;
import com.lftechnology.activitylogger.model.AppDetails;

import java.util.List;

/**
 * Created by DevilDewzone on 7/29/2016.
 */

//Commented codes are to be deleted.
public class InstalledBroadcastReceiver extends BroadcastReceiver {


    Context context;
    //AppDetails appDetails;
    ApplicationInfo applicationInfo;


    @Override
    public void onReceive(Context context, Intent aboutAppInfo) {

        //get app status
        this.context = context;

        // install uninstall display
        if (aboutAppInfo.getAction().equals("android.intent.action.PACKAGE_ADDED")) {

            Intent addIntent = new Intent(context, InstalledMonitoringService.class);
            //addIntent.putExtra("AddKey",);
            addIntent.putExtra("AddAction", aboutAppInfo.getAction());
            context.startService(addIntent);

        } else if (aboutAppInfo.getAction().equals("android.intent.action.PACKAGE_REPLACED")) {

            Intent rpIntent = new Intent(context, InstalledMonitoringService.class);
            rpIntent.putExtra("RpKey", context.getApplicationInfo().loadLabel(context.getPackageManager()));
            rpIntent.putExtra("RpAction", aboutAppInfo.getAction());
            context.startService(rpIntent);


        } else if (aboutAppInfo.getAction().equals("android.intent.action.PACKAGE.REMOVED")) {

            Intent rmIntent = new Intent(context, InstalledMonitoringService.class);
            rmIntent.putExtra("RmKey",context.getApplicationInfo().loadLabel(context.getPackageManager()));
            rmIntent.putExtra("RmAction", aboutAppInfo.getAction());
            context.startService(rmIntent);

        } else {
            Log.i("Message", "Nothing Done");

        }


    }
}






