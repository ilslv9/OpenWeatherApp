package com.ilslv.openweatherapp.data;

import com.google.gson.annotations.SerializedName;

public class WindInfoDto {
    private int speed;
    @SerializedName("deg")
    private int degree;

    public WindInfoDto(int speed, int degree) {
        this.speed = speed;
        this.degree = degree;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }
}
