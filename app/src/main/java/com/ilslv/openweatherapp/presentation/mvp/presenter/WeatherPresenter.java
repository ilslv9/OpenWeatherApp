package com.ilslv.openweatherapp.presentation.mvp.presenter;

import android.annotation.SuppressLint;
import android.location.Location;
import android.os.AsyncTask;

import com.ilslv.openweatherapp.data.database.dao.CachedWeatherDao;
import com.ilslv.openweatherapp.data.database.dao.PickedCitiesDao;
import com.ilslv.openweatherapp.data.dto.CachedWeatherDto;
import com.ilslv.openweatherapp.domain.WeatherInteractor;
import com.ilslv.openweatherapp.presentation.mvp.view.WeatherView;

public class WeatherPresenter {

    /**
     * Weather use case
     */
    private WeatherInteractor weatherInteractor;
    /**
     * View interface for contract
     */
    private WeatherView view;

    /**
     * Dao for saving picked cities
     */
    private PickedCitiesDao dao;

    /**
     * Dao for weather cash
     */
    private CachedWeatherDao weatherDao;

    public WeatherPresenter(WeatherInteractor weatherInteractor, PickedCitiesDao dao, CachedWeatherDao weatherDao) {
        this.weatherInteractor = weatherInteractor;
        this.dao = dao;
        this.weatherDao = weatherDao;
    }

    /**
     * Attach view callback for presenter
     *
     * @param view callback
     */
    public void attachView(WeatherView view) {
        this.view = view;
    }

    /**
     * User picked city - try to load weather info
     *
     * @param cityName city name of user picked city
     */
    @SuppressLint("StaticFieldLeak")
    public void onCityPicked(final String cityName, final boolean isCityShouldBeSaved) {
        view.showLoading(true);
        if (isCityShouldBeSaved) {
            dao.insertNewCity(cityName);
        }
        new AsyncTask<Void, Void, CachedWeatherDto>() {
            @Override
            protected CachedWeatherDto doInBackground(Void... nothing) {
                return weatherInteractor.loadWeatherByCity(cityName);
            }

            @Override
            protected void onPostExecute(CachedWeatherDto info) {
                super.onPostExecute(info);
                view.showLoading(false);
                if (info != null) {
                    weatherDao.saveWeather(info);
                    view.showWeatherInfo(info, false);
                } else {
                    CachedWeatherDto cashedInfo = weatherDao.getCashedWeatherIfExists();
                    if (cashedInfo != null) {
                        view.showWeatherInfo(cashedInfo, true);
                    } else {
                        view.showError("Can't loading weather :(");
                    }
                }
            }
        }.execute();
    }

    /**
     * User open application and if GPS is enabled than try to load weather info
     *
     * @param location user location
     */
    @SuppressLint("StaticFieldLeak")
    public void initWeather(final Location location) {
        view.showLoading(true);
        new AsyncTask<Void, Void, CachedWeatherDto>() {
            @Override
            protected CachedWeatherDto doInBackground(Void... nothing) {
                return weatherInteractor.loadWeatherByLocation(location);
            }

            @Override
            protected void onPostExecute(CachedWeatherDto info) {
                super.onPostExecute(info);
                view.showLoading(false);
                if (info != null) {
                    weatherDao.saveWeather(info);
                    view.showWeatherInfo(info, false);
                } else {
                    CachedWeatherDto cashedInfo = weatherDao.getCashedWeatherIfExists();
                    if (cashedInfo != null) {
                        view.showWeatherInfo(cashedInfo, true);
                    } else {
                        view.showError("Can't loading weather :(");
                    }
                }
            }
        }.execute();
    }

    /**
     * Avoid memory leak
     */
    public void detachView() {
        dao.closeDatabase();
        weatherDao.closeDatabase();
        view = null;
    }

}
