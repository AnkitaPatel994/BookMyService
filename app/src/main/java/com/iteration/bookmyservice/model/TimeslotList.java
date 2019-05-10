package com.iteration.bookmyservice.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TimeslotList {

    @SerializedName("status")
    private String status;
    @SerializedName("message")
    private String message;
    @SerializedName("timeslot")
    private ArrayList<Timeslot> TimeslotList;

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

    public ArrayList<Timeslot> getTimeslotList() {
        return TimeslotList;
    }

    public void setTimeslotList(ArrayList<Timeslot> timeslotList) {
        TimeslotList = timeslotList;
    }
}
