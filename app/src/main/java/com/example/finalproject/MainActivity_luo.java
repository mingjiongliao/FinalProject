/**
 * File name: MainActivity_luo.java
 * Author: Chunyuan Luo, ID# 040926918
 * Course: 19F_CST2335_010_020 Mobile Graphic interface Prog
 * Assignment: Final Project
 * Date: 2019-11-16
 * Professor: Adewole Adewumi
 * Purpose:  android activity applications design
 */
package com.example.finalproject;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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

/**
 * Main activity to display and handle all student's project
 * @author chunyuan luo
 */
public class MainActivity_luo extends AppCompatActivity {

    /**
     *Define a String variable message
     */
    private String message="This is the initial message";

    /**
     *
     *  main entrance that load activity layout
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firstlogin_luo);

        Button info=(Button)findViewById(R.id.buttontostudentinformation);
        Button recipe =(Button)findViewById(R.id.logintorecipe);
        Button qing=(Button)findViewById(R.id.currency);
        Button ye=(Button)findViewById(R.id.newsapi);
        Button liao=(Button)findViewById(R.id.electricmain);
        info.setOnClickListener(v->{
            Intent goToPage2 = new Intent(MainActivity_luo.this, Information.class);
            startActivity(goToPage2);
        });
        recipe.setOnClickListener(v -> {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);

            //This is the builder pattern, just call many functions on the same object:
            AlertDialog dialog1 = builder1.setTitle("You will get into recipe activity that is created by Chunyuan Luo")
                    .setMessage("Are you sure continue?")
                    .setPositiveButton("Yes", (d,w) -> {
                                Intent goToPage2 = new Intent(MainActivity_luo.this, RecipeSearchActivity.class);
                                startActivity(goToPage2);
                            }

                    )
                    //If you click the "Cancel" button:
                    .setNegativeButton("Cancel", (d,w) -> {  /* nothing */})
                    .create();

            //then show the dialog
            dialog1.show();

        });
        qing.setOnClickListener(v -> {
            Intent goToPage2 = new Intent(MainActivity_luo.this, ForCurrency_qing.class);
            startActivity(goToPage2);
        });

    }

    /**
     * inflate the recipe_menu items for use in the action bar
     * @param menu
     * @return boolean value of whether or not the action is successful
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menuqing, menu);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId())
        {
            //what to do when the menu item is selected:
            case R.id.action_sign_out:
                Intent nextPage=new Intent(this,Instruction_qing.class);
                startActivity(nextPage);
                break;
            case R.id.option1:
                Intent goToPage2 = new Intent(MainActivity_luo.this, Information.class);
                startActivity(goToPage2);
                break;
            case R.id.option2:
                String algonquin = "http://www.algonquincollege.com";
                Intent s = new Intent(Intent.ACTION_VIEW);
                s.setData( Uri.parse(algonquin) );
                startActivity(s);
//                alertExample();
                break;
            case R.id.item1:
                Toast.makeText(this, message , Toast.LENGTH_LONG).show();

                break;
            case R.id.item2:
                alertExample();
                break;
            case R.id.item3:

                Snackbar.make(findViewById(R.id.item3), "You would go to gmail webpage", Snackbar.LENGTH_LONG).show();
                String gmail = "http://www.gmail.com";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData( Uri.parse(gmail) );
                startActivity(i);
                break;
            case R.id.item4:
                String titwer = "https://www.facebook.com/TwitterInc/";
                Intent t = new Intent(Intent.ACTION_VIEW);
                t.setData( Uri.parse(titwer) );
                startActivity(t);
                break;
        }
        return true;
    }

    public void alertExample()
    {
        View middle = getLayoutInflater().inflate(R.layout.view_extra_stuff, null);
//        Button btn = (Button)middle.findViewById(R.id.view_button);
        EditText et = (EditText)middle.findViewById(R.id.view_edit_text);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Go to currency")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // What to do on Accept
                        Log.d("aaaaaaa","hhhhhhhh");
//                        message=et.getText().toString();
                        String currencyPage = "https://www.xe.com/currencyconverter/";
                        Intent c = new Intent(Intent.ACTION_VIEW);
                        c.setData( Uri.parse(currencyPage) );
                        startActivity(c);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
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

