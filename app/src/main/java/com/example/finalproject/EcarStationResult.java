package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class EcarStationResult extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecar_station_result);
        Button btnMap = (Button) findViewById(R.id.btn_map1);
        if (btnMap != null) {
            btnMap.setOnClickListener(clk -> {
                Intent ResultPage = new Intent(EcarStationResult.this, googlemap.class);
                startActivity(ResultPage);
            });
        }

    }}
