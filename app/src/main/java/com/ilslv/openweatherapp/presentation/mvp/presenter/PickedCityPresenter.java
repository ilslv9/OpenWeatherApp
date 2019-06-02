package com.ilslv.openweatherapp.presentation.mvp.presenter;

import com.ilslv.openweatherapp.data.PickedCitiesDao;
import com.ilslv.openweatherapp.presentation.mvp.view.PickedCityView;

import java.util.List;

public class PickedCityPresenter {

    private PickedCitiesDao dao;
    private PickedCityView view;

    public PickedCityPresenter(PickedCitiesDao dao) {
        this.dao = dao;
    }

    public void attachView(PickedCityView view) {
        this.view = view;
    }

    public void detachView() {
        view = null;
    }

    public void getPickedCities() {
        List<String> cities = dao.getPickedCities();
        if (cities.isEmpty()) {
            view.showEmpty();
        } else {
            view.showPickedCities(cities);
        }
    }

    public void insertPickedCity(String cityName) {
        dao.insertNewCity(cityName);
    }
}
