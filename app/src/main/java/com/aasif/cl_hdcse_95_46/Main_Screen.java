package com.aasif.cl_hdcse_95_46;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Main_Screen extends AppCompatActivity {
    private Button btnRegisterCustomer;
    private Button btnRegisterAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        getSupportActionBar().hide();

        btnRegisterCustomer = findViewById(R.id.btnRegisterCustomer);
        btnRegisterAdmin = findViewById(R.id.btnRegisterAdmin);

        btnRegisterCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent RegCustomer = new Intent(Main_Screen.this, Customer_Registration.class);
                startActivity(RegCustomer);
                finish();
            }
        });

        btnRegisterAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Creating an alert builder
                AlertDialog.Builder builder = new AlertDialog.Builder(Main_Screen.this);
                builder.setTitle("Enter Default Admin Password");

                final View customAlert = getLayoutInflater().inflate(R.layout.custom_alert_dialog, null);
                builder.setView(customAlert);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        EditText editText = customAlert.findViewById(R.id.EdtCheckPass);
                        String entered_pass = editText.getText().toString();


                        if (entered_pass.trim().matches("admin")) {
                            Intent RegAdmin = new Intent(Main_Screen.this, Admin_Registration.class);
                            startActivity(RegAdmin);
                            finish();
                        } else if (entered_pass.isEmpty()) {
                            Toast.makeText(Main_Screen.this, "Enter Password", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Main_Screen.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }

        });
    }
}