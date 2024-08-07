package com.example.petshop.model;

public class Product {
    private int idfood;
    private String foodname;
    private int foodprice;
    private int foodquantity;

    public Product(int idfood, String foodname, int foodprice, int foodquantity) {
        this.idfood = idfood;
        this.foodname = foodname;
        this.foodprice = foodprice;
        this.foodquantity = foodquantity;
    }

    public Product(String foodname, int foodprice, int foodquantity) {
        this.foodname = foodname;
        this.foodprice = foodprice;
        this.foodquantity = foodquantity;
    }

    public int getIdfood() {
        return idfood;
    }

    public void setIdfood(int idfood) {
        this.idfood = idfood;
    }

    public String getFoodname() {
        return foodname;
    }

    public void setFoodname(String foodname) {
        this.foodname = foodname;
    }

    public int getFoodprice() {
        return foodprice;
    }

    public void setFoodprice(int foodprice) {
        this.foodprice = foodprice;
    }

    public int getFoodquantity() {
        return foodquantity;
    }

    public void setFoodquantity(int foodquantity) {
        this.foodquantity = foodquantity;
    }
}
