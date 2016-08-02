package com.lftechnology.activitylogger.Charts;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.View;

import com.lftechnology.activitylogger.Communicators.CommunicatorEachAppDetailsValues;
import com.lftechnology.activitylogger.model.EachAppDetails;

import java.util.List;

/**
 * Created by sparsha on 7/13/2016.
 */
public class PieChart extends View {
    int completeCircle;
    List<EachAppDetails> eachAppDetailsList;
    int numberOfPie;
    public PieChart(Context context,int size) {
        super(context);
        completeCircle = 0;
        eachAppDetailsList = new CommunicatorEachAppDetailsValues().getEachAppDetailsList();
        numberOfPie = size;

        if(numberOfPie > eachAppDetailsList.size())
            numberOfPie = eachAppDetailsList.size();
    }
    @Override
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);
        float totalValue=0,startAngle=0,makeAngle;
        int x = getWidth();
        int y = getHeight();
        int radiusReference = x;
        if(x>y)radiusReference = y;

        int radius = radiusReference/3;
        RectF rectF = new RectF(x/2-radius,(4*y/6)-radius,x/2+radius,(4*y/6)+radius);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setColor(Color.WHITE);
        canvas.drawPaint(paint);

        String[] pieChartColors = {"#4D4D4D", "#5DA5DA", "#FAA43A", "#60BD68", "#F17CB0","#B2912F","#B276B2","#DECF3F","#F15854"};
        float[] appValuesDuration = new float[numberOfPie];
        String[] valueNames = new String[numberOfPie];
        for(int i = 0;i<appValuesDuration.length;i++){
            EachAppDetails current = eachAppDetailsList.get(i);
            appValuesDuration[i] = (float)current.eachAppUsageDuration;
            valueNames[i] = current.eachAppName;
        }

        for(float value: appValuesDuration){
            totalValue = totalValue +value;
        }
        Log.d("LOG","Total value is"+totalValue);

        for(int i = 0; i<appValuesDuration.length;i++){
            makeAngle = appValuesDuration[i] * completeCircle / totalValue;
            paint.setColor(Color.parseColor(pieChartColors[i]));
            canvas.drawArc(rectF,-startAngle,-makeAngle,true,paint);
            startAngle = startAngle + makeAngle;
        }

        if(completeCircle<360){
            completeCircle+=5;
            invalidate();
        }

    }

}
