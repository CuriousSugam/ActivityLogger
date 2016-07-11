package com.lftechnology.activitylogger.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.lftechnology.activitylogger.model.AppDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sugam Shakya
 * Created by Sugam on 7/5/2016.
 * This class provides the interface to insert, update, delete and query the app details in the application database
 */
public class SQLiteAccessLayer {

    private static final String DBNAME = "ActivityLoggerDatabase";
    private static final String TABLE_PACKAGE_INFO = "package_info_table";
    private static final int DATABASE_VERSION = 3;

    private static final String TABLE_COLUMN_ID = "_id";
    private static final String TABLE_COLUMN_UID = "uid";
    private static final String TABLE_COLUMN_PACKAGE_NAME = "package_name";
    private static final String TABLE_COLUMN_APPLICATION_NAME = "application_name";


    private ActivityLoggerSQLiteOpenHelper dbHelper;
    private SQLiteDatabase db;
    private AppDetails appDetails;

    /**
     *
     * @param context Context of the calling activity
     * @param appDetails Object of AppDetails which holds some data about an application
     */
    public SQLiteAccessLayer(Context context, AppDetails appDetails){
        dbHelper = new ActivityLoggerSQLiteOpenHelper(context);
        this.db = dbHelper.getWritableDatabase();
        this.appDetails = appDetails;
    }

    /**
     *
     * @param context Context of the calling activity
     */
    public SQLiteAccessLayer(Context context){
        dbHelper = new ActivityLoggerSQLiteOpenHelper(context);
        this.db = dbHelper.getWritableDatabase();
    }

    /**
     * This method will insert the data hold by the AppDetails object passed through the constructor
     * into the database
     *
     * @return the row ID of the newly inserted row, or -1 if an error occurred
     */
    public long insertIntoAppDetails(){
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_COLUMN_UID, this.appDetails.getUid());
        contentValues.put(TABLE_COLUMN_PACKAGE_NAME, this.appDetails.getPackageName() );
        contentValues.put(TABLE_COLUMN_APPLICATION_NAME, this.appDetails.getApplicationName());

        // insert the new row, returning the primary key of the row inserted
        long newInsertedRowId;
        newInsertedRowId = db.insert(TABLE_PACKAGE_INFO,null, contentValues);
        // TODO remove this code
//        Log.e("Database", "Inserted:=> "+this.appDetails.getApplicationName());

        return newInsertedRowId;
    }

    /**
     *  This method extracts the application information from the database
     * @return a List<AppDetails>
     */
    public List<AppDetails> queryAppDetails(){
        List<AppDetails> appDetailsList = new ArrayList<>();
        String[] columns = {TABLE_COLUMN_UID, TABLE_COLUMN_PACKAGE_NAME, TABLE_COLUMN_APPLICATION_NAME};
        Cursor cursor = db.query(TABLE_PACKAGE_INFO, columns, null, null, null, null, null, null);
        while(cursor.moveToNext()){
            AppDetails tempAppDetailsObj = new AppDetails();
            tempAppDetailsObj.setUid(cursor.getInt(cursor.getColumnIndex(TABLE_COLUMN_UID)));
            tempAppDetailsObj.setPackageName(cursor.getString(cursor.getColumnIndex(TABLE_COLUMN_PACKAGE_NAME)));
            tempAppDetailsObj.setApplicationName(cursor.getString(cursor.getColumnIndex(TABLE_COLUMN_APPLICATION_NAME)));
            appDetailsList.add(tempAppDetailsObj);
        }
        cursor.close();
        return appDetailsList;
    }

    /**
     * 
     *
     * @param whereClause the WHERE clause to apply when deleting. Passing null will delete all rows.
     * @param whereArgs  '?'s included in the where clause will be replaced by the values from whereArgs.
     *                   
     * deleteAnAppDetail("id=? and name=?",new String[]{"1","jack"});
     *
     * @return the number of rows affected if a whereClause is passed in, 0 otherwise.
     * To remove all rows and get a count pass "1" as the whereClause.
     */
    public int deleteAnAppDetail(String whereClause, String[] whereArgs ){
         return db.delete(TABLE_PACKAGE_INFO, whereClause, whereArgs);
    }


    /**
     *
     * @param whereClause the WHERE clause to apply when updating. Passing null will update all rows.
     * @param whereArgs '?'s included in the where clause will be replaced by the values from whereArgs.
     * @return  the number of rows affected
     */
    public int updateAppDetail(String whereClause, String[] whereArgs){
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_COLUMN_UID, this.appDetails.getUid());
        contentValues.put(TABLE_COLUMN_PACKAGE_NAME, this.appDetails.getPackageName() );
        contentValues.put(TABLE_COLUMN_APPLICATION_NAME, this.appDetails.getApplicationName());
        return db.update(TABLE_PACKAGE_INFO, contentValues, whereClause, whereArgs);
    }

    /**
     * This method checks if the app details have been added to the database or not
     *
     * @return true if the database is empty and no appdetails has been added to it, returns false if the database is not empty
     */
    public boolean isDatabaseEmpty(){
        return queryAppDetails().isEmpty();
    }




    class ActivityLoggerSQLiteOpenHelper extends SQLiteOpenHelper {

        public ActivityLoggerSQLiteOpenHelper(Context context) {
            super(context, DBNAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            // CREATE PACKAGE_INFO_TABLE
            String query = "Create table "+TABLE_PACKAGE_INFO+"("
                    +TABLE_COLUMN_ID+" integer primary key autoincrement, "
                    +TABLE_COLUMN_UID+" integer, "
                    +TABLE_COLUMN_PACKAGE_NAME+" varchar(255), "
                    +TABLE_COLUMN_APPLICATION_NAME+" varchar(30));";
            try{
                sqLiteDatabase.execSQL(query);
            }catch (SQLException e){
                e.printStackTrace();
            }
            //TODO: remove it later
            Log.e("Database", "Database onCreate method executed");
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("Drop table if exists "+TABLE_PACKAGE_INFO);
            try{
                onCreate(sqLiteDatabase);
            }catch (SQLException e){
                e.printStackTrace();
            }
            //TODO: remove it later
            Log.e("Database", "Database onUpgrade method executed");
        }

    }

}


