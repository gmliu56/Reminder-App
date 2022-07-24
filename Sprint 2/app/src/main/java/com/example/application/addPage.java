package com.example.application;

//import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.os.Build;
import android.widget.TextView;
import android.widget.TimePicker;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class addPage extends AppCompatActivity {
    //    Button button = null;
    Button button_done;
    Button button_back;
    Button button_camera;
    Button button_galley;
    EditText send_text;
    EditText send_tips;
    EditText send_duration;
    TimePicker time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_page);

        button_done = findViewById(R.id.addDone);
        button_back = findViewById(R.id.back_button);
        send_text = findViewById(R.id.text_activity);
        send_tips = findViewById(R.id.text_tips);
        send_duration = findViewById(R.id.duration1);
        time = findViewById(R.id.timePicker);

//        HashMap<String, Object> map = new HashMap<>();
////        map.put("Activity", send_text.getText().toString());
////        map.put("Tips", send_tips.getText().toString());
////        map.put("Hour", time.getHour());
////        map.put("Minute", time.getMinute());
////        map.put("Countdown", 9);

        button_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String day = getIntent().getStringExtra("date");
                String activity = send_text.getText().toString();
                String tips = send_tips.getText().toString();
                String stringDuration = send_duration.getText().toString();
                int duration;
                if(stringDuration.isEmpty() || activity.isEmpty()){
                    Toast.makeText(addPage.this, "Info missing!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    duration = Integer.parseInt(stringDuration);
                }
                int hour = time.getHour();
                int minute = time.getMinute();
                Task task = new Task(activity,tips,hour,minute,duration);
                FirebaseDatabase.getInstance().getReference().child("Tasks and Dates").child(day).push().setValue(task);

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


        //Galley function
//        button_galley.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v){
//                Intent intent = new Intent(Intent.ACTION_PICK, MediaStroe.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(intent, 3);
//            }
//            });

        //Camera function
//        button_camera.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v){
//                try {
//                    Intent intent = new Intent();
//                    intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
//                    startActivity(intent);
//                }catch (Exception e)
//                {
//                    e.printStackTrace();
//                }
//            }
//        });




//            public void onClick(View v) {
//                onBackPressed();
//            }
//            public void onClick(View v){
//                int hour, minute;
//                String am_pm;
//                if (Build.VERSION.SDK_INT >= 23 ){
//                    hour = picker.getHour();
//                    minute = picker.getMinute();
//                }
//                else{
//                    hour = picker.getCurrentHour();
//                    minute = picker.getCurrentMinute();
//                }
//                if(hour > 12) {
//                    am_pm = "PM";
//                    hour = hour - 12;
//                }
//                else
//                {
//                    am_pm="AM";
//                }
//                tvw.setText("Selected Date: "+ hour +":"+ minute+" "+am_pm);






    }

}
