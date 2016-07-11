package com.lftechnology.activitylogger;

//import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
//import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

    }


    @Override
    protected void onRestart() {

//        AlphaAnimation fadeIn = new AlphaAnimation(0.0f, 1.0f);
//        AlphaAnimation fadeOut = new AlphaAnimation(1.0f, 0.0f);
//
//        fadeIn.setDuration(3000);
//        fadeIn.setFillAfter(true);
//        fadeOut.setDuration(3000);
//        fadeOut.setFillAfter(true);
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}

