package com.example.finalproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

public class ViewStation extends AppCompatActivity {
    protected SQLiteDatabase db = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_station);
        //get the database:
        FavoriteDBhelper opener = new FavoriteDBhelper(this);
        db =  opener.getWritableDatabase();
//Get the information from the previous page:
        Intent fromPreviousPage = getIntent();
        String titleContent = fromPreviousPage.getStringExtra("Title");

        TextView titleTxt = (TextView)findViewById(R.id.title);
        titleTxt.setText(titleContent);
        long id = fromPreviousPage.getLongExtra("Id", -1);
        //When you click on the delete button:
        Button deleteButton = (Button)findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(b -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            //This is the builder pattern, just call many functions on the same object:
            AlertDialog dialog = builder.setTitle("Alert!")
                    .setMessage("Do you want to delete?")
                    .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            //If you click the "Delete" button
                            int numDeleted = db.delete(FavoriteDBhelper.TABLE_NAME, FavoriteDBhelper.COL_ID + "=?", new String[] {Long.toString(id)});

                            Log.i("ViewContact", "Deleted " + numDeleted + " rows");

                            //go back to previous page:
                            finish();
                        }

                    })
                    //If you click the "Cancel" button:
                    .setNegativeButton("Cancel", (d,w) -> {  /* nothing */})
                    .create();

            //then show the dialog
            dialog.show();
        });
    }
}
