package com.iteration.bookmyservice.model;

import com.google.gson.annotations.SerializedName;

public class Slider {

    @SerializedName("id")
    private String id;
    @SerializedName("banner")
    private String banner;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }
}
