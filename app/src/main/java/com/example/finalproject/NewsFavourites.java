package com.example.finalproject;


import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;


public class NewsFavourites extends AppCompatActivity {

    private boolean isTablet;
    private ArrayList<NewsObject> newsFavouriteList;
    private ListView newsFavouritesListView;
    private Button gobackBtn;
    private NewsAdapter adapter;
    private SQLiteDatabase db;

    public void setTablet(boolean tablet) {
        isTablet = tablet;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_favorites);

        gobackBtn = findViewById(R.id.news_gobackToMainButton);


        gobackBtn.setOnClickListener(v -> { finish();});

        newsFavouriteList = new ArrayList<>();
        newsFavouritesListView = findViewById(R.id.news_favourites_list_view);
        adapter = new NewsAdapter(this, R.layout.activity_news_view, newsFavouriteList);
        newsFavouritesListView.setAdapter(adapter);


        NewsDatabaseOpenHelper dbOpener = new NewsDatabaseOpenHelper(this);
        db = dbOpener.getWritableDatabase();

        loadFavourites();

        newsFavouritesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                NewsObject item = (NewsObject) parent.getItemAtPosition(position);

                Intent nextActivity = new Intent(Intent.ACTION_VIEW, Uri.parse(item.getArticleUrl()));
                startActivity(nextActivity);


            }
        });
        newsFavouritesListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {


                AlertDialog.Builder builder = new AlertDialog.Builder(NewsFavourites.this);
                AlertDialog dialog = builder.setTitle("Alert!")
                        .setMessage("Are you sure to delete?")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String test = "" + pos;
                                Log.v("position", test);
                                int query = db.delete(NewsDatabaseOpenHelper.TABLE_NAME,
                                        NewsDatabaseOpenHelper.COL_ID + "=?", new String[]{Long.toString(newsFavouriteList.get(pos).getId())});
                                newsFavouriteList.remove(pos);
                                adapter.notifyDataSetChanged();
                                //showSnackbar(menu, ("Article Deleted"), LENGTH_SHORT);

                            }
                        })
                        .setNegativeButton("Cancel", (d, w) -> {  /* nothing */})
                        .create();
                dialog.show();


                return true;
            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {




            case R.id.overflow_help:
                AlertDialog.Builder helpAlertBuilder = new AlertDialog.Builder(NewsFavourites.this);
                helpAlertBuilder.setTitle("Help");
                helpAlertBuilder.setMessage(R.string.news_menu_help);
                helpAlertBuilder.show();
                break;


        }


        return true;
    }


    private void loadFavourites() {
        String[] columns = {NewsDatabaseOpenHelper.COL_ID, NewsDatabaseOpenHelper.COL_TITLE, NewsDatabaseOpenHelper.COL_DESCRIPTION,
                NewsDatabaseOpenHelper.COL_ARTICLEURL, NewsDatabaseOpenHelper.COL_IMAGEURL};
        Cursor results = db.query(false, NewsDatabaseOpenHelper.TABLE_NAME, columns, null, null, null, null, null, null);

        int titleColumnIndex = results.getColumnIndex(NewsDatabaseOpenHelper.COL_TITLE);
        int descriptionColumnIndex = results.getColumnIndex(NewsDatabaseOpenHelper.COL_DESCRIPTION);
        int idColumnIndex = results.getColumnIndex(NewsDatabaseOpenHelper.COL_ID);
        int articleUrlColumnIndex = results.getColumnIndex(NewsDatabaseOpenHelper.COL_ARTICLEURL);
        int imageUrlColumnIndex = results.getColumnIndex(NewsDatabaseOpenHelper.COL_IMAGEURL);
        while (results.moveToNext()) {

            String title = results.getString(titleColumnIndex);
            String description = results.getString(descriptionColumnIndex);
            String articleUrl = results.getString(articleUrlColumnIndex);
            String imageUrl = results.getString(imageUrlColumnIndex);
            long id = results.getLong(idColumnIndex);


            newsFavouriteList.add(new NewsObject(id, title, articleUrl, imageUrl, description));
        }
        adapter.notifyDataSetChanged();
    }

    public void showSnackbar(View view, String message, int duration) {
        Snackbar.make(view, message, duration).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


}