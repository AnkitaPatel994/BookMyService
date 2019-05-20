package com.iteration.bookmyservice.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BookingList {

    @SerializedName("status")
    private String status;
    @SerializedName("message")
    private String message;
    @SerializedName("BookingList")
    private ArrayList<Booking> bookingList;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Booking> getBookingList() {
        return bookingList;
    }

    public void setBookingList(ArrayList<Booking> bookingList) {
        this.bookingList = bookingList;
    }
}
