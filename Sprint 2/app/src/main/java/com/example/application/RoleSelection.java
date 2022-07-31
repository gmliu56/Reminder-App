package com.example.application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RoleSelection extends AppCompatActivity {
    Button btn_patient;
    Button btn_caretaker;
    MyApplication myApplication = (MyApplication) this.getApplication();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(androidx.appcompat.R.style.Theme_AppCompat_DayNight);
        setContentView(R.layout.activity_role_selection);

        btn_patient = (Button) findViewById(R.id.button_patient);
        btn_caretaker = (Button) findViewById(R.id.button_caretaker);

        // Set role to patient and go to IncomingActivity
        btn_patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setClass(RoleSelection.this, IncomingActivity.class);
                startActivity(intent);

                finish();
            }
        });

        // Set role to caretaker and go to CalenderActivity
        btn_caretaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setClass(RoleSelection.this, CalenderActivity.class);
                startActivity(intent);

                finish();
            }
        });


    }
}