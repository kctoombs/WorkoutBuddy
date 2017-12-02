package com.example.ktoombs.firebaselogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.ktoombs.firebaselogin.R;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by ktoombs on 11/30/2017.
 */

public class WorkoutsActivity extends AppCompatActivity {
    //Chest
    //Back
    //Shoulders
    //Biceps
    //Triceps
    //Legs

    private Button signOut;
    private ImageButton favorites;
    private ImageButton home;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workouts);

        mAuth = FirebaseAuth.getInstance();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //Toolbar bottomToolbar = findViewById(R.id.bottomToolbar);

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
    }
}
