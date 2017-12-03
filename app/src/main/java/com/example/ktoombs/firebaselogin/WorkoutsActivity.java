package com.example.ktoombs.firebaselogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Created by ktoombs on 12/3/2017.
 */

public class WorkoutsActivity extends AppCompatActivity {

    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workouts);

        Bundle extras = getIntent().getExtras();
        String muscleGroup = extras.getString("muscleGroup");
        Log.d("debug", "*** " + muscleGroup);

        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent muscleGroupIntent = new Intent(getApplicationContext(), muscleGroupsActivity.class);
                startActivity(muscleGroupIntent);
            }
        });
    }
}
