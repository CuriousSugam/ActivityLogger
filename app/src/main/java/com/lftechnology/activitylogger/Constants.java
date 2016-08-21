package com.lftechnology.activitylogger;

/**
 * Created by sparsha on 7/6/2016.
 */
public enum Constants {
    DAILY(0),WEEKLY(1),MONTHLY(2),YEARLY(3),BEST(4);

    public final static String MOBILE_NETWORK = "mobile";
    public final static String OFFLINE = "offline";
    public final static String NETWORK_TYPE = "networkType";
    public final static String WIFI_NETWORK = "wifi";

    public int value;
    Constants(int mValue){ value = mValue;}




}
