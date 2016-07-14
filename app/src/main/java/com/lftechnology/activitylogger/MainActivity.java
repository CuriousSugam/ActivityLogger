package com.lftechnology.activitylogger;

import android.app.usage.UsageStats;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;

import com.lftechnology.activitylogger.Controller.SQLiteAccessLayer;
import com.lftechnology.activitylogger.Services.ConnectivityChangeMonitoringService;
import com.lftechnology.activitylogger.model.AppDetails;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<AppDetails> appDetailsFromDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
     *
     * @return a List of AppDetails objects containing the data of applications i.e List<AppDetails>
     */
    private List<AppDetails> getAppDetailsFromDatabase() {
        SQLiteAccessLayer sqLiteAccessLayer = new SQLiteAccessLayer(this);
        List<AppDetails> appDetailsList;
        if (sqLiteAccessLayer.isDatabaseEmpty()) {  // database empty
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
        sqLiteAccessLayer.closeDatabaseConnection();
        return appDetailsList;
    }


}