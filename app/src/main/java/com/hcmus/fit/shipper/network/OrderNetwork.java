package com.hcmus.fit.shipper.network;

import android.util.Log;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hcmus.fit.shipper.constant.API;
import com.hcmus.fit.shipper.models.ShipperInfo;
import com.hcmus.fit.shipper.ui.IncomeFragment;
import com.hcmus.fit.shipper.util.QueryUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class OrderNetwork {
    public static void getIncome(IncomeFragment fragment, int month) {
        RequestQueue queue = Volley.newRequestQueue(fragment.getContext());

        Map<String, String> params = new HashMap<>();
        params.put("shipperId", ShipperInfo.getInstance().getId());
        params.put("montha",String.valueOf(month));
        params.put("monthb",String.valueOf(month));
        params.put("daya",String.valueOf(1));
        String query = QueryUtil.createQuery(API.GET_INCOME, params);

        StringRequest req = new StringRequest(Request.Method.GET, query,
                response -> {
                    Log.d("income", response);
                    JSONObject json = null;
                    try {
                        json = new JSONObject(response);
                        int error = json.getInt("errorCode");

                        if (error == 0) {
                            JSONObject data = json.getJSONObject("data");
                            int totalValue = data.getInt("PurchaseMoney");
                            int totalIncome = data.getInt("Income");
                            int numOrder = data.getInt("Count");
                            fragment.updateIncome(totalValue, totalIncome, numOrder);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                },
                error -> Log.d("income", error.getMessage()))
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer " + ShipperInfo.getInstance().getToken());
                return params;
            }

        };

        queue.add(req);
    }

}
