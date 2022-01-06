package com.aasif.cl_hdcse_95_46;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
                "date_time text, price text, user_id INTEGER ,FOREIGN KEY (user_id) REFERENCES customers(user_id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

//    Creates the Customer
    public boolean createCustomer(Customers customers) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("name", customers.getName());
        cv.put("email", customers.getEmail());
        cv.put("password", customers.getPassword());

        long insert = db.insert("customers", null, cv);

        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

//    Checks for similar customer Emails
    public boolean checkDuplicateCustomerEmail(String email) {

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("select * from customers where email = ?", new String[]{email});

        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    // Creates new Admin
    public boolean createAdmins(Admins admins) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("name", admins.getName());
        cv.put("email", admins.getEmail());
        cv.put("password", admins.getPassword());

        long insert = db.insert("admins", null, cv);

        if (insert == -1) {
            return false;
        }
        return true;
    }

//    Checking for duplicate/ similar admin email
    public boolean checkDuplicateAdminName(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from admins where name = ?", new String[]{name});

        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

//    Checks the customer email and password when login
    public Boolean checkCustomerCredentials(String email, String pass) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("select user_id from customers where email = ? AND password = ?",
                new String[]{email, pass});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            int user_id = cursor.getInt(0);
            System.out.println(user_id);

//          Stores the user_id so when displaying appointments it displays only for the relevant users
            SharedPreferences sharedPreferences = con.getSharedPreferences("save userinfo", Context.MODE_PRIVATE);

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("username key", user_id);
            editor.commit();

            return true;

        } else {
            return false;
        }
    }

//  Checks admin email and password when login
    public Boolean checkAdminCredentials(String email, String pass) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("select * from admins where email = ? AND password = ?",
                new String[]{email, pass});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }


//    Checks the appointment by adding 10 mins to it
    public Boolean history(String startdate, String enddate) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM appointments" +
                " WHERE date_time" +
                " BETWEEN ? AND ?", new String[]{startdate, enddate});


        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }


    public boolean addAppointment(Appoinment appoinment) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        SharedPreferences sharedPreferences = con.getSharedPreferences("save userinfo", Context.MODE_PRIVATE);

        cv.put("id",appoinment.getId());
        cv.put("name", appoinment.getName());
        cv.put("date_time", appoinment.getDate());
        cv.put("price", appoinment.getPrice());
        cv.put("user_id", sharedPreferences.getInt("username key", 0));
        long insert = db.insert("appointments", null, cv);

        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }


//  Method to Update an appointment by the relevant customer
    public void updateAppointment(String row_id, String name, String date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("name", name);
        cv.put("date_time", date);

        long update = db.update("appointments",cv,"id=?",new String[]{row_id});

        if(update==-1){
            Toast.makeText(con.getApplicationContext(), "Failed to Update", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(con.getApplicationContext(), "Successfully Updated", Toast.LENGTH_SHORT).show();
        }
    }

//   Retrieving the customers name by using shared preference user_id
    public String getAppoinmentCustomerName() {

        SQLiteDatabase db = this.getReadableDatabase();
        String appoinName;
        SharedPreferences sharedPreferences = con.getSharedPreferences("save userinfo", Context.MODE_PRIVATE);
        String query = "SELECT name FROM customers WHERE user_id=" + sharedPreferences.getInt("username key", 0);
        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                appoinName = cursor.getString(0);
                return appoinName;
            } while (cursor.moveToNext());

        }else{

        }
        return appoinName="";
    }

//    Retireving the customers email by using shared preference to display on the account page
    public String getAppoinmentCustomerEmail() {

        SQLiteDatabase db = this.getReadableDatabase();
        String appoinName;
        SharedPreferences sharedPreferences = con.getSharedPreferences("save userinfo", Context.MODE_PRIVATE);
        String query = "SELECT email FROM customers WHERE user_id=" + sharedPreferences.getInt("username key", 0);
        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                appoinName = cursor.getString(0);
                return appoinName;
            } while (cursor.moveToNext());

        }else{

        }
        return appoinName="";
    }

//  Getting the no of appointments created by a specific user to calculate the total
    public long getAppoinmentTotal(int user_id) {

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT COUNT(id) FROM appointments WHERE user_id=" + user_id;

        SQLiteStatement statement = db.compileStatement(query);
        long count = statement.simpleQueryForLong();
        return count;
    }


//    Retrieving only the appointments booked by a customer
    @SuppressLint("Range")
    public ArrayList<HashMap<String, String>> GetAppointmentsForCustomer() {
        SQLiteDatabase db = this.getWritableDatabase();
        SharedPreferences sharedPreferences = con.getSharedPreferences("save userinfo", Context.MODE_PRIVATE);
        ArrayList<HashMap<String, String>> appointmentList = new ArrayList<>();
        String query = "SELECT id, name, date_time, price FROM appointments WHERE user_id=" + sharedPreferences.getInt("username key", 0);
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            HashMap<String, String> appointments = new HashMap<>();
            appointments.put("id",cursor.getString(cursor.getColumnIndex("id")));
            appointments.put("name", cursor.getString(cursor.getColumnIndex("name")));
            appointments.put("date_time", cursor.getString(cursor.getColumnIndex("date_time")));
            appointments.put("price", cursor.getString(cursor.getColumnIndex("price")));
            appointmentList.add(appointments);
        }
        return appointmentList;
    }


//    Retreving all the appointments booked by all the customers
    @SuppressLint("Range")
    public ArrayList<HashMap<String, String>> GetAllAppointments() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> appointmentList = new ArrayList<>();
        String query = "SELECT id, name, date_time, price, user_id FROM appointments";
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            HashMap<String, String> appointments = new HashMap<>();
            appointments.put("id",cursor.getString(cursor.getColumnIndex("id")));
            appointments.put("name", cursor.getString(cursor.getColumnIndex("name")));
            appointments.put("date_time", cursor.getString(cursor.getColumnIndex("date_time")));
            appointments.put("price", cursor.getString(cursor.getColumnIndex("price")));
            appointments.put("user_id", cursor.getString(cursor.getColumnIndex("user_id")));

            appointmentList.add(appointments);
        }
        return appointmentList;
    }

//    Delete an appointment by using the customers id
    public boolean deleteAppointment(String id){

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery( "DELETE FROM appointments WHERE id = ?",new String[]{id});


        if(cursor.moveToFirst()){
            return true;
        } else{
            return false;
        }
    }

//  Method used in admin activity to display the appointments booked to calculate bill
//  Retrieving the appointments booked by a customer using the user_id
    @SuppressLint("Range")
    public ArrayList<HashMap<String, String>> GetAppointmentsLogsForCustomer(int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> appointmentList = new ArrayList<>();
        String query = "SELECT id, name, date_time, price FROM appointments WHERE user_id=" + userId;
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            HashMap<String, String> appointments = new HashMap<>();
            appointments.put("id",cursor.getString(cursor.getColumnIndex("id")));
            appointments.put("name", cursor.getString(cursor.getColumnIndex("name")));
            appointments.put("date_time", cursor.getString(cursor.getColumnIndex("date_time")));
            appointments.put("price", cursor.getString(cursor.getColumnIndex("price")));
            appointmentList.add(appointments);
        }
        return appointmentList;
    }

}
