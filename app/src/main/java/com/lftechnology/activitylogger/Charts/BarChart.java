package com.lftechnology.activitylogger.Charts;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.view.ScaleGestureDetector;
import android.view.View;

/**
 * Created by sparsha on 7/21/2016.
 */
public class BarChart extends View implements View.OnTouchListener{
    int x,y;
    int canvasHeight,canvasWidth;
    int screenHeight, screenWidth;
    Paint paint;
    RectF bar;
    float marginX,marginY,spacing;
    float  barWidth,maxBarHeight,barLeft,barTop,barRight,barBottom;
    float textSize;
    String[] chartColors = {"#4D4D4D", "#5DA5DA", "#FAA43A", "#60BD68", "#F17CB0"};
    String text = "N/A";
    SharedPreferences sharedPreferences;
    Bitmap bitmapIconsOfApps;

    public BarChart(Context context) {
        super(context);
        sharedPreferences = context.getSharedPreferences("Top5Apps",Context.MODE_PRIVATE);

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;
        Log.d("LOG","Screen Width: "+screenWidth+"Screen Height" +screenHeight);
        barWidth = (float)screenWidth/(float)7;
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
        y = canvasHeight-(canvasHeight/10);

        spacing = (float)x/(float)21.6;
        marginX = spacing/2;
        marginY = spacing/2;



        Log.d("LOG",Integer.toString(x));
        Log.d("LOG",Integer.toString(y));
        float[] durationOfAppsUsed = new float[5];
        String[] namesOfAppsUsed = new String[5];
        float totalValue= 0;

        canvas.drawLine(marginX,y-marginY,x-marginX,y-marginY,paint);
        canvas.drawLine(marginX,marginY,marginX,y-marginY,paint);

        paint.setColor(Color.CYAN);

        barLeft = marginX +spacing;
        barRight = barLeft + barWidth;
        barBottom = y-marginY;
        barTop = barBottom - maxBarHeight;

        for(int i=0;i<durationOfAppsUsed.length;i++){
            durationOfAppsUsed[i] = (float) sharedPreferences.getLong("AppDuration"+i,1);
            namesOfAppsUsed[i] = sharedPreferences.getString("AppName"+i,"N/A");
            totalValue = totalValue+ durationOfAppsUsed[i];
        }

        for (int i=0;i<5;i++){
            barTop = barBottom -(float)0.8*(durationOfAppsUsed[i]/totalValue*maxBarHeight);
            if(barTop<marginY){barTop = marginY;}
            bar.set(barLeft,barTop,barRight,barBottom);
            paint.setColor(Color.parseColor(chartColors[i]));
            canvas.drawRect(bar,paint);
            try {
                ApplicationInfo applicationInfo =  getContext().getPackageManager().getApplicationInfo(namesOfAppsUsed[i],0);
                text = String.valueOf(getContext().getPackageManager().getApplicationLabel(applicationInfo));
                Drawable drawable = getContext().getPackageManager().getApplicationIcon(applicationInfo);
                bitmapIconsOfApps = ((BitmapDrawable)drawable).getBitmap();
                bitmapIconsOfApps = resizeBitmap(bitmapIconsOfApps,(float)0.6*barWidth);
            } catch (Exception e) {
                e.printStackTrace();
            }
            canvas.drawBitmap(bitmapIconsOfApps,(barLeft+((float)0.2*barWidth)),barTop-((float)0.6*barWidth),paint);
//            canvas.drawText(text,barLeft,barBottom+spacing,paint);
            barLeft = barRight+spacing;
            barRight = barLeft + barWidth;
        }
        if(maxBarHeight < screenHeight*(float)1.1){
            maxBarHeight+=50;
            invalidate();
        }


    }
    private Bitmap resizeBitmap(Bitmap bitmap,float width){
        int prevWidth = bitmap.getWidth();
        int prevHeight = bitmap.getHeight();
        float scale = width/(float) prevWidth;
        Matrix matrix = new Matrix();
        matrix.postScale(scale,scale);
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap,0,0,prevWidth,prevHeight,matrix,false);

        return  resizedBitmap;
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        float yAxis = motionEvent.getY();
        float xAxis = motionEvent.getX();

        if((yAxis>=0 && yAxis<canvasHeight/2) && (xAxis>=0))
        {
            Log.d("LOG","BarWidth is :"+barWidth);
            barWidth++;
            invalidate();
        }
        else
        {
            barWidth --;
            invalidate();
        }
        Log.d("LOG","BarWidth is"+barWidth);
        return true;
    }
}
