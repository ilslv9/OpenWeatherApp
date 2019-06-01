package com.ilslv.openweatherapp.data.repository;

import com.ilslv.openweatherapp.data.dto.InfoDto;

public interface WeatherRepository {

    InfoDto getWeatherByCity(String cityName);
    InfoDto getWeatherByLocation(Double latitude, Double longitude);

}
