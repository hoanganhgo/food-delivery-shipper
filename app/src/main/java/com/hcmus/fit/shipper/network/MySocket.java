package com.hcmus.fit.shipper.network;

import android.util.Log;

import com.hcmus.fit.shipper.constant.API;
import com.hcmus.fit.shipper.constant.EventConstant;
import com.hcmus.fit.shipper.models.Address;
import com.hcmus.fit.shipper.models.NotifyManager;
import com.hcmus.fit.shipper.models.NotifyModel;
import com.hcmus.fit.shipper.models.OrderManager;
import com.hcmus.fit.shipper.models.OrderModel;
import com.hcmus.fit.shipper.models.ShipperInfo;
import com.hcmus.fit.shipper.util.JsonUtil;
import com.hcmus.fit.shipper.util.NotifyUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import static com.hcmus.fit.shipper.constant.EventConstant.*;

public class MySocket {
    private static Socket instance = null;

    public static Socket getInstance() {
        if (instance == null) {
            try {
                instance = IO.socket(API.SERVER_SOCKET);
//                instance = IO.socket("https://87e83c19d91f.ngrok.io");
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }

        return instance;
    }

    public static void disconnect() {
        Log.d("socket", "disconnect...");
        instance.disconnect();
    }

    public static void connect() {
        Log.d("socket", "connect...");
        instance.on(CONNECT, onAuthenticate);
        instance.on(RESPONSE_CHANGE_STATUS_ROOM, statusRoom);
        instance.on(RESPONSE_SHIPPER_CONFIRM_ORDER, receiveOrder);
        instance.on(RESPONSE_NOTIFICATION, listenNotification);
        instance.connect();
    }

    private static final Emitter.Listener onAuthenticate = args -> {
        JSONObject json = new JSONObject();
        Log.d("socket", "authenticate.....");
        try {
            json.put("token", ShipperInfo.getInstance().getToken());
            instance.emit(AUTHENTICATE, json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    };

    private static final Emitter.Listener statusRoom = args -> {
        JSONObject json = (JSONObject) args[0];
        Log.d("socket", "status room >>>");
        Log.d("socket", json.toString());
    };

    private static final Emitter.Listener receiveOrder = args -> {
        JSONObject json = (JSONObject) args[0];
        Log.d("socket", "receive order <<<");
        Log.d("socket", json.toString());
        try {
            JSONObject data = json.getJSONObject("data");
            OrderModel orderModel = new OrderModel();
            orderModel.setOrderId(data.getString("_id"));
            orderModel.setSubTotal(data.getInt("Subtotal"));
            orderModel.setShipFee(data.getInt("ShippingFee"));
            orderModel.setTotal(data.getInt("Total"));
            orderModel.setDistance(data.getDouble("Distance"));
            orderModel.setPoint(10);

            JSONObject merchantJson = data.getJSONObject("Restaurant");
            orderModel.setMerchant( merchantJson.getString("Name"));
            orderModel.setMerchantAddress( JsonUtil.parseAddress(merchantJson));
            orderModel.setMerchantPhone( merchantJson.getString("Phone"));

            JSONObject customerJson = data.getJSONObject("User");
            orderModel.setCustomer(customerJson.getString("FullName"));

            String fullAddress = data.getString("Address");
            JSONObject locationJson = data.getJSONObject("Coor");
            double latitude = locationJson.getDouble("latitude");
            double longitude = locationJson.getDouble("longitude");
            Address address = new Address(fullAddress, latitude, longitude);
            orderModel.setCustomerAddress(address);
            orderModel.setCustomerPhone(data.getString("Phone"));
            JSONArray foodArray = data.getJSONArray("Foods");
            orderModel.createDishOrderList(foodArray);

            OrderManager.getInstance().setNewOrder(orderModel);

            int timeOut = data.getInt("requestTime");
            OrderManager.getInstance().showNewOrder(timeOut);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    };

    public static void confirmOrder(String orderId) {
        JSONObject json = new JSONObject();
        Log.d("socket", "confirm order >>>");
        try {
            json.put("orderID", orderId);
            instance.emit(REQUEST_SHIPPER_CONFIRM_ORDER, json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void skipOrder(String orderId) {
        JSONObject json = new JSONObject();
        Log.d("socket", "skip order <<<");
        try {
            json.put("orderID", orderId);
            instance.emit(REQUEST_SHIPPER_SKIP_ORDER, json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void tookFood(String orderId) {
        JSONObject json = new JSONObject();
        Log.d("socket", "took Food >>><<<");
        try {
            json.put("orderID", orderId);
            instance.emit(REQUEST_SHIPPER_TOOK_FOOD, json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void delivered(String orderId) {
        JSONObject json = new JSONObject();
        Log.d("socket", "<<>> Delivered >>><<<");
        try {
            json.put("orderID", orderId);
            instance.emit(REQUEST_SHIPPER_DELIVERED, json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void updateCoor(double latitude, double longitude) {
        JSONObject json = new JSONObject();
        Log.d("socket", "Update location --->>");
        try {
            JSONObject coorJson = new JSONObject();
            coorJson.put("lat", latitude);
            coorJson.put("lng", longitude);
            json.put("coor", coorJson);
            instance.emit(REQUEST_SHIPPER_CHANGE_COOR, json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static final Emitter.Listener listenNotification = args -> {
        JSONObject json = (JSONObject) args[0];
        Log.d("socket-notification", json.toString());

        try {
            JSONObject data = json.getJSONObject("data");
            String id = data.getString("_id");
            String title = data.getString("Title");
            String content = data.getString("Subtitle");
            String avatar = data.getString("Thumbnail");

            NotifyModel notifyModel = new NotifyModel(id, title, content, avatar);
            NotifyManager.getInstance().addNotifyModel(notifyModel);

            NotifyUtil.call(title, content);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    };

    public static void sendMessage(String orderId) {
        JSONObject json = new JSONObject();
        Log.d("socket-send_chat", ">> Send message >>");
        try {
            json.put("orderID", orderId);
            instance.emit(REQUEST_CHAT, json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static final Emitter.Listener listenChat = args -> {
        JSONObject json = (JSONObject) args[0];
        Log.d("socket-receive-chat", json.toString());

    };

}
