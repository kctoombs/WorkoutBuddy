package com.example.ktoombs.WorkoutBuddy;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

/**
 * Created by ktoombs on 12/12/2017.
 */

public class CustomFragment extends Activity {

    private ImageButton exitButton;
    private TextView curWorkout;
    private VideoView videoView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_fragment);
        Bundle bundle = getIntent().getExtras();

        curWorkout = findViewById(R.id.curWorkout);
        videoView = findViewById(R.id.video);
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

        String selectedWorkout = bundle.getString("workout");
        if (selectedWorkout != null) {
            curWorkout.setText(selectedWorkout);
        }

        exitButton = findViewById(R.id.exitButton);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Uri uri = Uri.parse(bundle.getString("video"));
        videoView.setVideoURI(uri);
        videoView.start();
    }

}
