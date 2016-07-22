package com.lftechnology.activitylogger.Charts;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.View;

/**
 * Created by sparsha on 7/13/2016.
 */
public class PieChart extends View {
    SharedPreferences sharedPreferences;
    int completeCircle;
    public PieChart(Context context) {
        super(context);
        sharedPreferences = context.getSharedPreferences("Top5Apps",Context.MODE_PRIVATE);
        completeCircle = 0;
    }
    @Override
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);
        float totalValue=0,startAngle=0,makeAngle;
        int x = getWidth();
        int y = getHeight();
        int radius = 300;
        RectF rectF = new RectF(x/2-radius,y/2-radius,x/2+radius,y/2+radius);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        canvas.drawPaint(paint);

        String[] pieChartColors = {"#4D4D4D","#5DA5DA","#FAA43A","#60BD68","#F17CB0"};
        float[] appValuesDuration = new float[5];
        String[] valueNames = new String[5];
        for(int i = 0;i<appValuesDuration.length;i++){
            appValuesDuration[i] = (float)sharedPreferences.getLong("AppDuration"+i,1);
            valueNames[i] = sharedPreferences.getString("AppName"+i,"n/A");
        }

        for(float value: appValuesDuration){
            totalValue = totalValue +value;
        }
        Log.d("LOG","Total value is"+totalValue);

        for(int i = 0; i<appValuesDuration.length;i++){
            makeAngle = appValuesDuration[i] * completeCircle / totalValue;
            paint.setColor(Color.parseColor(pieChartColors[i]));
            canvas.drawArc(rectF,startAngle,makeAngle,true,paint);
            startAngle = startAngle + makeAngle + 2;
        }

        // paint.setColor(Color.parseColor("#A134D3"));
        //canvas.drawCircle(x/2,y/2,radius,paint);



        paint.setColor(Color.WHITE);
        canvas.drawCircle(x/2,y/2,radius/2,paint);
        if(completeCircle<350){
            completeCircle+=5;
            invalidate();
        }

    }

}
