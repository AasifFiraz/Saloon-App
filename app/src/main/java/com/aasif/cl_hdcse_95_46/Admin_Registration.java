package com.aasif.cl_hdcse_95_46;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class Admin_Registration extends AppCompatActivity {
    private TextInputLayout txtRegName, txtRegEmail, txtRegPassword, txtRegConfPass;
    private TextInputEditText EdtRegName, EdtRegEmail, EdtRegPassword, EdtRegConfPass;
    private TextView txtAdminLoginPage;
    private Button btnRegisterAdmin;
    private RelativeLayout RlRegisterAdmin;
    private DBConnector dbcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_registration);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#F7BF50\">" + getString(R.string.app_name) + "</font>"));
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F7BF50")));

        txtRegName = findViewById(R.id.txtRegisterAdminFullName);
        txtRegEmail = findViewById(R.id.txtRegAdminEmail);
        txtRegPassword = findViewById(R.id.txtRegAdminPassword);
        txtRegConfPass = findViewById(R.id.txtRegAdminConfPassword);
        EdtRegName = findViewById(R.id.EdtRegisterAdminFullName);
        EdtRegEmail = findViewById(R.id.EdtRegAdminEmail);
        EdtRegPassword = findViewById(R.id.EdtRegAdminPass);
        EdtRegConfPass = findViewById(R.id.EdtRegAdminConfPass);
        txtAdminLoginPage = findViewById(R.id.txtAdminLogintext);
        btnRegisterAdmin = findViewById(R.id.btnAdminRegister);
        RlRegisterAdmin = findViewById(R.id.RlRegisterAdmin);

        txtAdminLoginPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent adminLoginscreen = new Intent(Admin_Registration.this, Admin_Login.class);
                startActivity(adminLoginscreen);
                finish();
            }
        });

        // Initializing validations onCreate
        checkName();
        checkEmail();
        checkPass();
        checkConfPass();

        btnRegisterAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!checkNameOnClick() | !checkEmailOnClick() | !checkPassOnClick()
                        | !checkConfPassOnClick()) {
                    Snackbar.make(RlRegisterAdmin, "Check Errors and Try Again",
                            BaseTransientBottomBar.LENGTH_LONG).setAction("Close", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    }).show();
                } else {

                    Admins admins;
                    String name = EdtRegName.getText().toString();
                    dbcon = new DBConnector(Admin_Registration.this);

                    try {
                        Boolean checkDuplicateAdminName = dbcon.checkDuplicateAdminName(name);

                        if (checkDuplicateAdminName == true) {
                            Snackbar.make(RlRegisterAdmin, "Admin Email Already Exists !\nCreate new Account with different email if you don't have one.", Snackbar.LENGTH_LONG)
                                    .setAction("CLOSE", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                        }
                                    })
                                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                                    .show();
                        } else {


                            admins = new Admins(null,EdtRegName.getText().toString(),
                                    EdtRegEmail.getText().toString(), EdtRegPassword.getText().toString());

                            //Creates the User
                            dbcon.createAdmins(admins);

                            Intent i = new Intent(getApplicationContext(), Admin_Login.class);
                            startActivity(i);

                        }
                    } catch (Exception e) {
                        Snackbar.make(RlRegisterAdmin, "Error Occurred when registering new admin\nPlease try again later.", Snackbar.LENGTH_LONG)
                                .setAction("CLOSE", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                    }
                                })
                                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                                .show();                       }
                }

            }
        });
}

    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), Main_Screen.class);
        startActivity(myIntent);
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent back = new Intent(Admin_Registration.this, Main_Screen.class);
        startActivity(back);
        finish();
    }


    public boolean checkNameOnClick() {

        String nameInput = EdtRegName.getText().toString().trim();

        if (!nameInput.matches("[a-zA-Z ]+")) {
            txtRegName.setError("Allows only Letters");
            return false;

        } else {
            txtRegName.setError(null);
            return true;
        }
    }

    public boolean checkEmailOnClick() {

        String emailInput = EdtRegEmail.getText().toString().trim();

        if (!emailInput.matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")) {
            txtRegEmail.setError("Enter Valid Email Address");
            return false;

        } else {
            txtRegEmail.setError(null);
            return true;
        }
    }

    public boolean checkPassOnClick() {

        String passInput = EdtRegConfPass.getText().toString().trim();

        if (passInput.isEmpty()) {
            txtRegPassword.setError("Password cannot be Empty");
            return false;

        } else {
            txtRegPassword.setError(null);
            return true;
        }
    }

    public boolean checkConfPassOnClick() {

        String confPasInput = EdtRegConfPass.getText().toString().trim();
        String password = EdtRegPassword.getText().toString();

        if (!confPasInput.equals(password)) {
            txtRegConfPass.setError("Password and Confirm Password must match");
            return false;

        } else {
            txtRegConfPass.setError(null);
            return true;
        }
    }

    public void checkName() {
        EdtRegName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Checks the Condition
                if (!s.toString().matches("[a-zA-Z ]+")) {
                    //When value is not equal to empty and contains numbers
                    //Shows the error
                    txtRegName.setError("Allows only Letters");

                    if (s.toString().isEmpty()) {
                        txtRegName.setError("Name cannot be Empty");


                    }

                } else {
                    //When value is equal to characer
                    //Hides the error Message
                    txtRegName.setError(null);


                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void checkEmail() {
        EdtRegEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!s.toString().matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")) {

                    txtRegEmail.setError("Enter Valid Email Address");

                    if (s.toString().isEmpty()) {
                        txtRegEmail.setError("Email cannot be Empty");
                    }

                } else {
                    txtRegEmail.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void checkPass() {
        EdtRegPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.toString().isEmpty()) {
                    txtRegPassword.setError("Password cannot be Empty");
                }
                else {
                    txtRegPassword.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void checkConfPass() {
        EdtRegConfPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String password = EdtRegPassword.getText().toString();
                if (!s.toString().equals(password)) {
                    txtRegConfPass.setError("Password and Confirm Password must match");

                    if (s.toString().isEmpty()) {
                        txtRegConfPass.setError("Confirm Password cannot be Empty");
                    }

                } else {
                    txtRegConfPass.setError(null);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}