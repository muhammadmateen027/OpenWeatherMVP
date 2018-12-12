package com.starter.openweathertest.presenter;

import com.starter.openweathertest.model.Weather;

public interface WeatherMVC {

    public interface Model {
        public void load(String city);
        public void onLoad(Weather weather);
        public void onError(String error);
    }

    public interface View {
        public void reload(String city);
        public void onLoad(Weather weather);
        public void onError(String error);
    }

    public interface Presenter {
        public void load(String city);
    }
}
