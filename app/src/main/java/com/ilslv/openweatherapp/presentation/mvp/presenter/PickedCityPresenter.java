package com.ilslv.openweatherapp.presentation.mvp.presenter;

import com.ilslv.openweatherapp.data.database.dao.PickedCitiesDao;
import com.ilslv.openweatherapp.presentation.mvp.view.PickedCityView;

import java.util.List;

public class PickedCityPresenter {

    /**
     * Dao for saving picked cities
     */
    private PickedCitiesDao dao;

    /**
     * View interface for contract
     */
    private PickedCityView view;

    public PickedCityPresenter(PickedCitiesDao dao) {
        this.dao = dao;
    }

    /**
     * Attach view callback for presenter
     *
     * @param view callback
     */
    public void attachView(PickedCityView view) {
        this.view = view;
    }

    /**
     * Avoid memory leak
     */
    public void detachView() {
        dao.closeDatabase();
        view = null;
    }

    /**
     * Load recently picked cities
     */
    public void getPickedCities() {
        List<String> cities = dao.getPickedCities();
        if (cities.isEmpty()) {
            view.showEmpty();
        } else {
            view.showPickedCities(cities);
        }
    }

    /**
     * insert new city name
     *
     * @param cityName name of city
     */
    public void insertPickedCity(String cityName) {
        dao.insertNewCity(cityName);
    }
}
