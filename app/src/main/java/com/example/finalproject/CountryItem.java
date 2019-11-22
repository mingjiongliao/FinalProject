package com.example.finalproject;

public class CountryItem {
    private String mCountryName;
    private int mFlagImage;
    private int oFlagImage;

    public CountryItem(String countryName, int flagImage){
        mCountryName=countryName;
        mFlagImage=flagImage;
    }

    public CountryItem(String countryName, int flagImage1,int flagImage2){
        mCountryName=countryName;
        mFlagImage=flagImage1;
        oFlagImage=flagImage2;
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
}