package com.lftechnology.activitylogger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.util.Log;
import android.widget.Toast;


import com.lftechnology.activitylogger.Services.InstalledMonitoringService;
import com.lftechnology.activitylogger.model.AppDetails;

/**
 * This class receives the broadcast of the installed apps and its package name
 */

//Commented codes are to be deleted.
public class InstalledBroadcastReceiver extends BroadcastReceiver {



    Context context;

    /**
     *
     * @param context = on receive context
     * @param insIntent = onReceive InstallApp Intent
     */

    @Override
    public void onReceive(Context context, Intent insIntent) {

        //get app status
        this.context = context;
//        Uri uri = aboutAppInfo.getData();
//        String packageName = uri.getSchemeSpecificPart();

        /**
         * newPackage = gets the name of installed package
         */

        String newPackage= insIntent.getData().getSchemeSpecificPart();
        Log.e("InstallCheckReceiver", newPackage);

        //these will be deleted after review

        // install uninstall display
//        if(aboutAppInfo.getAction().equals("android.intent.action.PACKAGE_REMOVED")) {
//                Toast.makeText(context, "Package Removed", Toast.LENGTH_LONG).show();
//                Intent rmIntent = new Intent(context, InstalledMonitoringService.class);
//                //rmIntent.setAction(Intent.ACTION_PACKAGE_FULLY_REMOVED);
//                rmIntent.putExtra("RmKey",newPackage);
//                rmIntent.putExtra("RmAction", aboutAppInfo.getAction());
//                context.startService(rmIntent);
//
//
////        } else if (aboutAppInfo.getAction().equals("android.intent.action.PACKAGE_REPLACED")) {
////
////            Intent rpIntent = new Intent(context, InstalledMonitoringService.class);
////           // rpIntent.putExtra("RpKey", packageName);
////            rpIntent.putExtra("RpAction", aboutAppInfo.getAction());
////            context.startService(rpIntent);
//
//
//        } else

        /**
         * This if condition checks the Installed Intent then starts service from Its installedServiceClass
         */
        if (insIntent.getAction().equals("android.intent.action.PACKAGE_ADDED")) {
            Toast.makeText(context, "Package Added By User", Toast.LENGTH_LONG).show();
            //Integer Uid = android.os.Process.getGidForName(aboutAppInfo.getAction().toString());
//            System.out.println(Uid);
            Intent newIntent = new Intent(context, InstalledMonitoringService.class);
            newIntent.putExtra("AddKey", newPackage);
            newIntent.putExtra("AddAction", insIntent.getAction());
            context.startService(newIntent);

        } else {
            Log.e("InstallPackage", "InstallError");

        }


    }

}






