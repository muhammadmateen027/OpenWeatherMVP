package com.starter.openweathertest.presenter;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;
import com.starter.openweathertest.parser.WeatherParser;
import com.starter.openweathertest.web.ResponseListener;
import com.starter.openweathertest.web.WebConstant;
import com.starter.openweathertest.web.WebController;

public class WeatherPresenter implements WeatherMVC.Presenter {

    WebController controller;
    WeatherMVC.Model model;
    public WeatherPresenter(Context context,WeatherModel model) {
        controller = new WebController(context);
        this.model=model;
    }

    @Override
    public void load(String city) {
        String url = WebConstant.URL+"&q="+city;
        Log.i("Weather url",url);
        controller.load(url, new ResponseListener() {
            @Override
            public void onSuccess(String result) {
                WeatherParser wp=new WeatherParser();
                model.onLoad(wp.parseWeather(result));
            }

            @Override
            public void onError(VolleyError error) {
                model.onError(error.getMessage());
            }
        });
    }
}
