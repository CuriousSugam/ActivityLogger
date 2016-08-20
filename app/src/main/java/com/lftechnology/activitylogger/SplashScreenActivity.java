package com.lftechnology.activitylogger;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;

import com.lftechnology.activitylogger.controller.SQLiteAccessLayer;
import com.lftechnology.activitylogger.model.AppDetails;
import com.lftechnology.activitylogger.model.NetworkUsageDetails;
import com.lftechnology.activitylogger.model.NetworkUsageSummary;
import com.lftechnology.activitylogger.utilities.CheckPermissions;

import java.util.ArrayList;
import java.util.List;


/**
 * This activity shows the splash screen containing the logo of the applications to cover up the delay
 * while the necessary data are being fetched from the system.
 * <p/>
 * Created by Sugam on 7/5/2016.
 */
public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!CheckPermissions.isPermissionForAccessSet(this)) {
            // TODO Add the code that informs the user that the permission needed for this app is to be set using a popup window
            finish();
            startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
        } else {
            List<AppDetails> appDetailsFromDatabase = getAppDetailsFromDatabase();
            NetworkUsageSummary wifiUsageSummary = new NetworkUsageSummary(this, Constants.WIFI_NETWORK);
            NetworkUsageSummary mobileDataUsageSummary = new NetworkUsageSummary(this, Constants.MOBILE_NETWORK);

            Intent intent = new Intent(this, MainActivity.class);
            intent.putParcelableArrayListExtra(MainActivity.APP_DETAILS, (ArrayList<? extends Parcelable>) appDetailsFromDatabase);

            if (!wifiUsageSummary.getNetworkUsageDetailsList().isEmpty()) {
                intent.putParcelableArrayListExtra(
                        MainActivity.MOST_WIFI_USED_APP,
                        (ArrayList<? extends Parcelable>) wifiUsageSummary.getNetworkUsageDetailsList());
                intent.putExtra(MainActivity.TOTAL_WIFI_DATA, wifiUsageSummary.getTotal());
            }
            if (!mobileDataUsageSummary.getNetworkUsageDetailsList().isEmpty()) {
                intent.putParcelableArrayListExtra(MainActivity.MOST_DATA_USED_APP,
                        (ArrayList<? extends Parcelable>) mobileDataUsageSummary.getNetworkUsageDetailsList());
                intent.putExtra(MainActivity.TOTAL_MOBILE_DATA, mobileDataUsageSummary.getTotal());
            }

            startActivity(intent);
            finish();
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
        List<AppDetails> appDetailsList = new ArrayList<>();
        PackageManager packageManager = getPackageManager();

        if (sqLiteAccessLayer.isDatabaseEmpty()) { // database empty
            List<PackageInfo> installedApps = RawAppInfo.getAllInstalledApps(this);
            List<ResolveInfo> systemApps = RawAppInfo.getSystemApps(this);

            for (PackageInfo packageInfo : installedApps) {
                AppDetails appDetails = new AppDetails(
                        packageInfo.applicationInfo.uid,
                        packageInfo.applicationInfo.packageName,
                        String.valueOf(packageInfo.applicationInfo.loadLabel(packageManager)),
                        RawAppInfo.INSTALLED_APP
                );

                SQLiteAccessLayer sqLiteAccessLayerForInsert = new SQLiteAccessLayer(this, appDetails);
                sqLiteAccessLayerForInsert.insertIntoAppDetails();
                appDetailsList.add(appDetails);
            }
            for (ResolveInfo resolveInfo : systemApps) {
                ApplicationInfo applicationInfo = resolveInfo.activityInfo.applicationInfo;
                AppDetails appDetails = new AppDetails(
                        applicationInfo.uid,                                         //uid
                        applicationInfo.packageName,                                 //packagename
                        String.valueOf(applicationInfo.loadLabel(packageManager)),  // applicationName
                        RawAppInfo.SYSTEM_APP                                       //applicationType
                );
                SQLiteAccessLayer sqLiteAccessLayerForInsert = new SQLiteAccessLayer(this, appDetails);
                sqLiteAccessLayerForInsert.insertIntoAppDetails();
                appDetailsList.add(appDetails);
            }
        } else {
            appDetailsList = new ArrayList<>(new SQLiteAccessLayer(this).queryAppDetails());
        }
        sqLiteAccessLayer.closeDatabaseConnection();
        return appDetailsList;
    }

}
