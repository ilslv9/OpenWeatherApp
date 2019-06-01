package com.ilslv.openweatherapp.data.dto;

public class OtherInfoDto {

    private String country;

    public OtherInfoDto(String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
