package com.lftechnology.activitylogger.communicators;

import com.lftechnology.activitylogger.model.EachAppDetails;

import java.util.List;


/**
 * Use this class to communicate different objects between different classes
 * Currently eachAppDetails Objects used
 */
public class CommunicatorEachAppDetailsValues {
    private static List<EachAppDetails> eachAppDetailsList;
    private static List<EachAppDetails> eachAppDetailsListDaily;
    private static List<EachAppDetails> eachAppDetailsListWeekly;
    private static List<EachAppDetails> eachAppDetailsListMonthly;
    private static List<EachAppDetails> eachAppDetailsListYearly;



    /**
     * Sets the list
     * @param mEachAppDetailsList
     */
    public void setDetailsList(List<EachAppDetails> mEachAppDetailsList) {
        eachAppDetailsList = mEachAppDetailsList;
    }

    /**
     * gets the list
     * @return
     */
    public List<EachAppDetails> getEachAppDetailsList() {
        return eachAppDetailsList;
    }

    public void setEachAppDetailsListEveryInterval(List<EachAppDetails> listDaily,List<EachAppDetails> listWeekly
            , List<EachAppDetails> listMonthly, List<EachAppDetails>listYearly){
        eachAppDetailsListDaily = listDaily;
        eachAppDetailsListWeekly = listWeekly;
        eachAppDetailsListMonthly = listMonthly;
        eachAppDetailsListYearly = listYearly;
    }
    public List<EachAppDetails> getEachAppDetailsListDaily() {
        return eachAppDetailsListDaily;

    }
    public List<EachAppDetails> getEachAppDetailsListWeekly() {
        return eachAppDetailsListWeekly;
    }
    public List<EachAppDetails> getEachAppDetailsListMonthly() {
        return eachAppDetailsListMonthly;
    }
    public List<EachAppDetails> getEachAppDetailsListYearly() {
        return eachAppDetailsListYearly;
    }

}
