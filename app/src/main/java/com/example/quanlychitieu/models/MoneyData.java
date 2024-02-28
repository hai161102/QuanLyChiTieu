package com.example.quanlychitieu.models;

public class MoneyData {

    public int id;
    protected float money;
    protected String description;

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
