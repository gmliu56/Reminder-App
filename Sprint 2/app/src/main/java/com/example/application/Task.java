package com.example.application;

import android.net.Uri;

// Task Object
public class Task {
    private String date;
    private String task_name;
    private String tips;
    private String time;
    private String imageUrl;
    private int hour;
    private int minute;
    private int timestamp; // Store time as integer number

    private boolean isComplete; // Completion status
    private String key; // Primary Key in Firebase

    public Task() {
    }

    public Task(String task_name, String tips, int hour, int minute, boolean completion, String imageUrl) {
        this.task_name = task_name;
        this.tips = tips;
        this.hour = hour;
        this.minute = minute;
        this.imageUrl = imageUrl;

        this.time = hour + ":" + minute;
        this.timestamp = (hour * 100) + minute;
        this.isComplete = completion;
    }

    // Constructor only for displaying text
    public Task(String task_name, String tips, String time){
        this.task_name = task_name;
        this.tips = tips;
        this.time = time;
    }
    // Getter
    public String getTask_name() {
        return task_name;
    }

    public String getTips(){
        return tips;
    }

    public String getTime(){ return time; }

    public String getDate() {
        return date;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public boolean getComplete(){ return isComplete; }

    public String getKey(){ return key; }

    // Setter
    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setTime(int hour, int minute){this.time = "hour" + ":" + "minute";}

    public void setComplete(boolean complete){ this.isComplete = complete; }

    public void setKey(String key){ this.key = key; }

}

