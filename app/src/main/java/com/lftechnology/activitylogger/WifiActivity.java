package com.lftechnology.activitylogger;

import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.lftechnology.activitylogger.adapter.NetworkDataAdapter;
import com.lftechnology.activitylogger.controller.SQLiteAccessLayer;
import com.lftechnology.activitylogger.model.NetworkUsageDetails;
import com.lftechnology.activitylogger.model.NetworkUsageSummary;
import com.lftechnology.activitylogger.utilities.NetworkStatus;
import com.lftechnology.activitylogger.utilities.Utilities;

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

    private NetworkDataAdapter adapter;
    private List<NetworkUsageDetails> networkDetailsListToAdapter;
    private long total;
    private boolean activityBlank = false;

    @BindView(R.id.swipe_refresh_wifi_activity)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.application_list_wifi_usage)
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);
        ButterKnife.bind(this);

        swipeRefreshLayout.setOnRefreshListener(this);
        NetworkUsageSummary networkUsageSummary = new NetworkUsageSummary(this, Constants.WIFI_NETWORK);
        networkDetailsListToAdapter = networkUsageSummary.getNetworkUsageDetailsList();
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(WifiActivity.this);
        total = networkUsageSummary.getTotal();
        adapter = new NetworkDataAdapter(WifiActivity.this, networkDetailsListToAdapter, total);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
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
            Utilities.copyToNetworkTable(
                    Constants.WIFI_NETWORK, WifiActivity.this);
            NetworkUsageSummary networkUsageSummary = new NetworkUsageSummary(WifiActivity.this, Constants.WIFI_NETWORK);
            networkDetailsListToAdapter = networkUsageSummary.getNetworkUsageDetailsList();
            total = networkUsageSummary.getTotal();
            adapter = new NetworkDataAdapter(WifiActivity.this, networkDetailsListToAdapter, total);
            Boolean viewSet = false;
            if(networkDetailsListToAdapter.isEmpty()){
                viewSet = true;
            }
            Utilities.flushNetworkTempTable(WifiActivity.this);
            Utilities.fillIntoNetworkTempTable(WifiActivity.this);
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











