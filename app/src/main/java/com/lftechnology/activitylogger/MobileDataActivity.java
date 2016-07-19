package com.lftechnology.activitylogger;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.lftechnology.activitylogger.Adapter.NetworkDataAdapter;
import com.lftechnology.activitylogger.Controller.SQLiteAccessLayer;
import com.lftechnology.activitylogger.Services.ConnectivityChangeMonitoringIntentService;
import com.lftechnology.activitylogger.model.NetworkUsageDetails;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MobileDataActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    NetworkDataAdapter adapter;

    private List<String> keyPackageName = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_data);

        SQLiteAccessLayer sqLiteAccessLayer = new SQLiteAccessLayer(this);
        List<NetworkUsageDetails> networkUsageDetailsList = sqLiteAccessLayer.queryNetworkUsageDetails(ConnectivityChangeMonitoringIntentService.MOBILE_NETWORK);
        Map<String, NetworkUsageDetails> mapNetworkDetails = new HashMap<>();

        for(NetworkUsageDetails networkUsageDetails : networkUsageDetailsList){
            if(!keyPackageName.contains(networkUsageDetails.getPackageName())){
                keyPackageName.add(networkUsageDetails.getPackageName());
                mapNetworkDetails.put(networkUsageDetails.getPackageName(), networkUsageDetails);
                Log.e("keyPackage", "add to key package: "+networkUsageDetails.getPackageName());
            }else{
                NetworkUsageDetails tempDetails = mapNetworkDetails.get(networkUsageDetails.getPackageName());
                tempDetails.setTotalRxBytes(tempDetails.getTotalRxBytes()+networkUsageDetails.getTotalRxBytes());
                tempDetails.setTotalTxBytes(tempDetails.getTotalTxBytes()+networkUsageDetails.getTotalTxBytes());
                mapNetworkDetails.put(networkUsageDetails.getPackageName(), tempDetails);
                Log.e("mapPackage", "mapUpdated: "+tempDetails.getPackageName()+" "+tempDetails.getTotalRxBytes()+" "+tempDetails.getTotalTxBytes());
            }
        }

        List<NetworkUsageDetails> networkDetailsListToAdapter = new ArrayList<>(mapNetworkDetails.values());

        recyclerView = (RecyclerView)findViewById(R.id.application_list_wifi_usage);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(MobileDataActivity.this);
        adapter = new NetworkDataAdapter(MobileDataActivity.this, networkDetailsListToAdapter);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}