package com.lftechnology.activitylogger;

import com.lftechnology.activitylogger.Controller.SQLiteAccessLayer;

import android.content.Context;
import android.content.SharedPreferences;
import android.app.usage.UsageStats;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
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
import android.util.Log;
import com.lftechnology.activitylogger.Controller.SQLiteAccessLayer;
import com.lftechnology.activitylogger.model.AppDetails;
import java.util.ArrayList;
import java.util.List;
public class MainActivity extends AppCompatActivity {


    ListView listView;
    ArrayAdapter<String> adapter;
    String[] namesOfApp,mostUsedApps, details;
    Long[] runTimeOfApp;
    Spinner spinner;
    ArrayAdapter<CharSequence> arrayAdapter;
    //static int intervals;
    private List<AppDetails> appDetailsFromDatabase;
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
=======
        //  check if the permissions for this app is set
        //       if the permission is not set then
        //              close the app and redirect to the usage permission access activity
        //       if the permission is set then
        //          check if the database is empty
        //               if the database is empty insert the app details into the database
        //               if the database is not empty then fetch the app details from the database

        if (!CheckPermissions.isPermissionForAccessSet(this)) {
            // TODO Add the code that informs the user that the permission needed for this app is to be set using a popup window
            finish();
            startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
        } else {
            appDetailsFromDatabase = getAppDetailsFromDatabase();
        }
    }

    /**
     * This method first checks if the app details has been added to database. If yes, the data from
     * the database if fetched and returned. If the data has not been added to database, it first adds
     * the data to the database and then returns it.
     * @return a List of AppDetails objects containing the data of applications i.e List<AppDetails>
>>>>>>> splashscreen
     */
    private List<AppDetails> getAppDetailsFromDatabase() {
        SQLiteAccessLayer sqLiteAccessLayer = new SQLiteAccessLayer(this);
        List<AppDetails> appDetailsList;
        if (sqLiteAccessLayer.isDatabaseEmpty()) {
            // TODO remove this
            Log.e("flowtest", "empty database");
            List<UsageStats> usageStatsList = RawAppInfo.getUsageStatsAppList(this);
            // iterate through each packageName object
            // get the uid of application
            // get the package name from the UsageStats object 'pack'
            // get the application label/name with the help of that package name
            appDetailsList = new ArrayList<>();
            for (UsageStats usageStats : usageStatsList) {
                String packageName = usageStats.getPackageName();
                ApplicationInfo applicationInfo;
                try {
                    applicationInfo = getPackageManager().getApplicationInfo(packageName, PackageManager.GET_META_DATA);
                    AppDetails appDetails = new AppDetails(applicationInfo.uid, packageName, String.valueOf(getPackageManager().getApplicationLabel(applicationInfo)));
                    SQLiteAccessLayer sqLiteAccessToInsert = new SQLiteAccessLayer(this, appDetails);
                    sqLiteAccessToInsert.insertIntoAppDetails();
                    appDetailsList.add(appDetails);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } else { // database is not empty
            SQLiteAccessLayer sqLiteAccessLayerToQuery = new SQLiteAccessLayer(this);
            appDetailsList = new ArrayList<>(sqLiteAccessLayerToQuery.queryAppDetails());
        }
        return appDetailsList;
    }
    void checkPermissionForAccess(){
        if (!CheckPermissions.isPermissionForAccessSet(this)) {
            // TODO Add the code that informs the user that the permission needed for this app is to be set using a popup window
            finish();
            startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
        } else {
            appDetailsFromDatabase = getAppDetailsFromDatabase();
        }
    }

}


