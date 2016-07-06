package com.lftechnology.activitylogger;
<<<<<<< HEAD
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.provider.Settings;
=======

//import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.view.View;

>>>>>>> splashscreen

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermissionForAccess()
    }
    /**
     * if you havent enabled permission, settings opens for granting permission access with this
     */
    private void checkPermissionForAccess(){
        if(RawAppInfo.getUsageStatsAppList(this).isEmpty())
            startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
    }
<<<<<<< HEAD
}
=======


}





>>>>>>> splashscreen
