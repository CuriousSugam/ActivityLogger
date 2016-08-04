package com.lftechnology.activitylogger.Charts;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by sparsha on 8/3/2016.
 */
public class LineChart extends SurfaceView implements Runnable {
    Thread thread;
    boolean canDraw = false, initialized = false;
    SurfaceHolder surfaceHolder;
    Paint linePaint, backgroundPaint, barPaint;
    Path linePath, barPathHorizontal;
    Canvas canvas;
    int canvasHeight, canvasWidth;
    float startPosX, startPosY, endPosX, endPosY, posAX, posAY, posBX, posBY;
    float spacingX;
    float values[];
    float totalValue = 0;
    int position = 0;
    boolean drawingComplete = false;

    //    String nameOfApp;
    public LineChart(Context context) {
        super(context);
        surfaceHolder = getHolder();
        backgroundPaint = new Paint();
        backgroundPaint.setColor(Color.BLUE);
        backgroundPaint.setStyle(Paint.Style.FILL);
        linePaint = new Paint();
        linePaint.setColor(Color.parseColor("#FAA43A"));
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(10);

        barPaint = new Paint();
        barPaint.setColor(Color.parseColor("#DDDDDD"));
        barPaint.setStyle(Paint.Style.STROKE);
        barPaint.setStrokeWidth(2);


        linePath = new Path();

        values = new float[5];

        for (int pos = 0; pos < values.length; pos++) {
            values[pos] = (float) Math.random() * 50;
            Log.d("Log", "The pos " + pos + " value is : " + values[pos]);
            totalValue += values[pos];
        }
//        if (appName.isEmpty())
//            nameOfApp = "N/A";
//        else
//            nameOfApp = appName;
    }

    @Override
    public void run() {

        while (canDraw) {
            if (!surfaceHolder.getSurface().isValid()) {
                continue;
            }
            if (!drawingComplete) {
                canvas = surfaceHolder.lockCanvas();

//                canvas.drawText(nameOfApp,100,100,linePaint);
                initializeStartPosition();
                canvas.drawColor(Color.WHITE);

                barPathHorizontal = new Path();

                float graphLineSpacingHorizontal = startPosY / 10;
                float graphLineHorizontal = startPosY;
                float spacing = canvasHeight / 50;


                for (int i = 0; i < 10; i++) {
                    barPathHorizontal.moveTo(startPosX, graphLineHorizontal);
                    barPathHorizontal.lineTo(canvasWidth - spacing, graphLineHorizontal);
                    canvas.drawPath(barPathHorizontal, barPaint);
                    graphLineHorizontal = graphLineHorizontal - graphLineSpacingHorizontal;
                }
                canvas.drawLine(startPosX, startPosY, startPosX, graphLineHorizontal + graphLineSpacingHorizontal, barPaint);
                canvas.drawLine(canvasWidth - spacing, startPosY, canvasWidth - spacing, graphLineHorizontal + graphLineSpacingHorizontal, barPaint);

                posBX = posAX + spacingX;
                posBY = startPosY - values[position] / totalValue * canvasHeight;

                linePath.moveTo(posAX, posAY);
                linePath.lineTo(posBX, posBY);
                linePath.addCircle(posBX, posBY, 20, Path.Direction.CCW);
                canvas.drawPath(linePath, linePaint);
//            canvas.drawCircle(endPosX,endPosY,10,linePaint);

                posAX = posBX;
                posAY = posBY;

                resize();
                surfaceHolder.unlockCanvasAndPost(canvas);

            }
        }
    }

    public void pause() {
        canDraw = false;
        while (true) {

            try {
                thread.join();
                break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        thread = null;

    }

    public void resume() {
        canDraw = true;
        thread = new Thread(this);
        thread.start();

    }

    private void resize() {
        if (position < values.length - 1)
            position++;
        else
            drawingComplete = true;
    }

    private void initializeStartPosition() {
        while (!initialized) {
            canvasHeight = canvas.getHeight();
            canvasWidth = canvas.getWidth();

            startPosX = 0 + (float) 0.1 * canvasWidth;
            startPosY = canvasHeight - (float) 0.1 * canvasHeight;
            spacingX = canvasWidth / 7;

            posAX = startPosX;
            posAY = startPosY;


            initialized = true;
        }

    }
}
