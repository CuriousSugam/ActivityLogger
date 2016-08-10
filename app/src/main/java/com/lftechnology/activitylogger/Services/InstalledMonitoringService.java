package com.lftechnology.activitylogger.Services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.Toast;

import com.lftechnology.activitylogger.Controller.SQLiteAccessLayer;
import com.lftechnology.activitylogger.model.AppDetails;

import java.util.ArrayList;
import java.util.List;


/**
 * This service performs the action of inserting and deleting
 */
public class InstalledMonitoringService extends IntentService {


    //int uid;
    //Context context;
    AppDetails appDetails;
    ApplicationInfo applicationInfo;
    PackageManager packageManager;
    SQLiteAccessLayer accessLayer;

    final Intent insIntent = new Intent(Intent.ACTION_PACKAGE_ADDED, null);
    final Intent rpIntent = new Intent(Intent.ACTION_PACKAGE_REPLACED, null);
    final Intent rmIntent = new Intent(Intent.ACTION_PACKAGE_REMOVED, null);


    public InstalledMonitoringService() {
        super("InstalledMonitoringService");
    }


    @Override
    protected void onHandleIntent(Intent serviceIntent) {
        Log.i("check", "message");
        if (serviceIntent.getStringExtra("AddAction").equals(Intent.ACTION_PACKAGE_ADDED)) {

            Log.e("packageInstallCheck", "inside the intent.actionPackageAdded");
            //insIntent.addCate
            //String dbAddIntent = serviceIntent.getStringExtra("AddKey");
            appDetails = new AppDetails(this.appDetails.getUid(), this.getPackageName(), this.getApplicationInfo().name);
            accessLayer = new SQLiteAccessLayer(this, appDetails);
            accessLayer.insertIntoAppDetails();
            List<AppDetails> listDetails = accessLayer.queryAppDetails();
            for (AppDetails appListDetails : listDetails) {
                Log.e("packageInstallCheck", appListDetails.getApplicationName() + appListDetails.getUid() + appListDetails.getPackageName());
            }
            accessLayer.closeDatabaseConnection();

        } else if (serviceIntent.getStringExtra("RpAction").equals(Intent.ACTION_PACKAGE_REPLACED)) {
            String dbRpIntent = serviceIntent.getStringExtra("RpKey");
            appDetails = new AppDetails(appDetails.getUid(), getPackageName(), dbRpIntent);
            accessLayer = new SQLiteAccessLayer(this, appDetails);
            accessLayer.insertIntoAppDetails();
            accessLayer.closeDatabaseConnection();

        } else if (serviceIntent.getStringExtra("RmAction").equals(Intent.ACTION_PACKAGE_REMOVED)) {
            String dbRmIntent = serviceIntent.getStringExtra("RmKey");
            appDetails = new AppDetails(appDetails.getUid(), getPackageName(), dbRmIntent);
            accessLayer = new SQLiteAccessLayer(this, appDetails);
            accessLayer.deleteAnAppDetail("?" + "android.intent.action.PACKAGE_REMOVED", new String[]{});
            accessLayer.closeDatabaseConnection();

        } else {
            //accessLayer.closeDatabaseConnection();
            Log.i("Error", "Service Error");
        }
    }
}





