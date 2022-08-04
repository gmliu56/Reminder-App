package com.example.application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class IndividualTaskPage extends AppCompatActivity {
    ImageView btn_back_event;
    EditText activity_detail2;
    EditText tips_detail2;
    TextView time_detail2;
    TextView completion_status;
    ImageView task_image2;
    Button button_submit;

    DatabaseReference databaseReference;
    private String day;
    private String complete;
    private String key;
    private boolean completion;
    Task taskSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_task_page);
        activity_detail2 = findViewById(R.id.activity_detail2);
        tips_detail2 = findViewById(R.id.tips_detail2);
        time_detail2 = findViewById(R.id.time_detail2);
        completion_status = findViewById(R.id.completion_detail);
        task_image2 = findViewById(R.id.task_image2);
        button_submit = findViewById(R.id.submit_button);
        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //ActionBar ab = getSupportActionBar();
        // Enable the Up button
//        assert ab != null;
//        ab.setDisplayHomeAsUpEnabled(true);
//        ab.setHomeButtonEnabled(true);

//        btn_back_event = findViewById(R.id.back_to_eventList);
//        btn_back_event.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                IndividualTaskPage.super.onBackPressed();
//            }
//        });

        // Retrieve task properties from the previous activity EventsList
        day = getIntent().getStringExtra("date");
        complete = getIntent().getStringExtra("complete");
        key = getIntent().getStringExtra("key");

        // Get the target reference (selected Task)

        if (complete.equals("NotComplete")){
            // Incomplete task
            databaseReference = FirebaseDatabase.getInstance()
                    .getReference("Tasks and Dates")
                    .child(day)
                    .child("NotComplete")
                    .child(key);
            databaseReference.addValueEventListener(new ValueEventListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    taskSelected = snapshot.getValue(Task.class);
                    if(taskSelected != null) {
                        // Update info on screen
                        activity_detail2.setText(taskSelected.getTask_name());
                        tips_detail2.setText(taskSelected.getTips());
                        time_detail2.setText(taskSelected.getTime());
                        completion_status.setText("Not Complete");

                        String imageUrl = taskSelected.getImageUrl();
                        Glide.with(IndividualTaskPage.this).load(imageUrl).into(task_image2);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            completion = false;
        }else{
            // Complete task
            String task_name = getIntent().getStringExtra("task_name");
            databaseReference = FirebaseDatabase.getInstance()
                    .getReference("Tasks and Dates")
                    .child(day);
            databaseReference.child("Complete").orderByChild("task_name").equalTo(task_name).addChildEventListener(new ChildEventListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    taskSelected = snapshot.getValue(Task.class);
                    if(taskSelected != null) {
                        // Update info on screen
                        activity_detail2.setText(taskSelected.getTask_name());
                        tips_detail2.setText(taskSelected.getTips());
                        time_detail2.setText(taskSelected.getTime());
                        completion_status.setText("Complete");
                        taskSelected.setKey(snapshot.getKey());
                        key = snapshot.getKey();
                        completion = true;

                        String imageUrl = taskSelected.getImageUrl();
                        Glide.with(IndividualTaskPage.this).load(imageUrl).into(task_image2);
                    }
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        // When submit button is clicked
        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create a new Task to push
                Task task = new Task(activity_detail2.getText().toString(),
                        tips_detail2.getText().toString(),
                        taskSelected.getHour(),
                        taskSelected.getMinute(),
                        completion,
                        taskSelected.getImageUrl());
                // Delete the previous entry
                FirebaseDatabase.getInstance()
                        .getReference("Tasks and Dates")
                        .child(day)
                        .child(complete)
                        .child(key).removeValue();
                // Add new entry
                FirebaseDatabase.getInstance()
                        .getReference("Tasks and Dates")
                        .child(day)
                        .child(complete)
                        .push().setValue(task);

                Toast.makeText(IndividualTaskPage.this, "Update successfully!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    // Fetch the selected Task from Firebase and update info on screen
    private void fetchTask(){

    }

}