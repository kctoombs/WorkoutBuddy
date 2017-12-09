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
    private ArrayAdapter<String> adapter;
    private Scraper scraper;

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
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        workouts.setAdapter(adapter);

        selectedMuscleGroup = findViewById(R.id.muscleGroup);
        selectedMuscleGroup.setText(muscleGroup + " " + "Workouts");
        selectedMuscleGroup.setTextSize(20);

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

        new parseTask().execute();

    }

    private class parseTask extends AsyncTask<URL, Integer, Long> {

        @Override
        protected Long doInBackground(URL... urls) {
            Log.d(TAG, "*** doInBackground ***");
            scraper = new Scraper();
            Scraper.parse(muscleGroup);

            /*Document doc = null;
            try {
                doc = Jsoup.connect("https://www.bodybuilding.com/exercises/muscle/" + muscleGroup.toLowerCase()).get();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Elements results = doc.select("div.ExCategory-results");
            for(Element row : results){
                Elements resultRows = row.children();
                for(Element result : resultRows){
                    //Log.d(TAG, "*** " + result.tagName());
                    if(result.className().contains("ExResult-row")){
                        Log.d(TAG, "***ExResult-row ***");
                        Elements resultCells = result.children();
                        Elements workoutInfo = resultCells.select("div.ExResult-cell.ExResult-cell--nameEtc");
                        String workout = workoutInfo.select("h3.ExHeading.ExResult-resultsHeading").text();
                        Log.d(TAG, "*** Workout: " + workout);
                    }
                }
            }*/
            return null;
        }

        @Override
        protected void onPostExecute(Long result) {
            Log.d(TAG, "*** onPostExecute ***");
            adapter.addAll(scraper.getWorkouts());
        }
    }
}
