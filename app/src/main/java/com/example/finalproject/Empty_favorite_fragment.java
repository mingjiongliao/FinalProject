/**
 * File name: Empty_favorite_fragment.java
 * Author: Chunyuan Luo, ID# 040926918
 * Course: 19F_CST2335_010_020 Mobile Graphic interface Prog
 * Assignment: Final Project
 * Date: 2019-11-16
 * Professor: Adewole Adewumi
 * Purpose:  an empty activity for phone, not for tablet
 */
package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Empty_favorite_fragment extends AppCompatActivity {


    /**
     * call fragment to display the layout
     * @param savedInstanceState
     */
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
                .add(R.id.favoriteFragment, fFragment)
                .addToBackStack("AnyName")
                .commit();
    }
}
