package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class ForCurrency extends AppCompatActivity {

    ArrayList<CountryItem> objects = new ArrayList<>( );

    private static int ACTIVITY_VIEW_CONTACT = 33;
    int positionClicked = 0;
    MyOwnAdapter myAdapter;
    private EditText result=null;
    private String baseValue=null;
    private String symbols=null;
//    private String response = "https://api.exchangeratesapi.io/latest?base="+base+"&symbols="+symbols;
    private ArrayList<CountryItem> mCountryList;
    private CountryAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_currency);


        EditText amount = (EditText)findViewById(R.id.input);
        result = (EditText)findViewById(R.id.result);
        Button convert = (Button)findViewById(R.id.convert);
        Button insert = (Button)findViewById(R.id.insert);
        ListView theList = (ListView)findViewById(R.id.the_list);



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


        MyDatabaseOpenHelper dbOpener = new MyDatabaseOpenHelper(this);
        SQLiteDatabase db = dbOpener.getWritableDatabase();

        //query all the results from the database:
        String [] columns = {MyDatabaseOpenHelper.COL_ID, MyDatabaseOpenHelper.COL_MESSAGE,MyDatabaseOpenHelper.COL_FLAG1,MyDatabaseOpenHelper.COL_FLAG2};
        Log.d("dddd", "onCreate: "+columns[0].toString()+columns[2].toString());
        Cursor results = db.query(false, MyDatabaseOpenHelper.TABLE_NAME, columns, null, null, null, null, null, null);
        //find the column indices:

        int mesColIndex = results.getColumnIndex(MyDatabaseOpenHelper.COL_MESSAGE);
        int idColIndex = results.getColumnIndex(MyDatabaseOpenHelper.COL_ID);
        int flag1ColIndex=results.getColumnIndex(MyDatabaseOpenHelper.COL_FLAG1);
        int flag2ColIndex=results.getColumnIndex(MyDatabaseOpenHelper.COL_FLAG2);

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
            if(amount.getText().toString()==null){
                //todo ask tutor
                Toast.makeText(this, "Please enter amount first", Toast.LENGTH_LONG).show();
            }else{
                //how to use api to get the rate
                baseValue =mCountryList.get(spinner1.getSelectedItemPosition()).getmCountryName();

                Log.d("", "onCreate: "+baseValue);
                symbols= mCountryList.get(spinner2.getSelectedItemPosition()).getmCountryName();
                String response = "https://api.exchangeratesapi.io/latest?base="+baseValue+"&symbols="+symbols;
                try{
                    double value=Double.parseDouble(amount.getText().toString());

                    Log.d("symbol", "onCreate: "+value);

//                    try {
//                        JSONObject jsonObject=new JSONObject(response);
//                        Log.d("symbol", "onCreate: "+response);
//                        JSONObject rateObject=jsonObject.getJSONObject("rates");
//
//                        Double rate=rateObject.getDouble(symbols);

                        ImageView imageViewFlag1 =findViewById(R.id.imageView1);
                        ImageView imageViewFlag2 =findViewById(R.id.imageView2);

                        imageViewFlag1.setImageResource(mCountryList.get(spinner1.getSelectedItemPosition()).getmFlagImage());
                        imageViewFlag2.setImageResource(mCountryList.get(spinner2.getSelectedItemPosition()).getmFlagImage());
                        result.setText(value+" "+baseValue+" = "+String.valueOf(3*value)+" "+symbols);
//                    }catch (JSONException e){
//                        Snackbar.make(insert, "jason failed", Snackbar.LENGTH_LONG).show();
//                    }



                    Toast.makeText(this, "Convert seccessfully!", Toast.LENGTH_LONG).show();
                }catch(Exception e){
                    Toast.makeText(this, "Please enter number!", Toast.LENGTH_LONG).show();
                }



            }
        });

        insert.setOnClickListener(v -> {
            ContentValues newRowValues = new ContentValues();
            //put string name in the NAME column:
            ImageView flag1Image=(ImageView)findViewById(R.id.imageView1);
            ImageView flag2Image=(ImageView)findViewById(R.id.imageView2);
            int flag1=Integer.valueOf(flag1Image.toString());
            Log.d("", "onCreate: "+flag1);
            int flag2=Integer.valueOf(flag2Image.toString());
            newRowValues.put(MyDatabaseOpenHelper.COL_MESSAGE, result.getText().toString());
            newRowValues.put(MyDatabaseOpenHelper.COL_MESSAGE, flag1);
            newRowValues.put(MyDatabaseOpenHelper.COL_MESSAGE, flag2);
            long newId = db.insert(MyDatabaseOpenHelper.TABLE_NAME, null, newRowValues);

            objects.add(new CountryItem(result.getText().toString(),flag1,flag2));
            //update the listView:
            myAdapter.notifyDataSetChanged();
            Snackbar.make(insert, "Insert sucessfully!", Snackbar.LENGTH_LONG).show();
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

            LayoutInflater inflater = getLayoutInflater();
            View thisRow = null;


            thisRow = inflater.inflate(R.layout.row_layout, null);






            TextView itemField = thisRow.findViewById(R.id.message);



            itemField.setText(getItem(p).toString());


            return thisRow;
        }
    }

    public void printCursor( Cursor c){
        c.moveToFirst();


        int mesColIndex = c.getColumnIndex(MyDatabaseOpenHelper.COL_MESSAGE);
        int idColIndex = c.getColumnIndex(MyDatabaseOpenHelper.COL_ID);

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
        mCountryList.add(new CountryItem("VND",R.drawable.vnd));
    }

}
