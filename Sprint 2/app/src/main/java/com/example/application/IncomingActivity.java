package com.example.application;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

// Alert Page to display the upcoming task (reminder) when it is close to the time or on time
public class IncomingActivity extends AppCompatActivity {

    ImageView button_task_complete; // Button for completing the task

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.incoming_act_page);
        button_task_complete = (ImageView) findViewById(R.id.button_task_complete);

        // When Task Complete Button is clicked
        button_task_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
}