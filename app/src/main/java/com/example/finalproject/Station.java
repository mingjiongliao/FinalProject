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


    public String getTitle() {
        return title;
    }


    public long getId() {
        return id;
    }

    public double getLatitude(){
        return latitude;
    }

    public double getLongtitude(){
        return longtitude;
    }

    public String getPhone(){
        return phone;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @NonNull
    @Override
    public String toString() {
        return "Station:" + title;
    }
}