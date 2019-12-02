package com.example.finalproject;
/**
 * File name: DataModel_recipe.java
 * Author: Chunyuan Luo, ID# 040926918
 * Course: 19F_CST2335_010_020 Mobile Graphic interface Prog
 * Assignment: Final Project
 * Date: 2019-11-16
 * Professor: Adewole Adewumi
 * Purpose:  data model to contain each recipe item
 */
public class DataModel_recipe {

    private String publisher;
    private String f2f_url;
    private String title;
    private String source_url;
    private String recipe_id;
    private String image_url;
    private String social_rank;
    private String publisher_url;

    /**
     * Constructor, used to assign value to class member variables
     * @param publisher
     * @param f2f_url
     * @param title
     * @param source_url
     * @param recipe_id
     * @param image_url
     * @param social_rank
     * @param publisher_url
     */
    //String publisher, String f2f_url, String recipe_title, String source_url, String recipe_id, String image_url, String social_rank, String publisher_url
    public DataModel_recipe(String publisher, String f2f_url, String title, String source_url, String recipe_id, String image_url, String social_rank, String publisher_url) {
        this.publisher = publisher;
        this.f2f_url = f2f_url;
        this.title = title;
        this.source_url = source_url;
        this.recipe_id = recipe_id;
        this.image_url = image_url;
        this.social_rank = social_rank;
        this.publisher_url = publisher_url;

    }

    /**
     * getter get publisher
     * @return publisher
     */
        public String getPublisher () {
            return publisher;
        }

    /**
     * setter set publisher
     *
     */
        public void setPublisher (String publisher){
            this.publisher = publisher;
        }

    /**
     * getter get f2f_url
     * @return f2f_url
     */
        public String getF2f_url () {
            return f2f_url;
        }
    /**
     * setter set f2f_url
     *
     */
        public void setF2f_url (String f2f_url){
            this.f2f_url = f2f_url;
        }

    /**
     * getter get title
     * @return title
     */
        public String getTitle () {
            return title;
        }

    /**
     * setter set title
     *
     */
        public void setTitle (String title){
            this.title = title;
        }

    /**
     * getter get source_url
     * @return source_url
     */
        public String getSource_url () {
            return source_url;
        }
    /**
     * setter set source_url
     *
     */
        public void setSource_url (String source_url){
            this.source_url = source_url;
        }

    /**
     * getter get recipe_id
     * @return recipe_id
     */
        public String getRecipe_id () {
            return recipe_id;
        }

    /**
     * setter set recipe_id
     *
     */
        public void setRecipe_id (String recipe_id){
            this.recipe_id = recipe_id;
        }

    /**
     * getter get image_url
     * @return image_url
     */
        public String getImage_url () {
            return image_url;
        }
    /**
     * setter set social_rank
     *
     */
        public void setImage_url (String image_url){
            this.image_url = image_url;
        }
    /**
     * getter get social_rank
     * @return social_rank
     */
        public String getSocial_rank () {
            return social_rank;
        }
    /**
     * setter set social_rank
     *
     */
        public void setSocial_rank (String social_rank){
            this.social_rank = social_rank;
        }

    /**
     * getter get publisher_url
     * @return publisher_url
     */
        public String getPublisher_url () {
            return publisher_url;
        }
    /**
     * setter set publisher_url
     *
     */
        public void setPublisher_url (String publisher_url){
            this.publisher_url = publisher_url;
        }


    }

