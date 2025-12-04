package com.example.myapplication.ui.Tickets;

import android.os.Parcel;
import android.os.Parcelable;

public class TicketType implements Parcelable {
    private final String name;
    private final double price;
    private int quantity;

    public TicketType(String name, double price) {
        this.name = name;
        this.price = price;
        this.quantity = 0;
    }

    protected TicketType(Parcel in) {
        name = in.readString();
        price = in.readDouble();
        quantity = in.readInt();
    }

    public static final Creator<TicketType> CREATOR = new Creator<TicketType>() {
        @Override
        public TicketType createFromParcel(Parcel in) {
            return new TicketType(in);
        }

        @Override
        public TicketType[] newArray(int size) {
            return new TicketType[size];
        }
    };

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeDouble(price);
        dest.writeInt(quantity);
    }
}
