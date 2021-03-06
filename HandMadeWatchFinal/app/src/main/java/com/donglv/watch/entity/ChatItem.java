package com.donglv.watch.entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by vcom on 09/12/2016.
 */
public class ChatItem {

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

    public static class Creator {
        @SerializedName("id")
        private String id;
        @SerializedName("name")
        private String name;
        @SerializedName("email")
        private String email;
        @SerializedName("created_at")
        private String created_at;
        @SerializedName("updated_at")
        private String updated_at;
        @SerializedName("avatar_id")
        private String avatar_id;

        public String getAvatar_id() {
            return avatar_id;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }
    }

    public static class Data {
        @SerializedName("id")
        private String id;
        @SerializedName("conversation_id")
        private String conversation_id;
        @SerializedName("content")
        private String content;
        @SerializedName("created_by")
        private String created_by;
        @SerializedName("updated_by")
        private String updated_by;
        @SerializedName("created_at")
        private String created_at;
        @SerializedName("updated_at")
        private String updated_at;
        @SerializedName("creator")
        private Creator creator;

        public String getId() {
            return id;
        }

        public String getConversation_id() {
            return conversation_id;
        }

        public String getContent() {
            return content;
        }

        public String getCreated_by() {
            return created_by;
        }

        public String getUpdated_by() {
            return updated_by;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public Creator getCreator() {
            return creator;
        }
    }
}
