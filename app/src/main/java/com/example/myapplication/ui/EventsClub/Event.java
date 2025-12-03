package com.example.myapplication.ui.EventsClub;

public class Event {
    private String month;
    private String day;
    private String time;
    private String name;

    public Event(String month, String day, String time, String name) {
        this.month = month;
        this.day = day;
        this.time = time;
        this.name = name;
    }

    public String getMonth() {
        return month;
    }

    public String getDay() {
        return day;
    }

    public String getTime() {
        return time;
    }

    public String getName() {
        return name;
    }
}
