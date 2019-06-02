package com.ilslv.openweatherapp.presentation.mvp.view;

import com.ilslv.openweatherapp.data.dto.CachedWeatherDto;

public interface WeatherView {
    void showError(String errorMessage);

    void showWeatherInfo(CachedWeatherDto info, boolean isFromCache);

    void showLoading(Boolean isLoading);
}
