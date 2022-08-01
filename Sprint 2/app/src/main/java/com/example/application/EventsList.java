package com.example.application;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Log.i(TAG, "onCreate: "+sdf.format(date).toString());
        // Add task button
        btn_add = findViewById(R.id.add_button2);
        recyclerView = findViewById(R.id.taskListView);

        databaseReference = FirebaseDatabase.getInstance().getReference("Tasks and Dates").child(sdf.format(date).toString());

        list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new EventListAdapter(this,list);
        recyclerView.setAdapter(myAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    Task task = dataSnapshot.getValue(Task.class);
                    list.add(task);
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Add button is clicked
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