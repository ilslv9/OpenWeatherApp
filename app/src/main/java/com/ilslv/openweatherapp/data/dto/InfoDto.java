package com.ilslv.openweatherapp.data.dto;

import com.google.gson.annotations.SerializedName;

public class InfoDto {

    @SerializedName("weather")
    private WeatherInfoDto[] weatherInfo;
    @SerializedName("main")
    private WeatherDto weather;
    @SerializedName("sys")
    private OtherInfoDto otherInfo;
    @SerializedName("wind")
    private WindInfoDto windInfo;
    @SerializedName("name")
    private String city;
    @SerializedName("dt")
    private long date;
    @SerializedName("clouds")
    private CloudDto clouds;

    public InfoDto(WeatherInfoDto[] weatherInfo,
                   WeatherDto weather,
                   OtherInfoDto otherInfo,
                   WindInfoDto windInfo,
                   String city,
                   long date,
                   CloudDto clouds) {
        this.weatherInfo = weatherInfo;
        this.weather = weather;
        this.otherInfo = otherInfo;
        this.windInfo = windInfo;
        this.city = city;
        this.date = date;
        this.clouds = clouds;
    }

    public WeatherInfoDto[] getWeatherInfo() {
        return weatherInfo;
    }

    public void setWeatherInfo(WeatherInfoDto[] weatherInfo) {
        this.weatherInfo = weatherInfo;
    }

    public WeatherDto getWeather() {
        return weather;
    }

    public void setWeather(WeatherDto weather) {
        this.weather = weather;
    }

    public OtherInfoDto getOtherInfo() {
        return otherInfo;
    }

    public void setOtherInfo(OtherInfoDto otherInfo) {
        this.otherInfo = otherInfo;
    }

    public WindInfoDto getWindInfo() {
        return windInfo;
    }

    public void setWindInfo(WindInfoDto windInfo) {
        this.windInfo = windInfo;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public CloudDto getClouds() {
        return clouds;
    }

    public void setClouds(CloudDto clouds) {
        this.clouds = clouds;
    }
}
