package com.lftechnology.activitylogger;

/**
 * Created by sparsha on 7/6/2016.
 */
public enum ConstantIntervals {
    DAILY(0),WEEKLY(1),MONTHLY(2),YEARLY(3),BEST(4);
    public int value;
    ConstantIntervals(int mValue){ value = mValue;}
}
