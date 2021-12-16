package com.aasif.cl_hdcse_95_46;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {
    private TextView txtcallUs, txtmailUs, txtOurLocation, txtOpenHours;
    private Button btnMakeAppoinment;
    private Button btnClick;

    public HomeFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtcallUs = view.findViewById(R.id.txtCallUs);
        txtmailUs = view.findViewById(R.id.txtMailUs);
        txtOurLocation = view.findViewById(R.id.txtOurLocation);
        txtOpenHours = view.findViewById(R.id.txtOpenHours);
        btnMakeAppoinment = view.findViewById(R.id.btnMakeAppoinment);

        btnMakeAppoinment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),Appoinment_Booking.class);
                startActivity(intent);
            }
        });

        txtcallUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
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

                AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
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
                                    Toast.makeText(getActivity(), "Error Occured", Toast.LENGTH_SHORT).show();
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
                "Wednesday                   9.00 AM - 5.30 PM\n" +
                "Thursday                       9.00 AM - 4.30 PM\n" +
                "Friday                             8.00 AM - 5.00 PM\n" +
                "Saturday                      10.00 AM - 5.00 PM";

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


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home,container,false);

    }
}
