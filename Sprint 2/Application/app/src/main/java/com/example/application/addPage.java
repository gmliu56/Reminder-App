package com.example.application;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class addPage extends AppCompatActivity {
    Button button = null;
    Button button_done = null;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_page);

//        button = (Button) findViewById(R.id.Time_set);
//        button.setBackgroundColor(Color.parseColor("#F8F8FF"));

        button_done = (Button) findViewById(R.id.addDone);
        button_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

}
