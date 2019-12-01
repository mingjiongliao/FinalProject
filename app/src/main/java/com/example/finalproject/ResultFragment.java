package com.example.finalproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


public class ResultFragment extends Fragment {

    private boolean isTablet;
    private Bundle dataFromActivity;
    private long id;

    public void setTablet(boolean tablet) { isTablet = tablet; }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        dataFromActivity = getArguments();
        // Inflate the layout for this fragment
        View result =  inflater.inflate(R.layout.result_fragment_detail, container, false);

        //show the title
        if(true){
            TextView title = (TextView)result.findViewById(R.id.txt_title);
            title.setText(dataFromActivity.getString("title"));
            TextView latitude = (TextView)result.findViewById(R.id.txt_Latitude1);
            latitude.setText("test");
            ListResult parent = (ListResult) getActivity();
            parent.getSupportFragmentManager().beginTransaction().commit();
        }


        return result;
    }
}
