package com.example.application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.application.utils.Utils;

public class EventsList extends AppCompatActivity {
    Button btn_add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_list);
        btn_add = findViewById(R.id.add_button2);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(EventsList.this,addPage.class);
                intent.putExtra("date", Utils.getNowTimeStr());
                startActivity(intent);
            }
        });

    }
}