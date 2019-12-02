package com.example.finalproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import java.util.Objects;

import static org.xmlpull.v1.XmlPullParser.END_TAG;
import static org.xmlpull.v1.XmlPullParser.START_TAG;
import static org.xmlpull.v1.XmlPullParser.TEXT;

public class ForCurrency_qing extends AppCompatActivity {

    ArrayList<CountryItem> objects = new ArrayList<>( );

    private static int ACTIVITY_VIEW_CONTACT = 33;
    int positionClicked = 0;
    MyOwnAdapter myAdapter;
    private EditText result=null;
    private EditText amount=null;
    private String amountValue=null;
    private String baseValue=null;
    private String symbols=null;
//    private ProgressDialog progressBar;
    private ProgressBar progressBar;
    private float rate=0.0f;
    private SharedPreferences prefs;
    SQLiteDatabase db;
    private String uvURL;
    private ArrayList<CountryItem> mCountryList;
    private CountryAdapter mAdapter;
    private String message="This is the initial message";
    public static final String ITEM_FLAG1 = "FLAG1";
    public static final String ITEM_FLAG2 = "FLAG2";
    public static final String ITEM_MESSAGE = "MESSAGE";
    public static final String ITEM_POSITION = "POSITION";
    public static final String ITEM_ID = "ID";

    /**
     * gets the data from database, get jason Object from API and set the result to the layout
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_currency_qing);
//        Toolbar tBar = (Toolbar)findViewById(R.id.toolbarqing);
//        setSupportActionBar(tBar);

        amount = (EditText)findViewById(R.id.input);
        result = (EditText)findViewById(R.id.result);
        Button convert = (Button)findViewById(R.id.convert);
        Button insert = (Button)findViewById(R.id.insert);
        ListView theList = (ListView)findViewById(R.id.the_list);
        boolean isTablet = findViewById(R.id.fragmentLocation) != null;
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE );

        prefs = getSharedPreferences("FileName", MODE_PRIVATE);
        amountValue = prefs.getString("Amount", "");
        if(!amountValue.isEmpty()){
            amount.setText(amountValue);
        }
        Log.d("prefs", "prefs "+amountValue);
        initList();
//        Spinner spinnerCountries=findViewById(R.id.spinner);
        Spinner spinner1 =(Spinner)findViewById(R.id.spinner1);
        Spinner spinner2 =(Spinner)findViewById(R.id.spinner2);
        mAdapter =new CountryAdapter(this,mCountryList);
        spinner1.setAdapter(mAdapter);
        spinner2.setAdapter(mAdapter);
        //set the spinner listener
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CountryItem clickedItem=(CountryItem)parent.getItemAtPosition(position);
                String clickedCountryName=clickedItem.getmCountryName();
                Toast.makeText(ForCurrency_qing.this,clickedCountryName+"selected",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //set spinner 2 listener
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CountryItem clickedItem=(CountryItem)parent.getItemAtPosition(position);
                String clickedCountryName=clickedItem.getmCountryName();
                Toast.makeText(ForCurrency_qing.this,clickedCountryName+"selected",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        CurrencyDatabaseOpenHelper dbOpener = new CurrencyDatabaseOpenHelper(this);

        db = dbOpener.getWritableDatabase();
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
            Log.d("shujukuID", "shu: "+id);
            //add the new Contact to the array list:
            objects.add(new CountryItem(message,flag1,flag2,id));
        }

        myAdapter = new MyOwnAdapter();
        theList.setAdapter(myAdapter);
        printCursor(results);

        // convert data to the result
        convert.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE );
//            progressBar = new ProgressDialog(v.getContext());
//            progressBar.setCancelable(true);
//            progressBar.setMessage("File downloading ...");
//            progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            progressBar.setProgress(0);
//            progressBar.setMax(100);
//            progressBar.show();


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
//                progressBar.cancel();
//                }catch(Exception e){
//                    Toast.makeText(this, "Please enter number!", Toast.LENGTH_LONG).show();
//                }



            }
        });
        //insert the data to the history
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

                objects.add(new CountryItem(result.getText().toString(),flag1,flag2,newId));
                //update the listView:
                myAdapter.notifyDataSetChanged();
                Snackbar.make(insert, "Insert sucessfully!", Snackbar.LENGTH_LONG).show();
            }

        });
        //set the list listener
        theList.setOnItemClickListener(( parent,  view,  position,  id) -> {
            Log.d("you clicked on :" , "item "+ position);
            //save the position in case this object gets deleted or updated
            positionClicked = position;
            Bundle dataToPass = new Bundle();
            dataToPass.putInt(ITEM_FLAG1, objects.get(position).getmFlagImage() );
            dataToPass.putInt(ITEM_FLAG2, objects.get(position).getoFlagImage() );
            dataToPass.putString(ITEM_MESSAGE,objects.get(position).getmCountryName()  );
            dataToPass.putInt(ITEM_POSITION, position);
            dataToPass.putLong(ITEM_ID, objects.get(position).getId());
            Log.d("getId", "onCreate: "+objects.get(position).getId());
            if(isTablet){
                DetailFragmentQing dFragment = new DetailFragmentQing(); //add a DetailFragment
                dFragment.setArguments( dataToPass ); //pass it a bundle for information
                dFragment.setTablet(true);  //tell the recipe_fragment if it's running on a tablet or not
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentLocation, dFragment) //Add the recipe_fragment in FrameLayout
                        .addToBackStack("Back") //make the back button undo the transaction
                        .commit(); //actually load the recipe_fragment.

            }else{
                //When you click on a row, open selected contact on a new page (ViewContact)
                CountryItem chosenOne = objects.get(position);
                Intent nextPage = new Intent(ForCurrency_qing.this, ViewMessage_qing.class);
                nextPage.putExtra("Flag1",chosenOne.getmFlagImage());
                nextPage.putExtra("Flag2",chosenOne.getoFlagImage());
                nextPage.putExtra("Message", chosenOne.getmCountryName());
                nextPage.putExtra("Id", objects.get(position).getId());
                Log.d("weizhi", "onCreate: "+position);
                Log.d("shenmeqingkuang", "onCreate: "+id);
                startActivityForResult(nextPage, ACTIVITY_VIEW_CONTACT);
            }



        });
    }

    /**
     * the MyOwnAdapter to set list view
     */
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

            View newView = inflater.inflate(R.layout.rowqing, parent, false );

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

    /**
     * print the data in the debug
     * @param c
     */
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

    /**
     * initiate the spinner
     */
    private void initList(){
        mCountryList=new ArrayList<>();
        mCountryList.add(new CountryItem("USD",R.drawable.usd));
        mCountryList.add(new CountryItem("CAD",R.drawable.cad));
        mCountryList.add(new CountryItem("CNY",R.drawable.chy));
        mCountryList.add(new CountryItem("JPY",R.drawable.jpy));
        mCountryList.add(new CountryItem("KRW",R.drawable.krw));
    }

    /**
     * create the toolbar
     * @param menu inflate menu
     * @return true and not
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.currencymenu, menu);
//        inflater.inflate(R.menu.example_menu, menu);


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

    /**
     * create menu option item
     * @param item menu item
     * @return true or not
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId())
        {
            //what to do when the menu item is selected:
            case R.id.action_sign_out:
                Toast.makeText(this, "Version 2.0 by Qing Wang", Toast.LENGTH_LONG).show();
                Intent nextPage=new Intent(this,Instruction_qing.class);
                startActivity(nextPage);
                break;
            case R.id.option1:
                Toast.makeText(this, "This is the initial message", Toast.LENGTH_LONG).show();
                Intent info=new Intent(this,Information.class);
                startActivity(info);
                break;
            case R.id.option2:
                Toast.makeText(this, "You clicked the search string", Toast.LENGTH_LONG).show();
                alertExample();
                break;
            case R.id.item1:
                Toast.makeText(this, message , Toast.LENGTH_LONG).show();
                Intent mainPage=new Intent(this,MainActivity_luo.class);
                startActivity(mainPage);
                break;
            case R.id.item2:
                Intent goToPage1 = new Intent(this, ElectricCarChargingStationFinder.class);
                startActivity(goToPage1);
                break;
            case R.id.item3:
                Intent goToPage2 = new Intent(this, RecipeSearchActivity.class);
                startActivity(goToPage2);
                break;
            case R.id.item4:
                Intent goToNews = new Intent(this, News_Activity_Main.class);
                startActivity(goToNews);
                break;
            case R.id.item5:
                Intent favcurrency=new Intent(this,favicurrency.class);
                startActivity(favcurrency);
        }
        return true;
    }

    /**
     * use dialog to prompt the user, if the user clicks "Yes", it starts new activity
     */
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

    /**
     * AsyncTask class would connect the http and get jason from API
     */
    private class RateQuery extends AsyncTask<String, Integer, String> {
        String ret = null;

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
                rate = (float) jObject.getJSONObject("rates").getDouble(symbols);

            } catch (JSONException e){
                ret="JSONException";
                Toast.makeText(ForCurrency_qing.this,e.getMessage(),Toast.LENGTH_SHORT).show();
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
            amountValue=amount.getText().toString();
            double value=Double.parseDouble(amountValue);
            result.setText(value+" "+baseValue+" = "+df.format(rate*value)+" "+symbols);
            Log.d("result for convert", "onPostExecute: "+result.getText().toString());
//            progressBar.dismiss();
            progressBar.setVisibility(View.INVISIBLE );
        }

        /**
         * override method on progress update
         * @param results Type 2
         */
        @Override                       //Type 2
        protected void onProgressUpdate(Integer... results) {

        }



    }

    /**
     * get the result from other activity
     * @param requestCode set by user
     * @param resultCode get this code from last activty
     * @param data bundle data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ACTIVITY_VIEW_CONTACT)
        {
            if(resultCode == RESULT_OK) //if you hit the delete button instead of back button
            {
                long id = data.getLongExtra(ITEM_ID, 0);
                Log.d("qing", "qing "+id);
                deleteMessageId((int)id);
            }
            if(resultCode==DetailFragmentQing.PUSHED_UPDATE){
                long id=data.getLongExtra(ITEM_ID,0);
                String message=data.getStringExtra("Message");
                Log.d("Wang", "onActivityResult: "+id+",  "+message);
                updateMessage(id,message);
            }
        }
    }

    /**
     * update database
     * @param id database id
     * @param message the result in database
     */
    public void updateMessage(long id,String message) {
        ContentValues newValues = new ContentValues();
        newValues.put(CurrencyDatabaseOpenHelper.COL_MESSAGE, message);
        db.update(CurrencyDatabaseOpenHelper.TABLE_NAME, newValues, CurrencyDatabaseOpenHelper.COL_ID + "=?", new String[] { Long.toString(id)} );
        CountryItem oldItem= objects.get(positionClicked);
        oldItem.update(message);
        myAdapter.notifyDataSetChanged();
    }

    /**
     * delete data in the database
     * @param id database id
     */
    public void deleteMessageId(long id) {
        //delete item from db and list
        Log.d("Aaaaaaaa","id="+id);

//        Message itemToDelete=null;
//        for(Message m: objects){
//            if(m.getId()==id){
//                itemToDelete =m;
//            }
//        }
        objects.remove(positionClicked);
        myAdapter.notifyDataSetChanged();
        int numDeleted = db.delete(CurrencyDatabaseOpenHelper.TABLE_NAME, CurrencyDatabaseOpenHelper.COL_ID + "=?", new String[] {Long.toString(id)});
//        int numDeleted =db.delete(CurrencyDatabaseOpenHelper.TABLE_NAME, CurrencyDatabaseOpenHelper.COL_ID + "=" + id, null);
        Log.d("Aaaaaaaa","numDeleted="+numDeleted);

    }

    /**
     * override onPause() method which start new activity will implement
     */
    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("Amount",amountValue);
        editor.commit();
    }
}