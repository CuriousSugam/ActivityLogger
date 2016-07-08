package com.lftechnology.activitylogger;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.lftechnology.activitylogger.Controller.SQLiteAccessLayer;
import com.lftechnology.activitylogger.model.AppDetails;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayAdapter<String> adapter;
    String[] namesOfApp,mostUsedApps, details;
    Long[] runTimeOfApp;
    Spinner spinner;
    ArrayAdapter<CharSequence> arrayAdapter;
    //static int intervals;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermissionForAccess();
    }
   @Override
    protected void onDestroy() {
        SharedPreferences sharedPreferences = getSharedPreferences("appName", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        super.onDestroy();
    }

    /**
     * if doesnot you havent enabled permission, settings opens for granting permission access with this
     */
    private void checkPermissionForAccess(){
        if(RawAppInfo.getUsageStatsAppList(this).isEmpty())
            startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
    }
}