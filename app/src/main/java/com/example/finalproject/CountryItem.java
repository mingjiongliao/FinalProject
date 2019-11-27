package com.example.finalproject;

public class CountryItem {
    private String mCountryName;
    private int mFlagImage;
    private int oFlagImage;
    private long id;

    public CountryItem(String countryName, int flagImage){
        mCountryName=countryName;
        mFlagImage=flagImage;
    }

    public CountryItem(String countryName, int flagImage1,int flagImage2,long id){
        mCountryName=countryName;
        mFlagImage=flagImage1;
        oFlagImage=flagImage2;
        this.id=id;
    }
    public String getmCountryName(){
        return mCountryName;
    }

    public int getmFlagImage(){
        return mFlagImage;
    }

    public int getoFlagImage() {
        return oFlagImage;
    }
    public long getId(){
        return this.id;
    }
}