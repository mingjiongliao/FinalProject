package com.example.finalproject;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

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

public class JSONTSearch extends AppCompatActivity {

    private Context thisApp ;
    EditText search;
    private ListAdapter adapter;

    //load activity layout to screen

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        search = (EditText)findViewById(R.id.editText);
        thisApp = this;


        // Create the adapter to convert the array to views
        ArrayList<DataModel> arrayOfDataModels = new ArrayList<DataModel>();//define an ArrayList
        adapter = new ListAdapter( arrayOfDataModels, this);//assign the array list to adapter
        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.listview);//get the listview
        listView.setAdapter(adapter);//set adapter into listView. the first time, the listView will display content of arraylist, but crrently it is empty

        Button searchButton = (Button)findViewById(R.id.button);
        searchButton.setOnClickListener( click -> {


            //get text of the user input such as 'beef' and then to string, then encode it


            //encode 'beef' to code that computer understand to
            //String query = Uri.encode(search.getText().toString() ); //Have to encode strings to send in URL

            String baseURL = "https://www.food2fork.com/api/search?key=fdfc2f97466caa0f5b142bc3b913c366&q=";//construct the new forme of url
            String searchText=search.getText().toString();//get search content
            String realURL = baseURL + Uri.encode(searchText);//construct the real url
            Log.d("newURL is:", realURL);
            this.runURL(realURL);
        });}

        private void runURL(String encodedURL)
        {
            MyNetworkURL URL = new MyNetworkURL();//define
            URL.execute( encodedURL);//The start point of the AsyncTask
        }

        //                                      Type1, Type2   Type3
        private class MyNetworkURL extends AsyncTask<String, String, String> {
            String responseType;


            @Override                       //Type 1
            protected String doInBackground(String... strings) {

                String result;

                Log.d("newURL0 is:", strings[0]);
                try {       // Connect to the server: use URL

                    URL url = new URL(strings[0]);//generate a URL object by giving a URL
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();// connect to server, return a URL Connection that represent a connection to the remote object referred to by the URL.
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
                return result;
            }

            @Override                   //Type 3
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                ArrayList<DataModel> aListDataModels=new ArrayList<>();

                try{
                    JSONObject json = new JSONObject(result);//assign the result into json of type JSONObject
                    String count = json.getString("count");//if the type is string, ues getString() to get the value of the object count
                    JSONArray items = json.getJSONArray("recipes");// if the type is object, use getJSONArray() method to get the value and assign it to items of type JSONArray

                    Log.d("response1 is:", "count");
                    Log.d("response1 is:", count);
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

                        DataModel recipesObj= new DataModel(publisher, f2f_url, title, source_url, recipe_id, image_url, social_rank,publisher_url ); //create a model object
                        aListDataModels.add(recipesObj);//add a model object into arraylist
                    }

                }catch(JSONException e){
                    e.printStackTrace();
                }


                adapter.setItemList(aListDataModels);//add response data to adapter    arrayList----> adapter ------->listView
                adapter.notifyDataSetChanged();//notify listview that data is changed and display it

            }

            @Override                       //Type 2
            protected void onProgressUpdate(String... values) {
                super.onProgressUpdate(values);
            }

        }

}


