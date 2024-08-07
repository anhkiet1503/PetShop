package com.example.petshop.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.petshop.database.DbHelper;

import java.sql.SQLData;
public class AdminDAO {
    private DbHelper dbHelper;

    public AdminDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    // login
    public boolean CheckAdLogin(String username, String password) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM ADMIN WHERE adminacc = ? AND adpassword = ?", new String[]{username, password});
        return cursor.getCount() > 0;
    }

    // search name
    public String SearchName(String username){
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT adname FROM ADMIN WHERE adminacc = ?", new String[]{username});
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            return cursor.getString(0);
        }else {
            return "";
        }
    }
}
