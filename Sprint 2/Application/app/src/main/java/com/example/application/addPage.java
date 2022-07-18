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

public class addPage extends AppCompatActivity {
//    Button button = null;
    Button button_done;
    Button button_camera;
    Button button_galley;
    //TimePicker picker;
    //TextView time;
    EditText send_text;
    EditText send_tips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_page);
        //time = (TextView)findViewById(R.id.time);
        //picker = (TimePicker)findViewById(R.id.datePicker1);
//        picker.setIs24HourView(true);

//        button = (Button) findViewById(R.id.Time_set);
//        button.setBackgroundColor(Color.parseColor("#F8F8FF"));
        send_text = findViewById(R.id.text_activity);
        send_tips = findViewById(R.id.text_tips);


        button_done = findViewById(R.id.addDone);
//        button_camera = findViewById(R.id.);
//        button_galley = findViewById(R.id.);

        button_done.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
               // int hour, minute;


                String str = send_text.getText().toString();
                String str2 = send_tips.getText().toString();
                //String am_pm;



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
//                time.setText("Selected Date: "+ hour +":"+ minute+" "+am_pm);





                Intent intent = new Intent(getApplicationContext(), CalenderActivity.class);
                intent.putExtra("message",str);
                intent.putExtra("message2",str2);
                //intent.putExtra("time",am_pm);
                startActivity(intent);
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
