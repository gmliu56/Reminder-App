package com.example.application;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Intent;
import android.widget.Button;
import android.widget.*;

public class MainActivity extends AppCompatActivity {
    Button button = null;
    //RadioButton radioBtn_patient = null;
    //RadioButton radioBtn_caretaker = null;
    RadioGroup radioGroup = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        radioGroup = findViewById(R.id.radioButton);
        button = (Button) findViewById(R.id.Login_button);
        //case R.id.Patient:

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override

            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.Patient:
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent();
                                intent.setClass(MainActivity.this, IncomingActivity.class);
                                startActivity(intent);
                            }
                        });
                        break;
                    case R.id.Caretaker:
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent();
                                intent.setClass(MainActivity.this, CalenderActivity.class);
                                startActivity(intent);
                                Log.i("TAG","case caretaker");
                            }
                        });
                        break;

                }
            }
        });
    }
}