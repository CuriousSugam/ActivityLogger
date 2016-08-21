package com.lftechnology.activitylogger.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;

import com.lftechnology.activitylogger.Constants;
import com.lftechnology.activitylogger.utilities.Utilities;

/**
 * ConnectivityChangeMonitoringIntentService is an IntentService. It keeps track of the network data
 * used by every application and updates the database
 * <p/>
 * Created by Sugam on 7/13/2016.
 */
public class ConnectivityChangeMonitoringIntentService extends IntentService {

    public ConnectivityChangeMonitoringIntentService() {
        super("ConnectivityMonitorService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        if (intent.hasExtra(Constants.NETWORK_TYPE)) {
            String networkType = intent.getStringExtra(Constants.NETWORK_TYPE);
            SharedPreferences sharedPreferences = getSharedPreferences("networkState", MODE_PRIVATE);

            if (networkType.equals(Constants.WIFI_NETWORK)) {    // when wifi is turned on
                Utilities.fillIntoNetworkTempTable(this);
                sharedPreferences.edit().putBoolean(Constants.WIFI_NETWORK, true).apply();
            } else if (networkType.equals(Constants.MOBILE_NETWORK)) { // when mobile data is turned on
                Utilities.fillIntoNetworkTempTable(this);
                sharedPreferences.edit().putBoolean(Constants.MOBILE_NETWORK, true).apply();
            } else if (networkType.equals(Constants.OFFLINE)) {  // when the neither wifi nor mobile data are turned on i.e when both are turned off
                if (sharedPreferences.getBoolean(Constants.WIFI_NETWORK, false)) {
                    Utilities.copyToNetworkTable(Constants.WIFI_NETWORK, this);
                    sharedPreferences.edit().putBoolean(Constants.WIFI_NETWORK, false).apply();
                    Utilities.flushNetworkTempTable(this);
                }else if (sharedPreferences.getBoolean(Constants.MOBILE_NETWORK, false)) {
                    Utilities.copyToNetworkTable(Constants.MOBILE_NETWORK, this);
                    sharedPreferences.edit().putBoolean(Constants.MOBILE_NETWORK, false).apply();
                    Utilities.flushNetworkTempTable(this);
                }
            }
        }
    }


}
