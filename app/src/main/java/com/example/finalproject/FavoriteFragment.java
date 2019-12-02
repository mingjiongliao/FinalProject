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

public class FavoriteFragment extends Fragment {
    private boolean isTablet;
    private Bundle dataFromActivity;
    private long db_id;
    private int id;

    public void setTablet(boolean tablet) { isTablet = tablet; }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        dataFromActivity = getArguments();
        db_id = dataFromActivity.getLong("db_id" );
        // Inflate the layout for this fragment
        View result =  inflater.inflate(R.layout.favorite_fragment, container, false);

        //show the title
        TextView favorite_fragment_title = (TextView)result.findViewById(R.id.favorite_fragment_title);
        favorite_fragment_title.setText("Title: "+dataFromActivity.getString("title"));

        //show the latitude/longtitude/phone:
        TextView  favorite_fragment_latitude = (TextView)result.findViewById(R.id.favorite_fragment_latitude);
        favorite_fragment_latitude.setText("Latitude: " + dataFromActivity.getDouble("latitude"));

        TextView favorite_fragment_longtitude = (TextView)result.findViewById(R.id.favorite_fragment_longtitude);
        favorite_fragment_longtitude.setText("Longtitude: "+ dataFromActivity.getDouble("longtitude"));

        TextView favorite_fragment_phone = (TextView)result.findViewById(R.id.favorite_fragment_phone);
        favorite_fragment_phone.setText("Contact number: "+ dataFromActivity.getString("phone"));

        // get the delete button, and add a click listener:
        Button deleteButton = (Button)result.findViewById(R.id.favorite_fragment_deleteButton);
        deleteButton.setOnClickListener( clk -> {


            if(isTablet) { //both the list and details are on the screen:
                GotoFavorite parent = (GotoFavorite) getActivity();
                parent.deleteStationId((int)db_id); //this deletes the item and updates the list
                //now remove the fragment since you deleted it from the database:
                // this is the object to be removed, so remove(this):
                parent.getSupportFragmentManager().beginTransaction().remove(this).commit();
            }
            //for Phone:
            else {
                Empty_favorite_fragment parent = (Empty_favorite_fragment) getActivity();
                Intent backToFragmentExample = new Intent(); //new intent to go back
                backToFragmentExample.putExtra("db_id", dataFromActivity.getLong("db_id"));

                parent.setResult(Activity.RESULT_OK, backToFragmentExample); //send data back to FragmentExample in onActivityResult()
                parent.finish(); //go back
            }

        });
        return result;
    }
}
