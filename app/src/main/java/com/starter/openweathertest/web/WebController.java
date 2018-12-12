package com.starter.openweathertest.web;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

public class WebController {
    private Context context;

    public WebController(Context context) {
        this.context = context;
    }

    public void load(String url, final ResponseListener listener) {
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                listener.onSuccess(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                listener.onError(volleyError);
            }
        });
        VolleyConf.getVolleySingleton(context).addToRequestQueue(request);
    }
}
