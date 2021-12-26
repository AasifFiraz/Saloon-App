package com.aasif.cl_hdcse_95_46;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AccountFragment extends Fragment {
    private TextView accountHolderText, accountHolderTextName, accountHolderTextEmail, btnLogout;
    private DBConnector dbcon;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account,container,false);

        accountHolderText= view.findViewById(R.id.accountHolderText);
        accountHolderTextName = view.findViewById(R.id.accountHolderTextName);
        accountHolderTextEmail = view.findViewById(R.id.accountHolderTextEmail);
        btnLogout = view.findViewById(R.id.btnLogout);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dbcon = new DBConnector(getActivity());

//       Retrieving the account holder name and displaying on textview
        String accountHolderName = dbcon.getAppoinmentCustomerName();
        accountHolderText.setText(accountHolderName);
        accountHolderTextName.setText("Name - "+accountHolderName);

//       Retrieving the account holder email and displaying on textview
        String accountHolderEmail = dbcon.getAppoinmentCustomerEmail();
        accountHolderTextEmail.setText("Email - "+accountHolderEmail);
        
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Logged Out Successfully", Toast.LENGTH_SHORT).show();
                Intent MainScreen = new Intent(getActivity(), Main_Screen.class);
                startActivity(MainScreen);
            }
        });
    }
}
