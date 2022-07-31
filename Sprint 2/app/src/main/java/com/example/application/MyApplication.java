package com.example.application;

import android.app.Application;

// The app crashes when this activity is used, saved for now
public class MyApplication extends Application {
    // Global variable for other class to access
    private boolean isRoleCaretaker;

    // Check the user's role, return true if user is caretaker
    // Return the boolean value of isRoleCaretaker
    public boolean isCaretaker(){
        return isRoleCaretaker;
    }

    // Set the role, isRoleCaretaker will be set to true if user is caretaker
    public void setRoleCaretaker(){
        this.isRoleCaretaker = true;
    }
    // Set the role, isRoleCaretaker will be set to false if user is patient
    public void setRolePatient(){
        this.isRoleCaretaker = false;
    }

}
