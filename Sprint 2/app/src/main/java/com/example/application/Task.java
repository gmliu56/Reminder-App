package com.example.application;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

// Task Object
public class Task {
    private String task_name;
    private String tips;
    private String time;
    //private Date delete
    //public static ArrayList<Task> taskArrayList = new ArrayList<>();
    private String imageUrl;

    public Task() {
    }

    public Task(String task_name, String tips, int hour, int minute,String time) {
        this.task_name = task_name;
        this.tips = tips;
        this.time = time;
    }

    public Task(String task_name, String tips, String time, String imageUrl) {
        this.task_name = task_name;
        this.tips = tips;
        this.time = time;
        this.imageUrl = imageUrl;
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

    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

