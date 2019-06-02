package com.ilslv.openweatherapp;

import android.Manifest;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.GrantPermissionRule;
import android.support.test.runner.AndroidJUnit4;

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

    private WeatherScreenObjectModel weatherScreenObjectModel;

    private static final String TEST_CITY = "MOSCOW";
    private static final String FAKE_CITY = "TEST";
    private static final String CITY_LIST_TEST_CITY = "LONDON";

    @Before
    public void initObjectModels() {
        weatherScreenObjectModel = new WeatherScreenObjectModel();
    }

    @Test
    public void onStartApplication() {
        weatherScreenObjectModel.startApplication()
                .checkThatWeatherInfoVisible();
    }

    @Test
    public void onCityClicked() {
        weatherScreenObjectModel.startApplication()
                .clickCityDialog()
                .inputCity(TEST_CITY)
                .pickCity()
                .checkThatWeatherInfoVisible();
    }

    @Test
    public void onFakeCityClick() {
        weatherScreenObjectModel.startApplication()
                .clickCityDialog()
                .inputCity(FAKE_CITY)
                .pickCity()
                .checkError(rule);
    }

    @Test
    public void onCityListClickAddFake() {
        weatherScreenObjectModel.startApplication()
                .clickCityList()
                .onClickAdd()
                .inputCity(FAKE_CITY)
                .pickCity()
                .checkError(rule);
    }

    @Test
    public void onCityListClickAddCity() {
        weatherScreenObjectModel.startApplication()
                .clickCityList()
                .onClickAdd()
                .inputCity(TEST_CITY)
                .pickCity()
                .checkThatWeatherInfoVisible();
    }

    @Test
    public void onCityPickedFromList() {
        weatherScreenObjectModel.startApplication()
                .clickCityDialog()
                .inputCity(CITY_LIST_TEST_CITY)
                .pickCity()
                .clickCityList()
                .onClickCityFromList(CITY_LIST_TEST_CITY)
                .checkThatWeatherInfoVisible();
    }
}
