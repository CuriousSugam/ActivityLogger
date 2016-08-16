package com.lftechnology.activitylogger.model;

import android.content.Context;
import android.util.Log;

import com.lftechnology.activitylogger.controller.SQLiteAccessLayer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class contains the methods to get the summary of the usage of the network.
 * <p/>
 * Created by Sugam on 8/16/2016.
 */
public class NetworkUsageSummary {
    private List<NetworkUsageDetails> networkUsageDetailsList;
    private long total;

    public NetworkUsageSummary(Context context, String networkType) {
        networkUsageDetailsList = getNetworkUsageDetails(context, networkType);
    }

    public List<NetworkUsageDetails> getNetworkUsageDetailsList() {
        return networkUsageDetailsList;
    }

    public long getTotal() {
        return total;
    }

    /**
     * get the List of NetworkUsageDetails objects that contain the mobile usage data statistics
     *
     * @return the List of NetworkUsageDetails objects
     */
    private List<NetworkUsageDetails> getNetworkUsageDetails(Context context, String networkType) {
        List<String> keyPackageName = new ArrayList<>();
        SQLiteAccessLayer sqLiteAccessLayer = new SQLiteAccessLayer(context);
        List<NetworkUsageDetails> networkUsageDetailsList =
                sqLiteAccessLayer.queryNetworkUsageDetails(networkType);
        Map<String, NetworkUsageDetails> mapNetworkDetails = new HashMap<>();

        for (NetworkUsageDetails networkUsageDetails : networkUsageDetailsList) {
            if (!keyPackageName.contains(networkUsageDetails.getPackageName())) {
                keyPackageName.add(networkUsageDetails.getPackageName());
                mapNetworkDetails.put(networkUsageDetails.getPackageName(), networkUsageDetails);
                this.total = this.total + networkUsageDetails.getTotalRxBytes() + networkUsageDetails.getTotalTxBytes();
            } else {
                NetworkUsageDetails tempDetails = mapNetworkDetails.get(networkUsageDetails.getPackageName());
                tempDetails.setTotalRxBytes(tempDetails.getTotalRxBytes() + networkUsageDetails.getTotalRxBytes());
                tempDetails.setTotalTxBytes(tempDetails.getTotalTxBytes() + networkUsageDetails.getTotalTxBytes());
                mapNetworkDetails.put(networkUsageDetails.getPackageName(), tempDetails);
                this.total = this.total + networkUsageDetails.getTotalRxBytes() + networkUsageDetails.getTotalTxBytes();
            }
        }

        List<NetworkUsageDetails> networkDetailsListToAdapter = new ArrayList<>(mapNetworkDetails.values());
        Log.e("checkNetworkList", "networkDetails list size : "+ networkDetailsListToAdapter.size());
        networkDetailsListToAdapter = sortNetworkDetailsList(networkDetailsListToAdapter);
        Log.e("checkNetworkList", "networkDetails list size after sorting : "+ networkDetailsListToAdapter.size());
        return networkDetailsListToAdapter;
    }

    /**
     * sorts the given List of NetworkUsageDetails in descending order;
     * highest network data consuming application at first
     *
     * @param networkUsageDetailsList List of NetworkUsageDetails objects
     * @return sorted List<NetworkUsageDetails>
     */
    private List<NetworkUsageDetails> sortNetworkDetailsList(List<NetworkUsageDetails> networkUsageDetailsList) {
        Collections.sort(networkUsageDetailsList, new Comparator<NetworkUsageDetails>() {
            @Override
            public int compare(NetworkUsageDetails networkUsageDetails, NetworkUsageDetails t1) {
                long total1 = networkUsageDetails.getTotalRxBytes() + networkUsageDetails.getTotalTxBytes();
                long total2 = t1.getTotalRxBytes() + t1.getTotalTxBytes();
                return total1 > total2 ? -1 : total1 < total2 ? 1 : 0;
            }
        });
        return networkUsageDetailsList;
    }

}
