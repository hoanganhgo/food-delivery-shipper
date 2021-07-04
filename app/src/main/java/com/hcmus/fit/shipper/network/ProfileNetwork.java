package com.hcmus.fit.shipper.network;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hcmus.fit.shipper.R;
import com.hcmus.fit.shipper.constant.API;
import com.hcmus.fit.shipper.models.ShipperInfo;
import com.hcmus.fit.shipper.ui.ProfileFragment;
import com.hcmus.fit.shipper.util.QueryUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class ProfileNetwork {
    public static void updateSetting(Context context, int maxOrder, int maxDistance, int maxAmount) {
        RequestQueue queue = Volley.newRequestQueue(context);

        Map<String, String> params = new HashMap<>();
        params.put("id", ShipperInfo.getInstance().getId());
        String query = QueryUtil.createQuery(API.UPDATE_SETTING, params);

        StringRequest req = new StringRequest(Request.Method.PUT, query,
                response -> {
                    Log.d("update setting", response);
                },
                error -> Log.d("update setting", error.getMessage()))
        {

            @Override
            public byte[] getBody() throws AuthFailureError {
                JSONObject json = null;
                try {
                    json = new JSONObject();
                    if (maxOrder >= 0) {
                        json.put("maxorder", maxOrder);
                    }

                    if (maxDistance >= 0) {
                        json.put("maxdistance", maxDistance);
                    }

                    if (maxAmount >= 0) {
                        json.put("maxamount", maxAmount);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return json.toString().getBytes(StandardCharsets.UTF_8);
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + ShipperInfo.getInstance().getToken());
                return headers;
            }
        };

        queue.add(req);
    }

    public static void getWithDraw(ProfileFragment fragment) {
        RequestQueue queue = Volley.newRequestQueue(fragment.getContext());

        StringRequest req = new StringRequest(Request.Method.GET, API.WITH_DRAWS,
                response -> {
                    Log.d("get with draw", response);

                    try {
                        JSONObject json = new JSONObject(response);
                        JSONArray data = json.getJSONArray("data");

                        boolean payment = false;

                        if (data.length() > 0) {
                            JSONObject reqJson = data.getJSONObject(0);
                            int status = reqJson.getInt("Status");
                            if (status != -1) {
                                payment = true;
                            }
                        } else {
                            payment = true;
                        }

                        if (!payment) {
                            ShipperInfo.getInstance().processWithDraw = true;
                            fragment.updateProcessingWithDraw();
                        } else {
                            ShipperInfo.getInstance().processWithDraw = false;
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                try {
                    Log.d("get with draw", error.getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                })
        {

            @Override
            public String getBodyContentType() {
                return "application/json";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + ShipperInfo.getInstance().getToken());
                return headers;
            }
        };

        queue.add(req);
    }

    public static void postWithDraw(Context context,int money) {
        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest req = new StringRequest(Request.Method.POST, API.WITH_DRAWS,
                response -> {
                    Log.d("post with draw", response);
                },
                error -> Log.d("post with draw", error.getMessage()))
        {

            @Override
            public byte[] getBody() throws AuthFailureError {
                JSONObject json = null;
                try {
                    json = new JSONObject();
                    json.put("amount", money);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return json.toString().getBytes(StandardCharsets.UTF_8);
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + ShipperInfo.getInstance().getToken());
                return headers;
            }
        };

        queue.add(req);
    }

}
