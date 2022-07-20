package com.example.application;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Intent;
import android.widget.Button;
import android.widget.*;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class CalenderActivity extends AppCompatActivity{
    Button button = null;
    TextView act_msg;
    TextView tip_msg;
   // TextView time_msg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calender_page);
        button = (Button) findViewById(R.id.add_button);
        //button.setBackgroundColor(Color.BLACK);


        Intent intent = getIntent();
        String str = intent.getStringExtra("message");
        String str2 = intent.getStringExtra("message2");
       // String str3 = intent.getStringExtra("time");
        act_msg = (TextView)findViewById(R.id.acticity1);

        act_msg.setText(str);

        tip_msg = (TextView)findViewById(R.id.tip);
        tip_msg.setText(str2);


        //time_msg = (TextView)findViewById(R.id.time);
        //time_msg.setText(str3.toString());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(CalenderActivity.this, addPage.class);
                startActivity(intent);
            }
        });


        // Retrieve user information if using Google sign-in
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();
        }

    }

}
