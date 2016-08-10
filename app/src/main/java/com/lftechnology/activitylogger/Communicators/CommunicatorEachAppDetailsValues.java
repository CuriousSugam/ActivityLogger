package com.lftechnology.activitylogger.Communicators;

import com.lftechnology.activitylogger.model.EachAppDetails;

import java.util.List;

/**
 * Created by sparsha on 7/28/2016.
 */

/**
 * Use this class to communicate different objects between different classes
 */
public class CommunicatorEachAppDetailsValues {
    private static List<EachAppDetails> eachAppDetailsList;

    /**
     * Sets the list
     * @param eachAppDetailsList
     */
    public void setDetailsList(List<EachAppDetails> eachAppDetailsList) {
        this.eachAppDetailsList = eachAppDetailsList;
    }

    /**
     * gets the list
     * @return
     */
    public List<EachAppDetails> getEachAppDetailsList() {
        return this.eachAppDetailsList;
    }
}
