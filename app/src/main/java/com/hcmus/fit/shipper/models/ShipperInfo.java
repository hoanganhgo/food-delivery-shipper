package com.hcmus.fit.shipper.models;

public class ShipperInfo {
    private static ShipperInfo instance = null;

    private String id = "";
    private String firstName = "";
    private String lastName = "";
    private String phoneNumber = "0123456789";
    private String email = "";
    private String avatar = "";
    private String token = "";
    private double latitude;
    private double longitude;

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

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
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
        return "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjYxYjdhMmZjNTM4MzQ2MzRjNjEzMzJiMyIsImV4cCI6MTY1MzgxNTUyMy4wNjMsInJvbGUiOiJzaGlwcGVyIiwiaWF0IjoxNjIyMDQxNzY3fQ.IUxWgD1vcSLLKAEph1sotwwi00Adis3aNU60b2F9y3Q";
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

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public void clear() {
        instance = null;
    }
}
