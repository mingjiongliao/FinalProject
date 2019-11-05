package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

import java.util.ArrayList;

public class ForCurrency extends AppCompatActivity {

    ArrayList<String> objects = new ArrayList<>( );
    private static int ACTIVITY_VIEW_CONTACT = 33;
    int positionClicked = 0;
    MyOwnAdapter myAdapter;
    private EditText result=null;

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

        myAdapter = new MyOwnAdapter();
        theList.setAdapter(myAdapter);

        convert.setOnClickListener(v -> {
            if(amount.getText().toString()==null){
                //todo ask tutor
                Toast.makeText(this, "Please enter amount first", Toast.LENGTH_LONG).show();
            }else{
                //how to use api to get the rate
                String currency =spinner1.getSelectedItem().toString();
                String transfer= spinner2.getSelectedItem().toString();

                try{
                    int value=Integer.parseInt(amount.getText().toString());
                    result.setText(value+" "+currency+" equal to "+value*3+" "+transfer);
                    Toast.makeText(this, "Convert seccessfully!", Toast.LENGTH_LONG).show();
                }catch(Exception e){
                    Toast.makeText(this, "Please enter number!", Toast.LENGTH_LONG).show();
                }



            }
        });

        insert.setOnClickListener(v -> {
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
            //startActivity(nextPage);
            startActivityForResult(nextPage, ACTIVITY_VIEW_CONTACT);
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
}
