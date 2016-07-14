package com.lftechnology.activitylogger.model;

import java.util.Date;

/**
 * NetworkUsageDetails class stores the uid, package name, date, total received bytes and total transmitted bytes
 * Created by Sugam on 7/11/2016.
 */
public class NetworkUsageDetails {
    private String packageName;
    private Date date;
    private long totalRxBytes;
    private long totalTxBytes;

    public NetworkUsageDetails() {
    }

    public NetworkUsageDetails(String packageName, long totalRxBytes, long totalTxBytes) {
        this.packageName = packageName;
        this.totalRxBytes = totalRxBytes;
        this.totalTxBytes = totalTxBytes;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
}
