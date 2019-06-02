package com.ilslv.openweatherapp.data.repository;

import android.util.Log;

import com.google.gson.Gson;
import com.ilslv.openweatherapp.data.Constants;
import com.ilslv.openweatherapp.data.dto.CachedWeatherDto;
import com.ilslv.openweatherapp.data.dto.InfoDto;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherRepositoryImpl implements WeatherRepository {

    /**
     * Constant for LOG
     */
    private static final String LOG_WEATHER_REMOTE_SOURCE = "weather_remote_source";

    /**
     * Loading weather from server by city
     *
     * @param cityName city name for query
     * @return Dto with weather info
     */
    @Override
    public CachedWeatherDto getWeatherByCity(String cityName) {
        String urlParameters = buildQueryParamsByCity(cityName);
        HttpURLConnection connection = null;
        try {
            URL url = new URL(Constants.BASE_WEATHER_URL + urlParameters);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content-Type",
                    "application/json");
            connection.setUseCaches(false);
            connection.setDoOutput(true);

            DataOutputStream wr = new DataOutputStream(
                    connection.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
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
            InfoDto dtoResponse = parseResponse(response.toString());
            return convertInfoDto(dtoResponse);
        } catch (Exception e) {
            Log.e(LOG_WEATHER_REMOTE_SOURCE, e.toString());
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    /**
     * Loading weather from server by location
     *
     * @param latitude  user latitude
     * @param longitude user longitude
     * @return Dto with weather info
     */
    @Override
    public CachedWeatherDto getWeatherByLocation(Double latitude, Double longitude) {
        String urlParameters = buildQueryParamsByLocation(latitude, longitude);
        HttpURLConnection connection = null;
        try {
            URL url = new URL(Constants.BASE_WEATHER_URL + urlParameters);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content-Type",
                    "application/json");
            connection.setUseCaches(false);
            connection.setDoOutput(true);

            DataOutputStream wr = new DataOutputStream(
                    connection.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
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
            InfoDto dtoResponse = parseResponse(response.toString());
            return convertInfoDto(dtoResponse);
        } catch (Exception e) {
            Log.e(LOG_WEATHER_REMOTE_SOURCE, e.toString());
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    /**
     * Build query params string if user loading weather by city
     *
     * @param cityName city for query
     * @return query params string for request
     */
    private String buildQueryParamsByCity(String cityName) {
        return String.format("?q=%s&appid=%s&units=metric", cityName, Constants.WEATHER_API_KEY);
    }

    /**
     * Build query params string if user loading weather by location
     *
     * @param latitude  user latitude
     * @param longitude user longitude
     * @return query params string for request
     */

    private String buildQueryParamsByLocation(Double latitude, Double longitude) {
        return String.format("?lat=%s&lon=%s&appid=%s&units=metric", latitude, longitude, Constants.WEATHER_API_KEY);
    }

    /**
     * Parse JSON response from server
     *
     * @param response JSON string from server
     * @return DTO from JSON by Gson parser
     */
    private InfoDto parseResponse(String response) {
        return new Gson().fromJson(response, InfoDto.class);
    }

    private CachedWeatherDto convertInfoDto(InfoDto dto) {
        CachedWeatherDto cachedWeatherDto = new CachedWeatherDto();
        cachedWeatherDto.setCity(dto.getCity());
        cachedWeatherDto.setDate(dto.getDate());
        cachedWeatherDto.setTemperature(dto.getWeather().getTemperature());
        cachedWeatherDto.setPressure(dto.getWeather().getPressure());
        cachedWeatherDto.setMaxTemp(dto.getWeather().getMaxTemp());
        cachedWeatherDto.setMinTemp(dto.getWeather().getMinTemp());
        cachedWeatherDto.setHumidity(dto.getWeather().getHumidity());
        cachedWeatherDto.setTitle(dto.getWeatherInfo()[0].getTitle());
        cachedWeatherDto.setDescription(dto.getWeatherInfo()[0].getDescription());
        cachedWeatherDto.setCountry(dto.getOtherInfo().getCountry());
        cachedWeatherDto.setSpeed(dto.getWindInfo().getSpeed());
        cachedWeatherDto.setPercent(dto.getClouds().getPercent());
        return cachedWeatherDto;
    }
}
