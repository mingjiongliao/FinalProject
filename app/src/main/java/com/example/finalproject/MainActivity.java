package com.example.finalproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button milestone1Button = (Button)findViewById(R.id.searchrecipybutton);
        milestone1Button.setOnClickListener ( Click ->
        {

//           AlertDialog.Builder builder = new AlertDialog.Builder(this);

            //This is the builder pattern, just call many functions on the same object:
 //           AlertDialog dialog = builder.setTitle("I’m ready for milestone 1")
 //                   .setMessage("Please give bonus marks")
 //                   .setPositiveButton("Yes", (d,w) -> {  /* nothing */}

 //                   )
                    //If you click the "Cancel" button:
  //                  .setNegativeButton("Cancel", (d,w) -> {  /* nothing */})
  //                  .create();

            //then show the dialog
  //          dialog.show();

            //show a notification: first parameter is any view on screen. second parameter is the text. Third parameter is the length (SHORT/LONG)
            Snackbar.make(milestone1Button, "I'm a snackbar", Snackbar.LENGTH_LONG).show();

            //show a notice window to say how many were updated.
            Toast.makeText(this, "I'm a toast", Toast.LENGTH_LONG).show();

        });



    }

}
