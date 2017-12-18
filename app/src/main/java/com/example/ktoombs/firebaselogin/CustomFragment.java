package com.example.ktoombs.firebaselogin;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.VideoView;

/**
 * Created by ktoombs on 12/12/2017.
 */

public class CustomFragment extends DialogFragment {

    private ImageButton exitButton;
    private TextView curWorkout;
    private VideoView videoView;

    public CustomFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.custom_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        curWorkout = getView().findViewById(R.id.curWorkout);
        videoView = getView().findViewById(R.id.video);

        Bundle bundle = getArguments();
        if (bundle != null) {
            curWorkout.setText(bundle.getString("workout"));
        }

        exitButton = getView().findViewById(R.id.exitButton);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });

        Uri uri=Uri.parse(bundle.getString("video"));
        videoView.setVideoURI(uri);
        videoView.start();
    }

}
