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

public class MainActivity extends AppCompatActivity {
    private TextInputLayout txtLoginEmail, txtLoginPassword;
    private TextInputEditText EdtLoginEmail, EdtLoginPassword;
    private TextView txtRegsiter;
    private Button btnCusLogin;
    private DBConnector dbcon;
    private RelativeLayout RlCusLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#F7BF50\">" + getString(R.string.app_name) + "</font>"));
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F7BF50")));

//        Assigning Text_input vairables to their id
        txtLoginEmail = findViewById(R.id.txtLoginEmail);
        txtLoginPassword = findViewById(R.id.txtLoginPassword);
        EdtLoginEmail = findViewById(R.id.EdtLoginEmail);
        EdtLoginPassword = findViewById(R.id.EdtLoginPass);
        btnCusLogin = findViewById(R.id.btnCustomerLogin);
        RlCusLogin = findViewById(R.id.RlCusLogin);

        txtRegsiter = findViewById(R.id.txtLogin);

        txtRegsiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent regsiter_page = new Intent(MainActivity.this, Customer_Registration.class);
                startActivity(regsiter_page);
                finish();
            }
        });

        checkEmail();
        checkPass();

        btnCusLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!checkPassOnClick() | !checkEmailOnClick()){
                    Snackbar.make(RlCusLogin, "Check the Errors and Try Again", BaseTransientBottomBar.LENGTH_LONG).setAction("Close", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    }).show();
                } else{
                    dbcon = new DBConnector(MainActivity.this);
                    String email = EdtLoginEmail.getText().toString();
                    String pass = EdtLoginPassword.getText().toString();

                    Boolean checkUsernamePass = dbcon.checkCustomerCredentials(email.trim(), pass.trim());

                    if(checkUsernamePass == true){
                        Toast.makeText(getApplicationContext(), "Login Successful !", Toast.LENGTH_LONG).show();
                        Intent MainScreen = new Intent(getApplicationContext(), Home_Page.class);
                        startActivity(MainScreen);
                    }else{
                        Toast.makeText(getApplicationContext(), "Incorrect Username or Password !", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), Customer_Registration.class);
        startActivity(myIntent);
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }

    public boolean checkEmailOnClick() {

        String emailInput = EdtLoginEmail.getText().toString().trim();

        if (!emailInput.matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")) {
            txtLoginEmail.setError("Enter Valid Email Address");
            return false;

        } else {
            txtLoginEmail.setError(null);
            return true;
        }
    }

    public boolean checkPassOnClick() {

        String passInput = EdtLoginPassword.getText().toString().trim();

        if (passInput.isEmpty()) {
            txtLoginPassword.setError("Password cannot be Empty");
            return false;

        } else {
            txtLoginPassword.setError(null);
            return true;
        }
    }

    public void checkEmail() {
        EdtLoginEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!s.toString().matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")) {

                    txtLoginEmail.setError("Enter Valid Email Address");

                    if (s.toString().isEmpty()) {
                        txtLoginEmail.setError("Email cannot be Empty");
                    }

                } else {
                    txtLoginEmail.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void checkPass() {
        EdtLoginPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.toString().isEmpty()) {
                    txtLoginPassword.setError("Password cannot be Empty");
                }
                else {
                    txtLoginPassword.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

}