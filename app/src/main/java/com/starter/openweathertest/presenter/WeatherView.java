package com.starter.openweathertest.presenter;

import android.app.Activity;
import android.content.Context;

import com.starter.openweathertest.MainActivity;
import com.starter.openweathertest.model.Weather;

public class WeatherView implements WeatherMVC.View {

    WeatherMVC.Model model;
    WeatherMVC.View view;

    public WeatherView(Context context, WeatherMVC.View view,String city) {
        this.view = view;
        model = new WeatherModel(context, this);
        model.load(city);
    }

    @Override
    public void reload(String city) {
        model.load(city);
    }

    @Override
    public void onLoad(Weather weather) {
        view.onLoad(weather);
    }

    @Override
    public void onError(String error) {
        view.onError(error);
    }
}
