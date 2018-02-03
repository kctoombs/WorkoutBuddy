package com.example.ktoombs.firebaselogin;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.net.URL;

/**
 * Created by ktoombs on 12/3/2017.
 */

public class WorkoutsActivity extends AppCompatActivity {

    private final String TAG = "debug";
    private Button backButton;
    private ImageButton favorites, home;
    private TextView selectedMuscleGroup;
    private String muscleGroup;
    private ListView workouts;
    private Scraper scraper;
    private CustomListAdapter adapter;
    private String curVideo = "", curWorkout = "";

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
        workouts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                curWorkout = scraper.getWorkouts().get(i);
                new videoTask(curWorkout).execute();
            }
        });

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


    private class videoTask extends AsyncTask<URL, Integer, Long> {

        private String videoURL;

        public videoTask(String videoURL){
            this.videoURL = videoURL;
        }

        @Override
        protected Long doInBackground(URL... urls) {
            curVideo = scraper.getVideo(videoURL);
            return null;
        }

        @Override
        protected void onPostExecute(Long result){
            try {
                createVideoActivity(curWorkout, curVideo);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void createVideoActivity(String curWorkout, String curVideo) throws InterruptedException {
        Intent videoIntent = new Intent(getApplicationContext(), CustomFragment.class);
        videoIntent.putExtra("workout", curWorkout);
        videoIntent.putExtra("video", curVideo);
        startActivity(videoIntent);
    }
}
