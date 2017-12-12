package com.example.ktoombs.firebaselogin;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ktoombs on 12/11/2017.
 */

public class CustomListAdapter extends ArrayAdapter {

    private Activity context;
    private ArrayList<String> workoutNames;
    private ArrayList<Bitmap> firstImages;
    private ArrayList<Bitmap> secondImages;

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

    public View getView(int position, View view, ViewGroup parent) {
        /*LinearLayout rowLayout = null;
        if(view == null){
            
        }*/
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.custom_list_row, null, true);
        TextView name = rowView.findViewById(R.id.workoutName);
        ImageView img1 = rowView.findViewById(R.id.firstImage);
        ImageView img2 = rowView.findViewById(R.id.secondImage);
        name.setText(workoutNames.get(position));
        img1.setImageBitmap(firstImages.get(position));
        img2.setImageBitmap(secondImages.get(position));
        return rowView;
    }
}
