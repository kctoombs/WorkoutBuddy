package com.example.ktoombs.firebaselogin;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

/**
 * Created by ktoombs on 12/11/2017.
 */

public class CustomListAdapter extends ArrayAdapter {

    private Activity context;
    private ArrayList<String> workoutNames;
    private ArrayList<Bitmap> firstImages;
    private ArrayList<Bitmap> secondImages;
    private final String TAG = "Debug";
    private Database database;
    private FirebaseAuth mAuth;

    public CustomListAdapter(Activity context, ArrayList<String> workoutNames,
                             ArrayList<Bitmap> firstImages, ArrayList<Bitmap> secondImages){
        super(context, R.layout.custom_list_row, workoutNames);
        this.context = context;
        this.workoutNames = new ArrayList<>(workoutNames);
        this.firstImages = new ArrayList<>(firstImages);
        this.secondImages = new ArrayList<>(secondImages);
    }

    @Override
    public int getCount() {
        return workoutNames.size();
    }

    public View getView(final int position, View view, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.custom_list_row, null, true);
        TextView name = rowView.findViewById(R.id.workoutName);
        ImageView img1 = rowView.findViewById(R.id.firstImage);
        ImageView img2 = rowView.findViewById(R.id.secondImage);
        final ImageButton star = rowView.findViewById(R.id.star);
        star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth = FirebaseAuth.getInstance();
                database = new Database(context);
                database.addFavorite(mAuth.getCurrentUser().getUid(), workoutNames.get(position),
                        firstImages.get(position).toString(), secondImages.get(position).toString());
                //ImageButton star = view.findViewById(R.id.star);
                if(star == null){
                    Log.d(TAG, "*** NULL ***");
                }
                if(star.getBackground().equals(R.drawable.star_border)){
                    Log.d(TAG, "*** If ***");
                    star.setBackgroundResource(R.drawable.star_filled_in);
                }
                else{
                    Log.d(TAG, "*** Else ***");
                    star.setBackgroundResource(R.drawable.star_border);
                }
                database.getFavoritesCount(mAuth.getCurrentUser().getUid());
            }
        });
        name.setText(workoutNames.get(position));
        img1.setImageBitmap(firstImages.get(position));
        img2.setImageBitmap(secondImages.get(position));
        return rowView;
    }
}
