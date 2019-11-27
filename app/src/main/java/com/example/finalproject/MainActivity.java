package com.example.finalproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;


public class MainActivity extends AppCompatActivity {

    private String message="This is the initial message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar tBar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(tBar);

        Button milestone1Button = (Button)findViewById(R.id.button);
        milestone1Button.setOnClickListener ( Click ->
        {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            //This is the builder pattern, just call many functions on the same object:

            AlertDialog dialog = builder.setTitle("This is information about our team member")
                    .setMessage("Do you want to go there?")
                    .setPositiveButton("Yes", (d,w) -> {
                                Intent student = new Intent(MainActivity.this, StudentInfo.class);
                                startActivity(student);
                    }


                    )
                    //If you click the "Cancel" button:
                    .setNegativeButton("Cancel", (d,w) -> {  /* nothing */})
                    .create();

            //then show the dialog
            dialog.show();

            //show a notification: first parameter is any view on screen. second parameter is the text. Third parameter is the length (SHORT/LONG)
            Snackbar.make(milestone1Button, "I'm a snackbar", Snackbar.LENGTH_LONG).show();

            //show a notice window to say how many were updated.
            Toast.makeText(this, "I'm a toast", Toast.LENGTH_LONG).show();



        });


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
            case R.id.help:
                Toast.makeText(this, "You clicked on sign out", Toast.LENGTH_LONG).show();
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
    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "We are processing", Toast.LENGTH_LONG).show();

    }

}
