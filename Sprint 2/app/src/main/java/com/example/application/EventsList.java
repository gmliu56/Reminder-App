package com.example.application;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.application.utils.Utils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

// Page of to-do list in a summary view
public class EventsList  extends addPage{
    Button btn_add;
    RecyclerView recyclerView;
    ArrayList<Task> list;
    EventListAdapter myAdapter;
    DatabaseReference databaseReference;
    Date date;
    ImageView image_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_list);

        // create a date
        String day = getIntent().getStringExtra("date");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = sdf.format(date).toString();
        image_back = findViewById(R.id.back_to_calendar);

        btn_add = findViewById(R.id.add_button2);
        recyclerView=findViewById(R.id.taskListView);

        list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new EventListAdapter(this,list);
        recyclerView.setAdapter(myAdapter);

        // Add tasks to ArrayList
        databaseReference = FirebaseDatabase.getInstance().getReference("Tasks and Dates");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    // target
                    Task task;
                    // check each date
                    String date = dataSnapshot.getKey();
                    if (TextUtils.isEmpty(date) || TextUtils.isEmpty(day)) {
                        continue;
                    }
                    // Do not display if dates do not match
                    if (!TextUtils.equals(date.replaceAll("-0", "-"), day.replaceAll("-0", "-"))) {
                        continue;
                    }
                    // Events of the day Contains specific times and events
                    if (dataSnapshot.getChildrenCount() < 1) {
                        continue;
                    }
                    // get a specific target date
                    // Add incomplete tasks to ArrayList
                    for (DataSnapshot dataSnapshotTmp : dataSnapshot.child("NotComplete").getChildren()) {
                        task = dataSnapshotTmp.getValue(Task.class);
                        if (task == null) {
                            continue;
                        }
                        // add date attribute
                        task.setDate(date);
                        list.add(task);
                    }
                    // Add other complete tasks to ArrayList
                    for (DataSnapshot dataSnapshotTmp : dataSnapshot.child("Complete").getChildren()) {
                        task = dataSnapshotTmp.getValue(Task.class);
                        if (task == null) {
                            continue;
                        }
                        // add date attribute
                        task.setDate(date);
                        list.add(task);
                    }
                }

                // Insert reminder event
                // add calender
                for(Task task : list) {
                    Utils.writeToCalendar(getApplicationContext(),task.getTask_name(),task.getTips(),task.getDate()+" "+task.getTime());
                }

                myAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        image_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(EventsList.this,CalenderActivity.class);
                startActivity(intent);
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go to addPage
                Intent intent = new Intent();
                intent.setClass(EventsList.this, addPage.class);
                intent.putExtra("date", Utils.getNowTimeStr());
                startActivity(intent);
            }
        });

    }
}
