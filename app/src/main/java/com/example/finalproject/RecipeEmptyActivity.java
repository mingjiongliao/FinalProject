package com.example.finalproject;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class RecipeEmptyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.empty_activity);

        Bundle dataToPass = getIntent().getExtras(); //get the data that was passed from FragmentExample

        //This is copied directly from FragmentExample.java lines 47-54
        RecipeDetailFragment dFragment = new RecipeDetailFragment();
        dFragment.setArguments( dataToPass ); //pass data to the the fragment
        dFragment.setTablet(false); //tell the Fragment that it's on a phone.
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragmentLocation, dFragment)
                .addToBackStack("AnyName")
                .commit();
    }
}