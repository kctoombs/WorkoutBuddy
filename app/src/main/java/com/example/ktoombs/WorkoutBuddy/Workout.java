package com.example.ktoombs.WorkoutBuddy;

import android.graphics.Bitmap;

/**
 * Created by ktoombs on 2/3/2018.
 */

public class Workout {

    private String workout;
    private Bitmap image1, image2;

    public Workout(String workout, Bitmap image1, Bitmap image2){
        this.workout = workout;
        this.image1 = image1;
        this.image2 = image2;
    }

    public String getWorkout() {
        return workout;
    }

    public Bitmap getImage1() {
        return image1;
    }

    public Bitmap getImage2() {
        return image2;
    }
}
