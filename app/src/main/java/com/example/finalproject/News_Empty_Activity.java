package com.example.finalproject;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class News_Empty_Activity extends AppCompatActivity {
    private NewsObject articleObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_empty);


        Bundle bundle = getIntent().getExtras();


        NewsFragment dFragment = new NewsFragment();
        dFragment.setArguments(bundle); //pass data to the the fragment
        dFragment.setTablet(false); //tell the Fragment that it's on a phone.

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.news_fragmentLocation, dFragment)
                .addToBackStack("")
                .commit();
    }
}