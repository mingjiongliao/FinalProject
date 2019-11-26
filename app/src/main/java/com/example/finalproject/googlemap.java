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
        String latitude = dataToPass.getStringExtra("latitude");
        String longitude = dataToPass.getStringExtra("longitude");

        String geo = "geo:37.7749,-122.4194";
     //   String geo = "geo:" + latitude + "," + longitude;
        Uri gmmIntentUri = Uri.parse(geo);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
        Toast.makeText(this, R.string.s2, Toast.LENGTH_SHORT).show();
    }
}
