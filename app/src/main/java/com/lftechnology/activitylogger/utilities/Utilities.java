package com.lftechnology.activitylogger.utilities;

import android.content.Context;
import android.net.TrafficStats;

import com.lftechnology.activitylogger.controller.SQLiteAccessLayer;
import com.lftechnology.activitylogger.model.AppDetails;
import com.lftechnology.activitylogger.model.NetworkUsageDetails;

import java.util.List;

/**
 * This class contains the utility methods that are needed by other classes for different operation.
 * <p/>
 * Created by Sugam on 8/16/2016.
 */
public class Utilities {

    /**
     * copies the content of the temporary network table to the network table of the database.
     * temporary network table: It consists of the initial network usage statistics of every application
     * network table: It consists of the final calculated usage statistics of every application
     *
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
            sqLiteAccessLayer.setNetworkUsageDetails(networkUsageDetails);
            sqLiteAccessLayer.insertTempNetworkDetails();
        }
        sqLiteAccessLayer.closeDatabaseConnection();
    }


    /**
     * takes the number of bytes as input and convert it to the readable memory format
     * for eg: if the input to the method is 1024 then it returns 1KB
     *
     * @param membytes number of bytes
     * @return memory size in the readable format
     */
    public static String memorySizeFormat(long membytes) {
        float bytes = (float) membytes;
        String returnValue;
        if (bytes > Math.pow(1024, 3)) {
            returnValue = String.format("%.2f %s",bytes / Math.pow(1024, 3),"GB");
        } else if (bytes > Math.pow(1024, 2)) {
            returnValue = String.format("%.2f %s", bytes / Math.pow(1024, 2), " MB");
        } else if (bytes > 1024) {
            returnValue = String.format("%.2f %s", bytes / (1024), " KB");
        } else {
            returnValue = String.format("%.2f %s", bytes, " bytes");
        }
        return returnValue;
    }
}
