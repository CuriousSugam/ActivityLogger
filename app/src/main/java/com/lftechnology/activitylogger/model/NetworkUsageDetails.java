package com.lftechnology.activitylogger.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;

/**
 * NetworkUsageDetails class stores the uid, package name, initialDate, total received bytes and total transmitted bytes
 * Created by Sugam on 7/11/2016.
 */
public class NetworkUsageDetails implements Parcelable {
    private String packageName;
    private Date initialDate;
    private long totalRxBytes;
    private long totalTxBytes;
    private Date finalDate;

    public NetworkUsageDetails() {
    }

    public NetworkUsageDetails(Parcel in){
        this.packageName = in.readString();
        this.initialDate = (Date)in.readSerializable();
        this.totalRxBytes = in.readLong();
        this.totalTxBytes = in.readLong();
        this.finalDate = (Date)in.readSerializable();
    }
    public NetworkUsageDetails(String packageName, long totalRxBytes, long totalTxBytes) {
        this.packageName = packageName;
        this.totalRxBytes = totalRxBytes;
        this.totalTxBytes = totalTxBytes;
    }

    public static final Creator<NetworkUsageDetails> CREATOR = new Creator<NetworkUsageDetails>() {
        @Override
        public NetworkUsageDetails createFromParcel(Parcel in) {
            return new NetworkUsageDetails(in);
        }

        @Override
        public NetworkUsageDetails[] newArray(int size) {
            return new NetworkUsageDetails[size];
        }
    };

    public Date getFinalDate() {
        return finalDate;
    }

    public void setInitialDate(Date initialDate) {
        this.initialDate = initialDate;
    }

    public void setFinalDate(Date finalDate) {
        this.finalDate = finalDate;
    }

    public Date getInitialDate() {
        return initialDate;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public long getTotalRxBytes() {
        return totalRxBytes;
    }

    public void setTotalRxBytes(long totalRxBytes) {
        this.totalRxBytes = totalRxBytes;
    }

    public long getTotalTxBytes() {
        return totalTxBytes;
    }

    public void setTotalTxBytes(long totalTxBytes) {
        this.totalTxBytes = totalTxBytes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.packageName);
        parcel.writeSerializable(initialDate);
        parcel.writeLong(this.totalRxBytes);
        parcel.writeLong(this.totalTxBytes);
        parcel.writeSerializable(this.finalDate);
    }
}
