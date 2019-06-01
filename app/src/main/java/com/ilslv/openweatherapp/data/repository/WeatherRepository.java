package com.ilslv.openweatherapp.data.repository;

import android.location.Location;

import com.ilslv.openweatherapp.data.dto.InfoDto;

public interface WeatherRepository {

    InfoDto getWeatherByCity(String cityName);
    InfoDto getCityByLocation(Location userLocation);

}
