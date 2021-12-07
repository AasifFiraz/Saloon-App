package com.aasif.cl_hdcse_95_46;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Appoinment_Booking extends AppCompatActivity {
    private TextView txtSelectedDate, txtSelectedTime, txtSelectedDuration, TextDate, TextTime;
    private Button btnSelectedDate, btnBookAppoinment, btnSelectedTime;
    private CheckBox serviceCheckBox;
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
        btnBookAppoinment = findViewById(R.id.btnBookAppoinment);
        TextDate = findViewById(R.id.TextDate);
        serviceCheckBox = findViewById(R.id.serviceCheckBox);
        TextTime = findViewById(R.id.TextTime);
        RlAppoinment = findViewById(R.id.RlAppoinmentBooking);

        btnSelectedTime.setBackgroundColor(Color.parseColor("#cccccc"));
        btnSelectedTime.setEnabled(false);

        btnBookAppoinment.setBackgroundColor(Color.parseColor("#cccccc"));
        btnBookAppoinment.setEnabled(false);


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
                        txtSelectedDate.setText(selectedDate);
                        TextDate.setVisibility(View.VISIBLE);
                        btnSelectedTime.setBackgroundColor(Color.parseColor("#F7BF50"));
                        btnSelectedTime.setEnabled(true);
                    }
                }, year,month,day);
                datedialog.getDatePicker().setMinDate(System.currentTimeMillis()+24*60*60*1000+24*60*60*1000);

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

                            if(hourOfDay > 17){
                                Snackbar.make(RlAppoinment, "Saloon is closed during this time\nSelect a time before 5.00 pm", Snackbar.LENGTH_LONG)
                                        .setAction("CLOSE", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {

                                            }
                                        })
                                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                                        .show();                               }
                            else if (hourOfDay >= 9 ) {
                                int hour = hourOfDay % 12;
                                txtSelectedTime.setText(String.format("%02d:%02d %s", hour == 0 ? 12 : hour,
                                        min, hourOfDay < 12 ? "am" : "pm"));
                                TextTime.setVisibility(View.VISIBLE);
                                txtSelectedDuration.setText("Duration - 10 mins for Haircut");


                               serviceCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                   @Override
                                   public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                                       if(b){
                                           btnBookAppoinment.setBackgroundColor(Color.parseColor("#F7BF50"));
                                           btnBookAppoinment.setEnabled(true);
                                       }else{
                                           btnBookAppoinment.setBackgroundColor(Color.parseColor("#cccccc"));
                                           btnBookAppoinment.setEnabled(false);
                                       }
                                   }
                               });

                            }
                            else {
                                Snackbar.make(RlAppoinment, "Select a time after 9.00 am", Snackbar.LENGTH_LONG)
                                        .setAction("CLOSE", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {

                                            }
                                        })
                                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                                        .show();                             }

                        }
                    }, 9, 0, true);
                    timepicker.show();
                }
            });
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
}