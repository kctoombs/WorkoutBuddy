package com.example.ktoombs.firebaselogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by ktoombs on 11/27/2017.
 */

public class HomePage extends AppCompatActivity {

    private Button signOut;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        mAuth = FirebaseAuth.getInstance();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

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
