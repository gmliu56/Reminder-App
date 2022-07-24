package com.example.application;

import java.time.LocalDate;
import java.time.LocalTime;

public class Task {
    String task_name;
    String tips;
    int hour;
    int minute;
    int countDownTime;

    public Task() {
    }

    public Task(String task_name, String tips, int hour, int minute, int countDownTime) {
        this.task_name = task_name;
        this.tips = tips;
        this.hour = hour;
        this.minute = minute;
        this.countDownTime = countDownTime;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public String getTask_name() {
        return task_name;
    }

    public String getTips() {
        return tips;
    }

    public int getCountDownTime() {
        return countDownTime;
    }

    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public void setCountDownTime(int countDownTime) {
        this.countDownTime = countDownTime;
    }
}

