package com.donglv.watch.entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by vcom on 14/11/2016.
 */
public class LikeResponse {


    @SerializedName("code")
    private String code;
    @SerializedName("data")
    private Data data;

    public String getCode() {
        return code;
    }

    public Data getData() {
        return data;
    }

    public static class Liked {
    }

    public static class Data {
        @SerializedName("id")
        private String id;
        @SerializedName("description")
        private String description;
        @SerializedName("title")
        private String title;
        @SerializedName("price")
        private String price;
        @SerializedName("updated_by")
        private String updated_by;
        @SerializedName("created_by")
        private String created_by;
        @SerializedName("created_at")
        private String created_at;
        @SerializedName("updated_at")
        private String updated_at;
        @SerializedName("liked")
        private ArrayList<Liked> liked;

        public String getId() {
            return id;
        }

        public String getDescription() {
            return description;
        }

        public String getTitle() {
            return title;
        }

        public String getPrice() {
            return price;
        }

        public String getUpdated_by() {
            return updated_by;
        }

        public String getCreated_by() {
            return created_by;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public ArrayList<Liked> getLiked() {
            return liked;
        }
    }
}
