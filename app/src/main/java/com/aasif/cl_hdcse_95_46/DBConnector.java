package com.aasif.cl_hdcse_95_46;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class DBConnector extends SQLiteOpenHelper {

    Context con;

    public DBConnector(Context context) {
        super(context, "Main_DB", null, 1);
        con = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table customers (user_id INTEGER PRIMARY KEY, name text, email text, password text)");
        db.execSQL("create table admins (id INTEGER PRIMARY KEY, name text, email text, password text)");


        db.execSQL("create table appointments (id INTEGER PRIMARY KEY, name text, " +
                "date text, price text, user_id INTEGER ,FOREIGN KEY (user_id) REFERENCES customers(user_id))");
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

        Cursor cursor = db.rawQuery("select user_id from customers where email = ? AND password = ?",
                new String[]{email,pass});
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            int user_id = cursor.getInt(0);
            System.out.println(user_id);

            SharedPreferences sharedPreferences = con.getSharedPreferences("save userinfo", Context.MODE_PRIVATE);

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("username key", user_id);
            editor.commit();

            return true;

        }else {
            return false;
        }
    }

    public void saveData() {

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

    public Boolean history(String startdate, String enddate) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM appointments" +
                " WHERE date" +
                " BETWEEN ? AND ?", new String[]{startdate, enddate});


        if(cursor.getCount()>0){
            return true;
        }else {
            return false;
        }
    }

    public boolean addAppointment(Appoinment appoinment){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        SharedPreferences sharedPreferences = con.getSharedPreferences("save userinfo", Context.MODE_PRIVATE);

        cv.put("name", appoinment.getName());
        cv.put("date", appoinment.getDate());
        cv.put("price", appoinment.getPrice());
        cv.put("user_id", sharedPreferences.getInt("username key", 0));
        long insert = db.insert("appointments",null,cv);

         if (insert == -1) {
            return false;
          }else {
             return true;
         }
    }

    @SuppressLint("Range")
    public ArrayList<HashMap<String, String>> GetAppointments(){
        SQLiteDatabase db = this.getWritableDatabase();
        SharedPreferences sharedPreferences = con.getSharedPreferences("save userinfo", Context.MODE_PRIVATE);
        ArrayList<HashMap<String, String>> appointmentList = new ArrayList<>();
        String query = "SELECT name, date, price FROM appointments WHERE user_id="+sharedPreferences.getInt("username key", 0);
        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()){
            HashMap<String,String> appointments = new HashMap<>();
            appointments.put("name",cursor.getString(cursor.getColumnIndex("name")));
            appointments.put("date",cursor.getString(cursor.getColumnIndex("date")));
            appointments.put("price",cursor.getString(cursor.getColumnIndex("price")));
            appointmentList.add(appointments);
        }
        return  appointmentList;
    }
}
