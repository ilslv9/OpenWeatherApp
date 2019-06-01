package com.ilslv.openweatherapp.data.dto;

import com.google.gson.annotations.SerializedName;

public class InfoDto {
    @SerializedName("weather")
    private WeatherInfoDto weatherInfo;
    @SerializedName("main")
    private WeatherDto weather;
}