package com.example.application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Intent;
import android.widget.Button;
import android.widget.*;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class CalenderActivity extends AppCompatActivity{
    Button add_button = null;
    Button view_button;
    CalendarView calendar;
    String day = "";
    //TextView act_msg;
    //TextView tip_msg;
    // TextView time_msg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calender_page);
        add_button = (Button) findViewById(R.id.add_button);
        view_button = findViewById(R.id.view_button);
        calendar = findViewById(R.id.calendarView);

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                day = i + "-" + (i1 + 1) + "-" + i2;
                Toast.makeText(CalenderActivity.this, day, Toast.LENGTH_SHORT).show();
                //FirebaseDatabase.getInstance().getReference().child("Tasks and Dates").child(day).push().setValue(new Task("drink","", 10, 30, 15));
            }
        });
        //button.setBackgroundColor(Color.BLACK);


        //Intent intent = getIntent();
        //String str = intent.getStringExtra("message");
        //String str2 = intent.getStringExtra("message2");
        // String str3 = intent.getStringExtra("time");
        //act_msg = (TextView)findViewById(R.id.acticity1);

        //act_msg.setText(str);

        //tip_msg = (TextView)findViewById(R.id.tip);
        //tip_msg.setText(str2);


        //time_msg = (TextView)findViewById(R.id.time);
        //time_msg.setText(str3.toString());
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(day.isEmpty()){
                    Toast.makeText(CalenderActivity.this, "Please tap on a date", Toast.LENGTH_LONG).show();
                }
                else {
                    Intent intent = new Intent();
                    intent.setClass(CalenderActivity.this, addPage.class);
                    intent.putExtra("date", day);
                    startActivity(intent);
                }
            }
        });

        view_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        // Retrieve user information if using Google sign-in
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();
        }

    }

}
