package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class EcarStationResult extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecar_station_result);
        Bundle dataToPass = getIntent().getExtras();
        String title = dataToPass.getString("title");
        String phone = dataToPass.getString("phone");
        double latitude = dataToPass.getDouble("latitude");
        double longitude = dataToPass.getDouble("longitude");
        TextView titleTxt = findViewById(R.id.txt_title);
        titleTxt.setText("Title: " + title);
        TextView phoneTxt = findViewById(R.id.txt_phone1);
        phoneTxt.setText("Phone: "+phone);
        TextView latitudeTxt = findViewById(R.id.txt_Latitude1);
        latitudeTxt.setText("Latitude: "+latitude);
        TextView longtitudeTxt = findViewById(R.id.txt_Longitude1);
        longtitudeTxt.setText("longtitude: " + longitude);
        Button btn = (Button)findViewById(R.id.btn_map1);
        btn.setOnClickListener(c->{

                    Intent mapPage = new Intent(EcarStationResult.this, googlemap.class);
                    mapPage.putExtra("latitude", latitude);
                    mapPage.putExtra("longtitude", longitude);
                    startActivity(mapPage);
                }
                );
    }}
