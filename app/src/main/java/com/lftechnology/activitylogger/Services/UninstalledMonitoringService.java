package com.lftechnology.activitylogger.Services;

import android.app.IntentService;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.lftechnology.activitylogger.Controller.SQLiteAccessLayer;
import com.lftechnology.activitylogger.model.AppDetails;

//import java.util.List;

/**
 * Created by DevilDewzone on 8/12/2016.
 */
public class UninstalledMonitoringService extends IntentService {

    AppDetails rmAppDetails;
    SQLiteAccessLayer rmAccessLayer;
    PackageManager rmPackageManger;
    ApplicationInfo rmApplicationInfo;

    public UninstalledMonitoringService() {
        super("UninstalledMonitoringService");
    }

    /**
     * @param rmServiceIntent = intent for uninstalled app broadcast receiving
     */

    @Override
    protected void onHandleIntent(Intent rmServiceIntent) {
        String newRmKey = rmServiceIntent.getStringExtra("RmKey");
        Integer rmUid = android.os.Process.getUidForName(newRmKey);

        /**
         * checks if the app is removed or fully removed then perform deleting that apps name uid and name from the database
         * final string rmAppName gives package name of the uninstalled app
         *
         */

        if (rmServiceIntent.getStringExtra("RmAction").equals(Intent.ACTION_PACKAGE_FULLY_REMOVED) || rmServiceIntent.getStringExtra("RmAction").equals(Intent.ACTION_PACKAGE_FULLY_REMOVED)) {

            rmPackageManger = getApplicationContext().getPackageManager();
            try {
                rmApplicationInfo = rmPackageManger.getApplicationInfo(newRmKey, 0);
            } catch (final PackageManager.NameNotFoundException e) {
                rmApplicationInfo = null;
            }
            final String rmAppName = (String) (rmApplicationInfo != null ? rmPackageManger.getApplicationLabel(rmApplicationInfo) : "Unknown");

            rmAppDetails = new AppDetails(rmUid, newRmKey, rmAppName);
            rmAccessLayer = new SQLiteAccessLayer(this, rmAppDetails);
            Log.e("RmAppDetails", rmUid + "" + rmAppName + "" + newRmKey);
            rmAccessLayer.deleteAnAppDetail(SQLiteAccessLayer.TABLE_COLUMN_PACKAGE_NAME + "=? ", new String[]{newRmKey});

            /**
             * for querying purpose
             */

//            List<AppDetails> rmListDetails = rmAccessLayer.queryAppDetails();
//            for (AppDetails rmAppListDetails : rmListDetails) {
//                Log.e("PackageUninstallCheck", rmAppListDetails.getUid() + rmAppListDetails.getPackageName() + rmAppListDetails.getApplicationName());
//            }
            rmAccessLayer.closeDatabaseConnection();

        } else {
            Log.e("UninstallError", "Uninstall Service Error");
        }
    }
}
