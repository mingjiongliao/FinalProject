package com.example.finalproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;

import static org.xmlpull.v1.XmlPullParser.END_TAG;
import static org.xmlpull.v1.XmlPullParser.START_TAG;
import static org.xmlpull.v1.XmlPullParser.TEXT;

public class ForCurrency extends AppCompatActivity {

    ArrayList<CountryItem> objects = new ArrayList<>( );

    private static int ACTIVITY_VIEW_CONTACT = 33;
    int positionClicked = 0;
    MyOwnAdapter myAdapter;
    private EditText result=null;
    private EditText amount=null;
    private String baseValue=null;
    private String symbols=null;
//    private ProgressBar progressBar;
    private float rate=0.0f;

    private String uvURL;
    private ArrayList<CountryItem> mCountryList;
    private CountryAdapter mAdapter;
    private String message="This is the initial message";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_currency);
        Toolbar tBar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(tBar);

        amount = (EditText)findViewById(R.id.input);
        result = (EditText)findViewById(R.id.result);
        Button convert = (Button)findViewById(R.id.convert);
        Button insert = (Button)findViewById(R.id.insert);
        ListView theList = (ListView)findViewById(R.id.the_list);
//        progressBar=(ProgressBar)findViewById(R.id.progressBar);



        initList();
//        Spinner spinnerCountries=findViewById(R.id.spinner);
        Spinner spinner1 =(Spinner)findViewById(R.id.spinner1);
        Spinner spinner2 =(Spinner)findViewById(R.id.spinner2);
        mAdapter =new CountryAdapter(this,mCountryList);
        spinner1.setAdapter(mAdapter);
        spinner2.setAdapter(mAdapter);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CountryItem clickedItem=(CountryItem)parent.getItemAtPosition(position);
                String clickedCountryName=clickedItem.getmCountryName();
                Toast.makeText(ForCurrency.this,clickedCountryName+"selected",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CountryItem clickedItem=(CountryItem)parent.getItemAtPosition(position);
                String clickedCountryName=clickedItem.getmCountryName();
                Toast.makeText(ForCurrency.this,clickedCountryName+"selected",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        CurrencyDatabaseOpenHelper dbOpener = new CurrencyDatabaseOpenHelper(this);

        SQLiteDatabase db = dbOpener.getWritableDatabase();
//        dbOpener.onUpgrade(db,1,2);

        //query all the results from the database:
        String [] columns = {CurrencyDatabaseOpenHelper.COL_ID, CurrencyDatabaseOpenHelper.COL_MESSAGE, CurrencyDatabaseOpenHelper.COL_FLAG1, CurrencyDatabaseOpenHelper.COL_FLAG2};
        Log.d("dddd", "onCreate: "+columns[0].toString()+columns[2].toString());
        Cursor results = db.query(false, CurrencyDatabaseOpenHelper.TABLE_NAME, columns, null, null, null, null, null, null);
        //find the column indices:

        int mesColIndex = results.getColumnIndex(CurrencyDatabaseOpenHelper.COL_MESSAGE);
        int idColIndex = results.getColumnIndex(CurrencyDatabaseOpenHelper.COL_ID);
        int flag1ColIndex=results.getColumnIndex(CurrencyDatabaseOpenHelper.COL_FLAG1);
        int flag2ColIndex=results.getColumnIndex(CurrencyDatabaseOpenHelper.COL_FLAG2);

        //iterate over the results, return true if there is a next item:
        while(results.moveToNext())
        {
            int flag1=results.getInt(flag1ColIndex);
            int flag2=results.getInt(flag2ColIndex);
            String message = results.getString(mesColIndex);
            long id = results.getLong(idColIndex);

            //add the new Contact to the array list:
            objects.add(new CountryItem(message,flag1,flag2));
        }

        myAdapter = new MyOwnAdapter();
        theList.setAdapter(myAdapter);
        printCursor(results);


        convert.setOnClickListener(v -> {
            ProgressDialog progressBar = new ProgressDialog(v.getContext());
            progressBar.setCancelable(true);
            progressBar.setMessage("File downloading ...");
            progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressBar.setProgress(0);
            progressBar.setMax(100);
            progressBar.show();


            if(amount.getText().toString()==null){
                //todo ask tutor
                Toast.makeText(this, "Please enter amount first", Toast.LENGTH_LONG).show();
            }else{
                //how to use api to get the rate
                baseValue =mCountryList.get(spinner1.getSelectedItemPosition()).getmCountryName();

                Log.d("base", "onCreate: "+baseValue);
                symbols= mCountryList.get(spinner2.getSelectedItemPosition()).getmCountryName();
                Log.d("symbol", "onCreate: "+symbols);
                uvURL = "https://api.exchangeratesapi.io/latest?base="+baseValue+"&symbols="+symbols;
//                try{
////                    double value=Double.parseDouble(amount.getText().toString());
////
////                    Log.d("symbol", "onCreate: "+value);
//
////                    try {
////                        JSONObject jsonObject=new JSONObject(response);
////                        Log.d("symbol", "onCreate: "+response);
////                        JSONObject rateObject=jsonObject.getJSONObject("rates");
////
////                        Double rate=rateObject.getDouble(symbols);

                        ImageView imageViewFlag1 =findViewById(R.id.imageView1);
                        ImageView imageViewFlag2 =findViewById(R.id.imageView2);

                        imageViewFlag1.setImageResource(mCountryList.get(spinner1.getSelectedItemPosition()).getmFlagImage());
                        imageViewFlag1.setTag(mCountryList.get(spinner1.getSelectedItemPosition()).getmFlagImage());
                        imageViewFlag2.setImageResource(mCountryList.get(spinner2.getSelectedItemPosition()).getmFlagImage());
                        imageViewFlag2.setTag(mCountryList.get(spinner2.getSelectedItemPosition()).getmFlagImage());
                        new RateQuery().execute();
//                        progressBar.setVisibility(View.INVISIBLE);
//                    }catch (JSONException e){
//                        Snackbar.make(insert, "jason failed", Snackbar.LENGTH_LONG).show();
//                    }



                    Toast.makeText(this, "Convert seccessfully!", Toast.LENGTH_LONG).show();
                progressBar.cancel();
//                }catch(Exception e){
//                    Toast.makeText(this, "Please enter number!", Toast.LENGTH_LONG).show();
//                }



            }
        });

        insert.setOnClickListener(v -> {
            ContentValues newRowValues = new ContentValues();
            //put string name in the NAME column:
            ImageView flag1Image=(ImageView)findViewById(R.id.imageView1);
            ImageView flag2Image=(ImageView)findViewById(R.id.imageView2);
            if(flag1Image.getTag()==null||flag2Image.getTag()==null||result.getText()==null){
                Snackbar.make(insert, "There was no result! Could not save...", Snackbar.LENGTH_LONG).show();
            }else{
                int flag1=(int)flag1Image.getTag();
                Log.d("", "onCreate: "+flag1);
                int flag2=(int)flag2Image.getTag();
                newRowValues.put(CurrencyDatabaseOpenHelper.COL_MESSAGE, result.getText().toString());
                newRowValues.put(CurrencyDatabaseOpenHelper.COL_FLAG1, flag1);
                newRowValues.put(CurrencyDatabaseOpenHelper.COL_FLAG2, flag2);
                long newId = db.insert(CurrencyDatabaseOpenHelper.TABLE_NAME, null, newRowValues);

                objects.add(new CountryItem(result.getText().toString(),flag1,flag2));
                //update the listView:
                myAdapter.notifyDataSetChanged();
                Snackbar.make(insert, "Insert sucessfully!", Snackbar.LENGTH_LONG).show();
            }

        });

        theList.setOnItemClickListener(( parent,  view,  position,  id) -> {
            Log.d("you clicked on :" , "item "+ position);
            //save the position in case this object gets deleted or updated
            positionClicked = position;

            //When you click on a row, open selected contact on a new page (ViewContact)
            CountryItem chosenOne = objects.get(position);
            Intent nextPage = new Intent(ForCurrency.this, ViewMessage.class);
            nextPage.putExtra("Flag1",chosenOne.getmFlagImage());
            nextPage.putExtra("Flag2",chosenOne.getoFlagImage());
            nextPage.putExtra("Message", chosenOne.toString());
            nextPage.putExtra("Id", position);
            startActivity(nextPage);
//            startActivityForResult(nextPage, ACTIVITY_VIEW_CONTACT);
        });
    }
    private class MyOwnAdapter extends BaseAdapter {

        public int getCount() {  return objects.size();  } //This function tells how many objects to show

        public CountryItem getItem(int position) { return objects.get(position);  }  //This returns the string at position p

        public long getItemId(int p) { return p; } //This returns the database id of the item at position p

        public View getView(int p, View recycled, ViewGroup parent)
        {


//            LayoutInflater inflater = getLayoutInflater();
//            View thisRow = null;
//            thisRow = inflater.inflate(R.layout.row_layout, null);
//            TextView itemField = thisRow.findViewById(R.id.message);
//            itemField.setText(getItem(p).toString());

            LayoutInflater inflater = getLayoutInflater();

            View newView = inflater.inflate(R.layout.row_layout, parent, false );

            CountryItem thisRow = getItem(p);

            ImageView flag1 = (ImageView) newView.findViewById(R.id.imageView1);
            TextView message = (TextView)newView.findViewById(R.id.message);
            ImageView flag2=(ImageView) newView.findViewById(R.id.imageView2);

//            if(thisRow!=null){
                flag1.setImageResource(thisRow.getmFlagImage());
                message.setText(thisRow.getmCountryName());
                flag2.setImageResource(thisRow.getoFlagImage());
//            }


            return newView;
        }
    }

    public void printCursor( Cursor c){
        c.moveToFirst();


        int mesColIndex = c.getColumnIndex(CurrencyDatabaseOpenHelper.COL_MESSAGE);
        int idColIndex = c.getColumnIndex(CurrencyDatabaseOpenHelper.COL_ID);

        //iterate over the results, return true if there is a next item:
        while(c.moveToNext())
        {
            String message = c.getString(mesColIndex);
            long id = c.getLong(idColIndex);

        }

    }
    private void initList(){
        mCountryList=new ArrayList<>();
        mCountryList.add(new CountryItem("USD",R.drawable.usd));
        mCountryList.add(new CountryItem("CAD",R.drawable.cad));
        mCountryList.add(new CountryItem("CNY",R.drawable.chy));
        mCountryList.add(new CountryItem("JPY",R.drawable.jpy));
        mCountryList.add(new CountryItem("KRW",R.drawable.krw));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu, menu);


        /* slide 15 material:  */
        MenuItem searchItem = menu.findItem(R.id.search_item);
        SearchView sView = (SearchView) searchItem.getActionView();
        sView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });



        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId())
        {
            //what to do when the menu item is selected:
            case R.id.action_sign_out:
                Toast.makeText(this, "You clicked on sign out", Toast.LENGTH_LONG).show();
                Intent nextPage=new Intent(this,Intruction.class);
                startActivity(nextPage);
                break;
            case R.id.option1:
                Toast.makeText(this, "This is the initial message", Toast.LENGTH_LONG).show();
                break;
            case R.id.option2:
                Toast.makeText(this, "You clicked the search string", Toast.LENGTH_LONG).show();
                alertExample();
                break;
            case R.id.item1:
                Toast.makeText(this, message , Toast.LENGTH_LONG).show();

                break;
            case R.id.item2:
                alertExample();
                break;
            case R.id.item3:
                Snackbar.make(findViewById(R.id.toolbar), "Go Back?", Snackbar.LENGTH_LONG).setAction("Yes",new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }).show();
        }
        return true;
    }

    public void alertExample()
    {
        View middle = getLayoutInflater().inflate(R.layout.view_extra_stuff, null);
//        Button btn = (Button)middle.findViewById(R.id.view_button);
        EditText et = (EditText)middle.findViewById(R.id.view_edit_text);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("The Message")
                .setPositiveButton("Positive", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // What to do on Accept
                        Log.d("aaaaaaa","hhhhhhhh");
                        message=et.getText().toString();

                    }
                })
                .setNegativeButton("Negative", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // What to do on Cancel
                    }
                }).setView(middle);

        builder.create().show();
    }
    private class RateQuery extends AsyncTask<String, Integer, String> {
        String ret = null;



        @Override                       //Type 1
        protected String doInBackground(String... strings) {
            try {       // Connect to the server:
                Log.d("RateQuery", "doInBackground: "+uvURL);
                JSONObject jObject = GetJsonData(uvURL);
                Log.d("ddd", "doInBackground: "+jObject);
                rate = (float) jObject.getJSONObject("rates").getDouble(symbols);

            } catch (JSONException e){
                ret="JSONException";
                Toast.makeText(ForCurrency.this,e.getMessage(),Toast.LENGTH_SHORT).show();
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
            DecimalFormat df=new DecimalFormat("#.##");
            double value=Double.parseDouble(amount.getText().toString());
            result.setText(value+" "+baseValue+" = "+df.format(rate*value)+" "+symbols);
        }

        @Override                       //Type 2
        protected void onProgressUpdate(Integer... results) {

        }



    }
}
