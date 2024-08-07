package com.example.petshop.model;

public class Orders {
    private int ordersID;
    private String user;
    private String product;
    private int total;
    private int quantity;

    public Orders(int ordersID, String user, String product, int total, int quantity) {
        this.ordersID = ordersID;
        this.user = user;
        this.product = product;
        this.total = total;
        this.quantity = quantity;
    }

    public Orders(String user, String product, int total, int quantity) {
        this.user = user;
        this.product = product;
        this.total = total;
        this.quantity = quantity;
    }

    public int getOrdersID() {
        return ordersID;
    }

    public void setOrdersID(int ordersID) {
        this.ordersID = ordersID;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
