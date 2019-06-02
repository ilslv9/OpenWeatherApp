package com.ilslv.openweatherapp.page_object_model;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;

import com.ilslv.openweatherapp.R;

public class CityDialogObjectModel {
    public CityDialogObjectModel inputCity(String cityName) {
        Espresso.onView(ViewMatchers.withId(R.id.city_input_edt)).perform(ViewActions.typeText(cityName));
        return this;
    }

    public WeatherScreenObjectModel pickCity() {
        Espresso.onView(ViewMatchers.withText("Pick")).perform(ViewActions.click());
        return new WeatherScreenObjectModel();
    }
}
