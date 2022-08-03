package com.example.application;

// Task Object
public class Task {
    private String date;
    private String task_name;
    private String tips;
    private String time; // Store time as String
    private int hour;
    private int minute;
    private int timestamp; // Store time as integer number

    private boolean isComplete; // Completion status
    private String key; // Primary Key in Firebase

    public Task() {
    }
    public Task(String task_name, String tips, int hour, int minute, String time) {
        this.task_name = task_name;
        this.tips = tips;
        this.hour = hour;
        this.minute = minute;

        this.time = time;
        this.timestamp = (hour * 100) + minute;
    }

    public Task(String task_name, String tips, int hour, int minute, boolean completion) {
        this.task_name = task_name;
        this.tips = tips;
        this.hour = hour;
        this.minute = minute;

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
    public String getTime(){return time;}

    public String getTask_name() {
        return task_name;
    }

    public String getTips() {
        return tips;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public String getDate() {
        return date;
    }

    public int getTimestamp() {
        return timestamp;
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

