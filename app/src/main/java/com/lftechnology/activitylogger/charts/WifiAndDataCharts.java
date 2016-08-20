package com.lftechnology.activitylogger.charts;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.lftechnology.activitylogger.R;
import com.lftechnology.activitylogger.model.NetworkUsageDetails;

import java.util.List;

/**
 * Creates a circular progress bar of and shows image of the most wifi and data used app respectively
 *
 * @author sparsha
 */
public class WifiAndDataCharts extends View {
    private long totalRxBytesWifi = 0, totalRxBytesData = 0;
    private NetworkUsageDetails mostWifiUsedAppDetails, mostDataUsedAppDetails;
    private final Paint wifiColor = new Paint();
    private final Paint dataColor = new Paint();
    private final Paint gapColor = new Paint();
    private ApplicationInfo applicationInfoWifi, applicationInfoData;
    private Bitmap iconOfMostWifiUsedApp, iconOfMostDataUsedApp;


    public WifiAndDataCharts(Context context) {
        super(context);
    }

    public WifiAndDataCharts(Context context, List<NetworkUsageDetails> wifi, List<NetworkUsageDetails> data) {
        super(context);
        if (!wifi.isEmpty()) {
            mostWifiUsedAppDetails = wifi.get(0);
            String packageNameAppWifi = mostWifiUsedAppDetails.getPackageName();
            try {
                applicationInfoWifi = context.getPackageManager().getApplicationInfo(packageNameAppWifi, PackageManager.GET_META_DATA);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

            for (NetworkUsageDetails current : wifi) {
                totalRxBytesWifi += current.getTotalRxBytes();
            }
        }
        if (!data.isEmpty()) {
            mostDataUsedAppDetails = data.get(0);
            String packageNameAppData = mostDataUsedAppDetails.getPackageName();
            try {
                applicationInfoData = context.getPackageManager().getApplicationInfo(packageNameAppData, PackageManager.GET_META_DATA);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            for (NetworkUsageDetails current : data) {
                totalRxBytesData += current.getTotalRxBytes();
            }
        }
        wifiColor.setColor(getResources().getColor(R.color.lightGreen));
        wifiColor.setStyle(Paint.Style.STROKE);

        dataColor.setColor(getResources().getColor(R.color.skyBlue));
        dataColor.setStyle(Paint.Style.STROKE);

        gapColor.setColor(getResources().getColor(R.color.dividerFaint));
        gapColor.setStyle(Paint.Style.STROKE);

        if (applicationInfoData != null) {
            Drawable iconOfMostDataUsedApp = context.getPackageManager().getApplicationIcon(applicationInfoData);
            this.iconOfMostDataUsedApp = ((BitmapDrawable) iconOfMostDataUsedApp).getBitmap();
        }
        if (applicationInfoWifi != null) {
            Drawable iconOfMostWifiUsedApp = context.getPackageManager().getApplicationIcon(applicationInfoWifi);
            this.iconOfMostWifiUsedApp = ((BitmapDrawable) iconOfMostWifiUsedApp).getBitmap();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float canvasHeight = canvas.getHeight();
        float canvasWidth = canvas.getWidth();
        float dataRingTop, dataRingLeft, dataRingRight, dataRingBottom;
        float wifiRingTop, wifiRingLeft, wifiRingRight, wifiRingBottom;
        float dataRingRadius = canvasHeight * (float) 0.2;
        float wifiRingRadius = canvasHeight * (float) 0.3;
        wifiColor.setStrokeWidth(wifiRingRadius / 8);
        dataColor.setStrokeWidth(wifiRingRadius / 8);
        gapColor.setStrokeWidth(wifiRingRadius/8);

        wifiRingLeft = (float) 0.3 * canvasWidth - wifiRingRadius;
        wifiRingRight = (float) 0.3 * canvasWidth + wifiRingRadius;
        wifiRingTop = (float) 0.5 * canvasHeight - wifiRingRadius;
        wifiRingBottom = (float) 0.5 * canvasHeight + wifiRingRadius;

        dataRingLeft = (float) 0.75 * canvasWidth - dataRingRadius;
        dataRingRight = (float) 0.75 * canvasWidth + dataRingRadius;
        dataRingTop = (float) 0.75 * canvasHeight - dataRingRadius;
        dataRingBottom = (float) 0.75 * canvasHeight + dataRingRadius;

        RectF rectFWifi = new RectF(wifiRingLeft, wifiRingTop, wifiRingRight, wifiRingBottom);
        RectF rectFData = new RectF(dataRingLeft, dataRingTop, dataRingRight, dataRingBottom);
        float maxRingAngle = 300;
        float angleGap = 360-maxRingAngle;    //30 degree angle gap
        if (totalRxBytesWifi != 0) {
            float startAngle = 90+angleGap/2;
            float sweepAngle = maxRingAngle * mostWifiUsedAppDetails.getTotalRxBytes() / totalRxBytesWifi;
            canvas.drawArc(rectFWifi, startAngle, sweepAngle, false, wifiColor);
            canvas.drawArc(rectFWifi,startAngle+sweepAngle,maxRingAngle-sweepAngle,false,gapColor);

        }
        if (totalRxBytesWifi == 0) {
            float startAngle = 90+angleGap/2;
            float sweepAngle = maxRingAngle;
            canvas.drawArc(rectFWifi,startAngle,sweepAngle,false,gapColor);

        }

        if (totalRxBytesData != 0) {
            float startAngle = 90+angleGap/2;
            float sweepAngle = maxRingAngle * mostDataUsedAppDetails.getTotalRxBytes() / totalRxBytesData;
            canvas.drawArc(rectFData,startAngle, sweepAngle, false, dataColor);
            canvas.drawArc(rectFData,startAngle+sweepAngle,maxRingAngle-sweepAngle,false,gapColor);

        }

        if (totalRxBytesData == 0) {
            float startAngle = 90+angleGap/2;
            float sweepAngle = maxRingAngle;
            canvas.drawArc(rectFData,startAngle, sweepAngle, false, gapColor);

        }

        if (iconOfMostDataUsedApp != null) {
            iconOfMostDataUsedApp = resizeBitmap(iconOfMostDataUsedApp, dataRingRadius / 2);
            canvas.drawBitmap(iconOfMostDataUsedApp, dataRingLeft + 3 * dataRingRadius / 4, dataRingTop + 3 * dataRingRadius / 4, dataColor);

        }
        if (iconOfMostWifiUsedApp != null) {
            iconOfMostWifiUsedApp = resizeBitmap(iconOfMostWifiUsedApp, wifiRingRadius / 2);
            canvas.drawBitmap(iconOfMostWifiUsedApp, wifiRingLeft + wifiRingRadius - wifiRingRadius / (float) 4
                    , wifiRingTop + wifiRingRadius - wifiRingRadius / (float) 4, wifiColor);
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
