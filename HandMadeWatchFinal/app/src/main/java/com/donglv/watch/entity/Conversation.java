package com.donglv.watch.entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by vcom on 13/12/2016.
 */
public class Conversation {


    @SerializedName("code")
    private String code;
    @SerializedName("data")
    private ArrayList<Data> data;

    public String getCode() {
        return code;
    }

    public ArrayList<Data> getData() {
        return data;
    }

    public static class Data {
        @SerializedName("id")
        private String id;
        @SerializedName("created_by")
        private String created_by;
        @SerializedName("updated_by")
        private String updated_by;
        @SerializedName("created_at")
        private String created_at;
        @SerializedName("updated_at")
        private String updated_at;

        public String getId() {
            return id;
        }
    }
}
