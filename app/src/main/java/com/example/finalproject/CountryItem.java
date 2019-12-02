package com.example.finalproject;

/**
 * This class will store Object countryItem
 */
public class CountryItem {
    private String mCountryName;
    private int mFlagImage;
    private int oFlagImage;
    private long id;

    /**
     * contructor
     * @param countryName countryName
     * @param flagImage flagImage
     */
    public CountryItem(String countryName, int flagImage){
        mCountryName=countryName;
        mFlagImage=flagImage;
    }

    /**
     * contructor with 3 parameter
     * @param countryName countryName pass by user
     * @param flagImage1  flagImage1 pass by user
     * @param flagImage2 flagImage2 pass by user
     * @param id id pass by user
     */
    public CountryItem(String countryName, int flagImage1,int flagImage2,long id){
        mCountryName=countryName;
        mFlagImage=flagImage1;
        oFlagImage=flagImage2;
        this.id=id;
    }

    /**
     * get country name
     * @return country name
     */
    public String getmCountryName(){
        return mCountryName;
    }

    /**
     * get flag image 1
     * @return int flag image
     */
    public int getmFlagImage(){
        return mFlagImage;
    }

    /**
     * get flag image 2
     * @return get flag image 2
     */
    public int getoFlagImage() {
        return oFlagImage;
    }

    /**
     * get id
     * @return int id
     */
    public long getId(){
        return this.id;
    }

    /**
     * update country name
     * @param mCountryName country name
     */
    public void update(String mCountryName){
        this.mCountryName=mCountryName;
    }
}