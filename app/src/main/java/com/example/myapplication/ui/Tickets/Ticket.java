package com.example.myapplication.ui.Tickets;

import java.util.UUID;

public class Ticket {
    private final String id;
    private final String month;
    private final String day;
    private final String time;
    private final String title;
    private boolean isBooked;

    public Ticket(String month, String day, String time, String title, boolean isBooked) {
        this.id = UUID.randomUUID().toString();
        this.month = month;
        this.day = day;
        this.time = time;
        this.title = title;
        this.isBooked = isBooked;
    }

    public String getId() {
        return id;
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

    public String getTitle() {
        return title;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void setBooked(boolean booked) {
        isBooked = booked;
    }
}
