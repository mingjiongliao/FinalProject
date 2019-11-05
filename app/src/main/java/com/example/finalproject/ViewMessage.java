package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

public class ViewMessage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_message);

        //todo could not get data from previous page
        Intent previousPage = getIntent();
        String message = previousPage.getStringExtra("message");
        String id = previousPage.getStringExtra("Id");

        EditText result = (EditText)findViewById(R.id.result);
        result.setText(message);
    }
}
