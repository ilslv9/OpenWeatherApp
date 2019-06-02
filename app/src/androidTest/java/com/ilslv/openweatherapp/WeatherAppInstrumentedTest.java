package com.ilslv.openweatherapp;

import android.Manifest;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.GrantPermissionRule;
import android.support.test.runner.AndroidJUnit4;

import com.ilslv.openweatherapp.page_object_model.CityPickerScreenObjectModel;
import com.ilslv.openweatherapp.page_object_model.WeatherScreenObjectModel;
import com.ilslv.openweatherapp.presentation.ui.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class WeatherAppInstrumentedTest {

    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

    @Rule
    public GrantPermissionRule grantPermissionRuleAfl = GrantPermissionRule.grant(android.Manifest.permission.ACCESS_FINE_LOCATION);
    @Rule
    public GrantPermissionRule grantPermissionRuleAcl = GrantPermissionRule.grant(Manifest.permission.ACCESS_COARSE_LOCATION);
    @Rule
    public GrantPermissionRule grantPermissionRuleInternet = GrantPermissionRule.grant(Manifest.permission.INTERNET);

    private CityPickerScreenObjectModel cityPickerScreenObjectModel;
    private WeatherScreenObjectModel weatherScreenObjectModel;

    @Before
    public void initObjectModels() {
        cityPickerScreenObjectModel = new CityPickerScreenObjectModel();
        weatherScreenObjectModel = new WeatherScreenObjectModel();
    }

    @Test
    public void onStartApplication() {
        weatherScreenObjectModel.startApplication()
                .checkThatWeatherInfoVisible();
    }

    @Test
    public void onCityClicked() {

    }
}
