package com.lftechnology.activitylogger;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by DevilDewzone on 7/5/2016.
 */
public class SplashScreenActivity extends AppCompatActivity {

    Handler handler;
    Runnable delayRun;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        // for navigation bar hide and full screen mode
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        /* previous code for full screen
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        */
        setContentView(R.layout.splash_screen_layout);


        //For fade in out animation
        //Setting the alpha values
        AlphaAnimation fadeIn = new AlphaAnimation(0.0f, 1.0f);
        AlphaAnimation fadeOut = new AlphaAnimation(1.0f, 0.0f);

        //defining the image views and the text views
        ImageView imageView = (ImageView) findViewById(R.id.splashscreen_img);
        TextView txtFirst = (TextView) findViewById(R.id.first_line);
        TextView txtSecond = (TextView) findViewById(R.id.second_line);
        TextView txtThird = (TextView) findViewById(R.id.third_line);

        //Providing Imageview fade in effect
        Animation anim_in = AnimationUtils.loadAnimation(this, R.anim.fade_in_splash_screen);
        imageView.setAnimation(anim_in);

        /* Unused Code for now
        Animation anim_out = AnimationUtils.loadAnimation(this, R.anim.fade_out_animation);
        imageView.setAnimation(anim_out);
        imageView.startAnimation(fadeIn);
        imageView.startAnimation(fadeOut);
        */

        //Start and end for the text animation
        txtFirst.startAnimation(fadeIn);
        txtFirst.startAnimation(fadeOut);
        txtSecond.setAnimation(fadeIn);
        txtSecond.setAnimation(fadeOut);
        txtThird.setAnimation(fadeIn);
        txtThird.setAnimation(fadeOut);
        fadeIn.setDuration(1500);
        //fadeIn.setFillBefore(false);
        fadeIn.setFillAfter(true);
        fadeOut.setDuration(1500);
        //fadeOut.setFillBefore(false);
        fadeOut.setFillAfter(true);
        fadeOut.setStartOffset(1200 + fadeIn.getStartOffset());


//        //First LIne animation fade in and out
//        TextView txtFirst = (TextView) findViewById(R.id.first_line);
//        Animation firstIn = AnimationUtils.loadAnimation(this, R.anim.fade_in_animation);
//        txtFirst.setAnimation(firstIn);
//        Animation firstOut = AnimationUtils.loadAnimation(this, R.anim.fade_out_animation);
//        txtFirst.setAnimation(firstOut);
//
//        //Second Line animation fade in and out
//        TextView txtSecond = (TextView) findViewById(R.id.second_line);
//        Animation secondIn = AnimationUtils.loadAnimation(this, R.anim.fade_in_animation);
//        txtSecond.setAnimation(secondIn);
//        Animation secondOut= AnimationUtils.loadAnimation(this, R.anim.fade_out_animation);
//        txtSecond.setAnimation(secondOut);
//
//        //Third Line animation fade in and out
//        TextView txtThird = (TextView) findViewById(R.id.third_line);
//        Animation ThirdIn = AnimationUtils.loadAnimation(this, R.anim.fade_in_animation);
//        txtThird.setAnimation(ThirdIn);
//        Animation thirdOut= AnimationUtils.loadAnimation(this, R.anim.fade_out_animation);
//        txtThird.setAnimation(thirdOut);
//
        //MainActivty passing after splash
     // SPlash to next activity wait timer
     new Timer().schedule(
             new TimerTask() {
                 @Override
                 public void run() {
                     gotoNext();

                 }
             },
             3000
     );
    }

    public void gotoNext(){
        startActivity(new Intent(SplashScreenActivity.this,MainActivity.class));
            finish();
    }

}
