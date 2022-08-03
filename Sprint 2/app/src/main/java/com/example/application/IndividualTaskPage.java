package com.example.application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class IndividualTaskPage extends AppCompatActivity {
    ImageView btn_back_event;
    TextView activity_detail2;
    TextView tips_detail2;
    TextView time_detail2;
    ImageView task_image2;

    DatabaseReference databaseReference;
    private String day;
    private String complete;
    private String key;

    Task taskSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_task_page);
        activity_detail2 = findViewById(R.id.activity_detail2);
        tips_detail2 = findViewById(R.id.tips_detail2);
        time_detail2 = findViewById(R.id.time_detail2);
        task_image2 = findViewById(R.id.task_image2);

        btn_back_event = findViewById(R.id.back_to_eventList);
        btn_back_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IndividualTaskPage.super.onBackPressed();
            }
        });

        // Retrieve task properties from the previous activity EventsList
        day = getIntent().getStringExtra("date");
        complete = getIntent().getStringExtra("complete");
        key = getIntent().getStringExtra("key");
        // Get the target reference (selected Task)
        databaseReference = FirebaseDatabase.getInstance()
                .getReference("Tasks and Dates")
                .child(day)
                .child(complete)
                .child(key);

        fetchTask();

    }

    // Fetch the selected Task from Firebase and update info on screen
    private void fetchTask(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                taskSelected = snapshot.getValue(Task.class);
                assert taskSelected != null;
                // Update info on screen
                activity_detail2.setText(taskSelected.getTask_name());
                tips_detail2.setText(taskSelected.getTips());
                time_detail2.setText(taskSelected.getTime());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}