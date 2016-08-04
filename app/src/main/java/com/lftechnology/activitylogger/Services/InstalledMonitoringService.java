package com.lftechnology.activitylogger.Services;

import android.app.IntentService;
//mport android.app.backup.SharedPreferencesBackupHelper;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.util.Log;
import android.widget.Toast;
//import android.preference.PreferenceManager;

import com.lftechnology.activitylogger.Controller.SQLiteAccessLayer;
import com.lftechnology.activitylogger.InstalledBroadcastReceiver;
import com.lftechnology.activitylogger.model.AppDetails;

/**
 * Created by DevilDewzone on 7/29/2016.
 */
public class InstalledMonitoringService extends IntentService {

//    // public final static String APP_STATE = "app_state";
//    public final static String APP_REPLACED = "android.intent.action.PACKAGE_REPLACED";
//    public final static String APP_ADDED = "android.intent.action.PACKAGE_ADDED";
//    public final static String APP_REMOVED = "android.intent.action.PACKAGE_REMOVED";

    //SQLiteAccessLayer updateDetails;
    // private IntentService intentService;
//    private Intent insIntent;
//    private Intent addIntent;
//    private Intent rmIntent;
//    private InstalledBroadcastReceiver insIntent;
//   private InstalledBroadcastReceiver addIntent;
//   private InstalledBroadcastReceiver rmIntent;
    private Context context;

    private AppDetails appDetails;
    //private SQLiteAccessLayer accessLayer;


    public InstalledMonitoringService() {
        super("InstalledMonitoringService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        ApplicationInfo uid;

//
//        if(intent.hasExtra(APP_STATE)) {
//            String app_state = intent.getStringExtra(APP_STATE);
//            //SharedPreferences sharedPreferences = getSharedPreferences("app_state", MODE_APPEND);

        if (intent.equals(Intent.ACTION_PACKAGE_ADDED)) {
            //insertIntoAppDetails();
            intent.getStringExtra("AddKey");
            //Toast.makeText(context, "PACKAGE_INSTALLED", Toast.LENGTH_LONG).show();

            //intent.getExtras();
            appDetails.getUid();
            appDetails = new AppDetails();
            appDetails.setPackageName(Intent.ACTION_PACKAGE_ADDED);
            appDetails.setApplicationName(Intent.ACTION_PACKAGE_ADDED);
            appDetails.setApplicationType(Intent.ACTION_PACKAGE_ADDED);
            //getAccessLayer();
            accessLayer.insertIntoAppDetails();


            //String app_installed = intent.getStringExtra("android.intent.action.PACKAGE_INSTALLED");
            //context.startService(insIntent);
//
        } else if (intent.equals(Intent.ACTION_PACKAGE_REPLACED)) {
            //insertIntoAppDetails();
            intent.getStringExtra("RpKey");
            //intent.getExtras();
            //appDetails
            appDetails.getUid();
            appDetails = new AppDetails();
            appDetails.setPackageName(Intent.ACTION_PACKAGE_REMOVED);
            appDetails.setApplicationName(Intent.ACTION_PACKAGE_REMOVED);
            appDetails.setApplicationType(Intent.ACTION_PACKAGE_REMOVED);
            accessLayer.insertIntoAppDetails();
            //String app_added = intent.getStringExtra("android.intent.action.PACKAGE_ADDED");
            //context.startService(addIntent);
        } else if (intent.equals(Intent.ACTION_PACKAGE_REMOVED)) {
            //insertIntoAppDetails();
            intent.getStringExtra("RmKey");
            //intent.getExtras();
            appDetails = new AppDetails();
            appDetails.getUid();
            appDetails.setApplicationType(Intent.ACTION_PACKAGE_REMOVED);
            appDetails.setApplicationName(Intent.ACTION_PACKAGE_REMOVED);
            appDetails.setPackageName(Intent.ACTION_PACKAGE_REMOVED);
            accessLayer.insertIntoAppDetails();
            //ccessLayer.deleteAnAppDetail(appDetails.getPackageName());
            accessLayer.updateAppDetail(appDetails.getApplicationName(), true);
//            if (intent.equals(Intent.ACTION_PACKAGE_REMOVED){
//                accessLayer.updateAppDetail(appDetails.getPackageName(),);
//            }

            //String app_removed = intent.getStringExtra("android.intent.action.PACKAGE_REMOVED");
            //context.startService(intent);
        } else {
            Log.i("Error:", "Package Error");
            //startService(intent);
        }
    }



    SQLiteAccessLayer accessLayer = new SQLiteAccessLayer(context, appDetails);
//
//        SQLiteAccessLayer accessLayer = new SQLiteAccessLayer(context, appDetails);
//        accessLayer.insertIntoAppDetails();
}


//SQLiteAccessLayer accessLayer = new SQLiteAccessLayer(this);
//        ContentValues newValues = new ContentValues();
//        newValues.put("android.intent.action.PACKAGE_INSTALLED", this.appDetails.getApplicationName());
//        newValues.put("android.intent.action.PACKAGE_ADDED", this.appDetails.getPackageName());
//        newValues.put("android.intent.action.PACKAGE_REMOVED", this.appDetails.getPackageName());
//        accessLayer.insertIntoAppDetails();





