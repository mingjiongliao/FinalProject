package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Empty_station extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_station);

        Bundle dataToPass = getIntent().getExtras(); //get the data that was passed from FragmentExample

    }
}
