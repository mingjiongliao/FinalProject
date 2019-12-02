package com.example.finalproject;



import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * this is class for database
 */
public class CurrencyDatabaseOpenHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "CurrencyDatabase";
    public static final int VERSION_NUM = 1;
    public static final String TABLE_NAME = "currencies";
    public static final String COL_ID = "currency_id";
    public static final String COL_MESSAGE = "result";
    public static final String COL_FLAG1 = "Flag1";
    public static final String COL_FLAG2 = "Flag2";


    /**
     * create a database
     * @param ctx activity
     */
    public CurrencyDatabaseOpenHelper(Activity ctx){
        //The factory parameter should be null, unless you know a lot about Database Memory management
        super(ctx, DATABASE_NAME, null, VERSION_NUM );
    }

    /**
     * create a table
     * @param db
     */
    public void onCreate(SQLiteDatabase db)
    {
        //Make sure you put spaces between SQL statements and Java strings:
        Log.d("haha", "onCreate:haha");
        db.execSQL("CREATE TABLE " + TABLE_NAME + "( "
                + COL_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_MESSAGE+" TEXT," + COL_FLAG1+" TEXT,"+ COL_FLAG2+" TEXT)");
//notice space and coma in SQL
        Log.d("haha", "onCreate:");
    }

    /**
     * update a table
     * @param db database
     * @param oldVersion old version
     * @param newVersion new version
     */
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        Log.i("Database upgrade", "Old version:" + oldVersion + " newVersion:"+newVersion);

        //Delete the old table:
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        //Create a new table:
        onCreate(db);
    }

    /**
     * down grade table
     * @param db db
     * @param oldVersion old version
     * @param newVersion new version
     */
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        Log.i("Database downgrade", "Old version:" + oldVersion + " newVersion:"+newVersion);

        //Delete the old table:
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        //Create a new table:
        onCreate(db);
    }
}