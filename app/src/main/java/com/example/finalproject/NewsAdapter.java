package com.example.finalproject;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class NewsAdapter extends BaseAdapter {

    private ArrayList<NewsObject> newsList;

    private Context mContext;
    private int layoutResourceId;

    public NewsAdapter(Context mContext, int layoutResourceId, ArrayList<NewsObject> newsList) {
        super();
        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.newsList = newsList;

    }

    @Override
    public int getCount() {
        return newsList.size();
    }

    @Override
    public NewsObject getItem(int i) {
        return newsList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return (getItem(i)).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(R.layout.activity_news_row, parent, false);
        TextView titleText = row.findViewById(R.id.news_row_title);;


        titleText.setText(getItem(position).getTitle());



        return row;
    }


    public void setListData(ArrayList<NewsObject> mListData) {
        this.newsList = mListData;
        notifyDataSetChanged();
    }

   }
