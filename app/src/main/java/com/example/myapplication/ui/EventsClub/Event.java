package com.example.myapplication.ui.EventsClub;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Event {
    private String name;
    private long date;
    private String duration;

    // Required empty public constructor for Firebase
    public Event() {}

    // Getters for Firebase deserialization
    public String getName() {
        return name;
    }

    public long getDate() {
        return date;
    }

    public String getDuration() { return duration; }

    // Helper methods for the adapter
    public String getMonth() {
        if (date == 0) return "";
        SimpleDateFormat sdf = new SimpleDateFormat("MMM", Locale.US);
        return sdf.format(new Date(date)).toUpperCase();
    }

    public String getDay() {
        if (date == 0) return "";
        SimpleDateFormat sdf = new SimpleDateFormat("dd", Locale.US);
        return sdf.format(new Date(date));
    }

    public String getTime() {
        if (date == 0) return "";
        // Example format: "Mon 5:00 p.m."
        SimpleDateFormat sdf = new SimpleDateFormat("EEE h:mm a", Locale.US);
        return sdf.format(new Date(date));
    }
}
