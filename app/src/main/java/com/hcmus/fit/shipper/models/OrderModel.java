package com.hcmus.fit.shipper.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class OrderModel {
    private String orderId;
    private int subTotal;
    private int shipFee;
    private int total;
    private int point;

    private String merchant;
    private int merchantTime;
    private Address merchantAddress;
    private String merchantPhone;

    private String customer;
    private int customerTime;
    private Address customerAddress;
    private String customerPhone;

    private ArrayList<DishOrder> dishOrderList = new ArrayList<>();

    private double distance;
    private long completeAt;
    private int status = 0;

    public OrderModel() {

    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    public int getMerchantTime() {
        return merchantTime;
    }

    public void setMerchantTime(int merchantTime) {
        this.merchantTime = merchantTime;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public int getCustomerTime() {
        return customerTime;
    }

    public void setCustomerTime(int customerTime) {
        this.customerTime = customerTime;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public long getCompleteAt() {
        return completeAt;
    }

    public void setCompleteAt(long completeAt) {
        this.completeAt = completeAt;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Address getMerchantAddress() {
        return merchantAddress;
    }

    public void setMerchantAddress(Address merchantAddress) {
        this.merchantAddress = merchantAddress;
    }

    public Address getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(Address customerAddress) {
        this.customerAddress = customerAddress;
    }

    public int getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(int subTotal) {
        this.subTotal = subTotal;
    }

    public int getShipFee() {
        return shipFee;
    }

    public void setShipFee(int shipFee) {
        this.shipFee = shipFee;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getMerchantPhone() {
        return merchantPhone;
    }

    public void setMerchantPhone(String merchantPhone) {
        this.merchantPhone = merchantPhone;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public ArrayList<DishOrder> getDishOrderList() {
        return dishOrderList;
    }

    public void createDishOrderList(JSONArray foodArray) throws JSONException {
        for (int i = 0; i < foodArray.length(); i++) {
            JSONObject foodJson = foodArray.getJSONObject(i);

            DishModel dishModel = new DishModel();
            dishModel.setId(foodJson.getString("id"));
            dishModel.setPrice(foodJson.getInt("Price"));
            dishModel.setName(foodJson.getString("Name"));
            dishModel.setOptions(foodJson.getJSONObject("Options").getString("notes"));
            dishModel.setOptionPrice(foodJson.getJSONObject("Options").getInt("totalPrice"));

            DishOrder dishOrder = new DishOrder(dishModel, foodJson.getInt("Quantity"));

            this.dishOrderList.add(dishOrder);
        }
    }
}
