package com.lftechnology.activitylogger;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lftechnology.activitylogger.Controller.SQLiteAccessLayer;
import com.lftechnology.activitylogger.model.AppDetails;



public class MainActivity extends AppCompatActivity {

    SQLiteAccessLayer sqLiteAccessLayer;
    AppDetails appDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

}
