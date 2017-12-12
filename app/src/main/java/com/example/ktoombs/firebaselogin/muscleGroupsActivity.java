package com.example.ktoombs.firebaselogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by ktoombs on 11/30/2017.
 */

public class muscleGroupsActivity extends AppCompatActivity {
    //Chest
    //Back
    //Shoulders
    //Biceps
    //Triceps
    //Legs
    //Core

    private Button signOut;
    private ImageButton favorites;
    private ImageButton home;
    private ListView workoutsList;
    private String[] muscleGroups;
    private ArrayAdapter<String> adapter;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muscle_groups);

        mAuth = FirebaseAuth.getInstance();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

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

        signOut = findViewById(R.id.signOut);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(loginIntent);
            }
        });

        setupWorkoutList();
    }

    private void setupWorkoutList(){
        workoutsList = findViewById(R.id.workoutList);
        muscleGroups = new String[]{"Chest", "Biceps", "Triceps", "Shoulders", "Lats", "Middle Back", "Lower Back",
                "Abdominals", "Quadriceps", "Hamstrings", "Glutes", "Calves"};
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, muscleGroups);
        workoutsList.setAdapter(adapter);
        workoutsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selected = (String) workoutsList.getItemAtPosition(i);
                Intent muscleGroupIntent = new Intent(getApplicationContext(), WorkoutsActivity.class);
                muscleGroupIntent.putExtra("muscleGroup", selected);
                startActivity(muscleGroupIntent);
            }
        });
    }
}
