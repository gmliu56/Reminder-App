package com.example.application;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

// Task Object
public class Task {
    private String date;
    private String task_name;
    private String tips;
    private String time;
    private int hour;
    private int minute;
    private int timestamp;

    public Task() {
    }
    public Task(String task_name, String tips, int hour, int minute, String time) {
        this.task_name = task_name;
        this.tips = tips;
        this.hour = hour;
        this.minute = minute;

        this.time = time; // Store time as String
        this.timestamp = (int)(hour * 100) + minute; // Store time as integer number
    }

    public Task(String task_name, String tips, int hour, int minute) {
        this.task_name = task_name;
        this.tips = tips;
        this.hour = hour;
        this.minute = minute;

        this.time = hour + ":" + minute; // Store time as String
        this.timestamp = (int)(hour * 100) + minute; // Store time as integer number
    }

    // Constructor only for displaying text
    public Task(String task_name, String tips, String time){
        this.task_name = task_name;
        this.tips = tips;
        this.time = time;
    }
    /*
        public void setHour(int hour) {
            this.hour = hour;
        }

        public void setMinute(int minute) {
            this.minute = minute;
        }
        public void setTime(int hour, int minute){this.time = "hour" + ":" + "minute";}

        public int getHour() {
            return hour;
        }

        public int getMinute() {
            return minute;
        }
        */
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

    public int getTimestamp() {
        return timestamp;
    }

    public void clearVariables(){
        this.task_name = "No Activity";
        this.tips = "";
        this.time = "";
    }

    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

