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
 * lists all the applications that used the mobile data and sorts the applications with the maximum
 * mobile data using application first.
 */
public class MobileDataActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private NetworkDataAdapter adapter;
    private long totalBytes = 0;
    private List<NetworkUsageDetails> networkDetailsListToAdapter;

    @BindView(R.id.swipeRefreshMobileActivity)
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_data);
        ButterKnife.bind(this);

        swipeRefreshLayout.setOnRefreshListener(this);
        networkDetailsListToAdapter = getMobileUsageDetails();

        recyclerView = (RecyclerView) findViewById(R.id.application_list_mobile_usage);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(MobileDataActivity.this);

        adapter = new NetworkDataAdapter(MobileDataActivity.this, networkDetailsListToAdapter, totalBytes);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onRefresh() {
        if(NetworkStatus.isMobileDataConnected(this)){
            FetchMobileDataAsyncTask fetchMobileDataAsyncTask = new FetchMobileDataAsyncTask();
            fetchMobileDataAsyncTask.execute();
        }else{
            swipeRefreshLayout.setRefreshing(false);
            Toast.makeText(MobileDataActivity.this, "Mobile Data not Connected", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     *  get the List of NetworkUsageDetails objects that contain the mobile usage data statistics
     *
     * @return the List of NetworkUsageDetails objects
     */
    private List<NetworkUsageDetails> getMobileUsageDetails() {
        List<String> keyPackageName = new ArrayList<>();
        SQLiteAccessLayer sqLiteAccessLayer = new SQLiteAccessLayer(this);
        List<NetworkUsageDetails> networkUsageDetailsList = sqLiteAccessLayer.queryNetworkUsageDetails(
                ConnectivityChangeMonitoringIntentService.MOBILE_NETWORK);
        Map<String, NetworkUsageDetails> mapNetworkDetails = new HashMap<>();
        totalBytes = 0;

        for (NetworkUsageDetails networkUsageDetails : networkUsageDetailsList) {
            if (!keyPackageName.contains(networkUsageDetails.getPackageName())) {
                keyPackageName.add(networkUsageDetails.getPackageName());
                mapNetworkDetails.put(networkUsageDetails.getPackageName(), networkUsageDetails);
                totalBytes = totalBytes + networkUsageDetails.getTotalRxBytes() + networkUsageDetails.getTotalTxBytes();
            } else {
                NetworkUsageDetails tempDetails = mapNetworkDetails.get(networkUsageDetails.getPackageName());
                tempDetails.setTotalRxBytes(tempDetails.getTotalRxBytes() + networkUsageDetails.getTotalRxBytes());
                tempDetails.setTotalTxBytes(tempDetails.getTotalTxBytes() + networkUsageDetails.getTotalTxBytes());
                totalBytes = totalBytes + networkUsageDetails.getTotalRxBytes() + networkUsageDetails.getTotalTxBytes();
                mapNetworkDetails.put(networkUsageDetails.getPackageName(), tempDetails);
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

    /**
     * This asynctask has 3 main objective:
     * 1. copy all the data from the temporary table to network table
     * 2. get all the mobile data usage statistics from the network table
     * 3. create and assign an adapter to the recycler view
     */
    private class FetchMobileDataAsyncTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {
            ConnectivityChangeMonitoringIntentService.copyToNetworkTable(
                    ConnectivityChangeMonitoringIntentService.MOBILE_NETWORK, MobileDataActivity.this);

            networkDetailsListToAdapter =  getMobileUsageDetails();
            adapter = new NetworkDataAdapter(MobileDataActivity.this, networkDetailsListToAdapter, totalBytes);
            Boolean viewSet = false;
            if(networkDetailsListToAdapter.isEmpty()){
                viewSet = true;
            }
            ConnectivityChangeMonitoringIntentService.flushNetworkTempTable(MobileDataActivity.this);
            ConnectivityChangeMonitoringIntentService.fillIntoNetworkTempTable(MobileDataActivity.this);
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
