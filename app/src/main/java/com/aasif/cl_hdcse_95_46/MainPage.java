package com.aasif.cl_hdcse_95_46;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        getSupportActionBar().hide();

        BottomNavigationView navigationView = findViewById(R.id.bottomNavigationView);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.flFragment, new HomeFragment())
                .commit();

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.homePage:
                        getSupportFragmentManager().beginTransaction().replace(R.id.flFragment,
                                new HomeFragment()).commit();
                        break;

                    case R.id.appoinmentPage:
                        getSupportFragmentManager().beginTransaction().replace(R.id.flFragment,
                                new AppointmentFragment()).commit();
                        break;
                    case R.id.aboutUsPage:
                        getSupportFragmentManager().beginTransaction().replace(R.id.flFragment,
                                new AboutUsFragment()).commit();
                        break;
                    case R.id.accountPage:
                        getSupportFragmentManager().beginTransaction().replace(R.id.flFragment,
                                new AccountFragment()).commit();
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Do You Want to Quit the app?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finishAffinity();
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
}