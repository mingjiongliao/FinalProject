package com.example.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class FavoriteDBhelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "MyDatabaseFile";
    public static final int VERSION_NUM = 1;
    public static final String TABLE_NAME = "Stations";
    public static final String COL_ID = "_id";
    public static final String COL_TITLE = "_title";
    public static final String COL_LATITUDE = "_latitude";
    public static final String COL_LONGTITUDE = "_longtitude";
    public static final String COL_PHONE = "_phone";

    public FavoriteDBhelper(Context ctx) {
        //The factory parameter should be null, unless you know a lot about Database Memory management
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    public void onCreate(SQLiteDatabase db) {
        //Make sure you put spaces between SQL statements and Java strings:
        db.execSQL("CREATE TABLE " + TABLE_NAME + "( "
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_TITLE + " TEXT," + COL_LATITUDE + " REAL," + COL_LONGTITUDE + " REAL," + COL_PHONE + " TEXT)");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("Database upgrade", "Old version:" + oldVersion + " newVersion:" + newVersion);

        //Delete the old table:
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        //Create a new table:
        onCreate(db);
    }

    public long insertData(long result) {

        return result;
    }

    //insert data
    public long insertData(String title, double latitude, double longtitude, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_TITLE, title);
        contentValues.put(COL_LATITUDE, latitude);
        contentValues.put(COL_LONGTITUDE, longtitude);
        contentValues.put(COL_PHONE, phone);

        long result = db.insert(TABLE_NAME, null, contentValues);

        return result; //if result = -1 data doesn't insert
    }

    // method to delete a Record
    public int deleteEntry(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        String where = "_id=?";
        int numberOFEntriesDeleted = db.delete(TABLE_NAME, where, new String[]{Integer.toString(id)});
        return numberOFEntriesDeleted;
    }

    //view data
    public Cursor viewData() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * from " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        Log.v("Cursor Object", DatabaseUtils.dumpCursorToString(cursor));
        return cursor;
    }
}
