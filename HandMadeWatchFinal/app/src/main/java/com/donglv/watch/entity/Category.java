package com.donglv.watch.entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by vcom on 15/11/2016.
 */
public class Category {
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

    public static class MediaCateGory {
        @SerializedName("id")
        private String id;
        @SerializedName("mime_type")
        private String mime_type;
        @SerializedName("original_name")
        private String original_name;
        @SerializedName("original_extension")
        private String original_extension;
        @SerializedName("size")
        private String size;
        @SerializedName("updated_by")
        private String updated_by;
        @SerializedName("created_by")
        private String created_by;
        @SerializedName("created_at")
        private String created_at;
        @SerializedName("updated_at")
        private String updated_at;

        public String getId() {
            return id;
        }

        public String getMime_type() {
            return mime_type;
        }

        public String getOriginal_name() {
            return original_name;
        }

        public String getOriginal_extension() {
            return original_extension;
        }

        public String getSize() {
            return size;
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
    }


    public static class Media {
        @SerializedName("id")
        private String id;
        @SerializedName("mime_type")
        private String mime_type;
        @SerializedName("original_name")
        private String original_name;
        @SerializedName("original_extension")
        private String original_extension;
        @SerializedName("size")
        private String size;
        @SerializedName("updated_by")
        private String updated_by;
        @SerializedName("created_by")
        private String created_by;
        @SerializedName("created_at")
        private String created_at;
        @SerializedName("updated_at")
        private String updated_at;

        public String getId() {
            return id;
        }

        public String getMime_type() {
            return mime_type;
        }

        public String getOriginal_name() {
            return original_name;
        }

        public String getOriginal_extension() {
            return original_extension;
        }

        public String getSize() {
            return size;
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
    }

    public static class Posts {
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
        @SerializedName("media")
        private ArrayList<Media> media;

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

        public ArrayList<Media> getMedia() {
            return media;
        }
    }

    public static class Data {
        @SerializedName("id")
        private String id;
        @SerializedName("description")
        private String description;
        @SerializedName("title")
        private String title;
        @SerializedName("updated_by")
        private String updated_by;
        @SerializedName("created_by")
        private String created_by;
        @SerializedName("created_at")
        private String created_at;
        @SerializedName("updated_at")
        private String updated_at;
        @SerializedName("posts_count")
        private String posts_count;
        @SerializedName("media")
        private ArrayList<MediaCateGory> media;
        @SerializedName("posts")
        private ArrayList<Posts> posts;

        public String getId() {
            return id;
        }

        public String getDescription() {
            return description;
        }

        public String getTitle() {
            return title;
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

        public String getPosts_count() {
            return posts_count;
        }

        public ArrayList<MediaCateGory> getMedia() {
            return media;
        }

        public ArrayList<Posts> getPosts() {
            return posts;
        }
    }
}
