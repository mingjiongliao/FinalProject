/**
 * File name: RecipeSearchActivity.java
 * Author: Chunyuan Luo, ID# 040926918
 * Course: 19F_CST2335_010_020 Mobile Graphic interface Prog
 * Assignment: Final Project
 * Date: 2019-11-16
 * Professor: Adewole Adewumi
 * Purpose:  android activity applications design
 */
package com.example.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Arrays;

/**
 * An database class to handle all request to database
 * @author chunyuan luo
 */
public class RecipeDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "MessagesDB";
    private static final String DB_TABLE = "Messages_Table";
    public static final int VERSION_NUM = 1;




    //columns
    private static final String COL_MESSAGEID = "MessageID";
    private static final String COL_publisher = "publisher";
    private static final String COL_f2f_url = "f2f_url";
    private static final String COL_title = "title";
    private static final String COL_source_url = "source_url";
    private static final String COL_recipe_id = "recipe_id";
    private static final String COL_image_url = "image_url";
    private static final String COL_social_rank = "social_rank";
    private static final String COL_publisher_url = "publisher_url";

    SQLiteDatabase db = this.getReadableDatabase();
    //queries
    private static final String CREATE_TABLE = "CREATE TABLE "+DB_TABLE+" ("+COL_MESSAGEID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
            +COL_publisher+" TEXT, "+
            COL_f2f_url+" TEXT, "+
            COL_title+" TEXT, "+
            COL_source_url+" TEXT, "+
            COL_recipe_id+" TEXT, "+
            COL_image_url+" TEXT, "+
            COL_social_rank+" TEXT, "+
            COL_publisher_url+" TEXT);";

    /**
     * constructor of RecipeDatabaseHelper class
     * @param context
     */
    public RecipeDatabaseHelper(Context context) {
        super(context, DB_NAME, null, 2);
    }

    /**
     * Create table
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    /**
     * handle upgrade if it happens
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
        onCreate(db);
    }

    /**
     * insert a item into database based on input parameters as column input
     * @param publisher
     * @param f2f_url
     * @param title
     * @param source_url
     * @param recipe_id
     * @param image_url
     * @param social_rank
     * @param publisher_url
     * @return result if the insert is sucessfull or not, /if result = -1 data doesn't insert
     */

    public long insertData(String publisher, String f2f_url, String title, String source_url, String recipe_id, String image_url, String social_rank, String publisher_url)
    {
        SQLiteDatabase db = this.getWritableDatabase();//open a database as write only
        ContentValues contentValues = new ContentValues();
        //contentValues.put(COL_MESSAGE, message);

        contentValues.put(COL_publisher, publisher);
        contentValues.put(COL_f2f_url, f2f_url);
        contentValues.put(COL_title, title);
        contentValues.put(COL_source_url, source_url);
        contentValues.put(COL_recipe_id, recipe_id);
        contentValues.put(COL_image_url, image_url);
        contentValues.put(COL_social_rank, social_rank);
        contentValues.put(COL_publisher_url, publisher_url);


        long result = db.insert(DB_TABLE, null, contentValues);

        return result ; //if result = -1 data doesn't insert
    }

    /**
     * print all the data on the logcat window
     * @return
     */
    //view data
    public Cursor viewAllData(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * from "+DB_TABLE;
        Cursor cursor = db.rawQuery(query, null);
        //this.PrintCursor(cursor);
        Log.v("Cursor Object", DatabaseUtils.dumpCursorToString(cursor));
        return cursor;
    }

    //view data
    /*
    public void PrintCursor(){
        SQLiteDatabase dbLite =  this.getReadableDatabase();

        Cursor cursor = dbLite.rawQuery("SELECT * FROM "+DB_TABLE,null);
        //int colIndex = cursor.getColumnIndex("message");
        //for(int i = 0; i < cursor.getCount(); i++){
        //    String m = cursor.getString(colIndex);
        //    System.out.println("Message: " + m);
        //    cursor.moveToNext();
        //}

        if (cursor.getCount() != 0){
            //while (c.moveToNext()){
            //Log.v("Cursor Object", DatabaseUtils.dumpCursorToString(c));

            //}

            Log.i("DB ver:", String.valueOf(db.getVersion()));
            Log.v("Nuber of Columns:", String.valueOf(cursor.getColumnCount()));
            Log.v("Col Name:", Arrays.toString(cursor.getColumnNames()));
            Log.v("number of results:", String.valueOf(cursor.getCount()));
            Log.v("Cursor Object", DatabaseUtils.dumpCursorToString(cursor));
            System.out.println("Col Name:"+ Arrays.toString(cursor.getColumnNames()));
            //int colIndex = cursor.getColumnIndex("message");
            for(int i = 0; i < cursor.getCount(); i++){
                int j = cursor.getColumnIndex(COL_MESSAGE);
                //String x1 = cursor.getString(j);
                //String x2 = cursor.getString(cursor.getColumnIndex(COL_ISSEND) );
                //String x3 = cursor.getString(cursor.getColumnIndex(COL_MESSAGEID) );

                //System.out.println("Message: " + x1);
                //System.out.println("IsSend: " + x2);
                //System.out.println("MessageID: " + x1);

                //Log.v("Message:", x1);
                //Log.v("IsSend:", x2);
                //Log.v("MessageID:", x3);

                cursor.moveToNext();
            }
            Log.v("Cursor Object", DatabaseUtils.dumpCursorToString(cursor));
        }
    }
*/

    /**
     * Delete a entry from database based on input parameter of recipe_id
     * @param Recipe_id
     * @return how many records been deleted
     */
    // method to delete a Record
    public int deleteEntry(String Recipe_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String where="recipe_id=?";
        int numberOFEntriesDeleted= db.delete(DB_TABLE, where, new String[]{Recipe_id});
        return numberOFEntriesDeleted;
    }

}