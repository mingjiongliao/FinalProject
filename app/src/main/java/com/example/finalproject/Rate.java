package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;

/**
 * rate activity which will show all rate about 1 EUR
 */
public class Rate extends AppCompatActivity {
    private  TextView itema;
    private  TextView itemb;
    private  TextView itemc;
    private  TextView itemd;
    private  TextView iteme;
    private  TextView itemf;
    private  TextView itemg;
    private  TextView itemh;
    private  TextView itemj;
    private  TextView itemk;
    private  TextView iteml;
    private String uvURL="https://api.exchangeratesapi.io/latest";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);
         itema=(TextView)findViewById(R.id.itema);
         itemb=(TextView)findViewById(R.id.itemb);
         itemc=(TextView)findViewById(R.id.itemc);
         itemd=(TextView)findViewById(R.id.itemd);
         iteme=(TextView)findViewById(R.id.iteme);
         itemf=(TextView)findViewById(R.id.itemf);
         itemg=(TextView)findViewById(R.id.itemg);
         itemh=(TextView)findViewById(R.id.itemh);
         itemj=(TextView)findViewById(R.id.itemj);
         itemk=(TextView)findViewById(R.id.itemk);
         iteml=(TextView)findViewById(R.id.iteml);
         new RateQuery().execute();

    }

    private class RateQuery extends AsyncTask<String, Integer, String> {
        String ret = null;
        float ratecad;
        float ratehkd;
        float rateisk;
        float ratephp;
        float ratedkk;
        float ratehuf;
        float rateczk;
        float rateaud;
        float rateron;
        float ratesek;
        float rateidr;
        /**
         * override method do in back ground
         * @param strings Type1
         * @return String
         */

        @Override                       //Type 1
        protected String doInBackground(String... strings) {
            try {       // Connect to the server:
                Log.d("RateQuery", "doInBackground: "+uvURL);
                JSONObject jObject = GetJsonData(uvURL);
                Log.d("ddd", "doInBackground: "+jObject);
                ratecad= (float) jObject.getJSONObject("rates").getDouble("CAD");
                ratehkd= (float) jObject.getJSONObject("rates").getDouble("HKD");
                rateisk= (float) jObject.getJSONObject("rates").getDouble("ISK");
                ratephp= (float) jObject.getJSONObject("rates").getDouble("PHP");
                ratedkk= (float) jObject.getJSONObject("rates").getDouble("DKK");
                ratehuf= (float) jObject.getJSONObject("rates").getDouble("HUF");
                rateczk= (float) jObject.getJSONObject("rates").getDouble("CZK");
                rateaud= (float) jObject.getJSONObject("rates").getDouble("AUD");
                rateron= (float) jObject.getJSONObject("rates").getDouble("RON");
                ratesek= (float) jObject.getJSONObject("rates").getDouble("SEK");
                rateidr= (float) jObject.getJSONObject("rates").getDouble("IDR");

            } catch (JSONException e){
                ret="JSONException";

            }


            //What is returned here will be passed as a parameter to onPostExecute:
            return ret;
        }


        public JSONObject GetJsonData(String url){
            String line = null;
            JSONObject jObject = null;
            try{
                URL u = new URL(url);
                Log.d("ddd", "doInBackground: "+url+"    "+u.toString());
                HttpURLConnection connection = (HttpURLConnection) u.openConnection();
                Log.d("ddd", "doInBackground: "+url+"    "+connection);
//                InputStream is = connection.getInputStream();
                connection.connect();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"), 8);

                StringBuilder sb = new StringBuilder();

                while((line = reader.readLine())!=null){
                    sb.append(line);
                }
                line = sb.toString();
                Log.d("ddd", "doInBackground: "+url+"    "+line);
                jObject= new JSONObject(line);

                connection.disconnect();
                connection.getInputStream().close();
//                sb.delete(0, sb.length());
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            } catch (JSONException e) {
                Log.i("GetJsonData", "Error parsing data " + e.toString());
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }


            return jObject;
        }
        @Override                   //Type 3
        protected void onPostExecute(String s) {
            itemb.setText(ratecad+" CAD");
            itemc.setText(ratehkd+" HKD");
            itemd.setText(rateisk+" ISK");
            iteme.setText(ratephp+" PHP");
            itemf.setText(ratedkk+" DKK");
            itemg.setText(ratehuf+" HUF");
            itemh.setText(rateczk+" CZK");
            itemj.setText(rateaud+" AUD");
            itemk.setText(rateron+" RON");
            iteml.setText(ratesek+" SEK");
        }

        /**
         * override method on progress update
         * @param results Type 2
         */
        @Override                       //Type 2
        protected void onProgressUpdate(Integer... results) {

        }



    }
}
