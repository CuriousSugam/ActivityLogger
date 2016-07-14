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

/**
 * Created by DevilDewzone on 7/5/2016.
 */

//opens a splashscreen in full window and animates the texts and
public class SplashScreenActivity extends AppCompatActivity {

    private final Handler handler = new Handler();

    private static final int ANIMATION_DURATION_IN = 1500;
    private static final int ANIMATION_DURATION_OUT = 1500;
    private static final int ANIMATION_DURATION_DELAY = 3000;
    private static final int ANIMATION_DURATION_INITIAL = 1200;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // for navigation bar hide and full screen mode
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        setContentView(R.layout.splash_screen_layout);
        setAnimation();
        intent = new Intent(this, MainActivity.class);

//        intent = new Intent(this, MainActivity.class);
//        intent.putParcelableArrayListExtra("appDetails", appDetails);

        //MainActivty passing after splash
        //SPlash to next activity wait timer
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //handler.postDelayed(this, ANIMATION_DURATION_DELAY);
                gotoNext(intent);
            }
        }, ANIMATION_DURATION_DELAY);
    }

    private void setAnimation() {
        //For fade in out animation and setting the alpha values
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

        /* Unused Code for now might be used for modifying later
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
        fadeIn.setDuration(ANIMATION_DURATION_IN);
        fadeIn.setFillAfter(true);
        fadeOut.setDuration(ANIMATION_DURATION_OUT);
        fadeOut.setFillAfter(true);
        fadeOut.setStartOffset(ANIMATION_DURATION_INITIAL + fadeIn.getStartOffset());

    }

    private void gotoNext(Intent intent) {
        startActivity(intent);
        finish();
    }
}

