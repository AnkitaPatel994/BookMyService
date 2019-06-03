package com.iteration.bookmyservice.model;

import com.google.gson.annotations.SerializedName;

public class Service {

    @SerializedName("service_id")
    private String service_id;
    @SerializedName("service_name")
    private String service_name;
    @SerializedName("service_price")
    private String service_price;
    @SerializedName("service_ex_period")
    private String service_ex_period;

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public String getService_price() {
        return service_price;
    }

    public void setService_price(String service_price) {
        this.service_price = service_price;
    }

    public String getService_ex_period() {
        return service_ex_period;
    }

    public void setService_ex_period(String service_ex_period) {
        this.service_ex_period = service_ex_period;
    }
}
