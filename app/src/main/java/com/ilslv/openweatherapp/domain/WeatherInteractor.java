package com.ilslv.openweatherapp.domain;

import com.ilslv.openweatherapp.data.dto.InfoDto;
import com.ilslv.openweatherapp.data.repository.WeatherRepository;

public class WeatherInteractor {

    private WeatherRepository repository;

    public WeatherInteractor(WeatherRepository repository) {
        this.repository = repository;
    }

    public InfoDto loadWeatherByCity(String cityName) {
        return repository.getWeatherByCity(cityName);
    }

}
