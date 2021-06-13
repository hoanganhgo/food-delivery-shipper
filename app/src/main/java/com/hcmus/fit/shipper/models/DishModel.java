package com.hcmus.fit.shipper.models;

public class DishModel {
    private String id;
    private String name;
    private String options;
    private int optionPrice;
    private int price;

    public DishModel() {
    }

    public DishModel(String id, String name, String options, int price) {
        this.id = id;
        this.name = name;
        this.options = options;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public int getOptionPrice() {
        return optionPrice;
    }

    public void setOptionPrice(int optionPrice) {
        this.optionPrice = optionPrice;
    }
}
