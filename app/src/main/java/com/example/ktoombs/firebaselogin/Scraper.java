package com.example.ktoombs.firebaselogin;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by ktoombs on 12/9/2017.
 */

public class Scraper {

    private static final String TAG = "debug";
    private static ArrayList<String> workouts;
    private static ArrayList<Bitmap> firstImages;
    private static ArrayList<Bitmap> secondImages;


    public Scraper(){
        workouts = new ArrayList<>();
        firstImages = new ArrayList<>();
        secondImages = new ArrayList<>();
    }

    public ArrayList<String> getWorkouts(){
        return this.workouts;
    }

    public ArrayList<Bitmap> getFirstImages(){
        return this.firstImages;
    }

    public ArrayList getSecondImages(){
        return this.secondImages;
    }

    public static void parse(String muscleGroup){
        int imageCount = 0;
        Document doc = null;
        Bitmap image;

        try {
            doc = Jsoup.connect("https://www.bodybuilding.com/exercises/muscle/" + muscleGroup.toLowerCase()).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Elements results = doc.select("div.ExCategory-results");
        for(Element row : results){
            Elements resultRows = row.children();
            for(Element result : resultRows){
                //Log.d(TAG, "*** " + result.tagName());
                if(result.className().contains("ExResult-row")){
                    Log.d(TAG, "***ExResult-row ***");
                    Elements resultCells = result.children();
                    Elements workoutInfo = resultCells.select("div.ExResult-cell.ExResult-cell--nameEtc");
                    String workout = workoutInfo.select("h3.ExHeading.ExResult-resultsHeading").text();
                    workouts.add(workout);
                    Log.d(TAG, "*** Workout: " + workout);
                    //Elements imageInfo = resultCells.select("div.ExResult-cell.ExResult-cell--imgs");
                    Elements imageInfo = resultCells.select("img");
                    for(Element img : imageInfo){
                        //Element curImage = img.select("img");
                        try {
                            URL url = new URL(img.attr("data-src"));
                            Log.d(TAG, "*** URL: " + url);
                            image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                            //Add every other image to firstImages. (All odd numbered images)
                            if(imageCount % 2 == 0) {
                                firstImages.add(image);
                            }
                            //Add all even numbered images to secondImages.
                            else{
                                secondImages.add(image);
                            }
                            imageCount++;
                        } catch(MalformedURLException e){
                            e.printStackTrace();
                        } catch (IOException io){
                            io.printStackTrace();
                        }

                    }
                }
            }
        }
        Log.d(TAG, "*** firstImages size: " + firstImages.size());
        Log.d(TAG, "*** secondImages size: " + secondImages.size());
    }
}

