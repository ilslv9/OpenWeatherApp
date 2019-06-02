package com.ilslv.openweatherapp.data.database.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import com.ilslv.openweatherapp.data.database.DatabaseHelper;
import com.ilslv.openweatherapp.data.dto.CachedWeatherDto;

public class CachedWeatherDao {

    /**
     * database connection
     */
    private SQLiteDatabase database;

    public CachedWeatherDao(DatabaseHelper helper) {
        database = helper.getWritableDatabase();
    }

    /**
     * Load weather info from cache
     * @return weather info
     */
    public CachedWeatherDto getCashedWeatherIfExists() {
        CachedWeatherDto dto = new CachedWeatherDto();
        int rows = (int) DatabaseUtils.queryNumEntries(database, "cashed_weather");
        if (rows == 0) {
            return null;
        } else {
            String sqlQueryText = "SELECT city, date, temperature, pressure, maxTemp, minTemp, humidity, title, description, country, speed, percent FROM cashed_weather";
            Cursor cursor = this.database.rawQuery(sqlQueryText, null);
            if (cursor != null) {
                cursor.moveToFirst();
                dto.setCity(cursor.getString(cursor.getColumnIndexOrThrow("city")));
                dto.setDate(cursor.getLong(cursor.getColumnIndexOrThrow("date")));
                dto.setTemperature(cursor.getDouble(cursor.getColumnIndexOrThrow("temperature")));
                dto.setPressure(cursor.getDouble(cursor.getColumnIndexOrThrow("pressure")));
                dto.setMaxTemp(cursor.getDouble(cursor.getColumnIndexOrThrow("maxTemp")));
                dto.setMinTemp(cursor.getDouble(cursor.getColumnIndexOrThrow("minTemp")));
                dto.setHumidity(cursor.getDouble(cursor.getColumnIndexOrThrow("humidity")));
                dto.setTitle(cursor.getString(cursor.getColumnIndexOrThrow("title")));
                dto.setDescription(cursor.getString(cursor.getColumnIndexOrThrow("description")));
                dto.setCountry(cursor.getString(cursor.getColumnIndexOrThrow("country")));
                dto.setSpeed(cursor.getDouble(cursor.getColumnIndexOrThrow("speed")));
                dto.setPercent(cursor.getDouble(cursor.getColumnIndexOrThrow("percent")));
                cursor.close();
                return dto;
            } else {
                return null;
            }
        }
    }

    /**
     * Save last loaded weather info
     * @param dto weather info
     */
    public void saveWeather(CachedWeatherDto dto) {
        database.execSQL("DELETE FROM cashed_weather");
        ContentValues values = new ContentValues();
        values.put("city", dto.getCity());
        values.put("date", dto.getDate());
        values.put("temperature", dto.getTemperature());
        values.put("pressure", dto.getPressure());
        values.put("maxTemp", dto.getMaxTemp());
        values.put("minTemp", dto.getMinTemp());
        values.put("humidity", dto.getHumidity());
        values.put("title", dto.getTitle());
        values.put("description", dto.getDescription());
        values.put("country", dto.getCountry());
        values.put("speed", dto.getSpeed());
        values.put("percent", dto.getPercent());
        long rowId = database.insert("cashed_weather", null, values);
    }

    /**
     * close database connection
     */
    public void closeDatabase() {
        database.close();
    }
}
