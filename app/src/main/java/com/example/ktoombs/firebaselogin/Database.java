package com.example.ktoombs.firebaselogin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
        sqLiteDatabase.execSQL("create table favorites (ID INTEGER PRIMARY KEY AUTOINCREMENT, UID VARCHAR, WORKOUT VARCHAR, IMAGE1 VARCHAR, IMAGE2 VARCHAR, VIDEO VARCHAR);");
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

    public boolean addFavorite(String uid, String workout, String image1, String image2/*, String video*/){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(UID, uid);
        values.put(WORKOUT, workout);
        values.put(IMAGE1, image1);
        values.put(IMAGE2, image2);
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

    public int getUserCount(){
        int count = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) AS count FROM users", null);
        if(cursor.moveToFirst()){
            count = cursor.getInt(cursor.getColumnIndex("count"));
            Log.d("TAG", "*** USER COUNT: " + count);
        }
        return count;
    }

    public int getFavoritesCount(String uid){
        int count = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) AS count FROM favorites WHERE uid = '" + uid + "'", null);
        if (cursor.moveToFirst()){
            count = cursor.getInt(cursor.getColumnIndex("count"));
            Log.d("TAG", "*** FAVORITES COUNT: " + count);
        }
        return count;
    }
}
