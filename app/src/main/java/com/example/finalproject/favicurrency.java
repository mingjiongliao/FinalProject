package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class favicurrency extends AppCompatActivity {
    ArrayList<CountryItem> objects = new ArrayList<>( );
    SQLiteDatabase db;
    MyOwnAdapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favicurrency);

        ListView theList = (ListView)findViewById(R.id.the_list);
        CurrencyDatabaseOpenHelper dbOpener = new CurrencyDatabaseOpenHelper(this);
        db = dbOpener.getWritableDatabase();
//        dbOpener.onUpgrade(db,1,2);

        //query all the results from the database:
        String [] columns = {CurrencyDatabaseOpenHelper.COL_ID, CurrencyDatabaseOpenHelper.COL_MESSAGE, CurrencyDatabaseOpenHelper.COL_FLAG1, CurrencyDatabaseOpenHelper.COL_FLAG2};
        Log.d("dddd", "onCreate: "+columns[0].toString()+columns[2].toString());
        Cursor results = db.query(false, CurrencyDatabaseOpenHelper.TABLE_NAME, columns, null, null, null, null, null, null);
        //find the column indices:

        int mesColIndex = results.getColumnIndex(CurrencyDatabaseOpenHelper.COL_MESSAGE);
        int idColIndex = results.getColumnIndex(CurrencyDatabaseOpenHelper.COL_ID);
        int flag1ColIndex=results.getColumnIndex(CurrencyDatabaseOpenHelper.COL_FLAG1);
        int flag2ColIndex=results.getColumnIndex(CurrencyDatabaseOpenHelper.COL_FLAG2);

        //iterate over the results, return true if there is a next item:
        while(results.moveToNext())
        {
            int flag1=results.getInt(flag1ColIndex);
            int flag2=results.getInt(flag2ColIndex);
            String message = results.getString(mesColIndex);
            long id = results.getLong(idColIndex);
            Log.d("shujukuID", "shu: "+id);
            //add the new Contact to the array list:
            objects.add(new CountryItem(message,flag1,flag2,id));
        }

        myAdapter = new MyOwnAdapter();
        theList.setAdapter(myAdapter);

    }
    private class MyOwnAdapter extends BaseAdapter {

        public int getCount() {  return objects.size();  } //This function tells how many objects to show

        public CountryItem getItem(int position) { return objects.get(position);  }  //This returns the string at position p

        public long getItemId(int p) { return p; } //This returns the database id of the item at position p

        public View getView(int p, View recycled, ViewGroup parent)
        {


//            LayoutInflater inflater = getLayoutInflater();
//            View thisRow = null;
//            thisRow = inflater.inflate(R.layout.row_layout, null);
//            TextView itemField = thisRow.findViewById(R.id.message);
//            itemField.setText(getItem(p).toString());

            LayoutInflater inflater = getLayoutInflater();

            View newView = inflater.inflate(R.layout.rowqing, parent, false );

            CountryItem thisRow = getItem(p);

            ImageView flag1 = (ImageView) newView.findViewById(R.id.imageView1);
            TextView message = (TextView)newView.findViewById(R.id.message);
            ImageView flag2=(ImageView) newView.findViewById(R.id.imageView2);

//            if(thisRow!=null){
            flag1.setImageResource(thisRow.getmFlagImage());
            message.setText(thisRow.getmCountryName());
            flag2.setImageResource(thisRow.getoFlagImage());
//            }


            return newView;
        }
    }
}
