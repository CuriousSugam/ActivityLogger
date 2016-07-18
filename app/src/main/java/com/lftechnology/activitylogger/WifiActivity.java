package com.lftechnology.activitylogger;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.net.Network;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;

import com.lftechnology.activitylogger.Controller.SQLiteAccessLayer;
import com.lftechnology.activitylogger.model.AppDetails;
import com.lftechnology.activitylogger.model.NetworkUsageDetails;
import com.lftechnology.activitylogger.Services.ConnectivityChangeMonitoringIntentService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WifiActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    NetworkDataAdapter adapter;

    private List<String> keyPackageName = new ArrayList<>();
    private List<NetworkUsageDetails> networkDetailsListToAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);
        Log.e("message", "welcome to wifi activity");

        SQLiteAccessLayer sqLiteAccessLayer = new SQLiteAccessLayer(this);
        List<NetworkUsageDetails> networkUsageDetailsList = sqLiteAccessLayer.queryNetworkUsageDetails();
        Map<String, NetworkUsageDetails> mapNetworkDetails = new HashMap<>();

        for(NetworkUsageDetails networkUsageDetails : networkUsageDetailsList){
            if(!keyPackageName.contains(networkUsageDetails.getPackageName())){
                keyPackageName.add(networkUsageDetails.getPackageName());
                mapNetworkDetails.put(networkUsageDetails.getPackageName(), networkUsageDetails);
            }else{
                NetworkUsageDetails tempDetails = mapNetworkDetails.get(networkUsageDetails.getPackageName());
                tempDetails.setTotalRxBytes(tempDetails.getTotalRxBytes()+networkUsageDetails.getTotalRxBytes());
                tempDetails.setTotalTxBytes(tempDetails.getTotalTxBytes()+networkUsageDetails.getTotalTxBytes());
                mapNetworkDetails.put(networkUsageDetails.getPackageName(), tempDetails);
            }
        }

        networkDetailsListToAdapter = new ArrayList<>(mapNetworkDetails.values());



        for(NetworkUsageDetails n : networkDetailsListToAdapter){
            Log.e("netDataUsed", n.getPackageName()+" "+n.getTotalTxBytes()/(1024*1024)+" "+n.getTotalRxBytes()/(1024*1024));
        }

        recyclerView = (RecyclerView)findViewById(R.id.application_list_wifi_usage);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(WifiActivity.this);
        adapter = new NetworkDataAdapter(WifiActivity.this, networkUsageDetailsList);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
    }

}
