package com.lftechnology.activitylogger;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.lftechnology.activitylogger.Controller.SQLiteAccessLayer;
import com.lftechnology.activitylogger.model.AppDetails;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayAdapter<?> arrayAdapter;
    List<AppDetails> appDetailsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermissionForAccess();
        setList();
    }
    /**
     * if you havent enabled permission, settings opens for granting permission access with this
     */
    private void checkPermissionForAccess(){
        if(RawAppInfo.getUsageStatsAppList(this).isEmpty())
            startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
    }
    private void setList(){
        listView = (ListView)findViewById(R.id.appInfoList);
        //TODO show the list of apps after put and extracted from DB

    }
}