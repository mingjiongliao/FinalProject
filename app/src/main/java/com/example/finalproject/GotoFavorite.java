package com.example.finalproject;

import androidx.annotation.Nullable;
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
    int positionClicked = 0;
    ListView theList;
    FavoriteDBhelper dbOpener;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goto_favorite);
        //get a database:
        dbOpener = new FavoriteDBhelper(this);
        theList = (ListView)findViewById(R.id.favoriteListview);
        boolean isTablet = findViewById(R.id.favoriteFragment) != null;
        //query all the results from the database:
        viewData();
        //This listens for items being clicked in the list view
        theList.setOnItemClickListener((parent, view, position, id) -> {
            Bundle dataToPass = new Bundle();
            dataToPass.putLong("db_id", stationList.get(position).getId());
            dataToPass.putString("title", stationList.get(position).getTitle());
            dataToPass.putDouble("latitude", stationList.get(position).getLatitude());
            dataToPass.putDouble("longtitude", stationList.get(position).getLongtitude());
            dataToPass.putString("phone", stationList.get(position).getPhone());

            if (isTablet){
                FavoriteFragment fFragment = new FavoriteFragment(); //add a DetailFragment
                fFragment.setArguments( dataToPass ); //pass it a bundle for information
                fFragment.setTablet(true);  //tell the fragment if it's running on a tablet or not
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.favoriteFragment, fFragment) //Add the fragment in FrameLayout
                        .addToBackStack("AnyName") //make the back button undo the transaction
                        .commit(); //actually load the fragment.
            }else { //if on a phone, start a new page/intent
                Intent emptyActivity = new Intent(this, Empty_favorite_fragment.class);
                emptyActivity.putExtras(dataToPass);
                startActivityForResult(emptyActivity, 345);
            }
        });


    }

    private void viewData() {
        Cursor cursor = dbOpener.viewData();

        if (cursor.getCount() != 0){
            while (cursor.moveToNext()){
                //use cursor to locate the data in the database table
                Station model = new Station( cursor.getString(1),cursor.getLong(0));
                model.setLatitude(cursor.getDouble(2));
                model.setLongtitude(cursor.getDouble(3));
                model.setPhone(cursor.getString(4));
                //populate in the listview
                stationList.add(model);
                StationAdapter adt = new StationAdapter(stationList, getApplicationContext());
                theList.setAdapter(adt);

            }
        }
    }

    //This function only gets called on the phone. The tablet never goes to a new activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       super.onActivityResult(requestCode, resultCode,data);
        if(requestCode == 345)
        {
            if(resultCode == RESULT_OK) //if you hit the delete button instead of back button
            {
                long id = data.getLongExtra("db_id", 0);
                deleteStationId((int)id);

            }
        }
    }

    public void deleteStationId(int id)
    {
// dete by the id
        dbOpener.deleteEntry(id);
        stationList.clear();
        viewData();
    }


}
