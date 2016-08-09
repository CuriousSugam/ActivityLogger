package com.lftechnology.activitylogger;

import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.lftechnology.activitylogger.Adapter.NetworkDataAdapter;
import com.lftechnology.activitylogger.Controller.SQLiteAccessLayer;
import com.lftechnology.activitylogger.Services.ConnectivityChangeMonitoringIntentService;
import com.lftechnology.activitylogger.Model.NetworkUsageDetails;
import com.lftechnology.activitylogger.Utilities.NetworkStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * lists all the applications that used the wifi data and sorts the applications with the maximum
 * wifi data using application first.
 */
public class WifiActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private long total = 0;

    private NetworkDataAdapter adapter;
    private List<NetworkUsageDetails> networkDetailsListToAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;

    private boolean activityBlank = false;

    @BindView(R.id.swipeRefreshWifiActivity)
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);
        ButterKnife.bind(this);

        swipeRefreshLayout.setOnRefreshListener(this);
        networkDetailsListToAdapter = getWifiUsageDetails();

        recyclerView = (RecyclerView) findViewById(R.id.application_list_wifi_usage);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(WifiActivity.this);
        adapter = new NetworkDataAdapter(WifiActivity.this, networkDetailsListToAdapter, total);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    /**
     *  get the List of NetworkUsageDetails objects that contain the wifi data usage statistics
     *
     * @return the List of NetworkUsageDetails objects
     */
    private List<NetworkUsageDetails> getWifiUsageDetails() {
        List<String> keyPackageName = new ArrayList<>();
        SQLiteAccessLayer sqLiteAccessLayer = new SQLiteAccessLayer(this);
        List<NetworkUsageDetails> networkUsageDetailsList = sqLiteAccessLayer.queryNetworkUsageDetails(
                ConnectivityChangeMonitoringIntentService.WIFI_NETWORK);
        Map<String, NetworkUsageDetails> mapNetworkDetails = new HashMap<>();
        total = 0;

        for (NetworkUsageDetails networkUsageDetails : networkUsageDetailsList) {
            if (!keyPackageName.contains(networkUsageDetails.getPackageName())) {
                keyPackageName.add(networkUsageDetails.getPackageName());
                mapNetworkDetails.put(networkUsageDetails.getPackageName(), networkUsageDetails);
                total += networkUsageDetails.getTotalRxBytes() + networkUsageDetails.getTotalTxBytes();
            } else {
                NetworkUsageDetails tempDetails = mapNetworkDetails.get(networkUsageDetails.getPackageName());
                tempDetails.setTotalRxBytes(tempDetails.getTotalRxBytes() + networkUsageDetails.getTotalRxBytes());
                tempDetails.setTotalTxBytes(tempDetails.getTotalTxBytes() + networkUsageDetails.getTotalTxBytes());
                mapNetworkDetails.put(networkUsageDetails.getPackageName(), tempDetails);
                total += networkUsageDetails.getTotalRxBytes() + networkUsageDetails.getTotalTxBytes();
            }
        }

        List<NetworkUsageDetails> networkDetailsListToAdapter = new ArrayList<>(mapNetworkDetails.values());
        Collections.sort(networkDetailsListToAdapter, new Comparator<NetworkUsageDetails>() {
            @Override
            public int compare(NetworkUsageDetails networkUsageDetails, NetworkUsageDetails t1) {
                long total1 = networkUsageDetails.getTotalRxBytes() + networkUsageDetails.getTotalTxBytes();
                long total2 = t1.getTotalRxBytes() + t1.getTotalTxBytes();
                return total1 > total2 ? -1 : total1 < total2 ? 1 : 0;
            }
        });
        return networkDetailsListToAdapter;
    }

    @Override
    public void onRefresh() {
        if (NetworkStatus.isWifiConnected(this)) {
            FetchWifiDataAsyncTask fetchWifiDataAsyncTask = new FetchWifiDataAsyncTask();
            fetchWifiDataAsyncTask.execute();
        } else {
            swipeRefreshLayout.setRefreshing(false);
            Toast.makeText(WifiActivity.this, "Wifi not Connected", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * This asynctask has 3 main objective:
     * 1. copy all the data from the temporary table to network table
     * 2. get all the wifi data usage statistics from the network table
     * 3. create and assign an adapter to the recycler view
     */
    private class FetchWifiDataAsyncTask extends AsyncTask<Void, Void, Boolean>{

        @Override
        protected Boolean doInBackground(Void... voids) {
            ConnectivityChangeMonitoringIntentService.copyToNetworkTable(
                    ConnectivityChangeMonitoringIntentService.WIFI_NETWORK, WifiActivity.this);

            networkDetailsListToAdapter =  getWifiUsageDetails();
            adapter = new NetworkDataAdapter(WifiActivity.this, networkDetailsListToAdapter, total);
            Boolean viewSet = false;
            if(networkDetailsListToAdapter.isEmpty()){
                viewSet = true;
            }
            ConnectivityChangeMonitoringIntentService.flushNetworkTempTable(WifiActivity.this);
            ConnectivityChangeMonitoringIntentService.fillIntoNetworkTempTable(WifiActivity.this);
            return viewSet;
        }

        @Override
        protected void onPostExecute(Boolean viewSet) {
            if(viewSet){
                recyclerView.setAdapter(adapter);
            }else{
                recyclerView.swapAdapter(adapter, false);
            }
            swipeRefreshLayout.setRefreshing(false);
        }
    }
}











