package com.example.application;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

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

        // 目标日期
        String day = getIntent().getStringExtra("date");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = sdf.format(date).toString();
        image_back = findViewById(R.id.back_to_calendar);
        // Add task button
        btn_add = findViewById(R.id.add_button2);
        recyclerView=findViewById(R.id.taskListView);

        databaseReference = FirebaseDatabase.getInstance().getReference("Tasks and Dates").child(currentDate);
        list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new EventListAdapter(this,list);
        recyclerView.setAdapter(myAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    // 目标对象
                    Task task = null;
                    // 日期
                    String date = dataSnapshot.getKey();
                    if (TextUtils.isEmpty(date) || TextUtils.isEmpty(day)) {
                        continue;
                    }
                    // 为了方便比较 包含 2022-08-01的格式，需要对数据进行处理
                    // 如果日期不匹配 不进行显示
                    if (!TextUtils.equals(date.replaceAll("-0", "-"), day.replaceAll("-0", "-"))) {
                        continue;
                    }
                    // 这一天里的事件  包含具体时间和事件
                    if (dataSnapshot.getChildrenCount() < 1) {
                        continue;
                    }
                    // 拿到具体对象
                    for (DataSnapshot dataSnapshotTmp : dataSnapshot.getChildren()) {
                        task = dataSnapshotTmp.getValue(Task.class);
                        if (null == task) {
                            continue;
                        }
                        // 把日期属性补进去
                        task.setDate(date);
                        list.add(task);
                    }
                }
                // 插入提醒事件
                //add calender
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