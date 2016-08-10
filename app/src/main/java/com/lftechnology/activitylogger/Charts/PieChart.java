package com.lftechnology.activitylogger.Charts;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
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
    Paint paint;

    /**
     *
     * @param context Calling Activity
     * @param size Number of desired PIEs
     */
    public PieChart(Context context,int size) {
        super(context);
        completeCircle = 0;
        eachAppDetailsList = new CommunicatorEachAppDetailsValues().getEachAppDetailsList();
        numberOfPie = size;
        paint  = new Paint();

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
        int imageY = y/15;
        int imageX = x - x/9;
        float imageSize = y/28;

        int radius = radiusReference/3;
        RectF rectF = new RectF(x/2-x/15-radius,y/2-radius,x/2-x/15+radius,y/2+radius);// A rect created with the circle's dimension

        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setColor(Color.WHITE);
        canvas.drawPaint(paint);

        String[] pieChartColors =
                {"#4D4D4D", "#5DA5DA", "#FAA43A", "#60BD68", "#F17CB0","#B2912F","#B276B2","#DECF3F","#F15854"};//Default colors
        float[] appValuesDuration = new float[numberOfPie];
        String[] valueNames = new String[numberOfPie];
        Bitmap[] bitmaps = new Bitmap[numberOfPie];
        for(int i = 0;i<appValuesDuration.length;i++){
            EachAppDetails current = eachAppDetailsList.get(i);
            appValuesDuration[i] = (float)current.eachAppUsageDuration;
            valueNames[i] = current.eachAppName;
            bitmaps[i] = ((BitmapDrawable)current.eachAppIcon).getBitmap();
        }

        for(float value: appValuesDuration){
            totalValue = totalValue +value;
        }
        Log.d("LOG","Total value is"+totalValue);

        for(int i = 0; i<appValuesDuration.length;i++){
            makeAngle = appValuesDuration[i] * completeCircle / totalValue;
            paint.setColor(Color.parseColor(pieChartColors[i%9]));
            canvas.drawArc(rectF,-startAngle,-makeAngle,true,paint);
            canvas.drawBitmap(resizeBitmap(bitmaps[i],imageSize),imageX,imageY,paint);
            canvas.drawCircle(imageX-imageSize/2,imageY+imageSize/2,10,paint);
            imageY = imageY +(int) imageSize;
            startAngle = startAngle + makeAngle;
        }
        /**
         * animates the canvas
         */
        if(completeCircle<360){
            completeCircle+=5;
            invalidate();                           //Redraw canvas
        }

    }

    private Bitmap resizeBitmap(Bitmap bitmap, float width) {
        int prevWidth = bitmap.getWidth();
        int prevHeight = bitmap.getHeight();
        float scale = width / (float) prevWidth;
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, prevWidth, prevHeight, matrix, false);

        return resizedBitmap;
    }

}
