package com.aasif.cl_hdcse_95_46;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Appointment_Edit_Activity_Customer extends AppCompatActivity {
    private TextView txtAppointmentName, txtAppointmentDate, txtAppointmentPrice, txtAppointmentTime, txtSelectedEditDate,
            EditDateText, EditNameText, EditTimeText, txtSelectedEditTime;
    private TextInputLayout txtEditNewName;
    private TextInputEditText edtEditNewName;
    private Button btnEditName, btnSelectNewDate,btnSelectNewTime, btnSaveEdit;
    private RelativeLayout RlEditAppointment;
    private DBConnector dbcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_edit_customer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#f7f7f7\">" + "Edit Appointment" + "</font>"));
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2c2c2c")));
        actionBar.setLogo(R.drawable.ic_edit_icon);

        txtAppointmentDate = findViewById(R.id.txtEditAppointmentDate);
        txtAppointmentName = findViewById(R.id.txtEditAppointmentName);
        txtAppointmentPrice = findViewById(R.id.txtEditAppointmentPrice);
        txtAppointmentTime = findViewById(R.id.txtEditAppointmentTime);
        txtEditNewName = findViewById(R.id.txtEditNewName);
        btnSelectNewDate = findViewById(R.id.btnSelectNewDate);
        btnSelectNewTime = findViewById(R.id.btnSelectNewTime);
        edtEditNewName = findViewById(R.id.EdtEditNewName);
        btnEditName = findViewById(R.id.btnEditName);
        btnSaveEdit = findViewById(R.id.btnSaveEdit);
        RlEditAppointment = findViewById(R.id.RlEditAppointment);
        EditDateText = findViewById(R.id.EditAppointmentDateText);
        EditNameText = findViewById(R.id.EditAppointmentNameText);
        EditTimeText = findViewById(R.id.EditAppointmentTimeText);
        txtSelectedEditDate = findViewById(R.id.txtSelectedEditDate);
        txtSelectedEditTime = findViewById(R.id.txtSelectedEditTime);

        // Calling the necessary methods
        checkName();

        Bundle bundle = getIntent().getExtras();
        String appId = bundle.getString("appointId");
        String appName = bundle.getString("appointName");
        String appPrice = bundle.getString("appointPrice");
        String appDate = bundle.getString("appointDate");
        String appTime = bundle.getString("appointTime");
        txtAppointmentName.setText(appName);
        txtAppointmentDate.setText(appDate);
        txtSelectedEditDate.setText(appDate);
        txtSelectedEditTime.setText(appTime);
        txtAppointmentTime.setText(appTime);
        txtAppointmentPrice.setText(appPrice);

        btnEditName.setBackgroundColor(Color.parseColor("#cccccc"));
        btnEditName.setEnabled(false);

        btnEditName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!checkNameOnClick()) {
                    Snackbar.make(RlEditAppointment, "Check Errors and Try Again",
                            BaseTransientBottomBar.LENGTH_LONG).setAction("Close", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    }).show();
                } else {

                    dbcon = new DBConnector(Appointment_Edit_Activity_Customer.this);

                    txtAppointmentName.setText(edtEditNewName.getText().toString());
                    txtAppointmentName.setTextColor(getResources().getColorStateList(R.color.yellowish));
                    EditNameText.setTextColor(getResources().getColorStateList(R.color.yellowish));

                }

            }
        });

        btnSelectNewDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar c = Calendar.getInstance();
                int year, month, day;
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datedialog = new DatePickerDialog(Appointment_Edit_Activity_Customer.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                        SimpleDateFormat simpledateformat = new SimpleDateFormat("EEEE LLLL dd");
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, month, day);
                        String TextDate = simpledateformat.format(newDate.getTime());

                        String selectedDate = day + "-" + (month + 1) + "-" + year;
                        dbcon = new DBConnector(Appointment_Edit_Activity_Customer.this);

                        txtAppointmentDate.setText(TextDate);
                        txtAppointmentDate.setTextColor(getResources().getColorStateList(R.color.yellowish));
                        EditDateText.setTextColor(getResources().getColorStateList(R.color.yellowish));
                        txtSelectedEditDate.setText(selectedDate);
                    }
                }, year, month, day);
                datedialog.getDatePicker().setMinDate(System.currentTimeMillis() + 24 * 60 * 60 * 1000 + 24 * 60 * 60 * 1000);

                datedialog.getDatePicker().setSpinnersShown(true);
                datedialog.show();

            }
        });

        btnSelectNewTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timepicker = new TimePickerDialog(Appointment_Edit_Activity_Customer.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int min) {

                        Calendar datetime = Calendar.getInstance();
                        Calendar c = Calendar.getInstance();
                        datetime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        datetime.set(Calendar.MINUTE, min);

                        if (hourOfDay > 17) {
                            Snackbar.make(RlEditAppointment, "Saloon is closed during this time\nSelect a time before 5.00 pm", Snackbar.LENGTH_LONG)
                                    .setAction("CLOSE", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                        }
                                    })
                                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                    .show();
                        } else if (hourOfDay >= 9) {

                            int hour = hourOfDay % 12;
                            String selectedTime = String.format("%02d:%02d:%02d", hourOfDay, min, 00);
                            txtSelectedEditTime.setText(selectedTime);

                            txtAppointmentTime.setText(String.format("%02d:%02d %s", hour == 0 ? 12 : hour,
                                    min, hourOfDay < 12 ? "am" : "pm"));

                            txtAppointmentTime.setTextColor(getResources().getColorStateList(R.color.yellowish));
                            EditTimeText.setTextColor(getResources().getColorStateList(R.color.yellowish));


                        } else {
                            Snackbar.make(RlEditAppointment, "Select a time after 9.00 am", Snackbar.LENGTH_LONG)
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

        btnSaveEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Minus 10 mins for time
                String timeMinus = txtSelectedEditTime.getText().toString();
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
                String DateTimeMinus = txtSelectedEditDate.getText().toString() + " " + newTimeMinus;

//               Plus 10 mins for time
                String timePlus = txtSelectedEditTime.getText().toString();
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
                String DateTimePlus = txtSelectedEditDate.getText().toString() + " " + newTimePlus;

                dbcon = new DBConnector(Appointment_Edit_Activity_Customer.this);

                Boolean forAppointmentdatestimes = dbcon.history(DateTimeMinus,DateTimePlus);

                if (forAppointmentdatestimes == true) {
                    Toast.makeText(getApplicationContext(), "Choose a Different Time", Toast.LENGTH_SHORT).show();
                }
                else {
                    //Updates Appointment
                    dbcon.updateAppointment(appId,txtAppointmentName.getText().toString(),txtSelectedEditDate.getText().toString()+" "+txtSelectedEditTime.getText().toString());
                    Toast.makeText(Appointment_Edit_Activity_Customer.this, "Success", Toast.LENGTH_SHORT).show();


                    Intent mIntent = getIntent();
                    String previousActivity= mIntent.getStringExtra("To_Edit_Act");

                    if(previousActivity.equals("CustomerAct")){
                        Intent cusAct = new Intent(getApplicationContext(), List_Page.class);
                        startActivity(cusAct);
                    }else{
                        Intent adminAct = new Intent(getApplicationContext(), AdminAppointments.class);
                        startActivity(adminAct);
                    }


                }
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        super.onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public boolean checkNameOnClick() {

        String nameInput = edtEditNewName.getText().toString().trim();

        if (!nameInput.matches("[a-zA-Z ]+")) {
            txtEditNewName.setError("Allows only Letters");
            return false;

        } else {
            txtEditNewName.setError(null);
            return true;
        }
    }

    public void checkName() {
        edtEditNewName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Checks the Condition
                if (!s.toString().matches("[a-zA-Z ]+")) {
                    //When value is not equal to empty and contains numbers
                    //Shows the error
                    txtEditNewName.setError("Allows only Letters");

                    if (s.toString().isEmpty()) {
                        btnEditName.setBackgroundColor(Color.parseColor("#cccccc"));
                        btnEditName.setEnabled(false);
                    }

                } else {
                    //When value is equal to character
                    //Hides the error Message
                    btnEditName.setBackgroundColor(Color.parseColor("#F7BF50"));
                    btnEditName.setEnabled(true);
                    txtEditNewName.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}