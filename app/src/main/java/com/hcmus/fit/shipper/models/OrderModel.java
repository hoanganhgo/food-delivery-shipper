package com.hcmus.fit.shipper.models;

public class OrderModel {
    private String orderCode;
    private int price;
    private int point;

    private String merchant;
    private int merchantPay;
    private int merchantTime;

    private String customer;
    private int customerFee;
    private int customerTime;
    private int distance;

    private long completeAt;

    public OrderModel() {

    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
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

    public int getMerchantPay() {
        return merchantPay;
    }

    public void setMerchantPay(int merchantPay) {
        this.merchantPay = merchantPay;
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

    public int getCustomerFee() {
        return customerFee;
    }

    public void setCustomerFee(int customerFee) {
        this.customerFee = customerFee;
    }

    public int getCustomerTime() {
        return customerTime;
    }

    public void setCustomerTime(int customerTime) {
        this.customerTime = customerTime;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public long getCompleteAt() {
        return completeAt;
    }

    public void setCompleteAt(long completeAt) {
        this.completeAt = completeAt;
    }
}
