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
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class ListResult extends AppCompatActivity {
    private ProgressBar progressBar;
    private String apiLine;
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
        String longtitudeFromUser = dataFromUser.getStringExtra("typedLongtitude");
        /**
         * show the user's input in the textview
         */
        TextView txtList = findViewById(R.id.txtList);
        txtList.setText("List of Stations for Location(Lat: " + LatitudeFromUser + " Long: "+ longtitudeFromUser+")");
        apiLine = "https://api.openchargemap.io/v3/poi/?output=json&latitude="+ LatitudeFromUser+ "&longitude=" + longtitudeFromUser+"&compact=true&verbose=false&maxresults=10";
      //  LocationQuery networkThread = new LocationQuery();
       // networkThread.execute();
        /**
         * find out the progressbar and listview
         */
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        ListView jsonList = findViewById(R.id.listResult);
        /**
         * make up an arrayList
         */
        Address[] listAddress = {
                new Address("Baseline", 34, 21.50,null),
                new Address("Barhaven", 56, 15.99,"819-321-2345"),
                new Address("Kanata", 42, 14.90,"613-234-4452"),
        };
        /**
         * populate the listview adapter
         */
        ArrayAdapter<Address> adapter = new ArrayAdapter<Address>(this,
                android.R.layout.simple_expandable_list_item_1, listAddress);
        jsonList.setAdapter(adapter);
        /**
         * use the setOnItemClickListener method for every list item when being clicked
         */
        jsonList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                /**
                 * set what to display in the toast for detail
                 */
                String item = "Title: " + listAddress[position].getTitle()+"\n"
                        +"Phone: " + listAddress[position].getPhone()+"\n"
                        + "Latitude: " + listAddress[position].getlatitude()+"\n"
                        + "Longtitude: " + listAddress[position].getLongitude()+"\n";

                Toast.makeText(getBaseContext(), item, Toast.LENGTH_LONG).show();

            }
        });

    }
    /*
    private class LocationQuery extends AsyncTask<String, Integer, String>
    {
        String tempValue, min, max, uv, weatherIcon;
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
                String result = sb.toString();

                //now a JSON table:
                JSONObject jObject = new JSONObject(result);
                JSONObject obj = String.valueOf(jObject.getJSONObject("AddressInfo"));
                uv = obj.
                Log.e("AsyncTask",  uv);


            }catch (Exception ex)
            {
                Log.e("Crash!!", ex.getMessage() );
            }

            return "Finished task";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(values[0]);
//
        }

        @Override
        protected void onPostExecute(String s) {

            progressBar.setVisibility(View.INVISIBLE);
        }


    }

     */
    }


