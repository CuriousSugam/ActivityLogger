package com.lftechnology.activitylogger.Services;

import android.app.IntentService;

import android.content.Context;
import android.content.Intent;

import android.util.Log;

import com.lftechnology.activitylogger.Controller.SQLiteAccessLayer;

import com.lftechnology.activitylogger.model.AppDetails;



/**
 * Created by DevilDewzone on 7/29/2016.
 */
public class InstalledMonitoringService extends IntentService {


    Context context;
    private AppDetails appDetails;
    SQLiteAccessLayer accessLayer = new SQLiteAccessLayer(context, appDetails);


    public InstalledMonitoringService() {
        super("InstalledMonitoringService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent.equals(Intent.ACTION_PACKAGE_ADDED)) {
            intent.getStringExtra("AddKey");
            appDetails = new AppDetails();
            appDetails.getUid();
            appDetails.setApplicationName(Intent.ACTION_PACKAGE_ADDED);
            appDetails.setPackageName(Intent.ACTION_PACKAGE_ADDED);
            appDetails.setApplicationType(Intent.ACTION_PACKAGE_ADDED);
            accessLayer.insertIntoAppDetails();

        } else if (intent.equals(Intent.ACTION_PACKAGE_REPLACED)) {
            intent.getStringExtra("RpKey");
            appDetails = new AppDetails();
            appDetails.getUid();
            appDetails.setApplicationName(Intent.ACTION_PACKAGE_REPLACED);
            appDetails.setPackageName(Intent.ACTION_PACKAGE_REPLACED);
            appDetails.setApplicationType(Intent.ACTION_PACKAGE_REPLACED);
            accessLayer.insertIntoAppDetails();

        } else if (intent.equals(Intent.ACTION_PACKAGE_REMOVED)) {
            intent.getStringExtra("RmKey");
            appDetails = new AppDetails();
            appDetails.getUid();
            //accessLayer.updateAppDetail(null,new String[]{appDetails.getPackageName()});
            //accessLayer.updateAppDetail(null,null);
            accessLayer.deleteAnAppDetail("id=? and name=android.intent.action.PACKAGE_REMOVED", new String[]{appDetails.getPackageName()});

        } else {
            Log.i("Error:", "Package Error");
            //startService(intent);
        }
    }


}






