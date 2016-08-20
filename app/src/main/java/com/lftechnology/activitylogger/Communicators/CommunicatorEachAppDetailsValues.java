package com.lftechnology.activitylogger.communicators;

import com.lftechnology.activitylogger.model.EachAppDetails;

import java.util.List;


/**
 * Use this class to communicate different objects between different classes
 * Currently eachAppDetails Objects used
 */
public class CommunicatorEachAppDetailsValues {
    private static List<EachAppDetails> eachAppDetailsList;

    /**
     * Sets the list
     * @param eachAppDetails
     */
    public void setDetailsList(List<EachAppDetails> eachAppDetails) {
        eachAppDetailsList = eachAppDetails;
    }

    /**
     * gets the list
     * @return
     */
    public List<EachAppDetails> getEachAppDetailsList() {
        return eachAppDetailsList;
    }
}
