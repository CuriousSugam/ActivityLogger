package com.lftechnology.activitylogger.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.lftechnology.activitylogger.controller.SQLiteAccessLayer;
import com.lftechnology.activitylogger.model.AppDetails;

/**
 * Created by DevilDewzone on 8/12/2016.
 */

public class UninstalledMonitoringService extends IntentService {

    private static final String REMOVED_APP_KEY = "RemovedAppKey";

    public UninstalledMonitoringService() {
        super("UninstalledMonitoringService");
    }

    /**
     * @param rmServiceIntent = intent for uninstalled app broadcast receiving
     */

    @Override
    protected void onHandleIntent(Intent rmServiceIntent) {

        AppDetails rmAppDetails;
        SQLiteAccessLayer rmAccessLayer;
        PackageManager rmPackageManger;
        ApplicationInfo rmApplicationInfo;

        String newRmKey = rmServiceIntent.getStringExtra(REMOVED_APP_KEY);
        Integer rmUid = android.os.Process.getUidForName(newRmKey);

        if (rmServiceIntent.getStringExtra("RmAction").equals(Intent.ACTION_PACKAGE_FULLY_REMOVED)
                || rmServiceIntent.getStringExtra("RmAction")
                .equals(Intent.ACTION_PACKAGE_FULLY_REMOVED)) {

            rmPackageManger = getApplicationContext().getPackageManager();
            try {
                rmApplicationInfo = rmPackageManger.getApplicationInfo(newRmKey, 0);
            } catch (final PackageManager.NameNotFoundException e) {
                rmApplicationInfo = null;
            }
            final String rmAppName = (String) (rmApplicationInfo != null ? rmPackageManger
                    .getApplicationLabel(rmApplicationInfo) : "Unknown");
            rmAppDetails = new AppDetails(rmUid, newRmKey, rmAppName);
            rmAccessLayer = new SQLiteAccessLayer(this, rmAppDetails);
            rmAccessLayer.deleteAnAppDetail(SQLiteAccessLayer.TABLE_COLUMN_PACKAGE_NAME + "=? ",
                    new String[]{newRmKey});
            rmAccessLayer.closeDatabaseConnection();

        }
    }
}
