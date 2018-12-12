package com.starter.openweathertest.web;

import com.android.volley.VolleyError;


public interface ResponseListener {
    void onSuccess(String result);
    void onError(VolleyError error);
}
