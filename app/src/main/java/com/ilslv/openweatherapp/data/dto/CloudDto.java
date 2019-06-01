package com.ilslv.openweatherapp.data.dto;

import com.google.gson.annotations.SerializedName;

public class CloudDto {
    @SerializedName("all")
    private double percent;

    public CloudDto(double percent) {
        this.percent = percent;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }
}
