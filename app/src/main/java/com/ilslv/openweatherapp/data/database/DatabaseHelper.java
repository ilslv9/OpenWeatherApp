package com.ilslv.openweatherapp.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "weather.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table picked_cities("
                + "id integer primary key autoincrement,"
                + "cityName text" + ");");

        db.execSQL("create table cashed_weather("
                + "city text,"
                + "date integer,"
                + "temperature real,"
                + "pressure real,"
                + "maxTemp real,"
                + "minTemp real,"
                + "humidity real,"
                + "title text,"
                + "description text,"
                + "country text,"
                + "speed real,"
                + "percent real" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
