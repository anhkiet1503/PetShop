package com.example.petshop.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.petshop.database.DbHelper;
import com.example.petshop.model.Account;
import com.example.petshop.model.Product;

import java.sql.SQLData;
import java.util.ArrayList;

public class UserDAO {
    private DbHelper dbHelper;

    public UserDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    // sign in
    public boolean CheckLogin(String username, String password) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM USER WHERE username = ? AND password = ?", new String[]{username, password});
        return cursor.getCount() > 0;
    }

    // check account
    public boolean CheckRegister(String username) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM USER WHERE username = ?", new String[]{username});
        return cursor.getCount() > 0;
    }

    // sign up
    public boolean Register(String name, String username, String password) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("username", username);
        contentValues.put("password", password);

        long check = sqLiteDatabase.insert("USER",null,contentValues);
        return check != -1;
    }

    // forget
    public String ForgetPass(String username){
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT password FROM USER WHERE username = ?", new String[]{username});
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            return cursor.getString(0);
        }else {
            return "";
        }
    }

    // take account list
    public ArrayList<Account> getAcc() {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        ArrayList<Account> list = new ArrayList<>();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM user",null);
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                list.add(new Account(cursor.getString(0), cursor.getString(1),cursor.getString(2)));
            }while (cursor.moveToNext());
        }

        return list;
    }

    // edit account
    public boolean editAccount(Account account) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("password", account.getPassword());
        contentValues.put("name", account.getName());

        int check = sqLiteDatabase.update("USER",contentValues,"username = ?",new String[]{account.getUsername()});
        return check > 0;
    }

    // delete account
    public boolean delAccount(String username) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

        int check = sqLiteDatabase.delete("USER","username = ?",new String[]{username});

        return check > 0;
    }
}
