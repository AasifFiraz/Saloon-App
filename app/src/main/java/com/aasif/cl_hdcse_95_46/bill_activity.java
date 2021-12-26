package com.aasif.cl_hdcse_95_46;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class bill_activity extends AppCompatActivity {
    private TextView txtTotalBillAmount, txtNoOfAppointments;
    private ListView lstBillAppointments;
    private DBConnector dbcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        getSupportActionBar().hide();

        dbcon = new DBConnector(this);
        txtTotalBillAmount = findViewById(R.id.txtTotalBillAmount);
        txtNoOfAppointments = findViewById(R.id.txtNoOfAppointments);
        lstBillAppointments = findViewById(R.id.LstBillAppointments);

        Intent i  = getIntent();
        String appUserId  = i.getStringExtra("appUser_Id");

        ArrayList<HashMap<String, String>> userList = dbcon.GetAppointmentsLogsForCustomer(Integer.parseInt(appUserId));

        ListAdapter adapter = new SimpleAdapter(this, userList, R.layout.appointments_list,
                new String[]{"name", "date", "price"}, new int[]{R.id.txtBookedName, R.id.txtBookedDate,
                R.id.txtBookedPrice});

        lstBillAppointments.setAdapter(adapter);

//      Getting the total from the number of bookings
        long tot = dbcon.getAppoinmentTotal(Integer.parseInt(appUserId));
        txtNoOfAppointments.setText("You Have "+tot+" Appointments");
        txtTotalBillAmount.setText("Your Total\nRs."+tot*800);

    }

}