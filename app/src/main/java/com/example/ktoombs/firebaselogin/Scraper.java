package com.example.ktoombs.firebaselogin;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by ktoombs on 12/9/2017.
 */

public class Scraper {

    private static final String TAG = "debug";
    private static ArrayList<String> workouts;
    private static ArrayList<String> allImages;

    public Scraper(){
        workouts = new ArrayList<>();
        allImages = new ArrayList<>();
    }

    public ArrayList<String> getWorkouts(){
        return this.workouts;
    }

    public static void parse(String muscleGroup){
        Document doc = null;
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
                        String url = img.attr("data-src");
                        //String absUrl = url.attr("src");
                        Log.d(TAG, "*** URL: " + url);
                        allImages.add(url);
                    }
                }
            }
        }
    }

}
