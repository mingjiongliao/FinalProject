package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.Array;
import java.util.ArrayList;

public class GotoFavorite extends AppCompatActivity {

    ArrayList<Station> stationList = new ArrayList<>();
    MyOwnAdapter myAdapter;
    int positionClicked = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goto_favorite);
        //get a database:
        FavoriteDBhelper dbOpener = new FavoriteDBhelper(this);
        SQLiteDatabase db = dbOpener.getWritableDatabase();

        //query all the results from the database:
        String[] columns = {FavoriteDBhelper.COL_ID, FavoriteDBhelper.COL_TITLE};
        Cursor results = db.query(false, FavoriteDBhelper.TABLE_NAME, columns, null, null, null, null, null, null);

        //find the column indices:
        int titleIndex = results.getColumnIndex(FavoriteDBhelper.COL_TITLE);
        int idColIndex = results.getColumnIndex(FavoriteDBhelper.COL_ID);
        ListView theList = (ListView) findViewById(R.id.the_list);
        //iterate over the results, return true if there is a next item:
        while (results.moveToNext()) {
            String title = results.getString(titleIndex);
            long id = results.getLong(idColIndex);

            //add the new Contact to the array list:
            stationList.add(new Station(title, id));
        }

        //create an adapter object and send it to the listVIew
        Station [] arr = new Station[stationList.size()];
        stationList.toArray(arr);

        theList = findViewById(R.id.listview);
        ArrayAdapter<Station> adapter = new ArrayAdapter<Station>(this,
                android.R.layout.simple_expandable_list_item_1, arr);
        theList.setAdapter(adapter);

        //This listens for items being clicked in the list view
        theList.setOnItemClickListener((parent, view, position, id) -> {
            Log.e("you clicked on :", "item " + position);
            //save the position in case this object gets deleted or updated
            positionClicked = position;

            //When you click on a row, open selected contact on a new page (ViewContact)
            Station chosenOne = stationList.get(position);
            Intent nextPage = new Intent(GotoFavorite.this, ViewStation.class);
            nextPage.putExtra("Title", chosenOne.getTitle());
            nextPage.putExtra("Id", id);
            startActivity(nextPage);
        });


    }

    //This class needs 4 functions to work properly:
    protected class MyOwnAdapter extends BaseAdapter
    {
        @Override
        public int getCount() {
            return stationList.size();
        }

        public Station getItem(int position){
            return stationList.get(position);
        }

        public View getView(int position, View old, ViewGroup parent)
        {
            LayoutInflater inflater = getLayoutInflater();

            View newView = inflater.inflate(R.layout.activity_goto_favorite, parent, false );


            return newView;
        }

        public long getItemId(int position)
        {
            return getItem(position).getId();
        }
    }

}
