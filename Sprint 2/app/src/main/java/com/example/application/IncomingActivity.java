package com.example.application;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
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
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

// Alert Page to display the upcoming task (reminder) when it is close to the time or on time
public class IncomingActivity extends AppCompatActivity {
    Task currentTask; // The current upcoming task object
    String currentDate; // Today's date
    int hour;
    int minute;

    ImageView button_task_complete; // Button for completing the task
    TextView activity_detail;
    TextView tips_detail;
    TextView time_detail;
    ImageView task_image;

    DatabaseReference incompleteTaskReference;
    String currentTaskKey;
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.incoming_act_page);
        activity_detail = findViewById(R.id.activity_detail);
        tips_detail = findViewById(R.id.tips_detail);
        time_detail = findViewById(R.id.time_detail);
        task_image = findViewById(R.id.task_image);
        button_task_complete = findViewById(R.id.button_task_complete);
        createNotificationChannel();

        // Fetch entry of tasks on current date from FireBase
        currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        incompleteTaskReference = FirebaseDatabase.getInstance().
                getReference("Tasks and Dates").child(currentDate).child("NotComplete");
        // Check if there are tasks today, fetch task if there is
        incompleteTaskReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.hasChildren() || incompleteTaskReference == null){
                    // child does not exist, meaning no task today
                    String noActivity = "No Activities Today";
                    activity_detail.setText(noActivity);
                    Toast.makeText(IncomingActivity.this,
                            "No Activities Found", Toast.LENGTH_SHORT).show();
                }else {
                    fetchEarliestActivity();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        // Task Complete Button is clicked, user set the task complete manually
        button_task_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelAlarm(); // Cancels the current alarm
                if(currentTask != null){
                    deleteEarliestActivity(); // Delete the current Task in "NotComplete" node
                    // Add the current Task to "Complete" node in Firebase
                    currentTask.setComplete(true);
                    FirebaseDatabase.getInstance().getReference().
                            child("Tasks and Dates").child(currentDate).child("Complete").
                            push().setValue(currentTask);

                    currentTask = null;
                    Toast.makeText(IncomingActivity.this,
                            "Task Completed!", Toast.LENGTH_LONG).show();

                    // Check if there are tasks today, fetch task if there is
                    incompleteTaskReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (!snapshot.hasChildren() || incompleteTaskReference == null) {
                                // child does not exist, meaning no task today
                                String noActivity = "No Activities Today";
                                activity_detail.setText(noActivity);
                                tips_detail.setText("");
                                time_detail.setText("");
                                Toast.makeText(IncomingActivity.this,
                                        "No Activities Found", Toast.LENGTH_SHORT).show();
                            } else {
                                fetchEarliestActivity();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });

                } else {
                Toast.makeText(IncomingActivity.this,
                        "Task Already Completed!", Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    private void createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "soundmemoChannel";
            String description = "Channel for soundmemo";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel("soundmemo", name, importance);
            notificationChannel.setDescription(description);
        }
    }

    // Method of fetching/pulling the earliest activity from Firebase
    private void fetchEarliestActivity(){
        // Sort the timestamp entry and only fetch the one on top
        incompleteTaskReference.orderByChild("timestamp").limitToFirst(1).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    currentTaskKey = snapshot.getKey();
                    currentTask = snapshot.getValue(Task.class);
                    assert currentTask != null;
                    currentTask.setKey(currentTaskKey);
                    // Update info on screen
                    activity_detail.setText(currentTask.getTask_name());
                    tips_detail.setText(currentTask.getTips());
                    time_detail.setText(currentTask.getTime());
                    // Set the alarm for the current task
                    setAlarm();
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

    // Method of setting alarm to the current task's time
    private void setAlarm(){
        // Initialize AlarmManager
        alarmMgr = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        // Set calendar to the current task time
        hour = currentTask.getHour();
        minute = currentTask.getMinute();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = calendar.get(Calendar.MINUTE);
        // Only set alarm when task is in the future
        if (hour > currentHour ||
                (hour == currentHour && minute > currentMinute)){
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);
            // Set alarm using calendar's hour and minute
            AlarmManager.AlarmClockInfo clockInfo = new AlarmManager.AlarmClockInfo(calendar.getTimeInMillis(), alarmIntent);
            // Alarm will fire at the exact hour and min,
            // but will use more system resource and battery life
            alarmMgr.setAlarmClock(clockInfo, alarmIntent);
            Toast.makeText(IncomingActivity.this,
                    "Alarm is set", Toast.LENGTH_SHORT).show();
        }

    }

    // Method of canceling the current alarm set
    private void cancelAlarm(){
        Intent intent = new Intent(this, AlarmReceiver.class);
        // The following intent must match exactly with the alarmIntent in setAlarm() method
        alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        if(alarmMgr == null){
            alarmMgr = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        }
        // Cancels the intent
        alarmMgr.cancel(alarmIntent);
    }

}