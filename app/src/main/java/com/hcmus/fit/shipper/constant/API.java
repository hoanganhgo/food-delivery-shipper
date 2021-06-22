package com.hcmus.fit.shipper.constant;

public class API {
    /**
     * 0: local,  1: release
     */
    private static final int env = 0;
    private static final String localHost = "http://10.0.2.2:8000";
    private static final String releaseHost = "https://api.shipper.flash.bin.edu.vn";
    private static final String localSocket = "http://10.0.2.2:8010";
    private static final String releaseSocket = "https://api.socket.flash.bin.edu.vn";
    private static final String SERVER = env == 0 ? localHost : releaseHost;
    public static final String SERVER_SOCKET = env == 0 ? localSocket : releaseSocket;

    public static final String SEND_PHONE_OTP = SERVER + "/auth/phone/otp/call";
    public static final String AUTH_PHONE_VERIFY = SERVER + "/auth/phone/otp/verify";
    public static final String GET_USER_INFO = SERVER + "/users/{id}";
    public static String CREATE_NEW_DISH = SERVER + "/restaurants/{restaurantID}/foodcategories/{foodCategoryID}/foods";
    public static String UPDATE_DISH = SERVER + "/restaurants/{restaurantID}/foodcategories/{foodCategoryID}/foods/{foodID}";
}
