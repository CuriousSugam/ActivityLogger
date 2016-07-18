package com.lftechnology.activitylogger;

import android.content.pm.PackageInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.pm.ApplicationInfo;
import android.content.Intent;
import android.provider.Settings;

import com.lftechnology.activitylogger.Controller.SQLiteAccessLayer;
import com.lftechnology.activitylogger.model.AppDetails;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<AppDetails> appDetailsFromDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        RawAppInfo.getAllInstalledApps(this);
        if (sqLiteAccessLayer.isDatabaseEmpty()) {  // database empty
            List<PackageInfo> packageInfoList = RawAppInfo.getAllInstalledApps(this);
            appDetailsList = new ArrayList<>();

            for (PackageInfo packageInfo : packageInfoList) {
                if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                    AppDetails appDetails = new AppDetails(
                            packageInfo.applicationInfo.uid,
                            packageInfo.packageName,
                            String.valueOf(getPackageManager().getApplicationLabel(packageInfo.applicationInfo)));
                    SQLiteAccessLayer sqLiteAccessLayerForInsert = new SQLiteAccessLayer(this, appDetails);
                    sqLiteAccessLayerForInsert.insertIntoAppDetails();
                    appDetailsList.add(appDetails);
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