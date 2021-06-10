package com.hcmus.fit.shipper.util;

import com.hcmus.fit.shipper.models.Address;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtil {
    public static Address parseAddress(JSONObject merchant) throws JSONException {
        JSONObject addressJson = merchant.getJSONObject("Address");
        String street = addressJson.getString("Street");
        String city = addressJson.getString("City");
        String district = addressJson.getString("District");
        String ward = addressJson.getString("Ward");
        String fullAddress = street + ", " + ward + ", " + district + ", " + city;

        JSONObject locationJson = merchant.getJSONObject("Location");
        JSONArray coordinates = locationJson.getJSONArray("coordinates");
        double latitude = coordinates.getDouble(0);
        double longitude = coordinates.getDouble(1);

        return new Address(fullAddress, latitude, longitude);
    }
}
