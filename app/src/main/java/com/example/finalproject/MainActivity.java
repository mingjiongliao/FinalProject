package com.example.finalproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;


public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        Button milestone1Button = (Button)findViewById(R.id.button);
        milestone1Button.setOnClickListener ( Click ->
        {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            //This is the builder pattern, just call many functions on the same object:
            AlertDialog dialog = builder.setTitle(getString(R.string.s5))
                    .setMessage(getString(R.string.s6))
                    .setPositiveButton(getString(R.string.s7), (d, w) -> {
                                Intent student = new Intent(MainActivity.this, StudentInfo.class);
                                startActivity(student);
                    }

                    )
                    //If you click the "Cancel" button:
                    .setNegativeButton(getString(R.string.s8), (d, w) -> {  /* nothing */})
                    .create();

            //then show the dialog
            dialog.show();

            //show a notification: first parameter is any view on screen. second parameter is the text. Third parameter is the length (SHORT/LONG)
            Snackbar.make(milestone1Button, "I'm a snackbar", Snackbar.LENGTH_LONG).show();

            //show a notice window to say how many were updated.
            Toast.makeText(this, "I'm a toast", Toast.LENGTH_LONG).show();



        });
        //go to wangqiang's page
        Button currency =(Button)findViewById(R.id.currency);
        currency.setOnClickListener(v -> {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);

            //This is the builder pattern, just call many functions on the same object:
            AlertDialog dialog1 = builder1.setTitle("You will get into currency activity that is created by qing wang")
                    .setMessage("Are you sure you will get in?")
                    .setPositiveButton("Yes", (d,w) -> {
                                Intent goToPage2 = new Intent(MainActivity.this, ForCurrency.class);
                                startActivity(goToPage2);
                            }

                    )
                    //If you click the "Cancel" button:
                    .setNegativeButton("Cancel", (d,w) -> {  /* nothing */})
                    .create();

            //then show the dialog
            dialog1.show();

        });
       //go to mingjiong's page
        Button mingjiong =(Button)findViewById(R.id.mingjiong);
        mingjiong.setOnClickListener(v -> {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);

            //This is the builder pattern, just call many functions on the same object:
            AlertDialog dialog1 = builder1.setTitle(getString(R.string.s9))
                    .setMessage(getString(R.string.s10))
                    .setPositiveButton(getString(R.string.s11), (d, w) -> {
                                Intent goToPageElectricCar = new Intent(MainActivity.this, ElectricCarChargingStationFinder.class);
                                startActivity(goToPageElectricCar);
                            }

                    )
                    //If you click the "Cancel" button:
                    .setNegativeButton(getString(R.string.s13), (d, w) -> {  /* nothing */})
                    .create();

            //then show the dialog
            dialog1.show();

        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, R.string.s12, Toast.LENGTH_LONG).show();

    }
}
