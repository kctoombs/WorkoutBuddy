package com.example.ktoombs.firebaselogin;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;

/**
 * Created by ktoombs on 12/3/2017.
 */

public class WorkoutsActivity extends AppCompatActivity {

    private final String TAG = "debug";
    private Button backButton;
    private ImageButton favorites;
    private ImageButton home;
    private TextView selectedMuscleGroup;
    private String muscleGroup;
    private ListView workouts;
    //private ArrayAdapter<String> adapter;
    private Scraper scraper;
    private CustomListAdapter adapter;

    //Scrape bodybuilding.com webpage
    //https://www.bodybuilding.com/exercises/muscle/chest
    //jsoup

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workouts);

        Bundle extras = getIntent().getExtras();
        muscleGroup = extras.getString("muscleGroup");
        Log.d("debug", "*** " + muscleGroup);

        workouts = findViewById(R.id.workouts);

        selectedMuscleGroup = findViewById(R.id.muscleGroup);
        selectedMuscleGroup.setText(muscleGroup + " " + "Workouts");
        selectedMuscleGroup.setTextSize(20);
        
        //This is for middle-back and lower-back
        if(muscleGroup.toLowerCase().contains("back")){
            muscleGroup = muscleGroup.replace(" ", "-");
        }

        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent muscleGroupIntent = new Intent(getApplicationContext(), muscleGroupsActivity.class);
                startActivity(muscleGroupIntent);
            }
        });

        favorites = findViewById(R.id.favorites);
        favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent favoritesIntent = new Intent(getApplicationContext(), FavoritesActivity.class);
                startActivity(favoritesIntent);
            }
        });

        home = findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent homeIntent = new Intent(getApplicationContext(), HomePage.class);
                startActivity(homeIntent);
            }
        });

        scraper = new Scraper();
        new parseTask().execute();

    }

    private class parseTask extends AsyncTask<URL, Integer, Long> {

        @Override
        protected Long doInBackground(URL... urls) {
            Log.d(TAG, "*** doInBackground ***");
            scraper.parse(muscleGroup);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adapter = new CustomListAdapter(WorkoutsActivity.this, scraper.getWorkouts(), scraper.getFirstImages(), scraper.getSecondImages());
                    workouts.setAdapter(adapter);
                }
            });
            return null;
        }

        @Override
        protected void onPostExecute(Long result) {
            Log.d(TAG, "*** onPostExecute ***");
            adapter.addAll(scraper.getWorkouts());
        }
    }
}
