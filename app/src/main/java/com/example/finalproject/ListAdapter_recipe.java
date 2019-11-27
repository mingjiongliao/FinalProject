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

    public ListAdapter_recipe(List<DataModel_recipe> listModels, Context context) {
        this.listModels = listModels;
        this.context = context;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return listModels.size();
    }//return the number of items

    @Override
    public Object getItem(int position) {
        return listModels.get(position);
    }

    @Override
    public long getItemId(int position) { return position;  }

    @Override
    public View getView(int position, View oldView, ViewGroup parent) {

        View newView;
        newView = inflater.inflate(R.layout.recipe_title, null);


            TextView messageText = (TextView)newView.findViewById(R.id.textView2);
            messageText.setText
                    (listModels.get(position).getTitle());
        return newView;

    }


    public void setItemList(List<DataModel_recipe> itemList) {
        this.listModels = itemList;
    }

}


