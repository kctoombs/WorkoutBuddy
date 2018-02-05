package com.example.ktoombs.WorkoutBuddy;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

/**
 * Created by ktoombs on 12/2/2017.
 */

public class FavoritesActivity extends AppCompatActivity {

    private Button signOut;
    private ImageButton workouts;
    private ImageButton home;
    private ListView workoutList;
    private TextView noFavoritesMessage;
    private FirebaseAuth mAuth;
    private CustomListAdapter listAdapter;
    private Database database;
    private ArrayList<Workout> favorites;
    private static ArrayList<String> favoriteWorkouts;
    private static ArrayList<Bitmap> firstImages;
    private static ArrayList<Bitmap> secondImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        mAuth = FirebaseAuth.getInstance();
        database = new Database(this);
        favorites = database.getFavorites(mAuth.getCurrentUser().getUid());
        noFavoritesMessage = findViewById(R.id.noFavorites);
        addWorkoutsAndImagesToAdapter();

        setupToolbar();
        setupWorkoutsButton();
        setupHomeButton();
        setupSignOutButton();
        setupWorkoutList();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void setupWorkoutsButton() {
        workouts = findViewById(R.id.workouts);
        workouts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent workoutsIntent = new Intent(getApplicationContext(),MuscleGroupsActivity.class);
                startActivity(workoutsIntent);
            }
        });
    }

    private void setupHomeButton() {
        home = findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent homeIntent = new Intent(getApplicationContext(), HomePage.class);
                startActivity(homeIntent);
            }
        });
    }

    private void setupSignOutButton() {
        signOut = findViewById(R.id.signOut);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(loginIntent);
            }
        });
    }

    private void setupWorkoutList(){
        workoutList = findViewById(R.id.workoutList);
        workoutList.setAdapter(listAdapter);
        listAdapter.addAll(favoriteWorkouts);
    }

    public void addWorkoutsAndImagesToAdapter(){
        favoriteWorkouts = new ArrayList<>();
        firstImages = new ArrayList<>();
        secondImages = new ArrayList<>();
        for(Workout workout : favorites){
            favoriteWorkouts.add(workout.getWorkout());
            firstImages.add(workout.getImage1());
            secondImages.add(workout.getImage2());
        }
        listAdapter = new CustomListAdapter(FavoritesActivity.this, favoriteWorkouts, firstImages, secondImages);
        if(favoriteWorkouts.isEmpty()){
            noFavoritesMessage.setVisibility(View.VISIBLE);
        }
    }
}
