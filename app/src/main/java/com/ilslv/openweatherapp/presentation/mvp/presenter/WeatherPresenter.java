package com.ilslv.openweatherapp.presentation.mvp.presenter;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import com.ilslv.openweatherapp.data.dto.InfoDto;
import com.ilslv.openweatherapp.domain.WeatherInteractor;
import com.ilslv.openweatherapp.presentation.mvp.view.WeatherView;

public class WeatherPresenter {

    private WeatherInteractor weatherInteractor;
    private WeatherView view;

    public WeatherPresenter(WeatherInteractor weatherInteractor) {
        this.weatherInteractor = weatherInteractor;
    }

    public void attachView(WeatherView view) {
        this.view = view;
    }

    @SuppressLint("StaticFieldLeak")
    public void onCityPecked(final String cityName) {
        new AsyncTask<Void, Void, InfoDto>() {
            @Override
            protected InfoDto doInBackground(Void... nothing) {
                return weatherInteractor.loadWeatherByCity(cityName);
            }

            @Override
            protected void onPostExecute(InfoDto info) {
                super.onPostExecute(info);
                view.showWeatherInfo(info);
            }
        }.execute();
    }

    public void dettachView() {
        view = null;
    }

}
