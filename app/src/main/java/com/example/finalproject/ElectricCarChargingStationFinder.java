package com.example.finalproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class ElectricCarChargingStationFinder extends AppCompatActivity {

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electric_car_charging_station_finder);
        /**
         * find the toolbar
         */
        Toolbar myToolbar = (Toolbar)findViewById(R.id.toolbar2);
        setSupportActionBar(myToolbar);
        /**
         * find the two input text editor:
         *  Latitude editor & //longtitude editor
         */
        EditText editTextLatitude = findViewById(R.id.editTextLatitude);
        EditText editTextLongitude = findViewById(R.id.editTextLongitude);
        /**
         * use a Lambda function to set a click listener
         */
        Button btnFind = (Button)findViewById(R.id.btnFind);
        if(btnFind != null){
            btnFind.setOnClickListener(clk -> {
                /**
                 * new intent to go to the next page
                 */
                Intent ResultPage = new Intent(ElectricCarChargingStationFinder.this, ListResult.class);
                /**
                 * transfer the input data--- Latitude & Longtitude to the next page(activity)
                 */
                ResultPage.putExtra("typedLatitude", editTextLatitude.getText().toString());
                ResultPage.putExtra("typedLongtitude", editTextLongitude.getText().toString());
                //go to the next activity
                startActivityForResult(ResultPage, 30);
            });
        }
        //set the sanckbar content to display details in it
        Snackbar.make(btnFind, getString(R.string.s1), Snackbar.LENGTH_SHORT).show();
        //show a welcome notice.
        Toast.makeText(this, R.string.s2, Toast.LENGTH_SHORT).show();
    }

    /**
     * inflate the menu into the toolbar
     * @param menu
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.help_menu_liao, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            //what to do when the menu item is selected:
            case R.id.id5:

                View middle = getLayoutInflater().inflate(R.layout.help_dialog_electric_car, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Electric Car Charging Station Finder help menu")
                        .setView(middle)
                .setCancelable(true);
                builder.create().show();
                break;
            case R.id.id6:
                
                break;
        }
        return true;
    }
}
