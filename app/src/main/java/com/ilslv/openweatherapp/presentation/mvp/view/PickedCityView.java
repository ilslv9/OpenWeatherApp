package com.ilslv.openweatherapp.presentation.mvp.view;

import java.util.List;

public interface PickedCityView {
    void showEmpty();

    void showPickedCities(List<String> cities);
}
