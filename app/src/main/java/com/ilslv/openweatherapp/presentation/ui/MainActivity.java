package com.ilslv.openweatherapp.presentation.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.constraint.Group;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.ilslv.openweatherapp.R;

public class MainActivity extends AppCompatActivity {

    private Group errorHint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        errorHint.setVisibility(View.GONE);
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
                return true;
            case R.id.person_city:
                showCityPickerDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initViews() {
        errorHint = findViewById(R.id.error_group);
    }

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
                            //TODO presenter
                        }
                    }
                })
                .setNegativeButton("Cancel", null)
                .create()
                .show();

    }
}
