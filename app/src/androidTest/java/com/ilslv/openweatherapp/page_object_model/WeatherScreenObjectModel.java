package com.ilslv.openweatherapp.page_object_model;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;

import com.ilslv.openweatherapp.R;

public class WeatherScreenObjectModel {

    public WeatherScreenObjectModel startApplication() {
        Espresso.onView(ViewMatchers.withId(R.id.toolbar)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        return this;
    }

    public WeatherScreenObjectModel checkThatWeatherInfoVisible() {
        Espresso.onView(ViewMatchers.withId(R.id.current_temperature)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        return this;
    }

}
