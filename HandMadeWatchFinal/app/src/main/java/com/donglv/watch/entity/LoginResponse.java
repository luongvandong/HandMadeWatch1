package com.donglv.watch.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by vcom on 11/11/2016.
 */
public class LoginResponse {
    @SerializedName("access_token")
    private String access_token;

    public String getAccess_token() {
        return access_token;
    }
}
