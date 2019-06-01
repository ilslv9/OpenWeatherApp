package com.ilslv.openweatherapp.data.dto;

public class WindInfoDto {
    private double speed;

    public WindInfoDto(double speed, int degree) {
        this.speed = speed;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}
