package com.hcmus.fit.shipper.util;

import android.content.Context;
import android.content.SharedPreferences;

public class StorageUtil {
    private static final String STORAGE_KEY = "shipper";
    public static String TOKEN_KEY = "token";

    private static SharedPreferences getStore(Context context) {
        return context.getSharedPreferences(STORAGE_KEY, 0);
    }

    public static void putString(Context context, String key, String value) {
        SharedPreferences sharedPreferences = getStore(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getString(Context context, String key) {
        SharedPreferences sharedPreferences = getStore(context);
        return sharedPreferences.getString(key, null);
    }

    public static void deleteKey(Context context, String key) {
        SharedPreferences sharedPreferences = getStore(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.apply();
    }
}
