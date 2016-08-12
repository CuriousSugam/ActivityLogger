package com.lftechnology.activitylogger.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.lftechnology.activitylogger.controller.SQLiteAccessLayer;
import com.lftechnology.activitylogger.model.AppDetails;

/**
 * This service performs the action of inserting and deleting
 */
public class InstalledMonitoringService extends IntentService {

    private static final String INSTALLED_APP_KEY = "AppAddKey";

    public InstalledMonitoringService() {
        super("InstalledMonitoringService");
    }

    /**
     * @param insServiceIntent = installed app service intent.
     * final String newInsKey gets the package name from the installed Broadcast Receiver
     * final string insAppName obtains app name from package name
     */
    @Override
    protected void onHandleIntent(Intent insServiceIntent) {
        AppDetails insAppDetails;
        ApplicationInfo insApplicationInfo;
        PackageManager insPackageManager;
        SQLiteAccessLayer insAccessLayer;

        final String newInsKey = insServiceIntent.getStringExtra(INSTALLED_APP_KEY);
        Integer insUid = android.os.Process.getUidForName(newInsKey);

        if (insServiceIntent.getStringExtra("AddAction").equals(Intent.ACTION_PACKAGE_ADDED)) {
            insPackageManager = getApplicationContext().getPackageManager();
            try {
                insApplicationInfo = insPackageManager.getApplicationInfo(newInsKey, 0);
            } catch (final PackageManager.NameNotFoundException e) {
                insApplicationInfo = null;
            }
            final String insAppName = (String) (insApplicationInfo != null ? insPackageManager
                    .getApplicationLabel(insApplicationInfo) : "Unknown");
            insAppDetails = new AppDetails(insUid, newInsKey, insAppName);
            insAccessLayer = new SQLiteAccessLayer(this, insAppDetails);
            insAccessLayer.insertIntoAppDetails();
            insAccessLayer.closeDatabaseConnection();
        }
    }
}





