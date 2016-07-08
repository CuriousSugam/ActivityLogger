package com.lftechnology.activitylogger.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * @author Sugam Shakya
 * Created by Sugam on 7/5/2016.
 *
 */
public class AppDetails implements Parcelable {
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
        setApplicationName(applicationName);
    }
    public AppDetails(){}

    protected AppDetails(Parcel in) {
        setUid(in.readInt());
        setPackageName(packageName);
        setApplicationName(applicationName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(uid);
        parcel.writeString(packageName);
        parcel.writeString(applicationName);
    }

    public static final Creator<AppDetails> CREATOR = new Creator<AppDetails>() {
        @Override
        public AppDetails createFromParcel(Parcel in) {
            Log.e("AppDetails", "Create from parcel: AppDetails");
            return new AppDetails(in);
        }

        @Override
        public AppDetails[] newArray(int size) {
            return new AppDetails[size];
        }
    };

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
