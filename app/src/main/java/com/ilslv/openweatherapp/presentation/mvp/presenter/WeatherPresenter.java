package com.ilslv.openweatherapp.presentation.mvp.presenter;

import android.annotation.SuppressLint;
import android.location.Location;
import android.os.AsyncTask;

import com.ilslv.openweatherapp.data.dto.InfoDto;
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

    public WeatherPresenter(WeatherInteractor weatherInteractor) {
        this.weatherInteractor = weatherInteractor;
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
    public void onCityPicked(final String cityName) {
        view.showLoading(true);
        new AsyncTask<Void, Void, InfoDto>() {
            @Override
            protected InfoDto doInBackground(Void... nothing) {
                return weatherInteractor.loadWeatherByCity(cityName);
            }

            @Override
            protected void onPostExecute(InfoDto info) {
                super.onPostExecute(info);
                view.showLoading(false);
                if (info != null) {
                    view.showWeatherInfo(info);
                } else {
                    view.showError("Can't loading weather :(");
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
        new AsyncTask<Void, Void, InfoDto>() {
            @Override
            protected InfoDto doInBackground(Void... nothing) {
                return weatherInteractor.loadWeatherByLocation(location);
            }

            @Override
            protected void onPostExecute(InfoDto info) {
                super.onPostExecute(info);
                view.showLoading(false);
                if (info != null) {
                    view.showWeatherInfo(info);
                } else {
                    view.showError("Can't loading weather :(");
                }
            }
        }.execute();
    }

    /**
     * Avoid memory leak
     */
    public void detachView() {
        view = null;
    }

}
