package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class ElectricCarChargingStationFinder extends AppCompatActivity {

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electric_car_charging_station_finder);
        /**
         * find the two input text editor:
         *  Latitude editor & //longtitude editor
         */
        EditText editTextLatitude = findViewById(R.id.editTextLatitude);
        EditText editTextLongitude = findViewById(R.id.editTextLongitude);
        /**
         * use a Lambda function to set a click listener
         */
        Button btnFind = (Button)findViewById(R.id.btnFind);
        if(btnFind != null){
            btnFind.setOnClickListener(clk -> {
                /**
                 * new intent to go to the next page
                 */
                Intent ResultPage = new Intent(ElectricCarChargingStationFinder.this, ListResult.class);
                /**
                 * transfer the input data--- Latitude & Longtitude to the next page(activity)
                 */
                ResultPage.putExtra("typedLatitude", editTextLongitude.getText().toString());
                ResultPage.putExtra("typedLongtitude", editTextLatitude.getText().toString());
                //go to the next activity
                startActivityForResult(ResultPage, 30);
            });
        }
        //set the sanckbar content to display details in it
        Snackbar.make(btnFind, "Input a latitude & longitude, Get your list of location", Snackbar.LENGTH_LONG).show();
        //show a welcome notice.
        Toast.makeText(this, "Welcome to Electric Car Charging Station Finder", Toast.LENGTH_LONG).show();
    }
}
