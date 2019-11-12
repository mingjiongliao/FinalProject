package com.example.finalproject;


public class Address {
    private String Title;
    private double Latitude;
    private double Longitude;
    private String Phone;
    public Address(){
        super();
    }
    //constuctor for the class
    public Address(String title, double latitude, double longitude, String phone) {
        super();
        this.Title = title;
        this.Latitude = latitude;
        this.Longitude = longitude;
        this.Phone = phone;
    }
    //getter for the class
    public String getTitle() {
        return Title;
    }
    //getter for the class
    public double getlatitude() {
        return Latitude;
    }
    //getter for the class
    public double getLongitude() {
        return Longitude;
    }
    //getter for the class
    public String getPhone() {
        return Phone;
    }
    //set the class format to display in the list
    @Override
    public String toString() {
        return "Station: " + this.Title + " (Click for details)";
    }
}