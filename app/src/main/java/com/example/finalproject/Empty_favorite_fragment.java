package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Empty_favorite_fragment extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.empty_favorite_fragment);

        Bundle dataToPass = getIntent().getExtras(); //get the data that was passed from FragmentExample

        //This is copied directly from FragmentExample.java lines 47-54
        FavoriteFragment fFragment = new FavoriteFragment();
        fFragment.setArguments( dataToPass ); //pass data to the the fragment
        fFragment.setTablet(false); //tell the Fragment that it's on a phone.
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.empty_favorite_fragmentXML, fFragment)
                .addToBackStack("AnyName")
                .commit();
    }
}
