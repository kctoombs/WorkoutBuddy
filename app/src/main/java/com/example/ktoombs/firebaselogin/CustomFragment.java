package com.example.ktoombs.firebaselogin;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import static android.content.ContentValues.TAG;

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
