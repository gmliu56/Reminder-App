package com.example.application;

//import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;

import com.example.application.utils.Utils;
import com.google.firebase.database.FirebaseDatabase;



public class addPage extends AppCompatActivity {

    Button button_done;
    Button button_back;
    Button button_camera;
    Button button_galley;
    EditText send_acts;
    EditText send_tips;
    //EditText send_duration;
    TimePicker time;
    String mSelectTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_page);
        initWidget();
        send_acts = findViewById(R.id.set_activity);
        send_tips = findViewById(R.id.set_tips);


        button_done = findViewById(R.id.addDone);

        button_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String day = getIntent().getStringExtra("date");
                String activity = send_acts.getText().toString();
                String tips = send_tips.getText().toString();
                //String stringDuration = send_duration.getText().toString();
                int duration;
                if(activity.isEmpty()){
                    Toast.makeText(addPage.this, "Info missing!", Toast.LENGTH_SHORT).show();
                    return;
                }
                int hour = time.getHour();
                int minute = time.getMinute();
                Task task = new Task(activity,tips,hour,minute);
                //Task.taskArrayList.add(task);
                FirebaseDatabase.getInstance().getReference().child("Tasks and Dates").child(day).push().setValue(task);

                //add calender
                Utils.writeToCalendar(getApplicationContext(),activity,tips,day+" "+hour+":"+minute);

                Intent intent = new Intent(addPage.this, CalenderActivity.class);
                startActivity(intent);
            }
        });

        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void initWidget() {
        button_done = findViewById(R.id.addDone);
        button_back = findViewById(R.id.back_button);
        send_acts = findViewById(R.id.set_activity);
        send_tips = findViewById(R.id.set_tips);
        //send_duration = findViewById(R.id.duration1);
        time = findViewById(R.id.timePicker);
    }


}
