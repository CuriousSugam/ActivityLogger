package com.lftechnology.activitylogger.charts;

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
import android.view.View;

import com.lftechnology.activitylogger.R;
import com.lftechnology.activitylogger.communicators.CommunicatorEachAppDetailsValues;
import com.lftechnology.activitylogger.model.EachAppDetails;

import java.util.List;

/**
 * Gives a graphical representation of data in bar charts
 */


public class BarChart extends View {
    int x, y;
    int canvasHeight, canvasWidth;
    int screenHeight, screenWidth;
    Paint paint;
    RectF bar;
    float marginX, marginY, spacing;
    float barWidth, maxBarHeight, barLeft, barTop, barRight, barBottom;
    float textSize;
    int[] chartColors = getResources().getIntArray(R.array.chartsColors);
    String text = "N/A";
    Bitmap bitmapIconsOfApps;
    List<EachAppDetails> eachAppDetailsList;
    int numberOfBars;
    int maxBars;

    /**
     * @param context The current UI/Activity
     * @param size    Numbers of bars needed
     */
    public BarChart(Context context, int size) {
        super(context);

        eachAppDetailsList = new CommunicatorEachAppDetailsValues().getEachAppDetailsList();
        maxBars = size;
        numberOfBars = size;

        if (numberOfBars > eachAppDetailsList.size())
            numberOfBars = eachAppDetailsList.size();

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;
        barWidth = (float) screenWidth / ((float) 2 * (maxBars));
        maxBarHeight = 0;
        setBackgroundColor(Color.WHITE);
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        textSize = 25;
        paint.setTextSize(textSize);
        bar = new RectF();
        //  this.setOnTouchListener(this);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvasHeight = getHeight();//Height of canvas created
        canvasWidth = getWidth();//Width of canvas created

        paint.setColor(Color.BLACK);
        x = canvasWidth;
        y = canvasHeight - (canvasHeight / 10);

        spacing = (float) x / (float) 50;
        marginX = spacing / 2;
        marginY = spacing / 2;

        float[] durationOfAppsUsed = new float[numberOfBars];
        String[] namesOfAppsUsed = new String[numberOfBars];
        float totalValue = 0;

        canvas.drawLine(marginX, y - marginY, x - marginX, y - marginY, paint);
        canvas.drawLine(marginX, marginY, marginX, y - marginY, paint);

        float graphLineSpacingHorizontal = (y - 2 * marginY) / 10;
        float graphLineHorizontal = y - marginY;

        for (int i = 0; i < 10; i++) {
            canvas.drawLine(marginX, graphLineHorizontal, x - marginX, graphLineHorizontal, paint);
            graphLineHorizontal = graphLineHorizontal - graphLineSpacingHorizontal;
        }

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
            if (barTop < graphLineHorizontal + graphLineSpacingHorizontal) {
                barTop = graphLineHorizontal + graphLineSpacingHorizontal;
            }
            bar.set(barLeft, barTop, barRight, barBottom);
            paint.setColor((chartColors[i % chartColors.length]));//Colors Repeat themselves after the 9th
            canvas.drawRect(bar, paint);
            EachAppDetails current = eachAppDetailsList.get(i);
            Drawable drawable = current.eachAppIcon;
            bitmapIconsOfApps = ((BitmapDrawable) drawable).getBitmap();//Convert drawable to bitmap
            bitmapIconsOfApps = resizeBitmap(bitmapIconsOfApps, (float) 0.8 * barWidth);
            canvas.drawBitmap(bitmapIconsOfApps, (barLeft + ((float) 0.1 * barWidth)), barTop - ((float) 0.8 * barWidth), paint);
            text = namesOfAppsUsed[i];
//            canvas.drawText(text, barLeft, barBottom + spacing, paint);
            barLeft = barRight + spacing;
            barRight = barLeft + barWidth;
        }

        /**
         * Animates the canvas
         */
        if (maxBarHeight < screenHeight * (float) 0.5) {            //If drawing is not completed
            maxBarHeight += 50;
            invalidate();                                           //Redraw the canvas
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

/*
    Used for checking bar width
    //TODO Remove

*/

//    @Override
//    public boolean onTouch(View view, MotionEvent motionEvent) {
//
//        float yAxis = motionEvent.getY();
//        float xAxis = motionEvent.getX();
//
//        if ((yAxis >= 0 && yAxis < canvasHeight / 2) && (xAxis >= 0)) {
//            Log.d("LOG", "BarWidth is :" + barWidth);
//            barWidth++;
//            invalidate();
//        } else {
//            barWidth--;
//            invalidate();
//        }
//        Log.d("LOG", "BarWidth is" + barWidth);
//        return true;
//    }
}
