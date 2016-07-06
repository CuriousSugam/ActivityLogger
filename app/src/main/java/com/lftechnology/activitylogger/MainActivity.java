package com.lftechnology.activitylogger;

import android.app.usage.UsageStats;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.lftechnology.activitylogger.Controller.ActivityLoggerSQLiteAccessLayer;
import com.lftechnology.activitylogger.model.AppDetails;

import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityLoggerSQLiteAccessLayer sqLiteAccessLayer;
    AppDetails appDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
}
