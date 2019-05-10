package com.iteration.bookmyservice.model;

import com.google.gson.annotations.SerializedName;

public class Timeslot {

    @SerializedName("t_id")
    private String t_id;
    @SerializedName("t_timeslot")
    private String t_timeslot;

    public String getT_id() {
        return t_id;
    }

    public void setT_id(String t_id) {
        this.t_id = t_id;
    }

    public String getT_timeslot() {
        return t_timeslot;
    }

    public void setT_timeslot(String t_timeslot) {
        this.t_timeslot = t_timeslot;
    }
}
