package com.aasif.cl_hdcse_95_46;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class Home_Page extends AppCompatActivity {
    private TextView txtcallUs, txtmailUs, txtOurLocation, txtOpenHours;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        getSupportActionBar().hide();


        txtcallUs = findViewById(R.id.txtCallUs);
        txtmailUs = findViewById(R.id.txtMailUs);
        txtOurLocation = findViewById(R.id.txtOurLocation);
        txtOpenHours = findViewById(R.id.txtOpenHours);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.homePage:
                        Toast.makeText(Home_Page.this, "Home", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.appoinmentPage:
                        Toast.makeText(Home_Page.this, "Appointment", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.aboutUsPage:
                        Toast.makeText(Home_Page.this, "About Us", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.accountPage:
                        Toast.makeText(Home_Page.this, "Accoount", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });

        txtcallUs.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(Home_Page.this);
                builder1.setMessage("Do You Want to Open Your Phone?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(Intent.ACTION_DIAL);
                                intent.setData(Uri.parse("tel:077 123 4566"));
                                startActivity(intent);

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
        });

        txtmailUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder1 = new AlertDialog.Builder(Home_Page.this);
                builder1.setMessage("Do You Want to Send An Email?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                try{
                                    Intent intent = new Intent (Intent.ACTION_VIEW , Uri.parse("mailto:" + "your_email"));
                                    intent.putExtra(Intent.EXTRA_SUBJECT, "your_subject");
                                    intent.putExtra(Intent.EXTRA_TEXT, "your_text");
                                    startActivity(intent);
                                }catch(ActivityNotFoundException e){
                                    Toast.makeText(getApplicationContext(), "Error Occured", Toast.LENGTH_SHORT).show();
                                }

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
        });


        String callUsText = "Call Us\n+94 77 123 4567";
        String mailUsText = "Mail Us\nsaloonsd@gmail.com";
        String locationText = "Our Location\n123/3 Bellanthara Road, Colombo";
        String openHours = "Open Hours\nMonday                         9.00 AM - 4.00 PM\n" +
                "Tuesday                      10.00 AM - 5.00 PM\n" +
                "Wednesday                   9.00 AM - 2.00 PM\n" +
                "Thursday                       9.00 AM - 3.00 PM\n" +
                "Friday                             8.00 AM - 5.00 PM\n" +
                "Saturday                      10.00 AM - 4.00 PM";

        // color change for call us text
        SpannableString callss =  new SpannableString(callUsText);
        callss.setSpan(new RelativeSizeSpan(1f), 0,7, 0);
        callss.setSpan(new ForegroundColorSpan(Color.parseColor("#F7BF50")), 0, 7, 0);
        // color change for mail us text
        SpannableString mailss =  new SpannableString(mailUsText);
        mailss.setSpan(new RelativeSizeSpan(1f), 0,7, 0);
        mailss.setSpan(new ForegroundColorSpan(Color.parseColor("#F7BF50")), 0, 7, 0);
        // color change for our location text
        SpannableString locationss =  new SpannableString(locationText);
        locationss.setSpan(new RelativeSizeSpan(1f), 0,7, 0);
        locationss.setSpan(new ForegroundColorSpan(Color.parseColor("#F7BF50")), 0, 12, 0);
        // color change for our open Hours text
        SpannableString hoursss =  new SpannableString(openHours);
        hoursss.setSpan(new RelativeSizeSpan(1f), 0,7, 0);
        hoursss.setSpan(new ForegroundColorSpan(Color.parseColor("#F7BF50")), 0, 10, 0);

        txtcallUs.setText(callss);
        txtmailUs.setText(mailss);
        txtOurLocation.setText(locationss);
        txtOpenHours.setText(hoursss);
    }

}