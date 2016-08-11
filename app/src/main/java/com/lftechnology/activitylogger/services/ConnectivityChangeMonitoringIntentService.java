package com.lftechnology.activitylogger.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.TrafficStats;

import com.lftechnology.activitylogger.controller.SQLiteAccessLayer;
import com.lftechnology.activitylogger.model.AppDetails;
import com.lftechnology.activitylogger.model.NetworkUsageDetails;

import java.util.List;

/**
 * ConnectivityChangeMonitoringIntentService is an IntentService. It keeps track of the network data
 * used by every application and updates the database
 * <p/>
 * Created by Sugam on 7/13/2016.
 */
public class ConnectivityChangeMonitoringIntentService extends IntentService {

    public final static String WIFI_NETWORK = "wifi";
    public final static String MOBILE_NETWORK = "mobile";
    public final static String OFFLINE = "offline";
    public final static String NETWORK_TYPE = "networkType";


    public ConnectivityChangeMonitoringIntentService() {
        super("ConnectivityMonitorService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        if (intent.hasExtra(NETWORK_TYPE)) {
            String networkType = intent.getStringExtra(NETWORK_TYPE);
            SharedPreferences sharedPreferences = getSharedPreferences("networkState", MODE_PRIVATE);

            if (networkType.equals(WIFI_NETWORK)) {    // when wifi is turned on
                // fill into the temporary table and set the current network type i.e 'wifi' into the shared preference
                fillIntoNetworkTempTable(this);
                sharedPreferences.edit().putBoolean(WIFI_NETWORK, true).apply();
            } else if (networkType.equals(MOBILE_NETWORK)) { // when mobile data is turned on
                // fill into the temporary table
                // set the current network type i.e 'mobile' into the shared preferences
                fillIntoNetworkTempTable(this);
                sharedPreferences.edit().putBoolean(MOBILE_NETWORK, true).apply();
            } else if (networkType.equals(OFFLINE)) {  // when the neither wifi nor mobile data are turned on i.e when both are turned off
                // get the recent network type from the shared preferences
                if (sharedPreferences.getBoolean(WIFI_NETWORK, false)) {
                    copyToNetworkTable(WIFI_NETWORK, this);
                    sharedPreferences.edit().putBoolean(WIFI_NETWORK, false).apply();
                    flushNetworkTempTable(this);
                }else if (sharedPreferences.getBoolean(MOBILE_NETWORK, false)) {
                    copyToNetworkTable(MOBILE_NETWORK, this);
                    sharedPreferences.edit().putBoolean(MOBILE_NETWORK, false).apply();
                    flushNetworkTempTable(this);
                }
            }
        }
    }

    /**
     * This method fills the initial network usage bytes of every application into the table of the
     * database
     */
    public static void fillIntoNetworkTempTable(Context context) {
        SQLiteAccessLayer sqLiteAccessLayer = new SQLiteAccessLayer(context);
        List<AppDetails> appDetailsList = sqLiteAccessLayer.queryAppDetails();
        for (AppDetails appDetails : appDetailsList) {
            long initialRxBytes = TrafficStats.getUidRxBytes(appDetails.getUid());
            long initialTxBytes = TrafficStats.getUidTxBytes(appDetails.getUid());
            NetworkUsageDetails networkUsageDetails = new NetworkUsageDetails(
                    appDetails.getPackageName(), initialRxBytes, initialTxBytes);
            SQLiteAccessLayer mSqLiteAccessLayer = new SQLiteAccessLayer(context, networkUsageDetails);
            mSqLiteAccessLayer.insertTempNetworkDetails();
            mSqLiteAccessLayer.closeDatabaseConnection();
        }
    }

    /**
     * copies the content of the temporary network table to the network table of the database.
     * temporary network table: It consists of the initial network usage statistics of every application
     * network table: It consists of the final calculated usage statistics of every application
     * @param networkType is the type of network that the data of network statistics belongs to. It
     *                    is either 'mobile' or 'wifi'
     */
    public static void copyToNetworkTable(String networkType, Context context) {
        SQLiteAccessLayer sqLiteAccessLayer = new SQLiteAccessLayer(context);
        sqLiteAccessLayer.insertNetworkDetails(context, networkType);
        sqLiteAccessLayer.closeDatabaseConnection();
    }

    /**
     * This method deletes all the records of the temporary network table and empties it in order to
     * reuse it for next session.
     */
    public static void flushNetworkTempTable(Context context) {
        SQLiteAccessLayer sqLiteAccessLayer = new SQLiteAccessLayer(context);
        sqLiteAccessLayer.emptyTempNetworkUsageDetails();
        sqLiteAccessLayer.closeDatabaseConnection();
    }

//    public static void updateNetworkTempTable()


}
