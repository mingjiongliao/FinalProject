package com.example.finalproject;


import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

public class NewsFragment extends Fragment {

    private boolean isTablet;
    private Bundle dataFromActivity;

    private long id;
    private String title;
    private String description;
    private TextView articleDescription;
    private TextView articleTitle;
    private ImageView articleImage;
    private TextView articleUrl;

    private Intent lastIntent;

    private Button addToFavouritesButton;
    private Button openInBrowser;
    private Button backBtn;

    private NewsObject newsObject;
    private NewsDatabaseOpenHelper dbOpener;

    public void setTablet(boolean tablet) {
        isTablet = tablet;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbOpener = new NewsDatabaseOpenHelper(NewsFragment.super.getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        newsObject = (NewsObject) bundle.getSerializable("Article");
        // Inflate the layout for this fragment
        View result = inflater.inflate(R.layout.activity_news_content, container, false);
        articleTitle = result.findViewById(R.id.news_content_frag_title);
        articleDescription = result.findViewById(R.id.news_details_description);
        articleImage = result.findViewById(R.id.news_frag_image);

        articleTitle.setText(newsObject.getTitle());
        articleDescription.setText(newsObject.getDescription());
        Picasso.get().load(newsObject.getImageUrl()).into(articleImage);

        openInBrowser = result.findViewById(R.id.news_urlbutton);
        addToFavouritesButton = result.findViewById(R.id.news_btn_save);
        backBtn = result.findViewById(R.id.news_btn_goback);

        SQLiteDatabase db = dbOpener.getWritableDatabase();

        backBtn.setOnClickListener(v -> {
            if (isTablet) {
                News_Activity_Main parent = (News_Activity_Main) getActivity();
                parent.getSupportFragmentManager().beginTransaction().remove(this).commit();
            } else {
                News_Empty_Activity parent = (News_Empty_Activity) getActivity();
                parent.finish();
            }
        });

        addToFavouritesButton.setOnClickListener(fav -> {

            ContentValues newRowValues = new ContentValues();
            newRowValues.put(NewsDatabaseOpenHelper.COL_TITLE, newsObject.getTitle());
            newRowValues.put(NewsDatabaseOpenHelper.COL_DESCRIPTION, newsObject.getDescription());
            newRowValues.put(NewsDatabaseOpenHelper.COL_ARTICLEURL, newsObject.getArticleUrl());
            newRowValues.put(NewsDatabaseOpenHelper.COL_IMAGEURL, newsObject.getImageUrl());
            long newId = db.insert(NewsDatabaseOpenHelper.TABLE_NAME, null, newRowValues);
            AlertDialog.Builder builder = new AlertDialog.Builder(NewsFragment.super.getActivity());
            AlertDialog dialog = builder.setMessage("Successfully Saved News to Favourites!")
                    .setPositiveButton("OK", (d, w) -> {  /* nothing */})
                    .create();
            dialog.show();

        });


        openInBrowser.setOnClickListener(browser -> {

            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(newsObject.getArticleUrl()));
            startActivity(browserIntent);

        });



        return result;
    }


}
