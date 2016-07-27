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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * lists all the applications that used the wifi data and sorts the applications with the maximum
 * wifi data using application first.
 */
public class MobileDataActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    NetworkDataAdapter adapter;
    private long totalBytes = 0;

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
                totalBytes = totalBytes + networkUsageDetails.getTotalRxBytes()+networkUsageDetails.getTotalTxBytes();
            }else{
                NetworkUsageDetails tempDetails = mapNetworkDetails.get(networkUsageDetails.getPackageName());
                tempDetails.setTotalRxBytes(tempDetails.getTotalRxBytes()+networkUsageDetails.getTotalRxBytes());
                tempDetails.setTotalTxBytes(tempDetails.getTotalTxBytes()+networkUsageDetails.getTotalTxBytes());
                totalBytes = totalBytes + networkUsageDetails.getTotalRxBytes()+networkUsageDetails.getTotalTxBytes();
                mapNetworkDetails.put(networkUsageDetails.getPackageName(), tempDetails);
            }
        }

        List<NetworkUsageDetails> networkDetailsListToAdapter = new ArrayList<>(mapNetworkDetails.values());
        Collections.sort(networkDetailsListToAdapter, new Comparator<NetworkUsageDetails>() {
            @Override
            public int compare(NetworkUsageDetails networkUsageDetails, NetworkUsageDetails t1) {
                long total1 = networkUsageDetails.getTotalRxBytes()+networkUsageDetails.getTotalTxBytes();
                long total2 = t1.getTotalRxBytes()+t1.getTotalTxBytes();
                return total1 > total2 ? -1 : total1 < total2 ? 1 : 0;
            }
        });
        recyclerView = (RecyclerView)findViewById(R.id.application_list_mobile_usage);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(MobileDataActivity.this);
        adapter = new NetworkDataAdapter(MobileDataActivity.this, networkDetailsListToAdapter, totalBytes);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}
