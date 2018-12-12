package com.starter.openweathertest.presenter;

import android.content.Context;

import com.starter.openweathertest.model.Weather;

public class WeatherModel implements WeatherMVC.Model {

    private WeatherMVC.Presenter presenter;
    private WeatherMVC.View view;

    public WeatherModel(Context context, WeatherView view){
        this.view=view;
        presenter = new WeatherPresenter(context,this);
    }

    @Override
    public void load(String city) {
        presenter.load(city);
    }

    @Override
    public void onLoad(Weather wether) {
        view.onLoad(wether);
    }

    @Override
    public void onError(String error) {
        view.onError(error);
    }
}
