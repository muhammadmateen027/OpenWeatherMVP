package com.starter.openweathertest;

import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.widget.Toast;
import java.util.Locale;

import com.sdsmdg.tastytoast.TastyToast;
import com.starter.openweathertest.model.Weather;
import com.starter.openweathertest.presenter.WeatherMVC;
import com.starter.openweathertest.presenter.WeatherView;
import com.starter.openweathertest.utils.Util;

public class MainActivity extends AppCompatActivity implements WeatherMVC.View, View.OnClickListener {
    TextView cityField, detailsField, currentTemperatureField, humidity_field,
            pressure_field, weatherIcon, updatedField;
    Button retryBtn;
    RelativeLayout parent_lv;
    Toolbar toolbar;
    ProgressBar loader;
    Typeface weatherFont;
    String city = "Petaling jaya";
    WeatherMVC.View viewHolder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        //setting the title
        toolbar.setTitle(getString(R.string.app_name));
        toolbar.setTitleTextColor(Color.WHITE);

        //placing toolbar in place of actionbar
        setSupportActionBar(toolbar);

        viewHolder = new WeatherView(this,MainActivity.this, city);
    }

    @Override
    public void reload(String city) {
        //TODO Nothing
    }

    @Override
    public void onLoad(Weather weather) {
        loader.setVisibility(View.GONE);
        retryBtn.setVisibility(View.GONE);
        Log.i("Weather Done",weather.getDescription());


        cityField.setText(weather.getName().toUpperCase(Locale.US) + ", "+weather.getCountry());
        detailsField.setText(weather.getDescription().toUpperCase(Locale.US));
        currentTemperatureField.setText(String.format("%.2f", weather.getTemp()) + "°");
        humidity_field.setText(getString(R.string.humidity_st)+" " + weather.getHumidity() + "%");


        pressure_field.setText(getString(R.string.pressure_st)+" " + weather.getPressure() + getString(R.string.hPa_st));
        updatedField.setText(Util.getDate(weather.getDt()));
        weatherIcon.setText(
                Html.fromHtml(
                        Util.setWeatherIcon(
                                weather.getId(),
                                weather.getSunrise() * 1000,
                                weather.getSunset() * 1000
                        )
                )
        );


    }

    @Override
    public void onError(String error) {
        loader.setVisibility(View.GONE);
        getToast(getString(R.string.no_city_msg), TastyToast.ERROR).show();
    }

    //    to initialize the buttons
    private void init(){
        loader = (ProgressBar) findViewById(R.id.loader);
        retryBtn = (Button) findViewById(R.id.retry_btn);
        parent_lv = (RelativeLayout) findViewById(R.id.parent_lv);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        cityField = (TextView) findViewById(R.id.city_field);
        updatedField = (TextView) findViewById(R.id.updated_field);
        detailsField = (TextView) findViewById(R.id.details_field);
        currentTemperatureField = (TextView) findViewById(R.id.current_temperature_field);
        humidity_field = (TextView) findViewById(R.id.humidity_field);
        pressure_field = (TextView) findViewById(R.id.pressure_field);
        weatherIcon = (TextView) findViewById(R.id.weather_icon);

        //        Set fonts to our custom fonts
        weatherFont = Typeface.createFromAsset(getAssets(), "fonts/weathericons-regular-webfont.ttf");
        weatherIcon.setTypeface(weatherFont);

        retryBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.retry_btn:
                searchWeather(city);
                break;
        }
    }
    // function to perform action
    public void searchWeather(String city) {
        parent_lv.setBackgroundColor(Util.getRandomColor());
        if (Util.isNetworkAvailable(getApplicationContext())) {
            retryBtn.setVisibility(View.GONE);
            loader.setVisibility(View.VISIBLE);
            viewHolder.reload(city);
        } else {
            getToast(getString(R.string.no_conn_msg), TastyToast.WARNING).show();
            retryBtn.setVisibility(View.VISIBLE);
            loader.setVisibility(View.GONE);
        }
    }

    private Toast getToast(String msg, int type){
        return TastyToast.makeText(getApplicationContext(), msg,
                TastyToast.LENGTH_LONG, type);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);

        //getting the search view from the menu
        MenuItem searchViewItem = menu.findItem(R.id.menuSearch);

        //getting search manager from systemservice
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        //getting the search view
        final SearchView searchView = (SearchView) searchViewItem.getActionView();

        //you can put a hint for the search input field
        searchView.setQueryHint(getString(R.string.search_hint));
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        //by setting it true we are making it iconified
        //so the search input will show up after taping the search iconified
        //if you want to make it visible all the time make it false
        searchView.setIconifiedByDefault(true);

        //here we will get the search query
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                city = query.trim();
                searchWeather(city);
                //do the search here
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;

    }
}
