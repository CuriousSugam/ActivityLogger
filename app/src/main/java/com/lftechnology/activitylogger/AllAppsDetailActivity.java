package com.lftechnology.activitylogger;

import android.app.Activity;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.CardView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.lftechnology.activitylogger.viewholders.AllAppsViewHolder;

import java.io.File;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by DevilDewzone on 8/12/2016.
 */
public class AllAppsDetailActivity extends Activity {

    private RawAppInfo rawAppInfo;
    private Context context;


    @BindView(R.id.app_icon)
    ImageView appIcon;

    @BindView(R.id.installed_date)
    TextView installedDate;

    @BindView(R.id.total_data_used)
    TextView totalDataUsed;

    @BindView(R.id.total_time_used)
    TextView totalTimeUsed;

    @BindView(R.id.last_time_used)
    TextView lastTimeUsed;

    @BindView(R.id.app_name)
    TextView appName;



    AllAppsViewHolder detailView;


    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        Log.e("activitycheck", "Inside all apps");
        setContentView(R.layout.card_view_app_details_layout);
        ButterKnife.bind(this);
//
//      appIcon= detailView.getApplicationIconImageView();
//        appName=detailView.getApplicationNameTextView();


    }
}