package com.example.ktoombs.WorkoutBuddy;

import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

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
        database = new Database(context);
        mAuth = FirebaseAuth.getInstance();

        if(database.isFavoritedByUser(mAuth.getCurrentUser().getUid(), workoutNames.get(position))){
            star.setBackgroundResource(R.drawable.star_filled_in);
        }
        else{
            star.setBackgroundResource(R.drawable.star_border);
        }

        star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(star.getBackground() == null){
                    Log.d(TAG, "*** NULL ***");
                }
                if(star.getBackground().getConstantState().equals(context.getResources().getDrawable(R.drawable.star_border).getConstantState())){
                    Log.d(TAG, "*** If ***");
                    star.setBackgroundResource(R.drawable.star_filled_in);
                    database.addFavorite(mAuth.getCurrentUser().getUid(), workoutNames.get(position),
                            firstImages.get(position), secondImages.get(position));
                }
                else{
                    Log.d(TAG, "*** Else ***");
                    star.setBackgroundResource(R.drawable.star_border);
                    database.removeFavorite(mAuth.getCurrentUser().getUid(), workoutNames.get(position));
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
