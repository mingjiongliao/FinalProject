/**
 * File name: RecipeSearchActivity.java
 * Author: Chunyuan Luo, ID# 040926918
 * Course: 19F_CST2335_010_020 Mobile Graphic interface Prog
 * Assignment: Final Project
 * Date: 2019-11-16
 * Professor: Adewole Adewumi
 * Purpose:  android activity applications design
 */
package com.example.finalproject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;

import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import static java.lang.Thread.sleep;

/**
 * An activity to get recipes that user want to search
 * @author chunyuan luo
 */
public class RecipeDetailFragment extends Fragment {

    private boolean isTablet;
    private Bundle dataFromActivity;
    //private String recipe_id;
    private int id;
    private String publisher;
    private String f2f_url;
    private String title;
    private String source_url;
    private String recipe_id;
    private String image_url;
    private String social_rank;
    private String publisher_url;
    private int favorite_ind;

    /**
     * Set isTablet to true of false based on imput parameter
     * @param tablet
     */
    public void setTablet(boolean tablet) { isTablet = tablet; }


    /**
     * Inflate the main Fragment screen, display information passed in from parant and register click event for all the button listeners
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        dataFromActivity = getArguments();
        //recipe_id = dataFromActivity.getString("Recipe_id" );
        id = dataFromActivity.getInt("id" );
        publisher = dataFromActivity.getString("Publisher" );
        f2f_url = dataFromActivity.getString("F2f_url" );
        title = dataFromActivity.getString("Title" );
        source_url = dataFromActivity.getString("Source_url" );
        recipe_id = dataFromActivity.getString("Recipe_id" );
        image_url = dataFromActivity.getString("Image_url" );
        social_rank = dataFromActivity.getString("Social_rank" );
        publisher_url = dataFromActivity.getString("Publisher_url" );
        favorite_ind = dataFromActivity.getInt("favorite_ind" );



        // Inflate the layout for this fragment
        View result =  inflater.inflate(R.layout.fragment, container, false);

        //ImageView imageViewFlag1 =(ImageView)result.findViewById(R.id.imageView0);



        //show the message
        TextView message = (TextView)result.findViewById(R.id.title);
        message.setText("Title: "+dataFromActivity.getString("Title"));

        //show the Image_url
        String image_url = dataFromActivity.getString("Image_url");
        TextView Image_url = (TextView)result.findViewById(R.id.image);
        Image_url.setText("Image_url: "+image_url);

        //show the message
        TextView Publisher_url = (TextView)result.findViewById(R.id.url);
        Publisher_url.setText("Publisher_url: "+dataFromActivity.getString("Publisher_url"));
        Linkify.addLinks(Publisher_url, Linkify.ALL);


        //show the id:
        TextView idView = (TextView)result.findViewById(R.id.idText);
        idView.setText("Listview ID=" + id);

        ImageView iv = (ImageView) result.findViewById(R.id.imageView1);
        Picasso.get().load(image_url).into(iv);
                //.resize(30,30).into(iv);

        //new DownloadImageTask(iv).execute(image_url);

        TextView position = (TextView)result.findViewById(R.id.position);
        position.setText("recipe_id = "+recipe_id);




        // get the delete button, and add a click listener:
        Button deleteButton = (Button)result.findViewById(R.id.deleteButton);
        Button addButton = (Button)result.findViewById(R.id.saveButton);
        if(favorite_ind==0){
            deleteButton.setVisibility(View.INVISIBLE);
        }
        else
            addButton.setVisibility(View.INVISIBLE);
        deleteButton.setOnClickListener( clk -> {


            if(isTablet) { //both the list and details are on the screen:
                RecipeSearchActivity parent = (RecipeSearchActivity)getActivity();
                parent.deleteMessageId(id); //this deletes the item and updates the list


                //now remove the fragment since you deleted it from the database:
                // this is the object to be removed, so remove(this):
                parent.getSupportFragmentManager().beginTransaction().remove(this).commit();
            }
            //for Phone:
            else {
                RecipeEmptyActivity parent = (RecipeEmptyActivity) getActivity();
                Intent backToFragmentExample = new Intent();
                backToFragmentExample.putExtra("recipe_id", dataFromActivity.getLong("Recipe_id"));
                backToFragmentExample.putExtra("button_action", "DELETE");
                backToFragmentExample.putExtra("id", id);

                parent.setResult(Activity.RESULT_OK, backToFragmentExample); //send data back to FragmentExample in onActivityResult()
                parent.finish(); //go back
            }

        });


        addButton.setOnClickListener( clk -> {


            try {
                Snackbar.make(addButton, "Save Favorite Item sucessfully!", Snackbar.LENGTH_LONG).show();
                Thread.sleep(100);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(isTablet) { //both the list and details are on the screen:
                RecipeSearchActivity parent = (RecipeSearchActivity)getActivity();
                parent.saveFavorite(publisher, f2f_url, title, source_url, recipe_id, image_url, social_rank,publisher_url ); //this deletes the item and updates the list


                //now remove the fragment since you deleted it from the database:
                // this is the object to be removed, so remove(this):
                parent.getSupportFragmentManager().beginTransaction().remove(this).commit();
            }
            //for Phone:
            else {
                RecipeEmptyActivity parent = (RecipeEmptyActivity) getActivity();

                Intent backToFragmentExample = new Intent();
                backToFragmentExample.putExtra("recipe_id", dataFromActivity.getLong("Recipe_id"));
                backToFragmentExample.putExtra("button_action", "ADD");
                backToFragmentExample.putExtra("id", id);


                //Snackbar.make(addButton, "Save Favorite Item sucessfully!", Snackbar.LENGTH_LONG).show();
                parent.setResult(Activity.RESULT_OK, backToFragmentExample); //send data back to FragmentExamplein onActivityResult（）
                parent.finish(); //go back
            }

        });

        return result;
    }


}

