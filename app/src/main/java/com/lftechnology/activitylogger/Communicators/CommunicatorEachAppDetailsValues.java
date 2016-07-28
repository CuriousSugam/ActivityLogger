package com.lftechnology.activitylogger.Communicators;

import com.lftechnology.activitylogger.EachAppDetails;

import java.util.List;

/**
 * Created by sparsha on 7/28/2016.
 */
public class CommunicatorEachAppDetailsValues {
    private static List<EachAppDetails> eachAppDetailsList;

    public void setDetailsList(List<EachAppDetails> eachAppDetailsList){
        this.eachAppDetailsList = eachAppDetailsList;
    }

    public List<EachAppDetails> getEachAppDetailsList(){
        return this.eachAppDetailsList;
    }
}
