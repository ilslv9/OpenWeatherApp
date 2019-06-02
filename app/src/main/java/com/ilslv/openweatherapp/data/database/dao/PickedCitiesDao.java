package com.ilslv.openweatherapp.data.database.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ilslv.openweatherapp.data.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class PickedCitiesDao {

    /**
     * database connection
     */
    private SQLiteDatabase database;

    public PickedCitiesDao(DatabaseHelper helper) {
        database = helper.getWritableDatabase();
    }

    /**
     * load picked cities from database
     *
     * @return cities list or empty list
     */
    public List<String> getPickedCities() {
        List<String> list = new ArrayList<>();
        String sqlQueryText = "SELECT cityName FROM picked_cities";
        Cursor cursor = this.database.rawQuery(sqlQueryText, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    /**
     * insert new city in database
     *
     * @param cityName name of city
     */
    public void insertNewCity(String cityName) {
        ContentValues cv = new ContentValues();
        cv.put("cityName", cityName);
        long rowID = database.insert("picked_cities", null, cv);
    }

    /**
     * close database connection
     */
    public void closeDatabase() {
        database.close();
    }

}
