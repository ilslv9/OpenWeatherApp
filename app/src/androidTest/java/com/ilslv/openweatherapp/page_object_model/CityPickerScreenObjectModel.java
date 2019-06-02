package com.ilslv.openweatherapp.page_object_model;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;

import com.ilslv.openweatherapp.R;

public class CityPickerScreenObjectModel {
    public CityDialogObjectModel onClickAdd() {
        Espresso.onView(ViewMatchers.withId(R.id.add_new_city)).perform(ViewActions.click());
        return new CityDialogObjectModel();
    }

    public WeatherScreenObjectModel onClickCityFromList(String cityName) {
        Espresso.onView(ViewMatchers.withText(cityName)).perform(ViewActions.click());
        return new WeatherScreenObjectModel();
    }
}
