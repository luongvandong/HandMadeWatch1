package com.donglv.watch.entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by vcom on 15/11/2016.
 */
public class ProductInfo {

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

    public static class Media {
        @SerializedName("id")
        private String id;
        @SerializedName("mime_type")
        private String mime_type;
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

    public static class Liked {
        @SerializedName("id")
        private String id;
        @SerializedName("content")
        private String content;
        @SerializedName("target_id")
        private String target_id;
        @SerializedName("target_type")
        private String target_type;
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

        public String getContent() {
            return content;
        }

        public String getTarget_id() {
            return target_id;
        }

        public String getTarget_type() {
            return target_type;
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

    public static class Likes {
        @SerializedName("id")
        private String id;
        @SerializedName("content")
        private String content;
        @SerializedName("target_id")
        private String target_id;
        @SerializedName("target_type")
        private String target_type;
        @SerializedName("updated_by")
        private String updated_by;
        @SerializedName("created_by")
        private String created_by;
        @SerializedName("created_at")
        private String created_at;
        @SerializedName("updated_at")
        private String updated_at;
        @SerializedName("creator")
        private Creator creator;

        public Creator getCreator() {
            return creator;
        }

        public String getId() {
            return id;
        }

        public String getContent() {
            return content;
        }

        public String getTarget_id() {
            return target_id;
        }

        public String getTarget_type() {
            return target_type;
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

    public static class Creator {
        @SerializedName("avatar_id")
        private String avatar_id;
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
        @SerializedName("isAdmin")
        private boolean isAdmin;

        public boolean isAdmin() {
            return isAdmin;
        }

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

    public static class Comments {
        @SerializedName("id")
        private String id;
        @SerializedName("content")
        private String content;
        @SerializedName("target_id")
        private String target_id;
        @SerializedName("target_type")
        private String target_type;
        @SerializedName("updated_by")
        private String updated_by;
        @SerializedName("created_by")
        private String created_by;
        @SerializedName("created_at")
        private String created_at;
        @SerializedName("updated_at")
        private String updated_at;
        @SerializedName("creator")
        private Creator creator;

        public String getId() {
            return id;
        }

        public String getContent() {
            return content;
        }

        public String getTarget_id() {
            return target_id;
        }

        public String getTarget_type() {
            return target_type;
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

        public Creator getCreator() {
            return creator;
        }
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
        @SerializedName("media")
        private ArrayList<Media> media;
        @SerializedName("liked")
        private ArrayList<Liked> liked;
        @SerializedName("likes")
        private ArrayList<Likes> likes;
        @SerializedName("comments")
        private ArrayList<Comments> comments;
        @SerializedName("sale_active")
        private int saleActive;
        @SerializedName("sale_price")
        private String salePrice;
        @SerializedName("currency")
        private String currency;
        @SerializedName("comments_count")
        private int comments_count;
        @SerializedName("likes_count")
        private int likes_count;
        @SerializedName("favorited")
        private ArrayList<HomeResponse.Favorited> favorited;

        public ArrayList<HomeResponse.Favorited> getFavorited() {
            return favorited;
        }

        public int getComments_count() {
            return comments_count;
        }

        public int getLikes_count() {
            return likes_count;
        }

        public String getCurrency() {
            return currency;
        }

        public String getSalePrice() {
            return salePrice;
        }

        public int getSaleActive() {
            return saleActive;
        }

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

        public ArrayList<Liked> getLiked() {
            return liked;
        }

        public ArrayList<Likes> getLikes() {
            return likes;
        }

        public ArrayList<Comments> getComments() {
            return comments;
        }
    }
}
