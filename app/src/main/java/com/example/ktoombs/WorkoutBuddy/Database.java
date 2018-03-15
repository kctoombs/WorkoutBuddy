package com.example.ktoombs.WorkoutBuddy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * Created by ktoombs on 1/6/2018.
 */

public class Database extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Database.db";
    private static final String UID = "uid";
    private static final String FIRST_NAME = "FIRST_NAME";
    private static final String LAST_NAME = "LAST_NAME";
    private static final String USERS_TABLE = "users";
    private static final String FAVORITES_TABLE = "favorites";
    private static final String WORKOUT = "WORKOUT";
    private static final String IMAGE1 = "IMAGE1";
    private static final String IMAGE2 = "IMAGE2";
    private static final String VIDEO = "VIDEO";
    private static final String TAG = "TAG";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d(TAG, "*** onCreate Database ***");
        sqLiteDatabase.execSQL("create table users ( ID INTEGER PRIMARY KEY AUTOINCREMENT, UID VARCHAR, FIRST_NAME VARCHAR, LAST_NAME VARCHAR);");
        sqLiteDatabase.execSQL("create table favorites (ID INTEGER PRIMARY KEY AUTOINCREMENT, UID VARCHAR, WORKOUT VARCHAR, IMAGE1 BLOB, IMAGE2 BLOB, VIDEO VARCHAR);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + USERS_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FAVORITES_TABLE);
        onCreate(sqLiteDatabase);
    }


    public boolean addUser(String uid, String firstName, String lastName){
        Log.d(TAG, "*** addUser ***");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(UID, uid);
        values.put(FIRST_NAME, firstName);
        values.put(LAST_NAME, lastName);

        long result = db.insert(USERS_TABLE, null, values);
        Log.d(TAG, "*** addUser RESULT: " + result);
        db.close();
        if(result == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public boolean addFavorite(String uid, String workout, Bitmap image1, Bitmap image2/*, String video*/){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        byte[] image1AsBytes = getBitmapAsByteArray(image1);
        byte[] image2AsBytes = getBitmapAsByteArray(image2);

        values.put(UID, uid);
        values.put(WORKOUT, workout.replace("'", ""));
        values.put(IMAGE1, image1AsBytes);
        values.put(IMAGE2, image2AsBytes);
        //values.put(VIDEO, video);

        long result = db.insert(FAVORITES_TABLE, null, values);
        Log.d(TAG, "*** addFavorite RESULT: " + result);
        db.close();
        if(result == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public void removeFavorite(String uid, String workout){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + FAVORITES_TABLE + " WHERE UID = '" + uid + "' AND WORKOUT = '" + workout + "'");
    }

    public int getUserCount(){
        int count = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) AS count FROM users", null);
        if(cursor.moveToFirst()){
            count = cursor.getInt(cursor.getColumnIndex("count"));
            Log.d("TAG", "*** USER COUNT: " + count);
        }
        cursor.close();
        db.close();
        return count;
    }

    public int getFavoritesCount(String uid){
        int count = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) AS count FROM favorites WHERE uid = '" + uid + "'", null);
        if (cursor.moveToFirst()){
            count = cursor.getInt(cursor.getColumnIndex("count"));
            Log.d("TAG", "*** FAVORITES COUNT: " + count);
        }
        cursor.close();
        db.close();
        return count;
    }

    public ArrayList<Workout> getFavorites(String uid){
        ArrayList<Workout> favorites = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT WORKOUT, IMAGE1, IMAGE2 FROM favorites WHERE uid = '" + uid + "'", null);
        while (cursor.moveToNext()){
            String curWorkout = cursor.getString(cursor.getColumnIndex("WORKOUT"));
            byte[] curImage1 = cursor.getBlob(cursor.getColumnIndex("IMAGE1"));
            byte[] curImage2 = cursor.getBlob(cursor.getColumnIndex("IMAGE2"));
            Bitmap image1AsBitmap = BitmapFactory.decodeByteArray(curImage1, 0, curImage1.length);
            Bitmap image2AsBitmap = BitmapFactory.decodeByteArray(curImage2, 0, curImage2.length);
            Workout curFavoriteWorkout = new Workout(curWorkout, image1AsBitmap, image2AsBitmap);
            favorites.add(curFavoriteWorkout);
        }
        cursor.close();
        db.close();
        return favorites;
    }

    public boolean isFavoritedByUser(String uid, String workoutName){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT WORKOUT FROM favorites WHERE uid = '" + uid + "' AND WORKOUT = '" + workoutName.replace("'", "") + "'", null);
        if(cursor.moveToFirst()){
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }

    public void removeTables(){
        SQLiteDatabase db = this.getWritableDatabase();
        onUpgrade(db, 1, 1);
        db.close();
    }

    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }
}
