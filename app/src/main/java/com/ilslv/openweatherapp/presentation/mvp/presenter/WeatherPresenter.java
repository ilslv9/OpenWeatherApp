package com.ilslv.openweatherapp.presentation.mvp.presenter;

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
    public void onCityPicked(final String cityName, final boolean isCityShouldBeSaved) {
        view.showLoading(true);
        if (isCityShouldBeSaved) {
            dao.insertNewCity(cityName);
        }
        new LoadWeatherByCity(view, weatherInteractor, weatherDao).execute(cityName);
    }

    /**
     * User open application and if GPS is enabled than try to load weather info
     *
     * @param location user location
     */
    public void initWeather(final Location location) {
        view.showLoading(true);
        new LoadWeatherByLocation(view, weatherInteractor, weatherDao).execute(location);
    }

    /**
     * Avoid memory leak
     */
    public void detachView() {
        dao.closeDatabase();
        weatherDao.closeDatabase();
        view = null;
    }

    private static final class LoadWeatherByLocation extends AsyncTask<Location, Void, CachedWeatherDto> {

        private WeatherView view;
        private WeatherInteractor interactor;
        private CachedWeatherDao dao;

        LoadWeatherByLocation(WeatherView view, WeatherInteractor interactor, CachedWeatherDao dao) {
            this.view = view;
            this.interactor = interactor;
            this.dao = dao;
        }

        @Override
        protected CachedWeatherDto doInBackground(Location... locations) {
            return interactor.loadWeatherByLocation(locations[0]);
        }

        @Override
        protected void onPostExecute(CachedWeatherDto info) {
            super.onPostExecute(info);
            if (view != null) {
                view.showLoading(false);
                if (info != null) {
                    dao.saveWeather(info);
                    view.showWeatherInfo(info, false);
                } else {
                    CachedWeatherDto cashedInfo = dao.getCashedWeatherIfExists();
                    if (cashedInfo != null) {
                        view.showWeatherInfo(cashedInfo, true);
                    } else {
                        view.showError("Can't loading weather :(");
                    }
                }
            }
        }
    }

    private static final class LoadWeatherByCity extends AsyncTask<String, Void, CachedWeatherDto> {
        private WeatherView view;
        private WeatherInteractor interactor;
        private CachedWeatherDao dao;

        LoadWeatherByCity(WeatherView view, WeatherInteractor interactor, CachedWeatherDao dao) {
            this.view = view;
            this.interactor = interactor;
            this.dao = dao;
        }

        @Override
        protected CachedWeatherDto doInBackground(String... cities) {
            return interactor.loadWeatherByCity(cities[0]);
        }

        @Override
        protected void onPostExecute(CachedWeatherDto info) {
            super.onPostExecute(info);
            if (view != null) {
                view.showLoading(false);
                if (info != null) {
                    dao.saveWeather(info);
                    view.showWeatherInfo(info, false);
                } else {
                    CachedWeatherDto cashedInfo = dao.getCashedWeatherIfExists();
                    if (cashedInfo != null) {
                        view.showWeatherInfo(cashedInfo, true);
                    } else {
                        view.showError("Can't loading weather :(");
                    }
                }
            }
        }
    }

}
