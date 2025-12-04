package com.example.myapplication.ui.Tickets;

public class Ticket {
    private String id;
    private String name;
    private double price;
    private int quantity;
    private String month;
    private String day;
    private String time;
    private String title;
    private boolean booked;

    public Ticket() {
        // Public no-arg constructor needed for Firestore
    }

    public Ticket(String id, String name, double price, int quantity, String month, String day, String time, String title, boolean booked) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.month = month;
        this.day = day;
        this.time = time;
        this.title = title;
        this.booked = booked;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isBooked() {
        return booked;
    }

    public void setBooked(boolean booked) {
        this.booked = booked;
    }
}
