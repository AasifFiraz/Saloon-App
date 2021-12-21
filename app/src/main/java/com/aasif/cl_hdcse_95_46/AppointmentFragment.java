package com.aasif.cl_hdcse_95_46;

import android.app.AlertDialog;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;

public class AppointmentFragment extends Fragment {
    ListView lvAppointments;
    TextView txtNoAppointment;
    ViewGroup viewGroup;
    Button btnEditAppointment, btnDeleteAppointment;

    public AppointmentFragment() {
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_appoinments, container, false);
        lvAppointments = view.findViewById(R.id.LstAppointments);
        txtNoAppointment = view.findViewById(R.id.txtNoAppointmentMessage);

        viewGroup = view.findViewById(R.id.content);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DBConnector dbcon = new DBConnector(getActivity());
        showAppointmentList(dbcon);

        lvAppointments.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.custom_alert_dialog_appointment, viewGroup, false);
                builder.setCancelable(true);
                builder.setView(view1);

                final AlertDialog alertDialog = builder.create();
                btnDeleteAppointment = view1.findViewById(R.id.btnDeleteAppointment);
                btnEditAppointment = view1.findViewById(R.id.btnEditAppointment);
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                ArrayList<HashMap<String, String>> userList = dbcon.GetAppointmentsForCustomer();

                ListAdapter adapter = new SimpleAdapter(getActivity(), userList, R.layout.appointments_list,
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
                       Intent appointment_edit_page = new Intent(getActivity(), Appointment_Edit_Activity_Customer.class);
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

    private void showAppointmentList(DBConnector dbcon) {
        ArrayList<HashMap<String, String>> userList = dbcon.GetAppointmentsForCustomer();

        ListAdapter adapter = new SimpleAdapter(getActivity(), userList, R.layout.appointments_list,
                new String[]{"name", "date", "price"}, new int[]{R.id.txtBookedName, R.id.txtBookedDate, R.id.txtBookedPrice});


        lvAppointments.setEmptyView(txtNoAppointment);
        lvAppointments.setAdapter(adapter);
    }

}
