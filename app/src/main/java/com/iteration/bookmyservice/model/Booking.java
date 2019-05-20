package com.iteration.bookmyservice.model;

import com.google.gson.annotations.SerializedName;

public class Booking {

    @SerializedName("booking_id")
    private String booking_id;
    @SerializedName("booking_name")
    private String booking_name;
    @SerializedName("booking_email")
    private String booking_email;
    @SerializedName("booking_phone")
    private String booking_phone;
    @SerializedName("booking_address")
    private String booking_address;
    @SerializedName("booking_service_id")
    private String booking_service_id;
    @SerializedName("service_name")
    private String service_name;
    @SerializedName("booking_date")
    private String booking_date;
    @SerializedName("booking_vinno")
    private String booking_vinno;
    @SerializedName("booking_comment")
    private String booking_comment;
    @SerializedName("booking_t_id")
    private String booking_t_id;
    @SerializedName("t_timeslot")
    private String t_timeslot;

    public String getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(String booking_id) {
        this.booking_id = booking_id;
    }

    public String getBooking_name() {
        return booking_name;
    }

    public void setBooking_name(String booking_name) {
        this.booking_name = booking_name;
    }

    public String getBooking_email() {
        return booking_email;
    }

    public void setBooking_email(String booking_email) {
        this.booking_email = booking_email;
    }

    public String getBooking_phone() {
        return booking_phone;
    }

    public void setBooking_phone(String booking_phone) {
        this.booking_phone = booking_phone;
    }

    public String getBooking_address() {
        return booking_address;
    }

    public void setBooking_address(String booking_address) {
        this.booking_address = booking_address;
    }

    public String getBooking_service_id() {
        return booking_service_id;
    }

    public void setBooking_service_id(String booking_service_id) {
        this.booking_service_id = booking_service_id;
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public String getBooking_date() {
        return booking_date;
    }

    public void setBooking_date(String booking_date) {
        this.booking_date = booking_date;
    }

    public String getBooking_vinno() {
        return booking_vinno;
    }

    public void setBooking_vinno(String booking_vinno) {
        this.booking_vinno = booking_vinno;
    }

    public String getBooking_comment() {
        return booking_comment;
    }

    public void setBooking_comment(String booking_comment) {
        this.booking_comment = booking_comment;
    }

    public String getBooking_t_id() {
        return booking_t_id;
    }

    public void setBooking_t_id(String booking_t_id) {
        this.booking_t_id = booking_t_id;
    }

    public String getT_timeslot() {
        return t_timeslot;
    }

    public void setT_timeslot(String t_timeslot) {
        this.t_timeslot = t_timeslot;
    }
}
