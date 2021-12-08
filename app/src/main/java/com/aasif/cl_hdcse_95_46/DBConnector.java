package com.aasif.cl_hdcse_95_46;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DBConnector extends SQLiteOpenHelper {

    Context con;

    public DBConnector(Context context) {
        super(context, "Main_DB", null, 1);
        con = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table customers (id INTEGER PRIMARY KEY, name text, email text, password text)");
        db.execSQL("create table admins (id INTEGER PRIMARY KEY, name text, email text, password text)");
        db.execSQL("create table appointments (id INTEGER PRIMARY KEY, name text, date text, time text, price text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public  boolean createCustomer(Customers customers){

    SQLiteDatabase db = this.getWritableDatabase();

    ContentValues cv = new ContentValues();
    cv.put("name", customers.getName());
    cv.put("email",customers.getEmail());
    cv.put("password", customers.getPassword());

    long insert = db.insert("customers", null, cv);

    if(insert == -1){
        return false;
    }else{
        return true;
    }
    }

    public boolean checkDuplicateCustomerEmail(String email){

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("select * from customers where email = ?",new String[]{email});

        if(cursor.getCount()>0){
            return true;
        }else {
            return false;
        }
    }

    public boolean createAdmins(Admins admins){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("name", admins.getName());
        cv.put("email", admins.getEmail());
        cv.put("password", admins.getPassword());

        long insert = db.insert("admins",null, cv);

        if (insert == -1) {
            return false;
        }
        return true;
    }

    public boolean checkDuplicateAdminName(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor =db.rawQuery("select * from admins where name = ?",new String[]{name});

        if(cursor.getCount()>0){
            return true;
        }else {
            return false;
        }
    }

    public Boolean checkCustomerCredentials(String email, String pass){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("select * from customers where email = ? AND password = ?",
                new String[]{email,pass});
        if(cursor.getCount()>0){
            return true;
        }else {
            return false;
        }
    }

    public Boolean checkAdminCredentials(String email, String pass){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("select * from admins where email = ? AND password = ?",
                new String[]{email,pass});
        if(cursor.getCount()>0){
            return true;
        }else {
            return false;
        }
    }


    public boolean addAppointment(Appoinment appoinment){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("name", appoinment.getName());
        cv.put("date", appoinment.getDate());
        cv.put("time", appoinment.getTime());
        cv.put("price", appoinment.getPrice());

        long insert = db.insert("appointments",null,cv);

         if (insert == -1) {
            return false;
          }else {
             return true;
         }
    }
}
