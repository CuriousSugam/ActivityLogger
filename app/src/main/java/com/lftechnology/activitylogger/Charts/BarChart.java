package com.lftechnology.activitylogger.Charts;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.lftechnology.activitylogger.Communicators.CommunicatorEachAppDetailsValues;
import com.lftechnology.activitylogger.model.EachAppDetails;

import java.util.List;

/**
 * Created by sparsha on 7/21/2016.
 */
public class BarChart extends View implements View.OnTouchListener {
    int x, y;
    int canvasHeight, canvasWidth;
    int screenHeight, screenWidth;
    Paint paint;
    RectF bar;
    float marginX, marginY, spacing;
    float barWidth, maxBarHeight, barLeft, barTop, barRight, barBottom;
    float textSize;
    String[] chartColors = {"#4D4D4D", "#5DA5DA", "#FAA43A", "#60BD68", "#F17CB0", "#B2912F", "#B276B2", "#DECF3F", "#F15854"};
    String text = "N/A";
    Bitmap bitmapIconsOfApps;
    List<EachAppDetails> eachAppDetailsList;
    int numberOfBars;

    public BarChart(Context context, int size) {
        super(context);

        eachAppDetailsList = new CommunicatorEachAppDetailsValues().getEachAppDetailsList();

        numberOfBars = size;

        if(numberOfBars > eachAppDetailsList.size())
            numberOfBars = eachAppDetailsList.size();

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;
        Log.d("LOG", "Screen Width: " + screenWidth + "Screen Height" + screenHeight);
        barWidth = (float) screenWidth / ((float) 3 * (numberOfBars) / (float) 2);
        maxBarHeight = 0;//TODO remove
        setBackgroundColor(Color.WHITE);
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        textSize = 25;
        paint.setTextSize(textSize);
        bar = new RectF();
        this.setOnTouchListener(this);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvasHeight = getHeight();
        canvasWidth = getWidth();

        paint.setColor(Color.BLACK);
        x = canvasWidth;
        y = canvasHeight - (canvasHeight / 10);

        spacing = (float) x / (float) 21.6;
        marginX = spacing / 2;
        marginY = spacing / 2;


        Log.d("LOG", Integer.toString(x));
        Log.d("LOG", Integer.toString(y));
        float[] durationOfAppsUsed = new float[numberOfBars];
        String[] namesOfAppsUsed = new String[numberOfBars];
        float totalValue = 0;

        canvas.drawLine(marginX, y - marginY, x - marginX, y - marginY, paint);
        canvas.drawLine(marginX, marginY, marginX, y - marginY, paint);

        paint.setColor(Color.CYAN);

        barLeft = marginX + spacing;
        barRight = barLeft + barWidth;
        barBottom = y - marginY;
        barTop = barBottom - maxBarHeight;

        for (int i = 0; i < durationOfAppsUsed.length; i++) {
            EachAppDetails current = eachAppDetailsList.get(i);
            durationOfAppsUsed[i] = (float) current.eachAppUsageDuration;
            namesOfAppsUsed[i] = current.eachAppName;
            totalValue = totalValue + durationOfAppsUsed[i];
        }

        for (int i = 0; i < numberOfBars; i++) {
            barTop = barBottom - (float) 0.8 * (durationOfAppsUsed[i] / totalValue * maxBarHeight);
            if (barTop < marginY) {
                barTop = marginY;
            }
            bar.set(barLeft, barTop, barRight, barBottom);
            paint.setColor(Color.parseColor(chartColors[i]));
            canvas.drawRect(bar, paint);
            EachAppDetails current = eachAppDetailsList.get(i);
            Drawable drawable = current.eachAppIcon;
            bitmapIconsOfApps = ((BitmapDrawable) drawable).getBitmap();
            bitmapIconsOfApps = resizeBitmap(bitmapIconsOfApps, (float) 0.6 * barWidth);
            canvas.drawBitmap(bitmapIconsOfApps, (barLeft + ((float) 0.2 * barWidth)), barTop - ((float) 0.6 * barWidth), paint);
            text = namesOfAppsUsed[i];
            canvas.drawText(text, barLeft, barBottom + spacing, paint);
            barLeft = barRight + spacing;
            barRight = barLeft + barWidth;
        }
        if (maxBarHeight < screenHeight * (float) 0.8) {
            maxBarHeight += 50;
            invalidate();
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


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        float yAxis = motionEvent.getY();
        float xAxis = motionEvent.getX();

        if ((yAxis >= 0 && yAxis < canvasHeight / 2) && (xAxis >= 0)) {
            Log.d("LOG", "BarWidth is :" + barWidth);
            barWidth++;
            invalidate();
        } else {
            barWidth--;
            invalidate();
        }
        Log.d("LOG", "BarWidth is" + barWidth);
        return true;
    }
}
