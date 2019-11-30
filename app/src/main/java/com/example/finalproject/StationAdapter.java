package com.example.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.finalproject.R;
import com.example.finalproject.Station;

import java.util.List;

public class StationAdapter extends BaseAdapter{
    private List<Station> Stations;
    private Context context;
    private LayoutInflater inflater;
//This class needs 4 functions to work properly:
    public StationAdapter(List<Station> stationList, Context context){
        this.Stations = stationList;
        this.context = context;
        this.inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return Stations.size();
    }

    public Station getItem(int position){
        return Stations.get(position);
    }

    public View getView(int position, View old, ViewGroup parent)
    {
        View view = old;

        if (view == null){
            view = inflater.inflate(R.layout.activity_view_station, null);

            TextView messageText = (TextView)view.findViewById(R.id.station_title);
            messageText.setText(Stations.get(position).getTitle());
        }
        return view;
    }

    public long getItemId(int position)
    {
        return position;
    }
}