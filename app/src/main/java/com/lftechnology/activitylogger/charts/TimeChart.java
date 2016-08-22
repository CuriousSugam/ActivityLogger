package com.lftechnology.activitylogger.charts;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;

import com.lftechnology.activitylogger.R;
import com.lftechnology.activitylogger.communicators.CommunicatorEachAppDetailsValues;
import com.lftechnology.activitylogger.model.EachAppDetails;

import java.util.List;

/**
 * Created by sparsha on 8/21/2016.
 * This Class Creates a circular progress bar of the most app used daily
 */
public class TimeChart extends View {
    private EachAppDetails mostUsedApp;
    private float totalValue = 0;
    private float usageRatio = 0;
    private float circleAnimator = 0;
    private final Paint dataColor = new Paint();
    private final Paint gapColor = new Paint();
    private final Paint textColor = new Paint();
    private final float maxAngle = 300;
    private Bitmap iconOfMostUsedApp;
    private String nameOfMostUsedApp;
    String dataUsed;
    public TimeChart(Context context) {
        super(context);
        List<EachAppDetails> eachAppDetailsListDaily = new CommunicatorEachAppDetailsValues().getEachAppDetailsListDaily();
        if(eachAppDetailsListDaily!=null){
            mostUsedApp = eachAppDetailsListDaily.get(0);
        }
        if(mostUsedApp!=null) {
            iconOfMostUsedApp = ((BitmapDrawable) mostUsedApp.eachAppIcon).getBitmap();
            for(EachAppDetails appDetails : eachAppDetailsListDaily){
                totalValue = totalValue + (float)appDetails.eachAppUsageDuration;
            }
            if(totalValue!=0){
                usageRatio = mostUsedApp.eachAppUsageDuration / totalValue;
            }
            dataUsed = String.format("%02dh ", (mostUsedApp.eachAppUsageDuration / 1000 / 3600))
                    + String.format("%02dm ", (((mostUsedApp.eachAppUsageDuration / 1000) % 3600) / 60))
                    + String.format("%02ds", ((mostUsedApp.eachAppUsageDuration / 1000) % 60));
            nameOfMostUsedApp = mostUsedApp.eachAppName;
        }
        dataColor.setColor(getResources().getColor(R.color.skyBlue));
        dataColor.setStyle(Paint.Style.STROKE);

        gapColor.setColor(getResources().getColor(R.color.dividerFaint));
        gapColor.setStyle(Paint.Style.STROKE);

        textColor.setStyle(Paint.Style.FILL);
        textColor.setColor(getResources().getColor(R.color.textColorPrimary));
        textColor.setTextAlign(Paint.Align.CENTER);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float canvasHeight = canvas.getHeight();
        float canvasWidth = canvas.getWidth();
        float dataRingTop, dataRingLeft, dataRingRight, dataRingBottom;
        float dataRingRadius = canvasHeight * (float) 0.35;
        float angleGap = 360 - maxAngle;
        final float textSize = canvasWidth / 30;
        textColor.setTextSize(textSize);
        dataColor.setStrokeWidth(dataRingRadius / 8);
        gapColor.setStrokeWidth(dataRingRadius / 8);

        dataRingLeft = (float) 0.5 * canvasWidth - dataRingRadius;
        dataRingRight = (float) 0.5 * canvasWidth + dataRingRadius;
        dataRingTop = (float) 0.5 * canvasHeight - dataRingRadius;
        dataRingBottom = (float) 0.5 * canvasHeight + dataRingRadius;

        RectF rectFData = new RectF(dataRingLeft, dataRingTop, dataRingRight, dataRingBottom);

        canvas.drawText("TIME", dataRingLeft + dataRingRadius, dataRingTop - textSize, textColor);
        if (usageRatio != 0) {
            float startAngle = 90 + angleGap / 2;
            float sweepAngle = circleAnimator * usageRatio;
            canvas.drawArc(rectFData, startAngle, sweepAngle, false, dataColor);
            canvas.drawArc(rectFData, startAngle + sweepAngle, maxAngle - sweepAngle, false, gapColor);
            canvas.drawText(nameOfMostUsedApp, dataRingLeft + dataRingRadius, dataRingBottom + textSize, textColor);
            canvas.drawText(dataUsed, dataRingLeft + dataRingRadius, dataRingBottom - 2 * textSize, textColor);
        }
        else if(usageRatio==0){
            float startAngle = 90 + angleGap / 2;
            float sweepAngle = circleAnimator * usageRatio;
            canvas.drawArc(rectFData, startAngle, sweepAngle, false, gapColor);
            canvas.drawText("APPS", dataRingLeft + dataRingRadius, dataRingBottom - dataRingRadius, textColor);
            canvas.drawText("NOT USED", dataRingLeft + dataRingRadius, dataRingBottom - dataRingRadius + textSize, textColor);
        }

        if (iconOfMostUsedApp != null) {
            iconOfMostUsedApp = resizeBitmap(iconOfMostUsedApp, dataRingRadius/2);
            canvas.drawBitmap(iconOfMostUsedApp, dataRingLeft + 3 * dataRingRadius / 4
                    , dataRingTop + 2* dataRingRadius / 3, dataColor);
        }
        if(circleAnimator<maxAngle){
            circleAnimator+=3;
            invalidate();
        }
    }
        /**
         * Returns a Square Bitmap object with the Desired size
         *
         * @param bitmap: The Bitmap object to be provided
         * @param width:  The desired width of the Bitmap
         * @return Resized square bitmap of desired width
         */
    private Bitmap resizeBitmap(Bitmap bitmap, float width) {
        int prevWidth = bitmap.getWidth();
        int prevHeight = bitmap.getHeight();
        float scale = width / (float) prevWidth;
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);

        return Bitmap.createBitmap(bitmap, 0, 0, prevWidth, prevHeight, matrix, false);
    }
}
