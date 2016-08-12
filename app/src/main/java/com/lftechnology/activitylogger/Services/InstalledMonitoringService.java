package com.lftechnology.activitylogger.Services;

import android.app.IntentService;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.lftechnology.activitylogger.Controller.SQLiteAccessLayer;
import com.lftechnology.activitylogger.model.AppDetails;


import java.util.List;


/**
 * This service performs the action of inserting and deleting
 */
public class InstalledMonitoringService extends IntentService {

    AppDetails insAppDetails;
    ApplicationInfo insApplicationInfo;
    PackageManager insPackageManager;
    SQLiteAccessLayer insAccessLayer;


    public InstalledMonitoringService() {
        super("InstalledMonitoringService");
    }

    /**
     * @param insServiceIntent = installed app service intent
     *  newInsKey gets the package name from the installed Broadcast Receiver
     * Then from the package name obtained.
     * final string insAppName= obtains app name from package name
     */


    @Override
    protected void onHandleIntent(Intent insServiceIntent) {

        String newInsKey = insServiceIntent.getStringExtra("AddKey");
        Integer insUid = android.os.Process.getUidForName(newInsKey);

        if (insServiceIntent.getStringExtra("AddAction").equals(Intent.ACTION_PACKAGE_ADDED)) {

            insPackageManager = getApplicationContext().getPackageManager();
            try {
                insApplicationInfo = insPackageManager.getApplicationInfo(newInsKey, 0);
            } catch (final PackageManager.NameNotFoundException e) {
                insApplicationInfo = null;
            }
            final String insAppName = (String) (insApplicationInfo != null ? insPackageManager.getApplicationLabel(insApplicationInfo) : "Unknown");
            insAppDetails = new AppDetails(insApplicationInfo.uid, newInsKey, insAppName);
            insAccessLayer = new SQLiteAccessLayer(this, insAppDetails);
            Log.e("InsAppDetails", insUid + "" + insAppName + "" + newInsKey);
            insAccessLayer.insertIntoAppDetails();

            /**
             * For Querying database only and checking
             */
//            List<AppDetails> insListDetails = insAccessLayer.queryAppDetails();
//            for (AppDetails insAppListDetails : insListDetails) {
//                Log.e("PackageInstallCheck", insAppListDetails.getUid() + insAppListDetails.getPackageName() + insAppListDetails.getApplicationName());
//            }
            insAccessLayer.closeDatabaseConnection();

        } else {
            //accessLayer.closeDatabaseConnection();
            Log.i("InstallError", "Install Service Error");
        }

        //these are just for checking purpose and will be deleted while creating pull request


//
//
//        Log.i("check", "message");
//            if (insIntent == null || insIntent.equals(Intent.ACTION_PACKAGE_ADDED)) {
//
////            try {
////                Integer Uid1 = android.os.Process.getUidForName(serviceIntent.getStringExtra("AddKey"));
////
////            } catch (NullPointerException e) {
////                e.printStackTrace();
////            }
//            //String package_name= serviceIntent.getData().getEncodedSchemeSpecificPart();
//            Log.e("packageInstallCheck", "inside the intent.actionPackageAdded");
//            //String packageName = String.valueOf(packageManager.getApplicationLabel(applicationInfo).toString());
//            //String dbAddIntent = serviceIntent.getStringExtra("AddKey");
//            packageManager = getApplicationContext().getPackageManager();
//            try {
//                applicationInfo = packageManager.getApplicationInfo(insKeyIntent, 0);
//            } catch (final PackageManager.NameNotFoundException e) {
//                applicationInfo = null;
//            }
//            final String app_name = (String) (applicationInfo != null ? packageManager.getApplicationLabel(applicationInfo) : "(Unknown)");
//
//            appDetails = new AppDetails(applicationInfo.uid, insKeyIntent, app_name);
//            accessLayer = new SQLiteAccessLayer(this, appDetails);
//            Log.e("applicationDetails", Uid + " " + app_name + " " + insKeyIntent);
//            accessLayer.insertIntoAppDetails();
//            List<AppDetails> listDetails = accessLayer.queryAppDetails();
////                for (applicationInfo: listDetails)
////                    if (applicationInfo.packageName.equals(dbAddIntent)) {
////                        Uid = applicationInfo.uid;
////                    }
//            for (AppDetails appListDetails : listDetails) {
//                Log.e("packageInstallCheck", appListDetails.getUid() + appListDetails.getPackageName() + appListDetails.getApplicationName());
//            }
//            accessLayer.closeDatabaseConnection();

        /**
         * Replacing would be done later To be Used when necessary
         * it is to be deleted while creating pull request
         * along with the check functions there
         *
         *
         */

//        } else if (serviceIntent.getStringExtra("RpAction").equals(Intent.ACTION_PACKAGE_REPLACED)) {
//            Log.i("rpIntent", "Inside rp intent if");
//            String dbRpIntent = serviceIntent.getStringExtra("RpKey");
//            appDetails = new AppDetails(appDetails.getUid(), getPackageName(), dbRpIntent);
//            accessLayer = new SQLiteAccessLayer(this, appDetails);
//            accessLayer.insertIntoAppDetails();
//            accessLayer.closeDatabaseConnection();

//        } else if (rmIntent.equals(Intent.ACTION_PACKAGE_REMOVED)) {
//
//            Log.e("rmIntent", "inside removed intent");
//            Integer Uid2 = android.os.Process.getUidForName(rmKeyIntent);
//            //String dbRmIntent = serviceIntent.getStringExtra("RmKey");
//            packageManager = getApplicationContext().getPackageManager();
//
//            try {
//                applicationInfo = packageManager.getApplicationInfo(rmKeyIntent, 0);
//            } catch (final PackageManager.NameNotFoundException e) {
//                applicationInfo = null;
//            }
//            final String app_name = (String) (applicationInfo != null ? packageManager.getApplicationLabel(applicationInfo) : "(Unknown)");
//            //appDetails = new AppDetails(Uid,dbRmIntent,app_name);
//            accessLayer = new SQLiteAccessLayer(this, appDetails);
//            Log.e("removeDetails", Uid2 + " " + app_name + " " + rmKeyIntent);
//            accessLayer.deleteAnAppDetail(Uid2 + app_name, new String[]{});
//
//            List<AppDetails> listDetails = accessLayer.queryAppDetails();
////                for (applicationInfo: listDetails)
////                    if (applicationInfo.packageName.equals(dbAddIntent)) {
////                        Uid = applicationInfo.uid;
////                    }
//            for (AppDetails appListDetails : listDetails) {
//                Log.e("packagerRemoveCheck", appListDetails.getUid() + appListDetails.getPackageName() + appListDetails.getApplicationName());
//            }
//            accessLayer.closeDatabaseConnection();
//


    }
//    private boolean checkInstall(String packageName){
//        PackageManager pm = getPackageManager();
//        try {pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
//            return pm.getApplicationInfo(packageName, 0).enabled;
//    }catch (PackageManager.NameNotFoundException e){
//            e.printStackTrace();
//            return false;
//        }

//    public String applicationName(Context ctx, ApplicationInfo info){
//        PackageManager pk = ctx.getPackageManager();
//        String label = pk.getApplicationLabel(info).toString();
//        return label;
//    }
}





