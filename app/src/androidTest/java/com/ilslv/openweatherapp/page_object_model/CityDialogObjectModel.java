package com.ilslv.openweatherapp.page_object_model;

public class CityDialogObjectModel {
    public CityDialogObjectModel inputCity(String cityName) {

        return this;
    }

    public WeatherScreenObjectModel pickCity() {

        return new WeatherScreenObjectModel();
    }
}
