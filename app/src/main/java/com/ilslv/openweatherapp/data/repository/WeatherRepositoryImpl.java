package com.ilslv.openweatherapp.data.repository;

import android.location.Location;
import android.util.Log;

import com.google.gson.Gson;
import com.ilslv.openweatherapp.data.Constants;
import com.ilslv.openweatherapp.data.dto.InfoDto;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherRepositoryImpl implements WeatherRepository {

    private static final String LOG_WEATHER_REMOTE_SOURCE = "weather_remote_source";

    @Override
    public InfoDto getWeatherByCity(String cityName) {
        String urlParameters = buildQueryParamsByCity(cityName);
        HttpURLConnection connection = null;
        try {
            URL url = new URL(Constants.BASE_WEATHER_URL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type",
                    "application/json");

            connection.setRequestProperty("Content-Length",
                    Integer.toString(urlParameters.getBytes().length));
            connection.setRequestProperty("Content-Language", "en-US");

            connection.setUseCaches(false);
            connection.setDoOutput(true);

            DataOutputStream wr = new DataOutputStream(
                    connection.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.close();

            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            return parseResponse(response.toString());
        } catch (Exception e) {
            Log.e(LOG_WEATHER_REMOTE_SOURCE, e.toString());
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    @Override
    public InfoDto getCityByLocation(Location userLocation) {
        return null;
    }

    private String buildQueryParamsByCity(String cityName) {
        return "?q=" + cityName + "&appid=" + Constants.WEATHER_API_KEY;
    }

    private String buildQueryParamsByLocation(Location userLocation) {
        return null;
    }

    private InfoDto parseResponse(String response) {
        return new Gson().fromJson(response, InfoDto.class);
    }
}
