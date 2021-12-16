package com.aasif.cl_hdcse_95_46;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class AdminAppointments extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_appointments);
        getSupportActionBar().hide();

        ListView lv = findViewById(R.id.lstAllAppointments);
        TextView txtNoAppointment = findViewById(R.id.txtNoAppointmentMessage);

        DBConnector dbcon = new DBConnector(this);
        ArrayList<HashMap<String, String>> userList = dbcon.GetAllAppointments();

        ListAdapter adapter = new SimpleAdapter(this, userList, R.layout.appointments_list,
                new String[]{"name","date","time","price"}, new int[]{R.id.txtBookedName, R.id.txtBookedDate,
                R.id.txtBookedTime,R.id.txtBookedPrice});

        if (adapter.getCount() < 0)
        {
            txtNoAppointment.setVisibility(View.VISIBLE);
        }else{
            lv.setAdapter(adapter);
        }
    }
}