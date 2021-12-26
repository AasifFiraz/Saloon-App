package com.aasif.cl_hdcse_95_46;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;

public class List_Page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_page);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#2c2c2c\">" + getString(R.string.app_name) + "</font>"));
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2c2c2c")));


        getSupportFragmentManager().beginTransaction()
                .replace(R.id.FrameAppointment, new AppointmentFragment())
                .commit();


    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), MainPage.class);
        startActivity(myIntent);
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent back = new Intent(List_Page.this, MainPage.class);
        startActivity(back);
        finish();
    }
}
