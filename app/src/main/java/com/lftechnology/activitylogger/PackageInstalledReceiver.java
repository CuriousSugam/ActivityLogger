package com.lftechnology.activitylogger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by sparsha on 7/5/2016.
 */
public class PackageInstalledReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context,"New Package Installed Shown",Toast.LENGTH_LONG).show();//TODO REMOVE
        Log.d("Log","Package Added");//TODO remove

        //TODO Add into DB
    }
}
