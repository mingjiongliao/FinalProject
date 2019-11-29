package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

public class googlemap extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_googlemap);
        Intent dataToPass = getIntent();
        double latitude = dataToPass.getDoubleExtra("latitude",0);
        double longitude = dataToPass.getDoubleExtra("longitude",0);

/**
 * use the code from google android docs, pass the data from input
 */
        String geo = "geo:" + latitude + "," + longitude;
        Uri gmmIntentUri = Uri.parse(geo);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
        Toast.makeText(this, "Location "+ latitude + "," + longitude, Toast.LENGTH_SHORT).show();
    }
}
