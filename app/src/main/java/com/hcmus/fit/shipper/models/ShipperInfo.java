package com.hcmus.fit.shipper.models;

import org.json.JSONException;
import org.json.JSONObject;

public class ShipperInfo {
    private static ShipperInfo instance = null;

    private String id = "";
    private String fullName = "";
    private String phoneNumber = "0123456789";
    private String email = "";
    private String avatar = "";
    private String token = "";
    private double latitude;
    private double longitude;
    private int wallet;
    public boolean processWithDraw = false;
    private int maxOrder;
    private int maxDistance;
    private int maxAmount;
    private boolean active = false;

    private ShipperInfo() {

    }

    public static ShipperInfo getInstance() {
        if (instance == null) {
            instance = new ShipperInfo();
        }
        return instance;
    }

    public String getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getToken() {
        return token;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getWallet() {
        return wallet;
    }

    public void setWallet(int wallet) {
        this.wallet = wallet;
    }

    public void spendWallet(int money) {
        this.wallet -= money;
    }

    public void setSetting(JSONObject settingJson) throws JSONException {
        this.maxOrder = settingJson.getInt("MaxOrder");
        this.maxDistance = settingJson.getInt("MaxDistance");
        this.maxAmount = settingJson.getInt("MaxAmount");
    }

    public int getMaxOrder() {
        return maxOrder;
    }

    public int getMaxDistance() {
        return maxDistance;
    }

    public int getMaxAmount() {
        return maxAmount;
    }

    public void setMaxOrder(int maxOrder) {
        this.maxOrder = maxOrder;
    }

    public void setMaxDistance(int maxDistance) {
        this.maxDistance = maxDistance;
    }

    public void setMaxAmount(int maxAmount) {
        this.maxAmount = maxAmount;
    }

    public void clear() {
        instance = null;
    }
}
