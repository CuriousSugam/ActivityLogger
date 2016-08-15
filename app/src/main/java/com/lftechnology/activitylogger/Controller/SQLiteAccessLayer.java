package com.lftechnology.activitylogger.controller;

import android.content.ContentValues;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.net.Network;
import android.net.TrafficStats;
import android.util.Log;

import com.lftechnology.activitylogger.model.AppDetails;
import com.lftechnology.activitylogger.model.NetworkUsageDetails;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Sugam Shakya
 *         Created by Sugam on 7/5/2016.
 *         This class provides the methods to insert, update, delete and query the app details in the application database
 */
public class SQLiteAccessLayer {

    private static final String DBNAME = "ActivityLoggerDatabase";

    private static final String TABLE_PACKAGE_INFO = "package_info_table";
    private static final String TABLE_NETWORK_TEMP = "network_temp_table";
    private static final String TABLE_NETWORK_INFO_TABLE = "network_info_table";

    private static final int DATABASE_VERSION = 13;

    private static final String TABLE_COLUMN_ID = "_id";
    private static final String TABLE_COLUMN_UID = "uid";
    private static final String TABLE_COLUMN_PACKAGE_NAME = "package_name";
    private static final String TABLE_COLUMN_APPLICATION_NAME = "application_name";
    private static final String TABLE_COLUMN_APPLICATION_TYPE = "application_type";
    private static final String TABLE_COLUMN_INITIAL_RX_BYTES = "initial_rx_bytes";
    private static final String TABLE_COLUMN_INITIAL_TX_BYTES = "initial_tx_bytes";
    private static final String TABLE_COLUMN_DATE_TIME = "date_time";
    private static final String TABLE_COLUMN_INITIAL_DATE_TIME = "initial_date_time";
    private static final String TABLE_COLUMN_NET_RX_BYTES = "final_rx_bytes";
    private static final String TABLE_COLUMN_NET_TX_BYTES = "final_tx_bytes";
    private static final String TABLE_COLUMN_FINAL_DATE_TIME = "final_date_time";
    private static final String TABLE_COLUMN_NETWORK_TYPE = "network_type";


    private ActivityLoggerSQLiteOpenHelper dbHelper;
    private SQLiteDatabase db;
    private AppDetails appDetails;
    private NetworkUsageDetails networkUsageDetails;

    /**
     * @param context    Context of the calling activity
     * @param appDetails Object of AppDetails which holds some data about an application
     */
    public SQLiteAccessLayer(Context context, AppDetails appDetails) {
        dbHelper = new ActivityLoggerSQLiteOpenHelper(context);
        this.db = dbHelper.getWritableDatabase();
        this.appDetails = appDetails;
    }

    /**
     * @param context             Context of the calling activity
     * @param networkUsageDetails Object of NetworkUsageDetails which holds the number of bytes received and
     *                            transmitted by the application
     */
    public SQLiteAccessLayer(Context context, NetworkUsageDetails networkUsageDetails) {
        dbHelper = new ActivityLoggerSQLiteOpenHelper(context);
        this.db = dbHelper.getWritableDatabase();
        this.networkUsageDetails = networkUsageDetails;
    }

    /**
     * @param context Context of the calling activity
     */
    public SQLiteAccessLayer(Context context) {
        dbHelper = new ActivityLoggerSQLiteOpenHelper(context);
        this.db = dbHelper.getWritableDatabase();
    }

    /**
     * This method will insert the data hold by the AppDetails object passed through the constructor
     * into the database
     *
     * @return the row ID of the newly inserted row, or -1 if an error occurred
     */
    public long insertIntoAppDetails() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_COLUMN_UID, this.appDetails.getUid());
        contentValues.put(TABLE_COLUMN_PACKAGE_NAME, this.appDetails.getPackageName());
        contentValues.put(TABLE_COLUMN_APPLICATION_NAME, this.appDetails.getApplicationName());
        contentValues.put(TABLE_COLUMN_APPLICATION_TYPE, this.appDetails.getApplicationType());

        // insert the new row, returning the primary key of the row inserted
        long newInsertedRowId;
        newInsertedRowId = db.insert(TABLE_PACKAGE_INFO, null, contentValues);
        // TODO remove this code
//        Log.e("Database", "Inserted:=> "+this.appDetails.getApplicationName());

        return newInsertedRowId;
    }

    /**
     * This method extracts the application information from the database
     *
     * @return a List<AppDetails>
     */
    public List<AppDetails> queryAppDetails() {
        List<AppDetails> appDetailsList = new ArrayList<>();
        String[] columns = {TABLE_COLUMN_UID, TABLE_COLUMN_PACKAGE_NAME, TABLE_COLUMN_APPLICATION_NAME, TABLE_COLUMN_APPLICATION_TYPE};
        Cursor cursor = db.query(TABLE_PACKAGE_INFO, columns, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            AppDetails tempAppDetailsObj = new AppDetails();
            tempAppDetailsObj.setUid(cursor.getInt(cursor.getColumnIndex(TABLE_COLUMN_UID)));
            tempAppDetailsObj.setPackageName(cursor.getString(cursor.getColumnIndex(TABLE_COLUMN_PACKAGE_NAME)));
            tempAppDetailsObj.setApplicationName(cursor.getString(cursor.getColumnIndex(TABLE_COLUMN_APPLICATION_NAME)));
            tempAppDetailsObj.setApplicationType(cursor.getString(cursor.getColumnIndex(TABLE_COLUMN_APPLICATION_TYPE)));
            appDetailsList.add(tempAppDetailsObj);
        }
        cursor.close();
        return appDetailsList;
    }

    /**
     * @param whereClause the WHERE clause to apply when deleting. Passing null will delete all rows.
     * @param whereArgs   '?'s included in the where clause will be replaced by the values from whereArgs.
     *                    <p/>
     *                    deleteAnAppDetail("id=? and name=?",new String[]{"1","jack"});
     * @return the number of rows affected if a whereClause is passed in, 0 otherwise.
     * To remove all rows and get a count pass "1" as the whereClause.
     */
    public int deleteAnAppDetail(String whereClause, String[] whereArgs) {
        return db.delete(TABLE_PACKAGE_INFO, whereClause, whereArgs);
    }


    /**
     * @param whereClause the WHERE clause to apply when updating. Passing null will update all rows.
     * @param whereArgs   '?'s included in the where clause will be replaced by the values from whereArgs.
     * @return the number of rows affected
     */
    public int updateAppDetail(String whereClause, String[] whereArgs) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_COLUMN_UID, this.appDetails.getUid());
        contentValues.put(TABLE_COLUMN_PACKAGE_NAME, this.appDetails.getPackageName());
        contentValues.put(TABLE_COLUMN_APPLICATION_NAME, this.appDetails.getApplicationName());
        return db.update(TABLE_PACKAGE_INFO, contentValues, whereClause, whereArgs);
    }

    /**
     * This method checks if the app details have been added to the database or not
     *
     * @return true if the database is empty and no appdetails has been added to it, returns false if the database is not empty
     */
    public boolean isDatabaseEmpty() {
        return queryAppDetails().isEmpty();
    }

    /**
     * This method will insert the data hold by the NetworkUsageDetails object passed through the constructor
     * into the database
     *
     * @return the row ID of the newly inserted row, or -1 if an error occurred
     */
    public long insertTempNetworkDetails() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_COLUMN_PACKAGE_NAME, this.networkUsageDetails.getPackageName());
        contentValues.put(TABLE_COLUMN_INITIAL_RX_BYTES, this.networkUsageDetails.getTotalRxBytes());
        contentValues.put(TABLE_COLUMN_INITIAL_TX_BYTES, this.networkUsageDetails.getTotalTxBytes());
        Log.e("insertIntoTempTable", this.networkUsageDetails.getPackageName() + " " + this.networkUsageDetails.getTotalTxBytes() +
                " " + this.networkUsageDetails.getTotalRxBytes());
        return db.insert(TABLE_NETWORK_TEMP, TABLE_COLUMN_DATE_TIME, contentValues);

    }

    /**
     * This method extracts the information of initial tranmissted and received bytes of the application
     * from the database
     *
     * @return a List<NetworkUsageDetails>
     */
    public List<NetworkUsageDetails> queryTempNetworkUsageDetails() {
        List<NetworkUsageDetails> networkUsageDetailsList = new ArrayList<>();
        String[] columns = {TABLE_COLUMN_PACKAGE_NAME, TABLE_COLUMN_DATE_TIME, TABLE_COLUMN_INITIAL_RX_BYTES, TABLE_COLUMN_INITIAL_TX_BYTES};
        Cursor cursor = db.query(TABLE_NETWORK_TEMP, columns, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            NetworkUsageDetails tempNetworkUsageDetailsObj = new NetworkUsageDetails();
            tempNetworkUsageDetailsObj.setPackageName(cursor.getString(cursor.getColumnIndex(TABLE_COLUMN_PACKAGE_NAME)));
            try {
                tempNetworkUsageDetailsObj.setInitialDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(cursor.getString(cursor.getColumnIndex(TABLE_COLUMN_DATE_TIME))));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            tempNetworkUsageDetailsObj.setTotalRxBytes(cursor.getLong(cursor.getColumnIndex(TABLE_COLUMN_INITIAL_RX_BYTES)));
            tempNetworkUsageDetailsObj.setTotalTxBytes(cursor.getLong(cursor.getColumnIndex(TABLE_COLUMN_INITIAL_TX_BYTES)));
            networkUsageDetailsList.add(tempNetworkUsageDetailsObj);
        }
        cursor.close();
        return networkUsageDetailsList;
    }

    /**
     * Delete the content of the temporary network table and flush the content out to reuse the network
     * temp table for the next time.
     */
    public void emptyTempNetworkUsageDetails() {
        db.delete(TABLE_NETWORK_TEMP, null, null);
    }

    /**
     * This method inserts the calculated network statistics data into the network table of the database
     *
     * @param context     context of the calling
     * @param networkType the network to whom the network statistics data belongs to and is to be inserted
     */
    public void insertNetworkDetails(Context context, String networkType) {
        List<NetworkUsageDetails> networkUsageDetailsList = queryTempNetworkUsageDetails();
        for (NetworkUsageDetails networkUsageDetails : networkUsageDetailsList) {
            String packageName = networkUsageDetails.getPackageName();
            Date date = networkUsageDetails.getInitialDate();
            int uid = 0;
            try {
                ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(packageName, PackageManager.GET_META_DATA);
                uid = applicationInfo.uid;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

            long netRxBytes = TrafficStats.getUidRxBytes(uid) - networkUsageDetails.getTotalRxBytes();
            long netTxBytes = TrafficStats.getUidTxBytes(uid) - networkUsageDetails.getTotalTxBytes();
            if (netRxBytes != 0 && netTxBytes != 0) {
                String query = "INSERT INTO " + TABLE_NETWORK_INFO_TABLE + "(" +
                        TABLE_COLUMN_PACKAGE_NAME + ", "
                        + TABLE_COLUMN_INITIAL_DATE_TIME + ", "
                        + TABLE_COLUMN_NET_RX_BYTES + ", "
                        + TABLE_COLUMN_NET_TX_BYTES + ", "
                        + TABLE_COLUMN_NETWORK_TYPE
                        + ") VALUES(?, ?, ?, ?, ?)"
                        + ";";
                SQLiteStatement sqLiteStatement = db.compileStatement(query);
                sqLiteStatement.bindString(1, packageName);
                sqLiteStatement.bindString(2, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
                sqLiteStatement.bindLong(3, netRxBytes);
                sqLiteStatement.bindLong(4, netTxBytes);
                sqLiteStatement.bindString(5, networkType);
                sqLiteStatement.executeInsert();
                Log.e("inserttoNetworkDetails", packageName + " " + networkType + " " + netRxBytes + " " + netTxBytes);
            }
        }
    }

    /**
     * This method extracts the network details of an application from the database
     * @param networkType the type of the network whose that is to be fetched. There are two options:
     *                    ConnectivityChangeMonitoringIntentService.MOBILE_NETWORK or
     *                    ConnectivityChangeMonitoringIntentService.WIFI_NETWORK
     * @return a List<NetworkUsageDetails>
     */
    public List<NetworkUsageDetails> queryNetworkUsageDetails(String networkType) {
        List<NetworkUsageDetails> networkUsageDetailsList = new ArrayList<>();
        Map<String, NetworkUsageDetails> appMap = new HashMap<>();

        String[] columns = {TABLE_COLUMN_PACKAGE_NAME, TABLE_COLUMN_INITIAL_DATE_TIME, TABLE_COLUMN_NET_RX_BYTES, TABLE_COLUMN_NET_TX_BYTES, TABLE_COLUMN_FINAL_DATE_TIME};
        String whereClause = TABLE_COLUMN_NETWORK_TYPE + "= ?";
        String[] whereArgs = new String[]{networkType};

        Cursor cursor = db.query(TABLE_NETWORK_INFO_TABLE, columns, whereClause, whereArgs, null, null, null, null);
        while (cursor.moveToNext()) {
            NetworkUsageDetails NetworkUsageDetailsObj = new NetworkUsageDetails();
            NetworkUsageDetailsObj.setPackageName(cursor.getString(cursor.getColumnIndex(TABLE_COLUMN_PACKAGE_NAME)));
            try {
                NetworkUsageDetailsObj.setInitialDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(cursor.getString(cursor.getColumnIndex(TABLE_COLUMN_INITIAL_DATE_TIME))));
                NetworkUsageDetailsObj.setFinalDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(cursor.getString(cursor.getColumnIndex(TABLE_COLUMN_FINAL_DATE_TIME))));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            NetworkUsageDetailsObj.setTotalRxBytes(cursor.getLong(cursor.getColumnIndex(TABLE_COLUMN_NET_RX_BYTES)));
            NetworkUsageDetailsObj.setTotalTxBytes(cursor.getLong(cursor.getColumnIndex(TABLE_COLUMN_NET_TX_BYTES)));
            appMap.put(cursor.getString(cursor.getColumnIndex(TABLE_COLUMN_PACKAGE_NAME)), NetworkUsageDetailsObj);
            networkUsageDetailsList.add(NetworkUsageDetailsObj);
        }

        cursor.close();
        return networkUsageDetailsList;
    }

    public void closeDatabaseConnection() {
        db.close();
    }

    /**
     * This class helps to create the database, tables and upgrade them
     */
    class ActivityLoggerSQLiteOpenHelper extends SQLiteOpenHelper {

        public ActivityLoggerSQLiteOpenHelper(Context context) {
            super(context, DBNAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            // CREATE PACKAGE_INFO_TABLE
            String query = "Create table " + TABLE_PACKAGE_INFO + "("
                    + TABLE_COLUMN_ID + " integer primary key autoincrement, "
                    + TABLE_COLUMN_UID + " integer, "
                    + TABLE_COLUMN_PACKAGE_NAME + " varchar(255), "
                    + TABLE_COLUMN_APPLICATION_NAME + " varchar(30), "
                    + TABLE_COLUMN_APPLICATION_TYPE + " varchar(10));";
            String createNetworkTempTableQuery = "CREATE TABLE IF NOT EXISTS " + TABLE_NETWORK_TEMP + " ("
                    + TABLE_COLUMN_PACKAGE_NAME + " varchar(255), "
                    + TABLE_COLUMN_DATE_TIME + " datetime default current_timestamp, "
                    + TABLE_COLUMN_INITIAL_RX_BYTES + " integer, "
                    + TABLE_COLUMN_INITIAL_TX_BYTES + " integer );";
            String createNetworkInfoTableQuery = "CREATE TABLE IF NOT EXISTS " + TABLE_NETWORK_INFO_TABLE + " ("
                    + TABLE_COLUMN_ID + " integer primary key autoincrement, "
                    + TABLE_COLUMN_PACKAGE_NAME + " varchar(255),"
                    + TABLE_COLUMN_INITIAL_DATE_TIME + " datetime, "
                    + TABLE_COLUMN_NET_RX_BYTES + " integer,"
                    + TABLE_COLUMN_NET_TX_BYTES + " integer,"
                    + TABLE_COLUMN_FINAL_DATE_TIME + " datetime default current_timestamp,"
                    + TABLE_COLUMN_NETWORK_TYPE + " varchar(7)"
                    + " );";


            try {
                sqLiteDatabase.execSQL(query);
                sqLiteDatabase.execSQL(createNetworkTempTableQuery);
                sqLiteDatabase.execSQL(createNetworkInfoTableQuery);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("Drop table if exists " + TABLE_PACKAGE_INFO);
            sqLiteDatabase.execSQL("Drop table if exists " + TABLE_NETWORK_TEMP);
            sqLiteDatabase.execSQL("Drop table if exists " + TABLE_NETWORK_INFO_TABLE);
            try {
                onCreate(sqLiteDatabase);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

}


