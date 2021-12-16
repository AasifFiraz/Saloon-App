package com.aasif.cl_hdcse_95_46;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Appoinment_Booking extends AppCompatActivity {
    private TextView txtSelectedDate, txtSelectedTime, txtSelectedDuration, TextDate, TextTime, TextPrice, TextConfirm, txtAppName;
    private Button btnSelectedDate, btnBookAppoinment, btnSelectedTime;
    private CheckBox serviceCheckBox;
    private DBConnector dbcon;
    private RelativeLayout RlAppoinment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appoinment_booking);
        getSupportActionBar().hide();

        txtSelectedDate = findViewById(R.id.txtSelectedDate);
        txtSelectedTime = findViewById(R.id.txtSelectedTime);
        txtSelectedDuration = findViewById(R.id.txtSelectedDuration);
        btnSelectedDate = findViewById(R.id.btnSelectDate);
        btnSelectedTime = findViewById(R.id.btnSelectTime);
        txtAppName = findViewById(R.id.txtappoinmentPersonName);
        btnBookAppoinment = findViewById(R.id.btnBookAppoinment);
        TextDate = findViewById(R.id.TextDate);
        TextPrice = findViewById(R.id.TotalPriceText);
        TextConfirm = findViewById(R.id.TextReviewandconfirm);
        serviceCheckBox = findViewById(R.id.serviceCheckBox);
        TextTime = findViewById(R.id.TextTime);
        RlAppoinment = findViewById(R.id.RlAppoinmentBooking);

        btnSelectedTime.setBackgroundColor(Color.parseColor("#cccccc"));
        btnSelectedTime.setEnabled(false);

        btnBookAppoinment.setBackgroundColor(Color.parseColor("#cccccc"));
        btnBookAppoinment.setEnabled(false);
        dbcon = new DBConnector(this);

        String appoinName = dbcon.getAppoinmentCustomerName();
        txtAppName.setText(appoinName);

        btnSelectedDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar c = Calendar.getInstance();
                int year, month, day;
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datedialog = new DatePickerDialog(Appoinment_Booking.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                        SimpleDateFormat simpledateformat = new SimpleDateFormat("EEEE LLLL dd yyyy");
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, month, day);
                        String selectedDate = simpledateformat.format(newDate.getTime());

                        txtSelectedDate.setText(day + "-" + (month + 1) + "-" + year);
                        dbcon = new DBConnector(Appoinment_Booking.this);

                        TextDate.setText(selectedDate);
                        btnSelectedTime.setBackgroundColor(Color.parseColor("#F7BF50"));
                        btnSelectedTime.setEnabled(true);
                    }
                }, year, month, day);
                datedialog.getDatePicker().setMinDate(System.currentTimeMillis() + 24 * 60 * 60 * 1000 + 24 * 60 * 60 * 1000);

                datedialog.getDatePicker().setSpinnersShown(true);
                datedialog.show();
            }
        });

        btnSelectedTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timepicker = new TimePickerDialog(Appoinment_Booking.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int min) {

                        Calendar datetime = Calendar.getInstance();
                        Calendar c = Calendar.getInstance();
                        datetime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        datetime.set(Calendar.MINUTE, min);


                        if (hourOfDay > 17) {
                            Snackbar.make(RlAppoinment, "Saloon is closed during this time\nSelect a time before 5.00 pm", Snackbar.LENGTH_LONG)
                                    .setAction("CLOSE", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                        }
                                    })
                                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                    .show();
                        } else if (hourOfDay >= 9) {

                            int hour = hourOfDay % 12;
                            txtSelectedTime.setText(String.format("%02d:%02d:%02d", hourOfDay, min, 00));
                            TextTime.setText(String.format("%02d:%02d %s", hour == 0 ? 12 : hour,
                                    min, hourOfDay < 12 ? "am" : "pm"));
                            txtSelectedDuration.setText("Duration - 10 mins for Haircut");


                            serviceCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                                    if (isChecked) {

                                        btnBookAppoinment.setBackgroundColor(Color.parseColor("#F7BF50"));
                                        btnBookAppoinment.setEnabled(true);
                                        TextPrice.setVisibility(View.VISIBLE);

                                        TextConfirm.setVisibility(View.VISIBLE);
                                        btnBookAppoinment.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {

                                                String myTime = txtSelectedTime.getText().toString();
                                                SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
                                                Date d = null;
                                                try {
                                                    d = df.parse(myTime);
                                                } catch (ParseException e) {
                                                    e.printStackTrace();
                                                }
                                                Calendar cal = Calendar.getInstance();
                                                cal.setTime(d);
                                                cal.add(Calendar.MINUTE, 10);
                                                String newTime = df.format(cal.getTime());
                                                String newDateTime = txtSelectedDate.getText().toString() + " " + newTime;
                                                Appoinment appoinment;
                                                dbcon = new DBConnector(Appoinment_Booking.this);

                                                appoinment = new Appoinment(null, txtAppName.getText().toString(), txtSelectedDate.getText().toString() + " " + txtSelectedTime.getText().toString(), "Rs.800");


                                                System.out.println(txtSelectedDate.getText().toString() + " " + txtSelectedTime.getText().toString());
                                                System.out.println(newDateTime);
                                                Boolean forAppointmentdatestimes = dbcon.history(txtSelectedDate.getText().toString() + " " + txtSelectedTime.getText().toString(), newDateTime);

                                                if (forAppointmentdatestimes == true) {
                                                    Toast.makeText(getApplicationContext(), "Date Taken", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    dbcon.addAppointment(appoinment);
                                                    Toast.makeText(getApplicationContext(), "No", Toast.LENGTH_SHORT).show();

                                                    Intent intent = new Intent(Appoinment_Booking.this, List_Page.class);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                            }

                                        });


                                    } else {
                                        btnBookAppoinment.setBackgroundColor(Color.parseColor("#cccccc"));
                                        btnBookAppoinment.setEnabled(false);
                                        TextPrice.setVisibility(View.INVISIBLE);
                                    }
                                }
                            });

                        } else {
                            Snackbar.make(RlAppoinment, "Select a time after 9.00 am", Snackbar.LENGTH_LONG)
                                    .setAction("CLOSE", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                        }
                                    })
                                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                    .show();
                        }

                    }
                }, 9, 0, true);
                timepicker.show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}