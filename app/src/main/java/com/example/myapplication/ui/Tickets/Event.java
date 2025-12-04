package com.example.myapplication.ui.Tickets;

public class Event {
    String day, month, title, time;
    boolean purchased;

    public Event(String month, String day, String title, String time, boolean purchased) {
        this.month = month;
        this.day = day;
        this.title = title;
        this.time = time;
        this.purchased = purchased;
    }

    public String getDay() {
        return day;
    }

    public String getMonth() {
        return month;
    }

    public String getTitle() {
        return title;
    }

    public String getTime() {
        return time;
    }

    public boolean isPurchased() {
        return purchased;
    }

    public void setPurchased(boolean purchased) {
        this.purchased = purchased;
    }
}