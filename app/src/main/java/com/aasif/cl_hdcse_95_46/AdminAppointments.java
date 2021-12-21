package com.aasif.cl_hdcse_95_46;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class AdminAppointments extends AppCompatActivity {
    ListView lvAppointments;
    TextView txtNoAppointment;
    Button btnEditAppointment, btnDeleteAppointment;
    ViewGroup viewGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_appointments);
        getSupportActionBar().hide();

        lvAppointments = findViewById(R.id.lstAllAppointments);
        txtNoAppointment = findViewById(R.id.txtNoAppointmentMessageAdmin);
        viewGroup = findViewById(R.id.content);

        DBConnector dbcon = new DBConnector(this);
        showAppointmentList(dbcon);

        lvAppointments.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                AlertDialog.Builder builder = new AlertDialog.Builder(AdminAppointments.this);
                View view1 = LayoutInflater.from(AdminAppointments.this).inflate(R.layout.custom_alert_dialog_appointment, viewGroup, false);
                builder.setCancelable(true);
                builder.setView(view1);

                final AlertDialog alertDialog = builder.create();
                btnDeleteAppointment = view1.findViewById(R.id.btnDeleteAppointment);
                btnEditAppointment = view1.findViewById(R.id.btnEditAppointment);
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                ArrayList<HashMap<String, String>> userList = dbcon.GetAppointmentsForCustomer();

                ListAdapter adapter = new SimpleAdapter(AdminAppointments.this, userList, R.layout.appointments_list,
                        new String[]{"name", "date", "price"}, new int[]{R.id.txtBookedName, R.id.txtBookedDate,
                        R.id.txtBookedPrice});


                HashMap<String, Object> obj = (HashMap<String, Object>) adapter.getItem(position);

                btnDeleteAppointment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String appoinmentId = (String) obj.get("id");
                        dbcon.deleteAppointment(appoinmentId);
                        showAppointmentList(dbcon);

                        alertDialog.dismiss();
                    }
                });

                btnEditAppointment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String appointmentId = (String) obj.get("id");
                        String appointmentName = (String) obj.get("name");
                        String appointmentPrice = (String) obj.get("price");
                        String appointmentDateTime = (String) obj.get("date");
                        String [] sepAppointmentDateTime = appointmentDateTime.split(" ");

                        Bundle bundle = new Bundle();
                        Intent appointment_edit_page = new Intent(AdminAppointments.this, Appointment_Edit_Activity_Admin.class);
                        bundle.putString("appointId",appointmentId);
                        bundle.putString("appointName", appointmentName);
                        bundle.putString("appointPrice",appointmentPrice);
                        bundle.putString("appointDate",sepAppointmentDateTime[0]);
                        bundle.putString("appointTime",sepAppointmentDateTime[1]);
                        appointment_edit_page.putExtras(bundle);
                        startActivity(appointment_edit_page);
                        alertDialog.dismiss();
                    }
                });

                alertDialog.show();

            }
        });
    }
    private void showAppointmentList (DBConnector dbcon){
        ArrayList<HashMap<String, String>> userList = dbcon.GetAppointmentsForCustomer();

        ListAdapter adapter = new SimpleAdapter(this, userList, R.layout.appointments_list,
                new String[]{"name","date","price"}, new int[]{R.id.txtBookedName, R.id.txtBookedDate,
                R.id.txtBookedPrice});


        lvAppointments.setEmptyView(txtNoAppointment);
        lvAppointments.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        androidx.appcompat.app.AlertDialog.Builder builder1 = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder1.setMessage("Do You Want to Logout?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent i = new Intent(getApplicationContext(), Main_Screen.class);
                        startActivity(i);
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        androidx.appcompat.app.AlertDialog alert11 = builder1.create();
        alert11.show();
    }
}