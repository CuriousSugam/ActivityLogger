package com.lftechnology.activitylogger.Services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import com.lftechnology.activitylogger.Controller.SQLiteAccessLayer;
import com.lftechnology.activitylogger.model.AppDetails;


/**
 * Created by DevilDewzone on 7/29/2016.
 */
public class InstalledMonitoringService extends IntentService {


    int uid;
    Context context;
    AppDetails appDetails ;



    SQLiteAccessLayer accessLayer;


    public InstalledMonitoringService() {
        super("InstalledMonitoringService");
    }



    @Override
    protected void onHandleIntent(Intent intent) {


        if (intent.equals(Intent.ACTION_PACKAGE_ADDED)) {
            String dbAddIntent=intent.getStringExtra("AddKey");
            appDetails= new AppDetails(uid,Intent.ACTION_PACKAGE_ADDED,dbAddIntent);
            accessLayer = new SQLiteAccessLayer(this, appDetails);
            accessLayer.insertIntoAppDetails();
            accessLayer.closeDatabaseConnection();


        } else if (intent.equals(Intent.ACTION_PACKAGE_REPLACED)) {
            String dbRpIntent=intent.getStringExtra("RpKey");
            appDetails = new AppDetails(uid,Intent.ACTION_PACKAGE_REPLACED,dbRpIntent);
            accessLayer = new SQLiteAccessLayer(this,appDetails);
            accessLayer.insertIntoAppDetails();
            accessLayer.closeDatabaseConnection();


        } else if (intent.equals(Intent.ACTION_PACKAGE_REMOVED)) {
            String dbRmIntent=intent.getStringExtra("RmKey");
            appDetails = new AppDetails(uid, Intent.ACTION_PACKAGE_REMOVED,dbRmIntent);
            accessLayer = new SQLiteAccessLayer(this,appDetails);
            accessLayer.deleteAnAppDetail("id= and name=android.intent.action.PACKAGE_REMOVED", null);
            accessLayer.closeDatabaseConnection();

        } else {
            //accessLayer.closeDatabaseConnection();
            Log.i("Error", "Package Error");
        }
    }
}





