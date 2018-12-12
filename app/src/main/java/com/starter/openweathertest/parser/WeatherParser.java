package com.starter.openweathertest.parser;

import com.starter.openweathertest.model.Weather;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class WeatherParser {
    private static final String ID = "id";
    private static final String DESCRIPTIOON = "description";
    private static final String TEMP = "temp";
    private static final String HUMIDITY = "humidity";
    private static final String PRESSURE = "pressure";
    private static final String DT = "dt";
    private static final String SUNRISE = "sunrise";
    private static final String SUNSET = "sunset";
    private static final String COUNTRY = "country";
    private static final String NAME = "name";

    public Weather parseWeather(String jsonResponse) {
        Weather weather = new Weather();
        try {
            JSONObject json = new JSONObject(jsonResponse);

            JSONObject details = json.getJSONArray("weather").getJSONObject(0);
            JSONObject main = json.getJSONObject("main");
            JSONObject sys = json.getJSONObject("sys");

            weather.setName(json.getString(NAME));
            weather.setId(details.getInt(ID));
            weather.setDescription(details.getString(DESCRIPTIOON));
            weather.setDt(new Date(json.getLong(DT) * 1000));
            weather.setCountry(sys.getString(COUNTRY));
            weather.setTemp(main.getDouble(TEMP));
            weather.setSunrise(sys.getLong(SUNRISE));
            weather.setSunset(sys.getLong(SUNSET));
            weather.setHumidity(main.getString(HUMIDITY));
            weather.setPressure(main.getString(PRESSURE));


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return weather;
    }
}
