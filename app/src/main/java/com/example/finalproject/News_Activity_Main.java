package com.example.finalproject;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import static android.widget.Toast.LENGTH_SHORT;

public class News_Activity_Main extends AppCompatActivity {

    private NewsAdapter adapter;
    private ArrayList<NewsObject> newsList;
    private ListView newsListView;
    private Button searchButton;
    private Button favouritesButton;
    private EditText searchEditText;
    private String NEWS_URL;
    private ProgressBar myProgressBar;
    private Toolbar main_menu;
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_main);

        boolean isTablet = findViewById(R.id.news_fragmentLocation) != null; //check if the FrameLayout is loaded

        myProgressBar = findViewById(R.id.news_progress_bar);
        myProgressBar.setVisibility(View.INVISIBLE);

        newsList = new ArrayList<>();
        searchEditText = findViewById(R.id.news_search_editText);
        searchButton = findViewById(R.id.news_searchnewsbutton);
        favouritesButton = findViewById(R.id.news_favorites);
        newsListView = findViewById(R.id.news_articlesListView);



        adapter = new NewsAdapter(this, R.layout.activity_news_row, newsList);
        adapter.setListData(newsList);
        newsListView.setAdapter(adapter);

        sharedPref = getSharedPreferences("News", MODE_PRIVATE);
        searchEditText.setText(sharedPref.getString("search", ""));

        searchButton.setOnClickListener(v ->  {
            myProgressBar.setVisibility(View.VISIBLE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("search", searchEditText.getText().toString());
            editor.commit();

            showSnackbar(v, ("Searching: " + searchEditText.getText().toString()), LENGTH_SHORT);
            NEWS_URL = "https://newsapi.org/v2/everything?apiKey=86040f22e77243c780ce80d73ecd6858&q=" + searchEditText.getText().toString() ;
            new AsyncHttpTask().execute(NEWS_URL);

                    newsList.clear();
                    new AsyncHttpTask().execute(NEWS_URL);


        });

        favouritesButton.setOnClickListener(favourites -> {
            Intent favouritesIntent = new Intent(this, NewsFavourites.class);
            startActivity(favouritesIntent);
        });


        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NewsObject item = (NewsObject) parent.getItemAtPosition(position);
                Bundle dataToPass = new Bundle();
                dataToPass.putSerializable("Article", item);

                if (isTablet) {
                    NewsFragment dFragment = new NewsFragment(); //add a DetailFragment
                    dFragment.setArguments(dataToPass); //pass it a bundle for information
                    dFragment.setTablet(true);  //tell the fragment if it's running on a tablet or not

                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.news_fragmentLocation, dFragment) //Add the fragment in FrameLayout
                            .addToBackStack("AnyName") //make the back button undo the transaction
                            .commit(); //actually load the fragment.
                } else //isPhone
                {
                    Intent nextActivity = new Intent(News_Activity_Main.this, News_Empty_Activity.class);
                    nextActivity.putExtras(dataToPass); //send data to next activity
                    startActivity(nextActivity); //make the transition
                }


            }
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
        inflater.inflate(R.menu.yemenu, menu);
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
                Toast.makeText(this, "You clicked on sign out", Toast.LENGTH_LONG).show();
                Intent nextPage=new Intent(this,Instruction_qing.class);
                startActivity(nextPage);
                break;
            case R.id.option1:
                Toast.makeText(this, "This is the initial message", Toast.LENGTH_LONG).show();
                break;
            case R.id.option2:
                Toast.makeText(this, "You clicked the search string", Toast.LENGTH_LONG).show();
                helpDialog();
                break;
            case R.id.item1:
                Toast.makeText(this, "go to main" , Toast.LENGTH_LONG).show();
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
                Intent goToNews = new Intent(this, ForCurrency_qing.class);
                startActivity(goToNews);
                break;
        }
        return true;
    }


    public void helpDialog()
    {
        View middle = getLayoutInflater().inflate(R.layout.activity_news_help_dialog, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Help")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // What to do on Cancel
                    }
                }).setView(middle);

        builder.create().show();
    }


    public class AsyncHttpTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... urls) {

            SystemClock.sleep(3000);
            String result = "";
            URL url;
            HttpsURLConnection urlConnection = null;
            try {
                url = new URL(urls[0]);
                urlConnection = (HttpsURLConnection) url.openConnection();

                if (result != null) {
                    String response = streamToString(urlConnection.getInputStream());
                    parseResult(response);
                    return result;
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }


        @Override
        protected void onPostExecute(String result) {
            updateView();
        }



        private void parseResult(String result) {
            try {
                JSONObject response = new JSONObject(result);
                JSONArray posts = response.optJSONArray("articles");
                NewsObject item;
                Float progress;
                for (int i = 0; i < posts.length(); i++) {

                    JSONObject post = posts.optJSONObject(i);
                    String title = post.optString("title");
                    String image = post.optString("urlToImage");
                    String description = post.optString("description");
                    String url = post.optString("url");
                    item = new NewsObject();
                    item.setTitle(title);
                    item.setImageUrl(image);
                    item.setArticleUrl(url);
                    item.setDescription(description);
                    newsList.add(item);

                    progress = ((i + 1) * 100f) / posts.length();
                    Log.d("load percent :", progress.toString());

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }

    void updateView(){
        adapter.notifyDataSetChanged();
        myProgressBar.setVisibility(View.INVISIBLE);
        myProgressBar.setVisibility(View.GONE);
    }


    String streamToString(InputStream newsStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(newsStream));
        String data;
        String result = "";

        while ((data = bufferedReader.readLine()) != null) {
            result += data;
        }

        if (null != newsStream) {
            newsStream.close();
        }


        return result;
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
