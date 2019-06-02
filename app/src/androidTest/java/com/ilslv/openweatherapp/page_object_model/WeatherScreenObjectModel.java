package com.ilslv.openweatherapp.page_object_model;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.RootMatchers;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;

import com.ilslv.openweatherapp.R;

import org.hamcrest.Matchers;

import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;

public class WeatherScreenObjectModel {

    public WeatherScreenObjectModel startApplication() {
        Espresso.onView(ViewMatchers.withId(R.id.toolbar)).check(matches(isDisplayed()));
        return this;
    }

    public WeatherScreenObjectModel checkThatWeatherInfoVisible() {
        Espresso.onView(ViewMatchers.withId(R.id.current_temperature)).check(matches(isDisplayed()));
        return this;
    }

    public CityDialogObjectModel clickCityDialog() {
        Espresso.onView(ViewMatchers.withId(R.id.person_city)).perform(ViewActions.click());
        return new CityDialogObjectModel();
    }

    public WeatherScreenObjectModel checkError(ActivityTestRule rule) {
        Espresso.onView(ViewMatchers.withText(R.string.error_hint)).inRoot(RootMatchers.withDecorView(Matchers.not(Matchers.is(rule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
        return this;
    }

    public CityPickerScreenObjectModel clickCityList() {
        Espresso.onView(ViewMatchers.withId(R.id.city_list)).perform(ViewActions.click());
        return new CityPickerScreenObjectModel();
    }

}
