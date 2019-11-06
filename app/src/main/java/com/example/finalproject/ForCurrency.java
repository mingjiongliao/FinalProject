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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
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

    ArrayList<String> objects = new ArrayList<>( );
    private static int ACTIVITY_VIEW_CONTACT = 33;
    int positionClicked = 0;
    MyOwnAdapter myAdapter;
    private EditText result=null;
    private String base=null;
    private String symbols=null;
//    private String response = "https://api.exchangeratesapi.io/latest?base="+base+"&symbols="+symbols;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_currency);
        Spinner spinner1 =(Spinner)findViewById(R.id.spinner1);
        Spinner spinner2 =(Spinner)findViewById(R.id.spinner2);
        EditText amount = (EditText)findViewById(R.id.input);
        result = (EditText)findViewById(R.id.result);
        Button convert = (Button)findViewById(R.id.convert);
        Button insert = (Button)findViewById(R.id.insert);
        ListView theList = (ListView)findViewById(R.id.the_list);

        MyDatabaseOpenHelper dbOpener = new MyDatabaseOpenHelper(this);
        SQLiteDatabase db = dbOpener.getWritableDatabase();

        //query all the results from the database:
        String [] columns = {MyDatabaseOpenHelper.COL_ID, MyDatabaseOpenHelper.COL_MESSAGE};
        Cursor results = db.query(false, MyDatabaseOpenHelper.TABLE_NAME, columns, null, null, null, null, null, null);
        //find the column indices:

        int mesColIndex = results.getColumnIndex(MyDatabaseOpenHelper.COL_MESSAGE);
        int idColIndex = results.getColumnIndex(MyDatabaseOpenHelper.COL_ID);

        //iterate over the results, return true if there is a next item:
        while(results.moveToNext())
        {
            String message = results.getString(mesColIndex);
            long id = results.getLong(idColIndex);

            //add the new Contact to the array list:
            objects.add(message);
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
                base =spinner1.getSelectedItem().toString();
                symbols= spinner2.getSelectedItem().toString();
                String response = "https://api.exchangeratesapi.io/latest?base="+base+"&symbols="+symbols;
                try{
                    double value=Double.parseDouble(amount.getText().toString());

                    Log.d("symbol", "onCreate: "+value);

//                    try {
//                        JSONObject jsonObject=new JSONObject(response);
//                        Log.d("symbol", "onCreate: "+response);
//                        JSONObject rateObject=jsonObject.getJSONObject("rates");
//
//                        Double rate=rateObject.getDouble(symbols);


                        result.setText(value+" "+base+" equal to "+String.valueOf(3*value)+" "+symbols);
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
            newRowValues.put(MyDatabaseOpenHelper.COL_MESSAGE, result.getText().toString());
            long newId = db.insert(MyDatabaseOpenHelper.TABLE_NAME, null, newRowValues);

            objects.add(result.getText().toString());
            //update the listView:
            myAdapter.notifyDataSetChanged();
            Snackbar.make(insert, "Insert sucessfully!", Snackbar.LENGTH_LONG).show();
        });

        theList.setOnItemClickListener(( parent,  view,  position,  id) -> {
            Log.d("you clicked on :" , "item "+ position);
            //save the position in case this object gets deleted or updated
            positionClicked = position;

            //When you click on a row, open selected contact on a new page (ViewContact)
            String chosenOne = objects.get(position);
            Intent nextPage = new Intent(ForCurrency.this, ViewMessage.class);
            nextPage.putExtra("Message", chosenOne.toString());
            nextPage.putExtra("Id", position);
            startActivity(nextPage);
//            startActivityForResult(nextPage, ACTIVITY_VIEW_CONTACT);
        });
    }
    private class MyOwnAdapter extends BaseAdapter {

        public int getCount() {  return objects.size();  } //This function tells how many objects to show

        public String getItem(int position) { return objects.get(position);  }  //This returns the string at position p

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

}
