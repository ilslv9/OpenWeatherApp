package com.ilslv.openweatherapp.presentation.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ilslv.openweatherapp.R;
import com.ilslv.openweatherapp.data.database.DatabaseHelper;
import com.ilslv.openweatherapp.data.database.dao.CachedWeatherDao;
import com.ilslv.openweatherapp.data.database.dao.PickedCitiesDao;
import com.ilslv.openweatherapp.data.dto.CachedWeatherDto;
import com.ilslv.openweatherapp.data.repository.WeatherRepositoryImpl;
import com.ilslv.openweatherapp.domain.WeatherInteractor;
import com.ilslv.openweatherapp.presentation.mvp.presenter.WeatherPresenter;
import com.ilslv.openweatherapp.presentation.mvp.view.WeatherView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements WeatherView, LocationListener {

    private Group errorHint;
    private TextView errorText;
    private Group weatherInfoGroup;
    private Toolbar toolbar;
    private TextView currentTemp;
    private TextView weatherType;
    private TextView weatherDescription;
    private TextView tempFromTo;
    private TextView cloudPercent;
    private TextView windSpeed;
    private TextView pressure;
    private TextView date;
    private TextView locationHint;
    private ProgressBar progressBar;

    private WeatherPresenter presenter;
    private LocationManager locationManager;

    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 501;
    private static final int ACTIVITY_CITY_PICKER_RESULT_CODE = 401;

    private boolean isFromResult = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        setSupportActionBar(toolbar);
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        presenter = new WeatherPresenter(new WeatherInteractor(new WeatherRepositoryImpl()),
                new PickedCitiesDao(databaseHelper),
                new CachedWeatherDao(databaseHelper));
        presenter.attachView(this);
        initLocationManager();
    }

    @Override
    protected void onStop() {
        super.onStop();
        locationManager.removeUpdates(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.person_location:
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Sorry :( But we can't detect your location without permissions, try to load weather by city", Toast.LENGTH_SHORT).show();
                    return true;
                }
                locationHint.setText(getString(R.string.location_gps_hint));
                presenter.initWeather(locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER));
                return true;
            case R.id.person_city:
                showCityPickerDialog();
                return true;
            case R.id.city_list:
                startActivityForResult(PickedCityListActivity.createIntent(getApplicationContext()), ACTIVITY_CITY_PICKER_RESULT_CODE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    initLocationManager();
                } else {
                    showCityPickerDialog();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == ACTIVITY_CITY_PICKER_RESULT_CODE) {
            isFromResult = true;
            locationHint.setText(getString(R.string.location_city_hint));
            String city = data.getStringExtra(PickedCityListActivity.BUNDLE_CITY_NAME);
            presenter.onCityPicked(city, false);
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void showError(String errorMessage) {
        weatherInfoGroup.setVisibility(View.GONE);
        errorHint.setVisibility(View.VISIBLE);
        errorText.setText(errorMessage);
        getSupportActionBar().setTitle(getString(R.string.app_name));
    }

    @Override
    public void showWeatherInfo(CachedWeatherDto info, boolean isFromCached) {
        if (isFromCached) {
            Toast.makeText(this, "You not connect to internet. We loaded last weather info", Toast.LENGTH_SHORT).show();
        }
        errorHint.setVisibility(View.GONE);
        weatherInfoGroup.setVisibility(View.VISIBLE);
        getSupportActionBar().setTitle(String.format("%s, %s", info.getCity(), info.getCountry()));
        currentTemp.setText(String.format("%s\u02daC", String.valueOf(info.getTemperature())));
        weatherType.setText(info.getTitle());
        weatherDescription.setText(info.getDescription());
        tempFromTo.setText(String.format("from %s to %s", info.getMinTemp(), info.getMaxTemp()));
        cloudPercent.setText(String.format("%s%%", String.valueOf(info.getPercent())));
        windSpeed.setText(String.format("%s m/s", String.valueOf(info.getSpeed())));
        pressure.setText(String.format("%s pha", String.valueOf(info.getPressure())));
        Date weatherDate = new Date(info.getDate() * 1000);
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.ENGLISH);
        date.setText(format.format(weatherDate));
    }

    @Override
    public void showLoading(Boolean isLoading) {
        if (isLoading) {
            progressBar.setVisibility(View.VISIBLE);
            errorHint.setVisibility(View.GONE);
            weatherInfoGroup.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            errorHint.setVisibility(View.VISIBLE);
            weatherInfoGroup.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        locationHint.setText(getString(R.string.location_gps_hint));
        presenter.initWeather(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        /*nothing*/
    }

    @Override
    public void onProviderEnabled(String provider) {
        /*nothing*/
    }

    @Override
    public void onProviderDisabled(String provider) {
        /*nothing*/
    }

    /**
     * Init views from activity_main
     */
    private void initViews() {
        errorHint = findViewById(R.id.error_group);
        errorText = findViewById(R.id.error_hint);
        toolbar = findViewById(R.id.toolbar);
        weatherInfoGroup = findViewById(R.id.weather_info_group);
        currentTemp = findViewById(R.id.current_temperature);
        weatherType = findViewById(R.id.weather_type);
        weatherDescription = findViewById(R.id.weather_description);
        tempFromTo = findViewById(R.id.temp_min_and_max);
        cloudPercent = findViewById(R.id.cloud_percent);
        windSpeed = findViewById(R.id.wind_speed);
        pressure = findViewById(R.id.pressure_value);
        locationHint = findViewById(R.id.location_params_hint);
        progressBar = findViewById(R.id.progress_bar);
        date = findViewById(R.id.date_value);
    }

    /**
     * Init location manager. If permissions is not granted - try to request and initialize manager again
     */
    private void initLocationManager() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100000, 100, this);
        if (!isFromResult) {
            locationHint.setText(getString(R.string.location_gps_hint));
            presenter.initWeather(locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER));
        }
    }

    /**
     * Build and show dialog with text input for city name
     */
    private void showCityPickerDialog() {
        final View cityPickerLayout = getLayoutInflater().inflate(R.layout.dialog_city_pick, null);
        new AlertDialog.Builder(this)
                .setTitle("Pick city")
                .setView(cityPickerLayout)
                .setPositiveButton("Pick", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TextInputEditText cityPickerEdt = cityPickerLayout.findViewById(R.id.city_input_edt);
                        if (cityPickerEdt.getText() == null || cityPickerEdt.getText().toString().equals("")) {
                            Toast.makeText(getApplicationContext(), "City name must not be empty", Toast.LENGTH_SHORT).show();
                        } else {
                            locationHint.setText(getString(R.string.location_city_hint));
                            presenter.onCityPicked(cityPickerEdt.getText().toString().trim(), true);
                        }
                    }
                })
                .setNegativeButton("Cancel", null)
                .create()
                .show();

    }
}
