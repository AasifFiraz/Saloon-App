package com.aasif.cl_hdcse_95_46;

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

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Appoinment_Booking_Admin extends AppCompatActivity {
    private TextView txtSelectedDate, txtSelectedTime, txtSelectedDuration, TextDate, TextTime, TextPrice;
    private Button btnSelectedDate, btnBookAppoinment, btnSelectedTime;
    private TextInputLayout txtAppointmentNameAdmin;
    private TextInputEditText EdtAppointmantNameAdmin;
    private CheckBox serviceCheckBox;
    private DBConnector dbcon;
    private RelativeLayout RlAppoinment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appoinment_booking_admin);
        getSupportActionBar().hide();

        txtSelectedDate = findViewById(R.id.txtSelectedDateAdmin);
        txtSelectedTime = findViewById(R.id.txtSelectedTimeAdmin);
        txtSelectedDuration = findViewById(R.id.txtSelectedDurationAdmin);
        btnSelectedDate = findViewById(R.id.btnSelectDateAdmin);
        btnSelectedTime = findViewById(R.id.btnSelectTimeAdmin);
        btnBookAppoinment = findViewById(R.id.btnBookAppoinmentAdmin);
        TextDate = findViewById(R.id.TextDateAdmin);
        TextPrice = findViewById(R.id.TotalPriceTextAdmin);
        serviceCheckBox = findViewById(R.id.serviceCheckBoxAdmin);
        TextTime = findViewById(R.id.TextTimeAdmin);
        txtAppointmentNameAdmin = findViewById(R.id.txtAppointmentNameAdmin);
        EdtAppointmantNameAdmin = findViewById(R.id.EdtAppointmentNameAdmin);
        RlAppoinment = findViewById(R.id.RlAppoinmentBookingAdmin);

        btnSelectedTime.setBackgroundColor(Color.parseColor("#cccccc"));
        btnSelectedTime.setEnabled(false);

        btnBookAppoinment.setBackgroundColor(Color.parseColor("#cccccc"));
        btnBookAppoinment.setEnabled(false);
        dbcon = new DBConnector(this);

        btnSelectedDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar c = Calendar.getInstance();
                int year, month, day;
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datedialog = new DatePickerDialog(Appoinment_Booking_Admin.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                        SimpleDateFormat simpledateformat = new SimpleDateFormat("EEEE LLLL dd");
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, month, day);
                        String selectedDate = simpledateformat.format(newDate.getTime());


                        txtSelectedDate.setText(day + "-" + (month + 1) + "-" + year);
                        dbcon = new DBConnector(Appoinment_Booking_Admin.this);


                        TextDate.setText(selectedDate);
                        btnSelectedTime.setBackgroundColor(Color.parseColor("#F7BF50"));
                        btnSelectedTime.setEnabled(true);
                    }
                }, year, month, day);
                datedialog.getDatePicker().setMinDate(System.currentTimeMillis()
//                      Setting the picker date 2 days after the current date
                        + 24 * 60 * 60 * 1000 + 24 * 60 * 60 * 1000);

                datedialog.getDatePicker().setSpinnersShown(true);
                datedialog.show();
            }
        });

        btnSelectedTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timepicker = new TimePickerDialog(Appoinment_Booking_Admin.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int min) {

                        Calendar datetime = Calendar.getInstance();
                        Calendar c = Calendar.getInstance();
                        datetime.set(c.HOUR_OF_DAY, hourOfDay);
                        datetime.set(c.MINUTE, min);

//                      Checking if Time selected by customer is after 5.00 pm
                        if (hourOfDay > 17) {
                            Snackbar.make(RlAppoinment, "Saloon is closed during this time\nSelect a time before 5.00 pm", Snackbar.LENGTH_LONG)
                                    .setAction("CLOSE", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                        }
                                    })
                                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                    .show();

//                      Checking if Time selected by customer is after 9.00 am and before 5.00 pm
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


                                    } else {
                                        btnBookAppoinment.setBackgroundColor(Color.parseColor("#cccccc"));
                                        btnBookAppoinment.setEnabled(false);
                                        TextPrice.setVisibility(View.INVISIBLE);
                                    }
                                }
                            });

//                      Displaying error message if time is before 9.00 am
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

        btnBookAppoinment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//              Storing the time selected and reducing 10 mins from it
                String timeMinus = txtSelectedTime.getText().toString();
                SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
                Date d = null;
                try {
                    d = df.parse(timeMinus);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Calendar cal = Calendar.getInstance();
                cal.setTime(d);
                cal.add(Calendar.MINUTE,-10);
                String newTimeMinus = df.format(cal.getTime());
                String DateTimeMinus = txtSelectedDate.getText().toString() + " " + newTimeMinus;

//              Storing the time selected and adding 10 mins to it
                String timePlus = txtSelectedTime.getText().toString();
                SimpleDateFormat dfp = new SimpleDateFormat("HH:mm:ss");
                Date dp = null;
                try {
                    dp = dfp.parse(timePlus);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Calendar calp = Calendar.getInstance();
                calp.setTime(dp);
                calp.add(Calendar.MINUTE,10);
                String newTimePlus = dfp.format(calp.getTime());
                String DateTimePlus = txtSelectedDate.getText().toString() + " " + newTimePlus;

                Appoinment appoinment;
                dbcon = new DBConnector(Appoinment_Booking_Admin.this);

                String appointCusName = EdtAppointmantNameAdmin.getText().toString().trim();

                appoinment = new Appoinment(null, appointCusName, txtSelectedDate.getText().toString() + " " + txtSelectedTime.getText().toString(), "Rs.800");

                Boolean forAppointmentdatestimes = dbcon.history(DateTimeMinus,DateTimePlus);

                // Checking if date and time are already available in database
                // Restricting customer to book a time which is already booked and also 10 mins after
                if (forAppointmentdatestimes == true) {
                    Toast.makeText(getApplicationContext(), "Date and Time are Booked", Toast.LENGTH_SHORT).show();
                } else {
                    dbcon.addAppointmentAdmin(appoinment);
                    Toast.makeText(getApplicationContext(), "Successfully Booked", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(Appoinment_Booking_Admin.this, AdminAppointments.class);
                    startActivity(intent);
                    finish();

                }
            }

        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}