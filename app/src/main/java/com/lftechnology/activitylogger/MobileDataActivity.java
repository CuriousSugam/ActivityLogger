package com.lftechnology.activitylogger;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.lftechnology.activitylogger.adapter.NetworkDataAdapter;
import com.lftechnology.activitylogger.fragments.NoDataFragment;
import com.lftechnology.activitylogger.model.NetworkUsageDetails;
import com.lftechnology.activitylogger.model.NetworkUsageSummary;
import com.lftechnology.activitylogger.utilities.NetworkStatus;
import com.lftechnology.activitylogger.utilities.Utilities;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * lists all the applications that used the mobile data and sorts the applications with the maximum
 * mobile data using application first.
 */
public class MobileDataActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private NetworkDataAdapter adapter;
    private List<NetworkUsageDetails> networkDetailsListToAdapter;
    private long totalBytes = 0;

    @BindView(R.id.swipe_refresh_mobile_activity)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.application_list_mobile_usage)
    RecyclerView recyclerView;

    private NoDataFragment noDataFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_data);
        ButterKnife.bind(this);

        NetworkUsageSummary networkUsageSummary = new NetworkUsageSummary(this, Constants.MOBILE_NETWORK);
        networkDetailsListToAdapter = networkUsageSummary.getNetworkUsageDetailsList();

        if(networkDetailsListToAdapter.isEmpty()){
            noDataFragment = new NoDataFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.rl_mobile_no_data_container, noDataFragment, "noDataImage")
                    .commit();
        }

        swipeRefreshLayout.setOnRefreshListener(this);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MobileDataActivity.this);
        totalBytes = networkUsageSummary.getTotal();
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
     * This asynctask has 3 main objective:
     * 1. copy all the data from the temporary table to network table
     * 2. get all the mobile data usage statistics from the network table
     * 3. create and assign an adapter to the recycler view
     */
    private class FetchMobileDataAsyncTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {
            Utilities.copyToNetworkTable(
                    Constants.MOBILE_NETWORK, MobileDataActivity.this);

            NetworkUsageSummary networkUsageSummary = new NetworkUsageSummary(MobileDataActivity.this, Constants.MOBILE_NETWORK);
            networkDetailsListToAdapter = networkUsageSummary.getNetworkUsageDetailsList();
            totalBytes = networkUsageSummary.getTotal();
            adapter = new NetworkDataAdapter(MobileDataActivity.this, networkDetailsListToAdapter, totalBytes);
            Boolean viewSet = false;
            if(networkDetailsListToAdapter.isEmpty()){
                viewSet = true;
            }
            Utilities.flushNetworkTempTable(MobileDataActivity.this);
            Utilities.fillIntoNetworkTempTable(MobileDataActivity.this);
            return viewSet;
        }

        @Override
        protected void onPostExecute(Boolean viewSet) {
            if(!networkDetailsListToAdapter.isEmpty()){
                Fragment fragment = getSupportFragmentManager().findFragmentByTag("noDataImage");
                getSupportFragmentManager().beginTransaction().remove(fragment).commit();
            }
            if(viewSet){
                recyclerView.setAdapter(adapter);
            }else{
                recyclerView.swapAdapter(adapter, false);
            }
            swipeRefreshLayout.setRefreshing(false);
        }
    }
}
