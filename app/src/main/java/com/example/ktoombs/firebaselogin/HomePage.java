package com.example.ktoombs.firebaselogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by ktoombs on 11/27/2017.
 */

public class HomePage extends AppCompatActivity {

    private Button signOut;
    private TextView user;
    private ImageButton favorites;
    private ImageButton workouts;
    FirebaseAuth mAuth;
    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        user = findViewById(R.id.username);
        mAuth = FirebaseAuth.getInstance();
        String username = mAuth.getCurrentUser().getDisplayName();
        if(username == null){
            Bundle extras = getIntent().getExtras();
            username = extras.getString("displayName");
        }

        String email = mAuth.getCurrentUser().getEmail();
        Log.d("debug", "*** " + username + " " + email);
        user.setText(username);
        user.setTextSize(25);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //Toolbar bottomToolbar = findViewById(R.id.bottomToolbar);

        workouts = findViewById(R.id.workouts);
        workouts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent workoutsIntent = new Intent(getApplicationContext(),muscleGroupsActivity.class);
                startActivity(workoutsIntent);
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
