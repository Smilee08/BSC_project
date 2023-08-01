package com.example.final_pro;

import android.app.Activity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class GetDateTime {

    Activity activity;
    String url = "https://timeapi.io/api/Time/current/coordinate?latitude=22.5726&longitude=88.3639";

    RequestQueue requestQueue;

    public GetDateTime(Activity activity) {
        this.activity = activity;
        requestQueue = Volley.newRequestQueue(activity);
    }

    public  void datetime(VolleyCallBack volleyCallBack) {
        JSONObject jsonObject = new JSONObject();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    volleyCallBack.onGetDateTime(response.getString("date"), response.getString("time"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(request);
    }

    public  interface VolleyCallBack {
        void  onGetDateTime(String date, String time);
    }
}
