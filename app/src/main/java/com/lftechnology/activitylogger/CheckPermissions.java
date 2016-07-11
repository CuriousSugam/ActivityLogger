package com.lftechnology.activitylogger;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.provider.Settings;

/**
 * This class checks if the permission required by the Activity Logger app is set.
 * If the permission is not set it redirects to the usage access settings activity
 *
 * Created by Sugam on 7/8/2016.
 */
public class CheckPermissions {

    /**
     * if you havent enabled permission, settings opens for granting permission access with this
     */
    public static boolean isPermissionForAccessSet(Context context) {

        try {
            PackageManager packageManager = context.getPackageManager();
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            AppOpsManager appOpsManager = (AppOpsManager)context.getSystemService(Context.APP_OPS_SERVICE);
            int mode = appOpsManager.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, applicationInfo.uid, applicationInfo.packageName);
            if(mode != AppOpsManager.MODE_ALLOWED){
                return false;

            }else{
                return true;
            }
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }

        //if (RawAppInfo.getUsageStatsAppList(context).isEmpty())
        //  context.startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
    }
}
