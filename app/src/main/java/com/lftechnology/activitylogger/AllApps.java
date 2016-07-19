package com.lftechnology.activitylogger;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.ResolveInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;

import com.lftechnology.activitylogger.Adapter.AllAppsAdapter;
import com.lftechnology.activitylogger.Adapter.AllSystemAppsRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllApps extends AppCompatActivity {

    RecyclerView recyclerView, systemAppsRecyclerView;
    GridLayoutManager gridLayoutManager, gridLayoutManagerForSystemApps;
    AllAppsAdapter adapter;
    AllSystemAppsRecyclerViewAdapter systemAppsRecyclerViewAdapter;

    private static final String SYSTEM_APP = "systemApps";
    private static final String INSTALLED_APP = "installedApps";

    private ProgressBar progressBar;
    private ScrollView scrollView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_all_apps);
        scrollView = (ScrollView)findViewById(R.id.scrollViewAllApplicationList);
        progressBar = (ProgressBar)findViewById(R.id.progress_bar);
//        spinner.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        recyclerView = (RecyclerView)findViewById(R.id.recycler_view_all_apps);

        systemAppsRecyclerView = (RecyclerView)findViewById(R.id.recycler_view_all_system_apps);


        Log.e("progresscheck", "before asynctask");
        new GetApplicationsTask().execute();


    }

    public class GetApplicationsTask extends AsyncTask<Void,Integer, Map<String, List> > {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected Map<String, List> doInBackground(Void... voids) {
            Map<String, List> map = new HashMap<>();
            Log.e("progresscheck", "during asynctask");
            map.put(INSTALLED_APP, RawAppInfo.getAllInstalledApps(AllApps.this));
            map.put(SYSTEM_APP, RawAppInfo.getSystemApps(AllApps.this));
            return map;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
        }

        @Override
        protected void onPostExecute(Map<String, List> stringListMap) {
            super.onPostExecute(stringListMap);
            adapter = new AllAppsAdapter(AllApps.this, stringListMap.get(INSTALLED_APP));

            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setHasFixedSize(true);

            systemAppsRecyclerViewAdapter = new AllSystemAppsRecyclerViewAdapter(AllApps.this, stringListMap.get(SYSTEM_APP));
            systemAppsRecyclerView.setAdapter(systemAppsRecyclerViewAdapter);
            recyclerView.setAdapter(adapter);
            Log.e("progresscheck", "after asynctask");
            scrollView.setVisibility(View.VISIBLE);

            systemAppsRecyclerView.setNestedScrollingEnabled(false);
            recyclerView.setHasFixedSize(true);

            gridLayoutManager = new GridLayoutManager(AllApps.this, 4);
            gridLayoutManagerForSystemApps = new GridLayoutManager(AllApps.this, 4);


            systemAppsRecyclerView.setLayoutManager(gridLayoutManagerForSystemApps);
            recyclerView.setLayoutManager(gridLayoutManager);
        }
    }
}
