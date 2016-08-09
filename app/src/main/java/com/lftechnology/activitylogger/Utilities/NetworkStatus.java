package com.lftechnology.activitylogger.Utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * This class provides the methods to simply check the connectivity status of the network;
 * both wifi and mobile data network.
 *
 * Created by Sugam on 8/8/2016.
 */
public class NetworkStatus {


    /**
     * it returns the connectivity status of the wifi network.
     * @param context context of the calling
     * @return true if wifi is connected else false
     */
    public static boolean isWifiConnected(Context context){
        boolean returnValue = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null){
            returnValue = (networkInfo.getType() == ConnectivityManager.TYPE_WIFI && networkInfo.isConnected());
        }
        return returnValue;
    }

    /**
     * it returns the connectivity status fo the mobile data network.
     *
     * @param context context of the calling
     * @return true if mobile data is connected else false
     */
    public static boolean isMobileDataConnected(Context context){
        boolean returnValue = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null){
            returnValue = (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE && networkInfo.isConnected());
        }
        return returnValue;
    }
}
