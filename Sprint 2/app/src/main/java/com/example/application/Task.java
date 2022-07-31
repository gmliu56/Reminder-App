package com.example.application;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

// Task Object
public class Task {
    public static ArrayList<Task> taskArrayList = new ArrayList<>();
    private String task_name;
    private String tips;
    private int hour;
    private int minute;
    private String time;
    //private Date delete

    public Task() {
    }

    public Task(String task_name, String tips, int hour, int minute,String time) {
        this.task_name = task_name;
        this.tips = tips;
        this.hour = hour;
        this.minute = minute;
        this.time = time;
        /*
        delete = null;
        this.countDownTime = countDownTime;
         */
    }

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
    public String getTime(){return time;}

    public String getTask_name() {
        return task_name;
    }

    public String getTips() {
        return tips;
    }

    //public int getCountDownTime() {
       // return countDownTime;
   // }

    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

   // public void setCountDownTime(int countDownTime) {
        //this.countDownTime = countDownTime;
    //}
}

