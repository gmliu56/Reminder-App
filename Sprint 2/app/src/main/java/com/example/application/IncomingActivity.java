package com.example.application;

import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.Locale;

// Alert Page to display the upcoming task (reminder) when it is close to the time or on time
public class IncomingActivity extends AppCompatActivity {
    Task currentTask; // The most recent upcoming task
    String currentDate;
    ImageView button_task_complete; // Button for completing the task
    TextView activity_detail;
    TextView tips_detail;
    TextView time_detail;
    ImageView task_image;
    DatabaseReference incompleteTaskReference;
    String currentTaskKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.incoming_act_page);
        activity_detail = findViewById(R.id.activity_detail);
        tips_detail = findViewById(R.id.tips_detail);
        time_detail = findViewById(R.id.time_detail);
        task_image = findViewById(R.id.task_image);
        button_task_complete = findViewById(R.id.button_task_complete);

        // Fetch entry of tasks on current date from FireBase
        currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        incompleteTaskReference = FirebaseDatabase.getInstance().getReference("Tasks and Dates").child(currentDate).child("NotComplete");
        fetchEarliestActivity();

        // Task Complete Button is clicked, user set the task complete manually
        button_task_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Delete the current Task in "NotComplete"
                deleteEarliestActivity();
                // Add the current Task to "Complete"
                FirebaseDatabase.getInstance().getReference().
                        child("Tasks and Dates").child(currentDate).child("Complete").
                        push().setValue(currentTask);
                if(currentTask != null){
                    currentTask.clearVariables();
                    Toast.makeText(IncomingActivity.this,
                            "Task Completed!", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(IncomingActivity.this,
                            "Task Already Completed!", Toast.LENGTH_LONG).show();
                }
                // Fetch next earliest task
                fetchEarliestActivity();
            }
        });

    }

    // Method of fetching/pulling the earliest activity from Firebase
    private void fetchEarliestActivity(){
        // Sort the timestamp entry and only fetch the one on top
        incompleteTaskReference.orderByChild("timestamp").limitToFirst(1).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot.getValue() == null || incompleteTaskReference == null){
                    // child does not exist, meaning no task today
                    String noActivity = "No Activities Today";
                    activity_detail.setText(noActivity);
                }else {
                    currentTaskKey = snapshot.getKey();
                    currentTask = snapshot.getValue(Task.class);
                    assert currentTask != null;
                    // Update info on screen
                    activity_detail.setText(currentTask.getTask_name());
                    tips_detail.setText(currentTask.getTips());
                    time_detail.setText(currentTask.getTime());
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

    // Method of deleting today's earliest incomplete activity in Firebase
    private void deleteEarliestActivity(){
        if(currentTaskKey != null) {
            incompleteTaskReference.child(currentTaskKey).removeValue();
        }
    }

}