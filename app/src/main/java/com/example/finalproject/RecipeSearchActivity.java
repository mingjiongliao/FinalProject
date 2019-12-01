/**
 * File name: RecipeSearchActivity.java
 * Author: Chunyuan Luo, ID# 040926918
 * Course: 19F_CST2335_010_020 Mobile Graphic interface Prog
 * Assignment: Final Project
 * Date: 2019-11-16
 * Professor: Adewole Adewumi
 * Purpose:  android activity applications design
 */
package com.example.finalproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


/**
 * An activity to get recipes that user want to search
 * @author chunyuan luo
 */
public class RecipeSearchActivity extends AppCompatActivity {

    /**
     *Define a Context variable thisApp
     */
    private Context thisApp ;

    /**
     * get the listview
     */
    ListView listView ;

    /**
     *Define a EditText variable search
     */
    EditText search;
    /**
     *Define a ListAdapter_recipe variable adapter
     */
    private ListAdapter_recipe adapter;

    RecipeDatabaseHelper db;

    /**
     * Create the adapter to convert the array to views
     */
    ArrayList<DataModel_recipe> arrayOfDataModelLuos = new ArrayList<DataModel_recipe>();//define an ArrayList
    ArrayList<DataModel_recipe> arrayOfDataModelFavorite = new ArrayList<DataModel_recipe>();

    private int favorite_ind = 0;

    private ProgressBar progressBar;

    /**
     * main entrance that load activity layout
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_activity_main);
        search = (EditText)findViewById(R.id.insertchoice);

        thisApp = this;



        //define a file in order to save information to, so that later the information can be fetched and used by another
        //parameter1: file name,
        //parameter2: permission
        SharedPreferences prefs = getApplicationContext().getSharedPreferences("finalProject_f", Context.MODE_PRIVATE );
        //EditText input_email = (EditText)findViewById(R.id.inputtext11);  //input_email: 指向你图里的inputtext11
        search.setText(     prefs.getString("searchText", "")     ); //input_email: 外面已近取到了的指向你图里的inputtext11 //如果没有值，为默认值，如果有值会调用之前的值
//String x = prefs.getString("email", "");
        //input_email.setText(x);

        adapter = new ListAdapter_recipe(arrayOfDataModelLuos, this);//assign the array list to adapter
        /**
         * get the listview
          */
        listView = (ListView) findViewById(R.id.listview);
        /**
         * Attach the adapter to a ListView
         * set adapter into listView. the first time, the listView will display content of arraylist, but crrently it is empty
         */
        listView.setAdapter(adapter);
        Button searchButton = (Button)findViewById(R.id.searchrecipybutton);
        /**
         * get text of the user input such as 'beef' and then to string, then encode it
         * encode 'beef' to code that computer understand to
         *
         */
       // db = new DatabaseHelper(this);
        boolean isTable = findViewById(R.id.fragmentLocation) != null;
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        progressBar.setMax(10);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setProgress(0);

        db = new RecipeDatabaseHelper(this);
        //viewData();

        listView.setOnItemClickListener((list, item, position, id) -> {
            Bundle dataToPass = new Bundle();
            dataToPass.putString("Publisher", arrayOfDataModelLuos.get(position).getPublisher());
            dataToPass.putString("F2f_url", arrayOfDataModelLuos.get(position).getF2f_url());
            dataToPass.putString("Title", arrayOfDataModelLuos.get(position).getTitle());
            dataToPass.putString("Source_url", arrayOfDataModelLuos.get(position).getSource_url());
            dataToPass.putString("Recipe_id", arrayOfDataModelLuos.get(position).getRecipe_id());
            dataToPass.putString("Image_url", arrayOfDataModelLuos.get(position).getImage_url());
            dataToPass.putString("Social_rank", arrayOfDataModelLuos.get(position).getSocial_rank());
            dataToPass.putString("Publisher_url", arrayOfDataModelLuos.get(position).getPublisher_url());
            dataToPass.putInt("id", position);
            dataToPass.putInt("favorite_ind", favorite_ind);

            Toast.makeText(RecipeSearchActivity.this,arrayOfDataModelLuos.get(position).getTitle()+"selected",Toast.LENGTH_SHORT).show();




            if (isTable){
                RecipeDetailFragment dFragment = new RecipeDetailFragment(); //add a DetailFragment
                dFragment.setArguments( dataToPass ); //pass it a bundle for information
                dFragment.setTablet(true);  //tell the recipe_fragment if it's running on a tablet or not
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.fragmentLocation, dFragment) //Add the recipe_fragment in FrameLayout
                        .addToBackStack("AnyName") //make the back button undo the transaction
                        .commit(); //actually load the recipe_fragment.
            }else {
                Intent RecipeEmptyActivity = new Intent(RecipeSearchActivity.this, RecipeEmptyActivity.class);
                RecipeEmptyActivity.putExtras(dataToPass);
                startActivityForResult(RecipeEmptyActivity, 345);
            }

        });

        searchButton.setOnClickListener( click -> {
            

            /**
             * tring query = Uri.encode(search.getText().toString() ); //Have to encode strings to send in URL
             */
            //prefs = getApplicationContext().getSharedPreferences("lab3_android", Context.MODE_PRIVATE);

            //EditText input_text2 = (EditText) findViewById(R.id.inputtext22);
            //search.setText(     prefs.setString("searchText", "")     );

            //String name="xixi";
            //String age="22";

            //input_text2.setText(prefs.getString("email", ""));
            progressBar.setVisibility(View.VISIBLE);
            String baseURL = "http://www.food2fork.com/api/search?key=fdfc2f97466caa0f5b142bc3b913c366&q=";//construct the new forme of url

            String searchText=search.getText().toString();//get search content

            SharedPreferences.Editor editor=prefs.edit();

            editor.putString("searchText", searchText);
            editor.commit();

            String realURL = baseURL + Uri.encode(searchText);//construct the real url
            realURL = "http://torunski.ca/FinalProjectChickenBreast.json";
            Log.d("newURL is:", realURL);

            this.runURL(realURL);
            Snackbar.make(searchButton, "Search sucessfully!", Snackbar.LENGTH_LONG).show();
            //progressBar.setVisibility(View.INVISIBLE);
        });}

    /**
     * gets the data from internet URL
     * @param encodedURL
     */
        private void runURL(String encodedURL)
        {
            MyNetworkURL URL = new MyNetworkURL();//define
            URL.execute( encodedURL);//The start point of the AsyncTask
        }


    /**
     * inflate the recipe_menu items for use in the action bar
     * @param menu
     * @return boolean value of whether or not the action is successful
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu, menu);

        return true;
    }

    /**
     * the actions for what to do when the recipe_menu item is selected:
     * @param item
     * @return boolean value of whether or not the action is successful
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {

            case R.id.help:
                alertExample();
                break;
            case R.id.favoriteRecipe:
                favorite_ind=1;
                viewFavoriteData();
                break;
            case R.id.item1:
                finish();
                break;
            case R.id.item2:
                Intent goToPage1 = new Intent(this, ForCurrency_qing.class);
                startActivity(goToPage1);
                break;
            case R.id.item3:
                Intent goToPage2 = new Intent(this, News_Activity_Main.class);
                startActivity(goToPage2);
                break;
            case R.id.item4:
                Intent goToNews = new Intent(this, ElectricCarChargingStationFinder.class);
                startActivity(goToNews);
                break;
        }
        return true;
    }

    /**
     * To dispaly recipe_dialog box
     */
    public void alertExample()
    {
        View middle = getLayoutInflater().inflate(R.layout.recipe_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(middle);
        builder.create().show();
    }

    /**
     * Asyn class that used to handle http calls and responses
     * @param
     * */
    //                                      Type1, Type2   Type3
        private class MyNetworkURL extends AsyncTask<String, Integer, String> {
            String responseType;


        /**
         * doInBackground method to be executed in background ASYNC for dealing with http
         * @param strings
         * @return String
         */
            @Override                       //Type 1
            protected String doInBackground(String... strings) {

                String result;

                Log.d("newURL0 is:", strings[0]);
                Integer count = 1;
                for (; count <= 5; count++) {
                    try {
                        Thread.sleep(100);
                        publishProgress(count);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //result=getJSON(strings[0]);
                try {       // Connect to the server: use URL

                    URL url = new URL(strings[0]);//generate a URL object by giving a URL
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();// connect to server, return a URL Connection that represent a connection to the remote object referred to by the URL.
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setRequestProperty("Content-length", "0");
                    urlConnection.setUseCaches(false);
                    urlConnection.setAllowUserInteraction(false);
                    urlConnection.setConnectTimeout(1000);
                    urlConnection.setReadTimeout(1000);
                    urlConnection.connect();
                    int response = urlConnection.getResponseCode();
                    Log.d("Peter1", "The response is: " + response);
                    InputStream inStream = urlConnection.getInputStream(); //get response from urlConnection object.
                                                                            // return an inputStream for reading from that connection
                                                                            // inStream: the result from API
                    //note: inputStream object is not string type


                    //Set up the JSON object parser:
                    // json is UTF-8 by default
                    //1.convert inStream obj to InputStreamReader obj
                    //2. convert InputStreamReader obj to BufferedReader obj
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, "UTF-8"), 8);//create a bufferedreader object, assigne the inStream to reader
                    //creata StringBuilder object
                    StringBuilder sb = new StringBuilder();
                    //read all the response into sb object
                    String line = null;
                    while ((line = reader.readLine()) != null) {  //read a line from BufferedReader object into string
                        sb.append(line + "\n"); //append special char and string together
                    }
                    result = sb.toString();//get string from sb Object which is a StringBuilder object
                    Log.d("response0 is:",result);
                } catch (MalformedURLException mfe) {
                    result = "Malformed URL exception";
                } catch (IOException ioe) {
                    result = "IO Exception. Is the Wifi connected?";
                }
                publishProgress(100);
                return result;
            }

        /**
         * onPostExecut to be executed before end of this ASYNC execution of http handling
         * @param result
         */
        @Override                   //Type 3
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
            //progressBar.setVisibility(View.VISIBLE);
            //progressBar.setProgress(20);
            //arrayOfDataModelLuos =new ArrayList<>();
            arrayOfDataModelLuos.clear();

                try{
                    JSONObject json = new JSONObject(result);//assign the result into json of type JSONObject
                    String count = json.getString("count");//if the type is string, ues getString() to get the value of the object count
                    JSONArray items = json.getJSONArray("recipes");// if the type is object, use getJSONArray() method to get the value and assign it to items of type JSONArray

                    Log.v("response1 is:", "count");
                    Log.v("response1 is:", count);
                    for (int i = 0; i < items.length(); i++) {

                        JSONObject aDataModel = items.getJSONObject(i);//get each data object from items of type JSONArray, means a row of array

                        String publisher = aDataModel.getString("publisher");
                        String f2f_url= aDataModel.getString("f2f_url");
                        String title = aDataModel.getString("title");
                        String source_url= aDataModel.getString("source_url");
                        String recipe_id= aDataModel.getString("recipe_id");
                        String image_url= aDataModel.getString("image_url");
                        String social_rank= aDataModel.getString("social_rank");
                        String publisher_url= aDataModel.getString("publisher_url");

                        Log.d("publisher:", publisher);
                        Log.d("f2f_url:",f2f_url);
                        Log.d("title:",title);
                        Log.d("source_url:",source_url);
                        Log.d("recipe_id:",recipe_id);
                        Log.d("image_url:",image_url);
                        Log.d("social_rank:",social_rank);
                        Log.d("publisher_url:",publisher_url);

                        DataModel_recipe recipesObj= new DataModel_recipe(publisher, f2f_url, title, source_url, recipe_id, image_url, social_rank,publisher_url ); //create a model object
                        arrayOfDataModelLuos.add(recipesObj);//add a model object into arraylist
                    }

                }catch(JSONException e){
                    e.printStackTrace();
                }

            favorite_ind=0;
            progressBar.setProgress(100);
            progressBar.setVisibility(View.INVISIBLE);
                //adapter.setItemList(arrayOfDataModelLuos);//add response data to adapter    arrayList----> adapter ------->listView
                //adapter.notifyDataSetChanged();//notify listview that data is changed and display it
            //ListAdapter_recipe adt = new ListAdapter_recipe(arrayOfDataModelLuos, getApplicationContext());
            //listView.setAdapter(adt);
            adapter.setItemList(arrayOfDataModelLuos);
            adapter.notifyDataSetChanged();

            }

        /**
         * To display ot handle the progress of execution
         * @param values
         */
        @Override                       //Type 2
            //protected void onProgressUpdate(String... values) {
        protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
                progressBar.setProgress(values[0]);
            }

        /**
         * input URL and call this URL and ensure the response is JSON type
         * @param url
         * @return String which is JSON compatible
         */
            private String getJSON(String url) {
                HttpURLConnection c = null;
                try {
                    URL u = new URL(url);
                    c = (HttpURLConnection) u.openConnection();
                    c.setRequestMethod("GET");
                    c.setRequestProperty("Content-length", "0");
                    c.setUseCaches(false);
                    c.setAllowUserInteraction(false);
                    c.setConnectTimeout(1000);
                    c.setReadTimeout(1000);
                    c.connect();
                    int status = c.getResponseCode();

                    switch (status) {
                        case 200:
                        case 201:
                            BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
                            StringBuilder sb = new StringBuilder();
                            String line;
                            while ((line = br.readLine()) != null) {
                                sb.append(line + "\n");
                            }
                            br.close();
                            return sb.toString();
                    }


                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    if (c != null) {
                        try {
                            c.disconnect();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
                return null;
            }

        }

    /**
     * This function only gets called on the phone when back from another activity. The tablet never goes to a new activity
     */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == 345)
        {
            if(resultCode == RESULT_OK) //if you hit the delete button instead of back button
            {
                String recipe_id = data.getStringExtra("recipe_id");
                String button_action = data.getStringExtra("button_action");
                int position = data.getIntExtra("id",0);

                if( button_action.equals("DELETE"))
                    deleteMessageId(position);
                else {  //add favorite
                    //int position = data.getIntExtra("id",0);
                    String publisher = arrayOfDataModelLuos.get(position).getPublisher();
                    String f2f_url =  arrayOfDataModelLuos.get(position).getF2f_url();
                    String title =  arrayOfDataModelLuos.get(position).getTitle();
                    String source_url =  arrayOfDataModelLuos.get(position).getSource_url();
                    //String recipe_id =  arrayOfDataModelLuos.get(position).getRecipe_id();
                    String image_url =  arrayOfDataModelLuos.get(position).getImage_url();
                    String social_rank =  arrayOfDataModelLuos.get(position).getSocial_rank();
                    String publisher_url =  arrayOfDataModelLuos.get(position).getPublisher_url();
                    db.insertData(publisher, f2f_url, title, source_url, recipe_id, image_url, social_rank,publisher_url );

                }

            }

        }
    }

    /**
     * Delete a favorite from favorite view list and database
     * @param position
     */
    public void deleteMessageId(int position)
    {
        //String recipe_id = arrayOfDataModelLuos.get(position).getRecipe_id();
        //db.deleteEntry(recipe_id);
        //arrayOfDataModelLuos.remove(position);

        String recipe_id = arrayOfDataModelFavorite.get(position).getRecipe_id();
        int numberOFEntriesDeleted = db.deleteEntry(recipe_id);
        Log.d("Delted favorite","id:"+numberOFEntriesDeleted);
        arrayOfDataModelFavorite.remove(position);

        arrayOfDataModelLuos=arrayOfDataModelFavorite;

        adapter.notifyDataSetChanged();
        //ListAdapter_recipe adt = new ListAdapter_recipe(arrayOfDataModelLuos, getApplicationContext());
        //listView.setAdapter(adt);


        //adapter.setItemList(arrayOfDataModelLuos);//add response data to adapter    arrayList----> adapter ------->listView
        //adapter.notifyDataSetChanged();//notify listview that data is changed and display it
        //db.deleteEntry(recipe_id);
        //arrayOfDataModelLuos.clear();
        //viewData();
    }

    /**
     * View all the data from database, obsolete
     */
    private void viewData(){

        Cursor cursor = db.viewAllData();             //get all data from table and assign the resultset to Curso
        if (cursor.getCount() != 0){              //
            while (cursor.moveToNext()){
                String publisher = cursor.getString(1);
                String f2f_url= cursor.getString(2);
                String title = cursor.getString(3);
                String source_url= cursor.getString(4);
                String recipe_id= cursor.getString(5);
                String image_url= cursor.getString(6);
                String social_rank= cursor.getString(7);
                String publisher_url= cursor.getString(8);
                //MessageModel model = new MessageModel(cursor.getString(1), cursor.getInt(2)==0?true:false);//get one message,
                //DataModel_recipe model = new DataModel_recipe(cursor.getString(1), cursor.getInt(2)==0?true:false,cursor.getLong(0));//get one messag
                DataModel_recipe recipesObj= new DataModel_recipe(publisher, f2f_url, title, source_url, recipe_id, image_url, social_rank,publisher_url );
                arrayOfDataModelLuos.add(recipesObj);   //Arraylist List<MessageModel> listMessage = new ArrayList<>();
                ListAdapter_recipe adt = new ListAdapter_recipe(arrayOfDataModelLuos, getApplicationContext());
                listView.setAdapter(adt);


                //adt.setItemList(arrayOfDataModelLuos);//add response data to adapter    arrayList----> adapter ------->listView
                //adt.notifyDataSetChanged();//notify listview that data is changed and display it
                //arrayOfDataModelLuos.setAdapter(adt);
            }
        }
    }

    /**
     * list Favorite on the main activity
     */
    private void viewFavoriteData(){
        //ArrayList<DataModel_recipe> arrayOfDataModelFavorite = new ArrayList<DataModel_recipe>();//define an ArrayList
        arrayOfDataModelFavorite.clear();
        Cursor cursor = db.viewAllData();             //get all data from table and assign the resultset to Curso
        if (cursor.getCount() != 0){              //
            while (cursor.moveToNext()){
                String publisher = cursor.getString(1);
                String f2f_url= cursor.getString(2);
                String title = cursor.getString(3);
                String source_url= cursor.getString(4);
                String recipe_id= cursor.getString(5);
                String image_url= cursor.getString(6);
                String social_rank= cursor.getString(7);
                String publisher_url= cursor.getString(8);
                //MessageModel model = new MessageModel(cursor.getString(1), cursor.getInt(2)==0?true:false);//get one message,
                //DataModel_recipe model = new DataModel_recipe(cursor.getString(1), cursor.getInt(2)==0?true:false,cursor.getLong(0));//get one messag
                DataModel_recipe recipesObj= new DataModel_recipe(publisher, f2f_url, title, source_url, recipe_id, image_url, social_rank,publisher_url );
                arrayOfDataModelFavorite.add(recipesObj);   //Arraylist List<MessageModel> listMessage = new ArrayList<>();
                //ListAdapter_recipe adt = new ListAdapter_recipe(arrayOfDataModelFavorite, getApplicationContext());
                //listView.setAdapter(adt);


                //adt.setItemList(arrayOfDataModelLuos);//add response data to adapter    arrayList----> adapter ------->listView
                //adt.notifyDataSetChanged();//notify listview that data is changed and display it
                //arrayOfDataModelLuos.setAdapter(adt);
            }

        }
        //arrayOfDataModelLuos.clear();
        arrayOfDataModelLuos=arrayOfDataModelFavorite;

        adapter.setItemList(arrayOfDataModelFavorite);

        adapter.notifyDataSetChanged();
    }

    /**
     * insert a favorte recipe as a item in the database
     * @param publisher
     * @param f2f_url
     * @param title
     * @param source_url
     * @param recipe_id
     * @param image_url
     * @param social_rank
     * @param publisher_url
     */
    public void saveFavorite(String publisher, String f2f_url,String title,String source_url,String recipe_id,
                              String image_url,String social_rank,String publisher_url ){
        db.insertData(publisher, f2f_url, title, source_url, recipe_id, image_url, social_rank,publisher_url );
    }

    }


