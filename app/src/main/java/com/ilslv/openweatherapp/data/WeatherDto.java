package com.ilslv.openweatherapp.data;

import com.google.gson.annotations.SerializedName;

public class WeatherDto {
    @SerializedName("temp")
    private double temperature;
    private double pressure;
    @SerializedName("temp_max")
    private double maxTemp;
    @SerializedName("temp_min")
    private double minTemp;
    private double humidity;

    public WeatherDto(double temperature, double pressure, double maxTemp, double minTemp, double humidity) {
        this.temperature = temperature;
        this.pressure = pressure;
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
        this.humidity = humidity;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public double getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(double maxTemp) {
        this.maxTemp = maxTemp;
    }

    public double getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(double minTemp) {
        this.minTemp = minTemp;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }
}
