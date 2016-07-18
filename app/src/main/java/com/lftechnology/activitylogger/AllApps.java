package com.lftechnology.activitylogger;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lftechnology.activitylogger.Adapter.AllAppsAdapter;

import java.util.ArrayList;
import java.util.List;

public class AllApps extends AppCompatActivity {

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    AllAppsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_apps);

        recyclerView = (RecyclerView)findViewById(R.id.recycler_view_all_apps);
        recyclerView.setHasFixedSize(true);

        List<PackageInfo> packageInfoList = RawAppInfo.getAllInstalledApps(this);
        List<PackageInfo> packageInfos = new ArrayList<>();
        for(PackageInfo packageInfo: packageInfoList){
            if((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                packageInfos.add(packageInfo);
            }
        }

        linearLayoutManager = new LinearLayoutManager(this);
        adapter = new AllAppsAdapter(this, packageInfos);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);


    }
}
