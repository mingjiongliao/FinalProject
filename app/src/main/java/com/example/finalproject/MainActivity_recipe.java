/**
 * File name: MainActivity_recipe.java
 * Author: Chunyuan Luo, ID# 040926918
 * Course: 19F_CST2335_010_020 Mobile Graphic interface Prog
 * Assignment: Final Project
 * Date: 2019-11-16
 * Professor: Adewole Adewumi
 * Purpose:  android activity applications design
 */
package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

/**
 * Main activity to display and request
 * @author chunyuan luo
 */
public class MainActivity_recipe extends AppCompatActivity {

    /**
     *
     *  main entrance that load activity layout
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_activity_main);

        Button milestone1Button = (Button)findViewById(R.id.searchrecipybutton);
        milestone1Button.setOnClickListener ( Click ->
        {

//           AlertDialog.Builder builder = new AlertDialog.Builder(this);

            //This is the builder pattern, just call many functions on the same object:
 //           AlertDialog recipe_dialog = builder.setTitle("I’m ready for milestone 1")
 //                   .setMessage("Please give bonus marks")
 //                   .setPositiveButton("Yes", (d,w) -> {  /* nothing */}

 //                   )
                    //If you click the "Cancel" button:
  //                  .setNegativeButton("Cancel", (d,w) -> {  /* nothing */})
  //                  .create();

            //then show the recipe_dialog
  //          recipe_dialog.show();

            //show a notification: first parameter is any view on screen. second parameter is the text. Third parameter is the length (SHORT/LONG)
            Snackbar.make(milestone1Button, "I'm a snackbar", Snackbar.LENGTH_LONG).show();

            //show a notice window to say how many were updated.
            Toast.makeText(this, "I'm a toast", Toast.LENGTH_LONG).show();

        });



    }

}
