package com.ilslv.openweatherapp.data.dto;

public class CachedWeatherDto {

    private String city;
    private long date;
    private double temperature;
    private double pressure;
    private double maxTemp;
    private double minTemp;
    private double humidity;
    private String title;
    private String description;
    private String country;
    private double speed;
    private double percent;

    public CachedWeatherDto(String city,
                            long date,
                            double temperature,
                            double pressure,
                            double maxTemp,
                            double minTemp,
                            double humidity,
                            String title,
                            String description,
                            String country,
                            double speed,
                            double percent) {
        this.city = city;
        this.date = date;
        this.temperature = temperature;
        this.pressure = pressure;
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
        this.humidity = humidity;
        this.title = title;
        this.description = description;
        this.country = country;
        this.speed = speed;
        this.percent = percent;
    }

    public CachedWeatherDto() {

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }
}
