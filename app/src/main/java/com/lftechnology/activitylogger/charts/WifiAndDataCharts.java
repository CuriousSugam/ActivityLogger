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
import com.lftechnology.activitylogger.adapter.NetworkDataAdapter;
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
    private final Paint textColor = new Paint();
    private ApplicationInfo applicationInfoWifi, applicationInfoData;
    private Bitmap iconOfMostWifiUsedApp, iconOfMostDataUsedApp;
    private String nameOfMostWifiUsedApp, nameOfMostDataUsedApp;
    private float ringAnimatorAngle = 0;
    String dataUsedWifi,dataUsedMobileData;


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
            dataUsedWifi = new NetworkDataAdapter().memorySizeFormat(mostWifiUsedAppDetails.getTotalRxBytes());
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
            dataUsedMobileData = new NetworkDataAdapter().memorySizeFormat(mostDataUsedAppDetails.getTotalRxBytes());
        }
        wifiColor.setColor(getResources().getColor(R.color.lightGreen));
        wifiColor.setStyle(Paint.Style.STROKE);

        dataColor.setColor(getResources().getColor(R.color.skyBlue));
        dataColor.setStyle(Paint.Style.STROKE);

        gapColor.setColor(getResources().getColor(R.color.dividerFaint));
        gapColor.setStyle(Paint.Style.STROKE);

        textColor.setStyle(Paint.Style.FILL);
        textColor.setColor(getResources().getColor(R.color.textColorPrimary));
        textColor.setTextAlign(Paint.Align.CENTER);

        if (applicationInfoData != null) {
            Drawable iconOfMostDataUsedApp = context.getPackageManager().getApplicationIcon(applicationInfoData);
            nameOfMostDataUsedApp = String.valueOf(context.getPackageManager().getApplicationLabel(applicationInfoData));
            this.iconOfMostDataUsedApp = ((BitmapDrawable) iconOfMostDataUsedApp).getBitmap();

        }
        if (applicationInfoWifi != null) {
            Drawable iconOfMostWifiUsedApp = context.getPackageManager().getApplicationIcon(applicationInfoWifi);
            nameOfMostWifiUsedApp = String.valueOf(context.getPackageManager().getApplicationLabel(applicationInfoWifi));
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
        gapColor.setStrokeWidth(wifiRingRadius / 8);
        final float textSize = canvasWidth / 30;
        textColor.setTextSize(textSize);

        wifiRingLeft = (float) 0.3 * canvasWidth - wifiRingRadius;
        wifiRingRight = (float) 0.3 * canvasWidth + wifiRingRadius;
        wifiRingTop = (float) 0.5 * canvasHeight - wifiRingRadius;
        wifiRingBottom = (float) 0.5 * canvasHeight + wifiRingRadius;

        dataRingLeft = (float) 0.75 * canvasWidth - dataRingRadius;
        dataRingRight = (float) 0.75 * canvasWidth + dataRingRadius;
        dataRingTop = (float) 0.65 * canvasHeight - dataRingRadius;
        dataRingBottom = (float) 0.65 * canvasHeight + dataRingRadius;

        RectF rectFWifi = new RectF(wifiRingLeft, wifiRingTop, wifiRingRight, wifiRingBottom);
        RectF rectFData = new RectF(dataRingLeft, dataRingTop, dataRingRight, dataRingBottom);
        float maxRingAngle = 300;
        float angleGap = 360 - maxRingAngle;    //30 degree angle gap

        canvas.drawText("WIFI", wifiRingLeft + wifiRingRadius, wifiRingTop - textSize, textColor);
        if (totalRxBytesWifi != 0) {
            float startAngle = 90 + angleGap / 2;
            float sweepAngle = ringAnimatorAngle * mostWifiUsedAppDetails.getTotalRxBytes() / totalRxBytesWifi;
            canvas.drawArc(rectFWifi, startAngle, sweepAngle, false, wifiColor);
            canvas.drawArc(rectFWifi, startAngle + sweepAngle, maxRingAngle - sweepAngle, false, gapColor);
            canvas.drawText(dataUsedWifi, wifiRingLeft + wifiRingRadius, wifiRingBottom - 2 * textSize, textColor);
            canvas.drawText(nameOfMostWifiUsedApp, wifiRingLeft + wifiRingRadius, wifiRingBottom + textSize, textColor);

        } else if (totalRxBytesWifi == 0) {
            float startAngle = 90 + angleGap / 2;
            float sweepAngle = maxRingAngle;
            canvas.drawArc(rectFWifi, startAngle, sweepAngle, false, gapColor);
            canvas.drawText("WIFI", wifiRingLeft + wifiRingRadius, wifiRingBottom - wifiRingRadius, textColor);
            canvas.drawText("NOT USED", wifiRingLeft + wifiRingRadius, wifiRingBottom - wifiRingRadius + textSize, textColor);

        }

        canvas.drawText("MOBILE DATA", dataRingLeft + dataRingRadius, dataRingTop - textSize, textColor);
        if (totalRxBytesData != 0) {
            float startAngle = 90 + angleGap / 2;
            float sweepAngle = ringAnimatorAngle * mostDataUsedAppDetails.getTotalRxBytes() / totalRxBytesData;
            canvas.drawArc(rectFData, startAngle, sweepAngle, false, dataColor);
            canvas.drawArc(rectFData, startAngle + sweepAngle, maxRingAngle - sweepAngle, false, gapColor);
            canvas.drawText(nameOfMostDataUsedApp, dataRingLeft + dataRingRadius, dataRingBottom + textSize, textColor);
            canvas.drawText(dataUsedMobileData, dataRingLeft + dataRingRadius, dataRingBottom - 2 * textSize, textColor);


        } else if (totalRxBytesData == 0) {
            float startAngle = 90 + angleGap / 2;
            float sweepAngle = maxRingAngle;
            canvas.drawArc(rectFData, startAngle, sweepAngle, false, gapColor);
            canvas.drawText("MOBILE DATA", dataRingLeft + dataRingRadius, dataRingBottom - dataRingRadius, textColor);
            canvas.drawText("NOT USED", dataRingLeft + dataRingRadius, dataRingBottom - dataRingRadius + textSize, textColor);

        }

        if (iconOfMostDataUsedApp != null) {
            iconOfMostDataUsedApp = resizeBitmap(iconOfMostDataUsedApp, dataRingRadius / 2);
            canvas.drawBitmap(iconOfMostDataUsedApp, dataRingLeft + 3 * dataRingRadius / 4
                    , dataRingTop + 2 * dataRingRadius / 3, dataColor);

        }
        if (iconOfMostWifiUsedApp != null) {
            iconOfMostWifiUsedApp = resizeBitmap(iconOfMostWifiUsedApp, wifiRingRadius / 2);
            canvas.drawBitmap(iconOfMostWifiUsedApp, wifiRingLeft + 3 * wifiRingRadius / 4
                    , wifiRingTop + 2* wifiRingRadius / 3, wifiColor);
        }

        if(ringAnimatorAngle<maxRingAngle){
            ringAnimatorAngle+=10;
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
