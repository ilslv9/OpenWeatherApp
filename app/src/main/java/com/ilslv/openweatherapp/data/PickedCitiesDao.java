package com.ilslv.openweatherapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class PickedCitiesDao {

    private SQLiteDatabase database;

    public PickedCitiesDao(Context context) {
        database = new DatabaseHelper(context).getWritableDatabase();
    }

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

    public void insertNewCity(String cityName) {
        ContentValues cv = new ContentValues();
        cv.put("cityName", cityName);
        long rowID = database.insert("picked_cities", null, cv);
    }

    public void closeDatabase() {
        database.close();
    }

}
