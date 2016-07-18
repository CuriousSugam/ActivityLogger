package com.lftechnology.activitylogger.model;

import java.util.Date;

/**
 * NetworkUsageDetails class stores the uid, package name, initialDate, total received bytes and total transmitted bytes
 * Created by Sugam on 7/11/2016.
 */
public class NetworkUsageDetails {
    private String packageName;
    private Date initialDate;
    private long totalRxBytes;
    private long totalTxBytes;
    private Date finalDate;

    public NetworkUsageDetails() {
    }

    public NetworkUsageDetails(String packageName, long totalRxBytes, long totalTxBytes) {
        this.packageName = packageName;
        this.totalRxBytes = totalRxBytes;
        this.totalTxBytes = totalTxBytes;
    }

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
}
