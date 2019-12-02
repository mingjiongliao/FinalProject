package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class EcarStationResult extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set view
        setContentView(R.layout.activity_ecar_station_result);
        //new bundle to pass the data
        Bundle dataToPass = getIntent().getExtras();
        String title = dataToPass.getString("title");
        String phone = dataToPass.getString("phone");
        double latitude = dataToPass.getDouble("latitude");
        double longitude = dataToPass.getDouble("longitude");
        //set the widgets content
        TextView titleTxt = findViewById(R.id.txt_title);
        titleTxt.setText("Title: " + title);
        TextView phoneTxt = findViewById(R.id.txt_phone1);
        phoneTxt.setText("Phone: "+phone);
        TextView latitudeTxt = findViewById(R.id.txt_Latitude1);
        latitudeTxt.setText("Latitude: "+latitude);
        TextView longtitudeTxt = findViewById(R.id.txt_Longitude1);
        longtitudeTxt.setText("longtitude: " + longitude);
        //set the button click event to pass the data from user to next page
        Button btn = (Button)findViewById(R.id.btn_map1);
        btn.setOnClickListener(c->{
        //pass latitude and longtitude from the user
                    Intent mapPage = new Intent(EcarStationResult.this, googlemap.class);
                    mapPage.putExtra("latitude", latitude);
                    mapPage.putExtra("longtitude", longitude);
                    startActivity(mapPage);
                }
        );
        //set the save button click event to save item to the favorite list
        Button btnSave = (Button)findViewById(R.id.saveButton);

        btnSave.setOnClickListener(c->{

                    FavoriteDBhelper dbOpener = new FavoriteDBhelper(this);
                    //save into database too
                    SQLiteDatabase db = dbOpener.getWritableDatabase();
                    ContentValues newRowValues = new ContentValues();
                    newRowValues.put(FavoriteDBhelper.COL_TITLE, title);
                    newRowValues.put(FavoriteDBhelper.COL_LATITUDE, latitude);
                    newRowValues.put(FavoriteDBhelper.COL_LONGTITUDE, longitude);
                    newRowValues.put(FavoriteDBhelper.COL_PHONE, phone);
                    //insert in the database:
                    long newId = db.insert(FavoriteDBhelper.TABLE_NAME, null, newRowValues);
                    Toast.makeText(this, title+" is save to your favoriate", Toast.LENGTH_SHORT).show();
                }
        );
    }}