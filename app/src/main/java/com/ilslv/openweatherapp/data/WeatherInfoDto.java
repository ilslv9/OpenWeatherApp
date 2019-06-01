package com.ilslv.openweatherapp.data;

import com.google.gson.annotations.SerializedName;

public class WeatherInfoDto {
    private Long id;
    @SerializedName("main")
    private String title;
    private String description;
    private String icon;

    public WeatherInfoDto(Long id, String title, String description, String icon) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.icon = icon;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
