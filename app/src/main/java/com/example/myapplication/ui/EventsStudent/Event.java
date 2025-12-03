package com.example.myapplication.ui.EventsStudent;

public class Event {
    private String time;
    private String duration;
    private String title;
    private String location;
    private long date; // Date of the event in milliseconds

    public Event(String time, String duration, String title, String location, long date) {
        this.time = time;
        this.duration = duration;
        this.title = title;
        this.location = location;
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public String getDuration() {
        return duration;
    }

    public String getTitle() {
        return title;
    }

    public String getLocation() {
        return location;
    }

    public long getDate() {
        return date;
    }
}
