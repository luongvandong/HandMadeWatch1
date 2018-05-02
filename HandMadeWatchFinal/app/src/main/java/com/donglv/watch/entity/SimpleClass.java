package com.donglv.watch.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by vcom on 11/11/2016.
 */
public class SimpleClass {

    @SerializedName("code")
    private String code;
    @SerializedName("description")
    private String description;

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
