package com.donglv.watch.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Dr.Cuong on 1/20/2017.
 */

public class UploadImageResponse {

    @SerializedName("code")
    private int code;
    @SerializedName("data")
    private Data data;

    public int getCode() {
        return code;
    }

    public Data getData() {
        return data;
    }

    public static class Data {
        @SerializedName("created_at")
        private String created_at;
        @SerializedName("created_by")
        private String created_by;
        @SerializedName("id")
        private String id;
        @SerializedName("mime_type")
        private String mime_type;
        @SerializedName("original_extension")
        private String original_extension;
        @SerializedName("original_name")
        private String original_name;
        @SerializedName("size")
        private int size;
        @SerializedName("updated_at")
        private String updated_at;

        public String getCreated_at() {
            return created_at;
        }

        public String getCreated_by() {
            return created_by;
        }

        public String getId() {
            return id;
        }

        public String getMime_type() {
            return mime_type;
        }

        public String getOriginal_extension() {
            return original_extension;
        }

        public String getOriginal_name() {
            return original_name;
        }

        public int getSize() {
            return size;
        }

        public String getUpdated_at() {
            return updated_at;
        }
    }
}
