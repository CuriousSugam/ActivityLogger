package com.lftechnology.activitylogger;

import android.app.FragmentManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.lftechnology.activitylogger.Charts.PieChart;
import com.lftechnology.activitylogger.Fragments.FragmentTop5ActivityCharts;
import com.lftechnology.activitylogger.Interfaces.ChartsFragmentsCommunicator;

public class ShowsTop5AppsActivity extends AppCompatActivity implements ChartsFragmentsCommunicator {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shows_top5_apps);
    }

    @Override
    public void setPieChartImage() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTop5ActivityCharts charts = (FragmentTop5ActivityCharts) fragmentManager.findFragmentById(R.id.fragmentCharts);
        View view = new PieChart(this);
        Bitmap bitmap = Bitmap.createBitmap(1000,1000, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        charts.setImage(bitmap);

    }

    @Override
    public void setBarChartImage() {
        //TODO
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTop5ActivityCharts charts = (FragmentTop5ActivityCharts) fragmentManager.findFragmentById(R.id.fragmentCharts);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.barchart);
        charts.setImage(bitmap);


    }
}
