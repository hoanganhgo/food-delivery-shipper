package com.hcmus.fit.shipper.network;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hcmus.fit.shipper.MainActivity;
import com.hcmus.fit.shipper.activities.OTPLoginActivity;
import com.hcmus.fit.shipper.constant.API;
import com.hcmus.fit.shipper.models.ShipperInfo;
import com.hcmus.fit.shipper.util.JWTUtils;
import com.hcmus.fit.shipper.util.QueryUtil;
import com.hcmus.fit.shipper.util.StorageUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignInNetwork {

    public static void sendPhoneOTP(Context context, String phoneNumber) {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest req = new StringRequest(Request.Method.POST, API.SEND_PHONE_OTP,
                response -> {
                    Log.d("sendPhoneOTP", response);
                    JSONObject json = null;
                    try {
                        json = new JSONObject(response);
                        int error = json.getInt("errorCode");

                        if (error == 0) {
                            Intent intent = new Intent(context, OTPLoginActivity.class);
                            context.startActivity(intent);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                },
                error -> Log.d("OTP", error.getMessage()))
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("phone", phoneNumber);
                return params;
            }
        };

        queue.add(req);
    }

    public static void authPhoneVerify(Context context, String phoneNumber, String OTP) {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest req = new StringRequest(Request.Method.POST, API.AUTH_PHONE_VERIFY,
                response -> {
                    Log.d("Verify", response);
                    JSONObject json = null;
                    try {
                        json = new JSONObject(response);
                        int error = json.getInt("errorCode");

                        if (error == 0) {
                            JSONObject data = json.getJSONObject("data");
                            String token = data.getString("token");
                            ShipperInfo.getInstance().setToken(token);

                            //save token
                            StorageUtil.putString(context, StorageUtil.TOKEN_KEY, token);

                            Intent intent = new Intent(context, MainActivity.class);
                            context.startActivity(intent);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                },
                error -> Log.d("Verify", error.getMessage()))
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("phone", phoneNumber);
                params.put("otp", OTP);
                return params;
            }
        };

        queue.add(req);
    }

    public static void getUserInfo(Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);

        Map<String, String> params = new HashMap<>();
        String userId = JWTUtils.getUserIdFromToken(ShipperInfo.getInstance().getToken());
        params.put("id", userId);
        String query = QueryUtil.createQuery(API.GET_USER_INFO, params);

        StringRequest req = new StringRequest(Request.Method.GET, query,
                response -> {
                    Log.d("ShipperInfo", response);
                    JSONObject json = null;
                    try {
                        json = new JSONObject(response);
                        int error = json.getInt("errorCode");

                        if (error == 0) {
                            JSONObject data = json.getJSONObject("data");
                            String id = data.getString("id");
                            String phone = data.getString("Phone");
                            String fullName = data.getString("FullName");
                            String email = data.getString("Email");
                            String avatarUrl = data.getString("Avatar");
                            int wallet = data.getInt("Wallet");
                            JSONObject settingJson = data.getJSONObject("Setting");
                            ShipperInfo.getInstance().setId(id);
                            ShipperInfo.getInstance().setPhoneNumber(phone);
                            ShipperInfo.getInstance().setFullName(fullName);
                            ShipperInfo.getInstance().setEmail(email);
                            ShipperInfo.getInstance().setAvatar(avatarUrl);
                            ShipperInfo.getInstance().setWallet(wallet);
                            ShipperInfo.getInstance().setSetting(settingJson);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                },
                error -> {
                    try {
                        Log.d("ShipperInfo", error.getMessage());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                })
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

    public static void getUserWallet(Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);

        Map<String, String> params = new HashMap<>();
        params.put("id", ShipperInfo.getInstance().getId());
        String query = QueryUtil.createQuery(API.GET_USER_INFO, params);

        StringRequest req = new StringRequest(Request.Method.GET, query,
                response -> {
                    Log.d("ShipperInfo", response);
                    JSONObject json = null;
                    try {
                        json = new JSONObject(response);
                        int error = json.getInt("errorCode");

                        if (error == 0) {
                            JSONObject data = json.getJSONObject("data");
                            int wallet = data.getInt("Wallet");
                            ShipperInfo.getInstance().setWallet(wallet);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                },
                error -> {
                try {
                    Log.d("ShipperInfo", error.getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                })
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
