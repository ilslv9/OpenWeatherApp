package com.ilslv.openweatherapp.presentation.mvp.view;

import com.ilslv.openweatherapp.data.dto.InfoDto;

public interface WeatherView {
    void showError(String errorMessage);

    void showWeatherInfo(InfoDto info);

    void showLoading(Boolean isLoading);
}
