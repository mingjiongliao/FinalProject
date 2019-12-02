/**
 * File name: ListAdapter_recipe.java
 * Author: Chunyuan Luo, ID# 040926918
 * Course: 19F_CST2335_010_020 Mobile Graphic interface Prog
 * Assignment: Final Project
 * Date: 2019-11-16
 * Professor: Adewole Adewumi
 * Purpose:  recipe adapter to present each item in ListView
 */
package com.example.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ListAdapter_recipe extends BaseAdapter {

    private List<DataModel_recipe> listModels;
    private Context context;
    private LayoutInflater inflater;

    /**
     * Constructor, set arraylist to adapter and get root view
     * @param listModels
     * @param context
     */
    public ListAdapter_recipe(List<DataModel_recipe> listModels, Context context) {
        this.listModels = listModels;
        this.context = context;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * getter
     * @return return the number of items
     */
    @Override
    public int getCount() {
        return listModels.size();
    }//return the number of items

    /**
     * getter
     * @param position which position of item
     * @return return a item based on the position of arraylist items
     */
    @Override
    public Object getItem(int position) {
        return listModels.get(position);
    }

    /**
     * getter
     * @param position
     * @return return the item position
     */
    @Override
    public long getItemId(int position) { return position;  }

    /**
     * inflate the layout of item to display on listview and set the value to each item
     * @param position
     * @param oldView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View oldView, ViewGroup parent) {

        View newView;
        newView = inflater.inflate(R.layout.recipe_title, null);


            TextView messageText = (TextView)newView.findViewById(R.id.textView2);
            messageText.setText
                    (listModels.get(position).getTitle());
        return newView;

    }

    /**
     * set data which is arraylist to adapter
     * @param itemList
     */
    public void setItemList(List<DataModel_recipe> itemList) {
        this.listModels = itemList;
    }

}



