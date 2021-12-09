package com.aasif.cl_hdcse_95_46;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class Appointments_List extends AppCompatActivity {
    private ListView lstAppointments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointments_list);
        getSupportActionBar().hide();

        DBConnector dbcon = new DBConnector(this);

        ArrayList<HashMap<String, String>> userList = dbcon.GetAppointments();
        ListView lv = (ListView) findViewById(R.id.lstAppointments);
        ListAdapter adapter = new SimpleAdapter(Appointments_List.this, userList, R.layout.appointments_list,
                new String[]{"name","date","time","price"}, new int[]{R.id.txtBookedName, R.id.txtBookedDate,
                R.id.txtBookedTime,R.id.txtBookedPrice});
        lv.setAdapter(adapter);
    }
}