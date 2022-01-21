package com.gmail.mateendev3.androiddbcrudops.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    //creating members
    private static final String DB_NAME = "Student.db";
    private static final int VERSION = 1;

    //public constructor
    public DBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //creating table in db
        final String CREATE_TABLE_QUERY =
                "create table Students (ID INTEGER Primary Key AutoIncrement, ROLL_NO TEXT, NAME TEXT)";
        db.execSQL(CREATE_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    /**
     * method to add record to db
     *
     * @param rollNo rollNo to add
     * @param name   Name to add
     * @return true if data added successfully else flase
     */
    public boolean insertData(String rollNo, String name) {
        //getting instance of db
        SQLiteDatabase database = getWritableDatabase();
        //ContentValues obj to store values and put value into db
        ContentValues values = new ContentValues();
        values.put("ROLL_NO", rollNo);
        values.put("NAME", name);
        //local variable to store value did our values are stored in the db or not
        long areInserted = database.insert("Students", null, values);
        return areInserted != -1;
    }

    /**
     * method to get data from the db
     *
     * @return Cursor object with all db data
     */
    public Cursor getDataFromDB() {
        //getting instance of db
        SQLiteDatabase db = getReadableDatabase();
        //query to get data form the db
        final String GET_DATA_FROM_TABLE_QUERY =
                "select * from Students";
        //creating Cursor object from given query with appropriate values and returning it
        return db.rawQuery(GET_DATA_FROM_TABLE_QUERY, null);
    }

    public boolean deleteRecord(String rollNo) {
        //getting instance of db
        SQLiteDatabase db = getWritableDatabase();
        //getting Cursor object with values from db using given query
        Cursor cursor = db.rawQuery("select * from Students where ROLL_NO = ?", new String[]{rollNo});
        if (cursor.getCount() != 0) {
            //local variable to store value that the record has been deleted successfully or not
            int isDeleted = db.delete("Students", "ROLL_NO = ?", new String[]{rollNo});
            return isDeleted == 1;
        } else
            return false;
    }

    public boolean updateRecord(String rollNo, String name) {
        //getting instance of db
        SQLiteDatabase db = getWritableDatabase();
        //getting Cursor object with values from db using given query
        Cursor cursor = db.rawQuery("select * from Students where ROLL_NO = ?", new String[]{rollNo});
        if (cursor.getCount() > 0) {
            //ContentValues object
            ContentValues values = new ContentValues();
            values.put("name", name);
            //local variable to store value that the record has been updated successfully or not
            int isUpdated = db.update("Students", values, "ROLL_NO = ?", new String[]{rollNo});
            return isUpdated == 1;

        } else
            return false;
    }
}
