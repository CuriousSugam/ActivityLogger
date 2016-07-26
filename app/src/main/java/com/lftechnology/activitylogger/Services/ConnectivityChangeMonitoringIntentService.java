package com.lftechnology.activitylogger.Services;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.TrafficStats;

import com.lftechnology.activitylogger.Controller.SQLiteAccessLayer;
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

    public ConnectivityChangeMonitoringIntentService() {
        super("ConnectivityMonitorService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        if (intent.hasExtra("networkType")) {
            String networkType = intent.getStringExtra("networkType");
            SharedPreferences sharedPreferences = getSharedPreferences("networkState", MODE_PRIVATE);

            if (networkType.equals(WIFI_NETWORK)) {    // when wifi is turned on
                // fill into the temporary table and set the current network type i.e 'wifi' into the shared preference
                fillIntoNetworkTempTable();
                sharedPreferences.edit().putBoolean("wifi", true).apply();
            } else if (networkType.equals(MOBILE_NETWORK)) { // when mobile data is turned on
                // fill into the temporary table
                // set the current network type i.e 'mobile' into the shared preferences
                fillIntoNetworkTempTable();
                sharedPreferences.edit().putBoolean("mobile", true).apply();
            } else if (networkType.equals(OFFLINE)) {  // when the neither wifi nor mobile data are turned on i.e when both are turned off
                // get the recent network type from the shared preferences
                if (sharedPreferences.getBoolean("wifi", false)) {
                    copyToNetworkTable(WIFI_NETWORK);
                    sharedPreferences.edit().putBoolean("wifi", false).apply();
                    flushNetworkTempTable();
                }else if (sharedPreferences.getBoolean("mobile", false)) {
                    copyToNetworkTable(MOBILE_NETWORK);
                    sharedPreferences.edit().putBoolean("mobile", false).apply();
                    flushNetworkTempTable();
                }
            }
        }
    }

    /**
     * This method fills the initial network usage bytes of every application into the table of the
     * database
     */
    private void fillIntoNetworkTempTable() {
        SQLiteAccessLayer sqLiteAccessLayer = new SQLiteAccessLayer(this);
        List<AppDetails> appDetailsList = sqLiteAccessLayer.queryAppDetails();
        for (AppDetails appDetails : appDetailsList) {
            long initialRxBytes = TrafficStats.getUidRxBytes(appDetails.getUid());
            long initialTxBytes = TrafficStats.getUidTxBytes(appDetails.getUid());
            NetworkUsageDetails networkUsageDetails = new NetworkUsageDetails(
                    appDetails.getPackageName(), initialRxBytes, initialTxBytes);
            SQLiteAccessLayer mSqLiteAccessLayer = new SQLiteAccessLayer(this, networkUsageDetails);
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
    private void copyToNetworkTable(String networkType) {
        SQLiteAccessLayer sqLiteAccessLayer = new SQLiteAccessLayer(this);
        sqLiteAccessLayer.insertNetworkDetails(this, networkType);
        sqLiteAccessLayer.closeDatabaseConnection();
    }

    /**
     * This method deletes all the records of the temporary network table and empties it in order to
     * reuse it for next session.
     */
    private void flushNetworkTempTable() {
        SQLiteAccessLayer sqLiteAccessLayer = new SQLiteAccessLayer(this);
        sqLiteAccessLayer.emptyTempNetworkUsageDetails();
        sqLiteAccessLayer.closeDatabaseConnection();
    }


}
