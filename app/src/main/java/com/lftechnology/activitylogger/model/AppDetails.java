package com.lftechnology.activitylogger.model;

/**
 * @author Sugam Shakya
 * Created by Sugam on 7/5/2016.
 */
public class AppDetails {
    private int uid;
    private String packageName;
    private String applicationName;

    /**
     *
     * @param uid uid of the application
     * @param packageName package name of the application
     * @param applicationName application label
     */
    public AppDetails(int uid, String packageName, String applicationName){
        setUid(uid);
        setPackageName(packageName);
        setPackageName(applicationName);
    }
    public AppDetails(){}

    /**
     *
     * @param uid uid of the application
     */
    public void setUid(int uid) {
        this.uid = uid;
    }

    /**
     *
     * @param packageName package name of the application
     */
    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    /**
     *
     * @param applicationName application label
     */
    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    /**
     *
     * @return uid of the application
     */
    public int getUid() {
        return uid;
    }

    /**
     *
     * @return package name of the application
     */
    public String getPackageName() {
        return packageName;
    }

    /**
     *
     * @return application label
     */
    public String getApplicationName() {
        return applicationName;
    }
}
