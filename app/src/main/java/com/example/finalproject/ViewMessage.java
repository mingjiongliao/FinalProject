package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

public class ViewMessage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_message);


        Intent previousPage = getIntent();
//        int flag1=previousPage.getIntExtra("Flag1");
//        int flag2=previousPage.getIntExtra("Flag2");
        String message = previousPage.getStringExtra("Message");
        String id = previousPage.getStringExtra("Id");

        EditText result = (EditText)findViewById(R.id.result);
        ImageView imageView1=(ImageView)findViewById(R.id.imageView1);
        ImageView imageView2=(ImageView)findViewById(R.id.imageView2);

        result.setText(message);
//        imageView1.setImageResource(flag1);

    }
}
