package com.iteration.bookmyservice.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TokenUpdateResponse {

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("massage")
    @Expose
    private String massage;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

}
