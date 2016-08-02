package com.lftechnology.activitylogger.Services;

import android.app.IntentService;
//mport android.app.backup.SharedPreferencesBackupHelper;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
//import android.preference.PreferenceManager;

import com.lftechnology.activitylogger.Controller.SQLiteAccessLayer;
import com.lftechnology.activitylogger.InstalledBroadcastReceiver;
import com.lftechnology.activitylogger.model.AppDetails;

/**
 * Created by DevilDewzone on 7/29/2016.
 */
public class InstalledMonitoringService extends IntentService {

    // public final static String APP_STATE = "app_state";
    public final static String APP_INSTALLED = "android.intent.action.PACKAGE_INSTALLED";
    public final static String APP_ADDED = "android.intent.action.PACKAGE_ADDED";
    public final static String APP_REMOVED = "android.intent.action.PACKAGE_REMOVED";

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
    // private SQLiteAccessLayer accessLayer;


    public InstalledMonitoringService() {
        super("InstalledMonitoringService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

//
//        if(intent.hasExtra(APP_STATE)) {
//            String app_state = intent.getStringExtra(APP_STATE);
//            //SharedPreferences sharedPreferences = getSharedPreferences("app_state", MODE_APPEND);

        if (intent.equals(APP_INSTALLED)) {
            //insertIntoAppDetails();
            intent.getStringExtra("KEY");
            //intent.getExtras();
            appDetails.setApplicationName(APP_INSTALLED);
            getAccessLayer();
            // accessLayer.insertIntoAppDetails();


            //String app_installed = intent.getStringExtra("android.intent.action.PACKAGE_INSTALLED");
            //context.startService(insIntent);

        } else if (intent.equals(APP_ADDED)) {
            //insertIntoAppDetails();
            intent.getStringExtra("KEY");
            //intent.getExtras();
            appDetails.setApplicationName(APP_ADDED);
            getAccessLayer();
            //accessLayer.insertIntoAppDetails();
            //String app_added = intent.getStringExtra("android.intent.action.PACKAGE_ADDED");
            //context.startService(addIntent);
        } else if (intent.equals(APP_REMOVED)) {
            //insertIntoAppDetails();
            intent.getStringExtra("KEY");
            //intent.getExtras();
            appDetails.setApplicationName(APP_REMOVED);
            getAccessLayer();
            //accessLayer.insertIntoAppDetails();
            //String app_removed = intent.getStringExtra("android.intent.action.PACKAGE_REMOVED");
            //context.startService(intent);
        } else {
            Log.i("Error:", "Package Error");
            //startService(intent);
        }
    }


    SQLiteAccessLayer accessLayer = new SQLiteAccessLayer(context, appDetails);

    public SQLiteAccessLayer getAccessLayer() {
        accessLayer.insertIntoAppDetails();
        return accessLayer;
    }
}
    //SQLiteAccessLayer accessLayer = new SQLiteAccessLayer(this);
//        ContentValues newValues = new ContentValues();
//        newValues.put("android.intent.action.PACKAGE_INSTALLED", this.appDetails.getApplicationName());
//        newValues.put("android.intent.action.PACKAGE_ADDED", this.appDetails.getPackageName());
//        newValues.put("android.intent.action.PACKAGE_REMOVED", this.appDetails.getPackageName());
//        accessLayer.insertIntoAppDetails();





