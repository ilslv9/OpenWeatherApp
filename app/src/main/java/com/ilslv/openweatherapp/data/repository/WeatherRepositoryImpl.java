package com.ilslv.openweatherapp.data.repository;

import android.location.Location;

import com.ilslv.openweatherapp.data.dto.InfoDto;

public class WeatherRepositoryImpl implements WeatherRepository {

    public WeatherRepositoryImpl() {

    }

    @Override
    public InfoDto getWeatherByCity(String cityName) {
        return null;
    }

    @Override
    public InfoDto getCityByLocation(Location userLocation) {
        return null;
    }
}
