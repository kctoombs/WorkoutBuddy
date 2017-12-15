package com.example.ktoombs.firebaselogin;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by ktoombs on 12/12/2017.
 */

public class CustomFragment extends DialogFragment {

    private ImageButton exitButton;
    private TextView curWorkout;

    public CustomFragment(){

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.custom_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        curWorkout = getView().findViewById(R.id.curWorkout);
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
    }
}
