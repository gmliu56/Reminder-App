package com.example.application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class IndividualTaskPage extends AppCompatActivity {
    ImageView btn_back_event;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        btn_back_event=findViewById(R.id.back_to_eventList);
        btn_back_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(IndividualTaskPage.this,EventsList.class);
                startActivity(intent);
            }
        });

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_task_page);
    }
}