package com.ilslv.openweatherapp.data.repository;

import com.ilslv.openweatherapp.data.dto.CachedWeatherDto;

public interface WeatherRepository {

    CachedWeatherDto getWeatherByCity(String cityName);

    CachedWeatherDto getWeatherByLocation(Double latitude, Double longitude);

}
