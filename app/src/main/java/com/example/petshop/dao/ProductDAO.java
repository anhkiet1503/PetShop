package com.example.petshop.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.petshop.database.DbHelper;
import com.example.petshop.model.Product;

import java.util.ArrayList;

public class ProductDAO {
    private DbHelper dbHelper;

    public ProductDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    // take product list
    public ArrayList<Product> getDS() {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();

        ArrayList<Product> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM food", null);
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                list.add(new Product(cursor.getInt(0),cursor.getString(1),cursor.getInt(2),cursor.getInt(3)));
            }while (cursor.moveToNext());
        }

        return list;
    }

    // add product
    public boolean addNewProduct(Product product) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("foodname",product.getFoodname());
        contentValues.put("foodprice",product.getFoodprice());
        contentValues.put("foodquantity",product.getFoodquantity());

        long check = sqLiteDatabase.insert("FOOD",null,contentValues);

        return check != -1;
    }

    // edit product
    public boolean editProduct(Product product) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("foodname", product.getFoodname());
        contentValues.put("foodprice", product.getFoodprice());
        contentValues.put("foodquantity", product.getFoodquantity());

        int check = sqLiteDatabase.update("FOOD",contentValues,"idfood = ?", new String[]{String.valueOf(product.getIdfood())});
        return check > 0;
    }

    // delete product
    public boolean delProduct(int idfood) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

        int check = sqLiteDatabase.delete("FOOD","idfood = ?",new String[]{String.valueOf(idfood)});

        return check > 0;
    }

    // take product
    public ArrayList<Product> getProduct(String foodname) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        String temp = foodname + '%';

        ArrayList<Product> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM food WHERE foodname LIKE ?", new String[]{temp});
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                list.add(new Product(cursor.getInt(0),cursor.getString(1),cursor.getInt(2),cursor.getInt(3)));
            }while (cursor.moveToNext());
        }

        return list;
    }

    // search product
    public boolean CheckProduct(String foodname) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        String temp = foodname + '%';

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM food WHERE foodname LIKE ?", new String[]{temp});
        return cursor.getCount() > 0;
    }
}
