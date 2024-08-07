package com.example.petshop.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(Context context) {
        super(context, "PETSHOP", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String qUser = "CREATE TABLE USER(username TEXT PRIMARY KEY, password TEXT, name TEXT)";
        db.execSQL(qUser);

        String qAdmin = "CREATE TABLE ADMIN(adminacc TEXT PRIMARY KEY, adpassword TEXT, adname TEXT)";
        db.execSQL(qAdmin);

        String qFood = "CREATE TABLE FOOD(idfood INTEGER PRIMARY KEY AUTOINCREMENT, foodname TEXT, foodprice INTEGER, foodquantity INTEGER)";
        db.execSQL(qFood);

        String qOrders = "CREATE TABLE ORDERS(ordersID INTEGER PRIMARY KEY AUTOINCREMENT,user TEXT, product TEXT, total INTEGER, quantity INTEGER)";
        db.execSQL(qOrders);

        String dUser = "INSERT INTO USER VALUES('teki', 'teki123', 'TEKI')";
        db.execSQL(dUser);

        String dAdmin = "INSERT INTO ADMIN VALUES('admin', 'admin123', 'AK')";
        db.execSQL(dAdmin);

        String dFood = "INSERT INTO FOOD VALUES(1, 'CAT', 160000, 48)";
        db.execSQL(dFood);

        String dOrders = "INSERT INTO ORDERS VALUES(1, 'teki', 'CAT', 320000, 2)";
        db.execSQL(dOrders);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS USER");
            db.execSQL("DROP TABLE IF EXISTS ADMIN");
            db.execSQL("DROP TABLE IF EXISTS FOOD");
            db.execSQL("DROP TABLE IF EXISTS ORDERS");

            onCreate(db);
        }
    }
}