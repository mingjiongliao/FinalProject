package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ListResult extends AppCompatActivity {
    private ProgressBar progressBar;
    private Context thisApp;
    private String apiLine;
    private String resultObj;
    private LocationQuery networkThread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_result);
        /**
         * retrieve data from the old intent(activity)
         */
        Intent dataFromUser = getIntent();
        /**
         * retrieve Latitude from the old page
         */
        String LatitudeFromUser = dataFromUser.getStringExtra("typedLatitude");
        //retrieve Longtitude from the old page
        String LongtitudeFromUser = dataFromUser.getStringExtra("typedLongtitude");
        /**
         * show the user's input in the textview
         */

        apiLine = "https://api.openchargemap.io/v3/poi/?output=json&latitude="+ LatitudeFromUser+ "&longitude=" + LongtitudeFromUser+"&maxresults=10";
        //apiLine = "https://api.openchargemap.io/v3/poi/?output=json&countrycode=CA&latitude=45.347571&longitude=-75.756140&maxresults=5";
        networkThread = new LocationQuery();
        networkThread.execute(apiLine);
        /**
         * find out the progressbar and listview
         */
        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        /**
         * make up an arrayList
         */

        /**
         * populate the listview adapter
         */
       // ListView jsonList = findViewById(R.id.listResult);
       // ArrayAdapter<Address> adapter = new ArrayAdapter<Address>(this,
        //        android.R.layout.simple_expandable_list_item_1, listAddress);
       // jsonList.setAdapter(adapter);
        /**
         * use the setOnItemClickListener method for every list item when being clicked
         */

    }

    private class LocationQuery extends AsyncTask<String, Integer, String>
    {
        /**
         * use the connection to return the string from the url
         * @param params
         * @return jason obejct store in the string
         */
        @Override
        protected String doInBackground(String ... params) {
            try {
                //create the network connection:
                URL url = new URL(apiLine);
                HttpURLConnection newConnection = (HttpURLConnection) url.openConnection();
                InputStream inStream = newConnection.getInputStream();
                //create a JSON object from the response
                BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null)
                {
                    sb.append(line + "\n");
                }
                resultObj = sb.toString();

                //now a JSON table:

                //Log.e("AsyncTask",  uv);


            }catch (Exception ex)
            {
                Log.e("Crash!!", ex.getMessage() );
            }

            return resultObj;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String s) {
            /**
             * initialize the list to store the json object array
             */
            TextView txtList = findViewById(R.id.txtList);
            try {
                JSONArray jObject = new JSONArray(s);
                int Length = jObject.length();
                if (Length==0){

                    txtList.setText(R.string.s3);
                }else{
                    txtList.setText(R.string.s4);
                ArrayList<Address> listAddress = new ArrayList<Address>();
                    /**
                     * parse the list of json object into the address object I created
                     */
                for (int i=0; i < jObject.length(); i++)
                {
                    JSONObject anObject = jObject.getJSONObject(i);
                    String title = anObject.getJSONObject("AddressInfo").getString("Title");
                    String phone = anObject.getJSONObject("AddressInfo").getString("ContactTelephone1");
                    double latitude = anObject.getJSONObject("AddressInfo").getDouble("Latitude");
                    double longitude = anObject.getJSONObject("AddressInfo").getDouble("Longitude");
                    listAddress.add(new Address(title,latitude,longitude,phone));
                }
                    /**
                     * into the adapter
                     */
                ListView jsonList = findViewById(R.id.listResult);
                ArrayAdapter<Address> adapter = new ArrayAdapter<Address>(getBaseContext(),
                        android.R.layout.simple_expandable_list_item_1, listAddress);
                jsonList.setAdapter(adapter);
                    /**
                     * set the event for this listview
                     */
                jsonList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position,
                                            long id) {
                                String item = "Title: " + listAddress.get(position).getTitle()+"\n"
                                +"Phone: " + listAddress.get(position).getPhone()+"\n"
                                + "Latitude: " + listAddress.get(position).getlatitude()+"\n"
                                + "Longtitude: " + listAddress.get(position).getLongitude()+"\n";
                        /**
                         * make a Toast to display the content
                         */
                        Toast.makeText(getBaseContext(), item, Toast.LENGTH_SHORT).show();

                    }
                });
                }
            } catch (JSONException e) {
                // Oops

            }

        }

    }
    }


