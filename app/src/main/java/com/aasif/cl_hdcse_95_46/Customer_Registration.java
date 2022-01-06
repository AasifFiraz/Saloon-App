package com.aasif.cl_hdcse_95_46;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityOptionsCompat;

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

public class Customer_Registration extends AppCompatActivity {
    private TextInputLayout txtRegName, txtRegEmail, txtRegPassword, txtRegConfPass;
    private TextInputEditText EdtRegName, EdtRegEmail, EdtRegPassword, EdtRegConfPass;
    private TextView txtLogin;
    private Button btnRegister;
    private RelativeLayout RlRegisterUser;
    private DBConnector dbcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_registration);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#F7BF50\">" + getString(R.string.app_name) + "</font>"));
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F7BF50")));

//      Assigning Text_input variables to their id
        txtRegName = findViewById(R.id.txtRegisterFullName);
        txtRegEmail = findViewById(R.id.txtRegEmail);
        txtRegPassword= findViewById(R.id.txtRegPassword);
        txtRegConfPass = findViewById(R.id.txtRegConfPassword);
        EdtRegName = findViewById(R.id.EdtRegisterFullName);
        EdtRegEmail = findViewById(R.id.EdtRegEmail);
        EdtRegPassword = findViewById(R.id.EdtRegPass);
        EdtRegConfPass = findViewById(R.id.EdtRegConfPass);
        btnRegister = findViewById(R.id.btnRegister);
        RlRegisterUser = findViewById(R.id.RlRegisterUser);

        txtLogin = findViewById(R.id.txtLogin);

        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent login_page = new Intent(Customer_Registration.this, MainActivity.class);
                startActivity(login_page);
                finish();
//                overridePendingTransition(R.anim.zoomenter, R.anim.zoomenter);
            }
        });

        // Calling the necessary methods
        checkName();
        checkEmail();
        checkPass();
        checkConfPass();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!checkNameOnClick() | !checkEmailOnClick() | !checkPassOnClick()
                        | !checkConfPassOnClick()) {
                    Snackbar.make(RlRegisterUser, "Check Errors and Try Again",
                            BaseTransientBottomBar.LENGTH_LONG).setAction("Close", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    }).show();
                } else {

                    Customers customers;
                    String email = EdtRegEmail.getText().toString();
                    dbcon = new DBConnector(Customer_Registration.this);

                    try {
                        Boolean checkDuplicateCustomerEmail = dbcon.checkDuplicateCustomerEmail(email);

                        if (checkDuplicateCustomerEmail == true) {
                            Snackbar.make(RlRegisterUser, "Email Already Exists\nCreate new Account with different email if you don't have one"
                                    , BaseTransientBottomBar.LENGTH_LONG).setAction("Close", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                }
                            }).show();
                        } else {
                            customers = new Customers(null,EdtRegName.getText().toString(),
                                    EdtRegEmail.getText().toString(), EdtRegPassword.getText().toString());

                            //Creates the User
                            dbcon.createCustomer(customers);

                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(i);

                        }
                    } catch (Exception e) {
                        Snackbar.make(RlRegisterUser, "Error Occurred when registering the user\nPlease try again later.", Snackbar.LENGTH_LONG)
                                .setAction("CLOSE", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                    }
                                })
                                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                                .show();
                    }
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

        Intent intent = new Intent(Customer_Registration.this, Main_Screen.class);
        startActivity(intent);
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