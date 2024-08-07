package com.example.petshop.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.petshop.database.DbHelper;
import com.example.petshop.model.Orders;

import java.util.ArrayList;

public class OrdersDAO {
    private DbHelper dbHelper;

    public OrdersDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    // take orders admin list
    public ArrayList<Orders> getOrdersAdmin() {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        ArrayList<Orders> list = new ArrayList<>();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM orders",null);
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                list.add(new Orders(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getInt(4)));
            }while (cursor.moveToNext());
        }

        return list;
    }

    // delete orders
    public boolean delOrders(int ordersID) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

        int check = sqLiteDatabase.delete("ORDERS","ordersID = ?",new String[]{String.valueOf(ordersID)});

        return check > 0;
    }

    // take orders user
    public ArrayList<Orders> getOrdersUser(String user) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        ArrayList<Orders> list = new ArrayList<>();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM ORDERS WHERE user = ?",new String[]{user});
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                list.add(new Orders(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getInt(4)));
            }while (cursor.moveToNext());
        }

        return list;
    }

    // new orders user
    public boolean addOrdersUser(Orders orders) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("user",orders.getUser());
        contentValues.put("product",orders.getProduct());
        contentValues.put("total",orders.getTotal());
        contentValues.put("quantity",orders.getQuantity());

        long check = sqLiteDatabase.insert("ORDERS",null,contentValues);

        return check != -1;
    }

    // edit orders
    public boolean editOrdersUser(Orders orders) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("user",orders.getUser());
        contentValues.put("product",orders.getProduct());
        contentValues.put("total",orders.getTotal());
        contentValues.put("quantity",orders.getQuantity());

        int check = sqLiteDatabase.update("ORDERS",contentValues,"ordersID = ?", new String[]{String.valueOf(orders.getOrdersID())});
        return check > 0;
    }
}
