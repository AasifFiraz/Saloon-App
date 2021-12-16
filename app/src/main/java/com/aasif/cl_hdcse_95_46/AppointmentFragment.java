package com.aasif.cl_hdcse_95_46;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;

public class AppointmentFragment extends Fragment {

    public AppointmentFragment() {
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_appoinments,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListView lv = view.findViewById(R.id.LstAppointments);
        TextView txtNoAppointment = view.findViewById(R.id.txtNoAppointmentMessage);

        DBConnector dbcon = new DBConnector(getActivity());
        ArrayList<HashMap<String, String>> userList = dbcon.GetAppointments();

        ListAdapter adapter = new SimpleAdapter(getActivity(), userList, R.layout.appointments_list,
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
