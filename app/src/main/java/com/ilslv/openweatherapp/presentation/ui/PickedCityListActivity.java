package com.ilslv.openweatherapp.presentation.ui;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ilslv.openweatherapp.R;
import com.ilslv.openweatherapp.data.database.DatabaseHelper;
import com.ilslv.openweatherapp.data.database.dao.PickedCitiesDao;
import com.ilslv.openweatherapp.presentation.mvp.presenter.PickedCityPresenter;
import com.ilslv.openweatherapp.presentation.mvp.view.PickedCityView;

import java.util.List;

public class PickedCityListActivity extends AppCompatActivity implements PickedCityView, PickedCitiesListAdapter.OnCityClickListener {

    public static final String BUNDLE_CITY_NAME = "bundle_city_name";

    public static Intent createIntent(Context context) {
        return new Intent(context, PickedCityListActivity.class);
    }

    private RecyclerView citiesList;
    private TextView emptyMessage;
    private FloatingActionButton addCity;
    private Toolbar toolbar;
    private PickedCitiesListAdapter adapter;

    private PickedCityPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recently_picked_cities);
        initViews();
        initToolbar();
        initRecyclerView();
        presenter = new PickedCityPresenter(new PickedCitiesDao(new DatabaseHelper(this)));
        addCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCityPickerDialog();
            }
        });
    }

    @Override
    public void showEmpty() {
        citiesList.setVisibility(View.GONE);
        emptyMessage.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.attachView(this);
        presenter.getPickedCities();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.detachView();
    }

    @Override
    public void showPickedCities(List<String> cities) {
        emptyMessage.setVisibility(View.GONE);
        citiesList.setVisibility(View.VISIBLE);
        adapter.setCities(cities);
    }

    @Override
    public void onCityPicked(String city) {
        finishWithResult(city);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Init views from activity_recently_picked_cities
     */
    private void initViews() {
        citiesList = findViewById(R.id.cities_list);
        emptyMessage = findViewById(R.id.empty_hint);
        addCity = findViewById(R.id.add_new_city);
        toolbar = findViewById(R.id.toolbar);
    }

    /**
     * Init activity toolbar
     */
    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.city_picker_title);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
    }

    /**
     * Init cities recycler view
     */
    private void initRecyclerView() {
        adapter = new PickedCitiesListAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        DividerItemDecoration decoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        citiesList.addItemDecoration(decoration);
        citiesList.setLayoutManager(new LinearLayoutManager(this));
        citiesList.setAdapter(adapter);
    }

    /**
     * Send picked result
     *
     * @param city for result
     */
    private void finishWithResult(String city) {
        Intent data = new Intent();
        data.putExtra(BUNDLE_CITY_NAME, city);
        setResult(Activity.RESULT_OK, data);
        finish();
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
                            presenter.insertPickedCity(cityPickerEdt.getText().toString());
                            finishWithResult(cityPickerEdt.getText().toString());
                        }
                    }
                })
                .setNegativeButton("Cancel", null)
                .create()
                .show();

    }
}
