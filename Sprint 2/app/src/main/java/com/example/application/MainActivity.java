package com.example.application;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.splashscreen.SplashScreen;

import com.example.application.utils.ToastUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

// Page when user launch the app, it will check if there is user signed in
public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    MyApplication myApplication = (MyApplication) this.getApplication();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Handle the splash screen transition.
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTheme(R.style.Theme_Application);

        // get user authentication instance
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        // check signed-in instance and role
        if (currentUser != null){
            // If user is currently logged in, take them to corresponding page
            if(true){
                Intent caretakerIntent = new Intent(MainActivity.this, CalenderActivity.class);
                startActivity(caretakerIntent);
                finish();
            }else{
                Intent patientIntent = new Intent(MainActivity.this, IncomingActivity.class);
                startActivity(patientIntent);
                finish();
            }
        }else{
            // If user is NOT signed in, direct them to LoginActivity
            Intent signInIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(signInIntent);
            finish();
        }


        // Ask for calender permission
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_CALENDAR)!= PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.WRITE_CALENDAR)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.READ_CALENDAR,Manifest.permission.WRITE_CALENDAR},1);
        }


    } // End of onCreate() method


    // Method of Requesting calender permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_CALENDAR)!= PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.WRITE_CALENDAR)!= PackageManager.PERMISSION_GRANTED){
            ToastUtils.showShortToast(getApplicationContext(),"must allow calendar permission");
        }
    }

}