package com.example.finalproject;

import androidx.annotation.NonNull;

public class Station {
    //Android Studio hint: to create getter and setter, put mouse on variable and click "alt+insert"
    protected String title;
    protected long id;
    protected double latitude;
    protected double longtitude;
    protected String phone;

    /**Constructor:*/
    public Station(String n, long i)
    {
        title =n;
        id = i;
    }

    public void update(String n)
    {
        title = n;
    }

    /**Chaining constructor: */
    public Station(String n) { this(n, 0);}

//getter
    public String getTitle() {
        return title;
    }
    //getter
    public long getId() {
        return id;
    }
//setter
    public void setLatitude(double lat){
        latitude = lat;
    }
    //setter
    public void setLongtitude(double lon){
        longtitude = lon;
    }
    //setter
    public void setPhone(String phone){
        this.phone = phone;
    }
    //setter
    public double getLatitude() {
        return latitude;
    }
    //setter
    public double getLongtitude() {
        return longtitude;
    }
    //setter
    public String getPhone() {
        return phone;
    }

    @NonNull
    @Override
    //content fo the class object
    public String toString() {
        return "Station:" + title;
    }
}