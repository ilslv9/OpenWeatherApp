package com.ilslv.openweatherapp.domain;

import android.location.Location;

import com.ilslv.openweatherapp.data.dto.InfoDto;
import com.ilslv.openweatherapp.data.repository.WeatherRepository;

public class WeatherInteractor {

    /**
     * Weather remote data source
     */
    private WeatherRepository repository;

    public WeatherInteractor(WeatherRepository repository) {
        this.repository = repository;
    }

    /**
     * Loading weather from server by city
     * @param cityName city name for query
     * @return Dto with weather info
     */
    public InfoDto loadWeatherByCity(String cityName) {
        return repository.getWeatherByCity(cityName);
    }

    /**
     * Loading weather from server by user location
     * @param location user location
     * @return Dto with weather info
     */
    public InfoDto loadWeatherByLocation(Location location) {
        return repository.getWeatherByLocation(location.getLatitude(), location.getLongitude());
    }

}
