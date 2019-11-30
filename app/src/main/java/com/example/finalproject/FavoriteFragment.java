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
        id = dataFromActivity.getInt("id" );
        // Inflate the layout for this fragment
        View result =  inflater.inflate(R.layout.favorite_fragment, container, false);

        //show the message
        TextView favorite_fragment_title = (TextView)result.findViewById(R.id.favorite_fragment_title);
        favorite_fragment_title.setText("Title: "+dataFromActivity.getString("item"));

        //show the id:
        TextView idView = (TextView)result.findViewById(R.id.idText);
        idView.setText("Listview ID=" + id);

        TextView position = (TextView)result.findViewById(R.id.position);
        position.setText("DB id = "+db_id);

        // get the delete button, and add a click listener:
        Button deleteButton = (Button)result.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener( clk -> {


            if(isTablet) { //both the list and details are on the screen:
                ChatRoomActivity parent = (ChatRoomActivity)getActivity();
                parent.deleteMessageId((int)db_id); //this deletes the item and updates the list


                //now remove the fragment since you deleted it from the database:
                // this is the object to be removed, so remove(this):
                parent.getSupportFragmentManager().beginTransaction().remove(this).commit();
            }
            //for Phone:
            else {
                EmptyActivity parent = (EmptyActivity) getActivity();
                Intent backToFragmentExample = new Intent();
                backToFragmentExample.putExtra("db_id", dataFromActivity.getLong("db_id"));

                parent.setResult(Activity.RESULT_OK, backToFragmentExample); //send data back to FragmentExample in onActivityResult()
                parent.finish(); //go back
            }

        });
        return result;
    }
}
