package com.example.application;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

// Default page when caretakers sign-in and/or open their app
public class CalenderActivity extends AppCompatActivity {
    Button add_button = null;
    Button view_button = null;
    CalendarView calendar;
    String day = "";
    //TextView act_msg;
    //TextView tip_msg;
    // TextView time_msg;
    //FirebaseAuth fAuth;
    private DrawerLayout drawerLayout;
    private NavigationView nav_view;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calender_page);
        add_button = findViewById(R.id.add_button);
        view_button = findViewById(R.id.view_button);
        calendar = findViewById(R.id.calendarView);
        //fAuth = FirebaseAuth.getInstance();

        // Calender user changes date and store it in "day"
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                day = i + "-" + (i1 + 1) + "-" + i2;
                Log.i(TAG, "onSelectedDayChange: "+day);
                Toast.makeText(CalenderActivity.this, day, Toast.LENGTH_SHORT).show();
                //FirebaseDatabase.getInstance().getReference().child("Tasks and Dates").child(day).push().setValue(new Task("drink","", 10, 30, 15));
            }
        });

        // Go to EventList when user clicks VIEW TASK
        view_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(CalenderActivity.this, EventsList.class);
                startActivity(intent);
            }
        });

        // Go to addPage when user clicks ADD TASK
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(day.isEmpty()){
                    Toast.makeText(CalenderActivity.this, "Please tap on a date", Toast.LENGTH_LONG).show();
                }
                else {
                    Intent intent = new Intent();
                    intent.setClass(CalenderActivity.this, addPage.class);
                    intent.putExtra("date", day);
                    startActivity(intent);
                }
            }
        });

        // Toolbar/Navigation on top
        drawerLayout = findViewById(R.id.drawer_layout);
        nav_view = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        // Make the Navigation drawer icon always appear on the action bar
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_navigation_white);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Respond to each menu item clicked
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_switch_role:
                        // User wants to switch to another role, go to RoleSelection
                        Toast.makeText(CalenderActivity.this, "Switching Role",Toast.LENGTH_SHORT).show();
                        Intent intentSwitchRole = new Intent(CalenderActivity.this, RoleSelection.class);
                        startActivity(intentSwitchRole);
                        finish();
                        return true;
                    case R.id.nav_logout:
                        // User wants to log out, display msg, sign out and return to LoginActivity
                        Toast.makeText(CalenderActivity.this, "Logging out",Toast.LENGTH_SHORT).show();
                        FirebaseAuth.getInstance().signOut();
                        Intent intentLogOut = new Intent(CalenderActivity.this, LoginActivity.class);
                        startActivity(intentLogOut);
                        finish();
                        return true;
                }
                return true;
            }
        });

    }// End of onCreate() Method

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null)
        //FirebaseUser currentUser = fAuth.getCurrentUser();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if (item.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
