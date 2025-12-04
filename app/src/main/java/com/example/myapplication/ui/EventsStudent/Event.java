package com.example.myapplication.ui.EventsStudent;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Event {
    private String name;
    private String location;
    private long date;
    private String duration;


    public Event() {}

    public Event(String name, String location, long date, String duration) {
        this.name = name;
        this.location = location;
        this.date = date;
        this.duration = duration;
    }

    // Getters
    public String getName() { // Changed from 'getTitle' to 'getName'
        return name;
    }

    public String getLocation() {
        return location;
    }

    public long getDate() {
        return date;
    }

    public String getDuration() {
        return duration;
    }


    public String getTime() {
        if (date == 0) return "";
        SimpleDateFormat sdf = new SimpleDateFormat("h:mm a", Locale.US);
        return sdf.format(new Date(date));
    }
}
