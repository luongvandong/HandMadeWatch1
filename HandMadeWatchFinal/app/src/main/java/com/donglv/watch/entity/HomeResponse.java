package com.donglv.watch.entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by vcom on 11/11/2016.
 */
public class HomeResponse {

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
    public static class Creator{
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
        @SerializedName("enable")
        private String enable;
        @SerializedName("first_name")
        private String first_name;
        @SerializedName("last_name")
        private String last_name;
        @SerializedName("telephone")
        private String telephone;
        @SerializedName("fax")
        private String fax;
        @SerializedName("company")
        private String company;
        @SerializedName("address1")
        private String address1;
        @SerializedName("address2")
        private String address2;
        @SerializedName("city")
        private String city;
        @SerializedName("post_code")
        private String post_code;
        @SerializedName("country")
        private String country;
        @SerializedName("region_state")
        private String region_state;

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

        public String getAvatar_id() {
            return avatar_id;
        }

        public String getEnable() {
            return enable;
        }

        public String getFirst_name() {
            return first_name;
        }

        public String getLast_name() {
            return last_name;
        }

        public String getTelephone() {
            return telephone;
        }

        public String getFax() {
            return fax;
        }

        public String getCompany() {
            return company;
        }

        public String getAddress1() {
            return address1;
        }

        public String getAddress2() {
            return address2;
        }

        public String getCity() {
            return city;
        }

        public String getPost_code() {
            return post_code;
        }

        public String getCountry() {
            return country;
        }

        public String getRegion_state() {
            return region_state;
        }
    }
    public static class Media {
        @SerializedName("id")
        private String id;
        @SerializedName("mime_type")
        private String mime_type;
        @SerializedName("original_extension")
        private String original_extension;
        @SerializedName("size")
        private int size;
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

        public int getSize() {
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
    public static class Likes {
        @SerializedName("creator")
        public Creator creator;
        public Creator getCreator() {
            return creator;
        }
    }
    public static class Liked {

        @SerializedName("id")
        public String id;
        @SerializedName("content")
        public String content;
        @SerializedName("target_id")
        public String target_id;
        @SerializedName("target_type")
        public String target_type;
        @SerializedName("updated_by")
        public String updated_by;
        @SerializedName("created_by")
        public String created_by;
        @SerializedName("created_at")
        public String created_at;
        @SerializedName("updated_at")
        public String updated_at;


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


    public static class Favorited{

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
        @SerializedName("likes_count")
        private int likes_count;
        @SerializedName("media")
        private ArrayList<Media> media;
        @SerializedName("liked")
        private ArrayList<Liked> liked;
        @SerializedName("likes")
        private ArrayList<Likes> likes;
        @SerializedName("favorited")
        private ArrayList<Favorited> favorited;
        @SerializedName("currency")
        private String currency;
        @SerializedName("comments_count")
        private int comments_count;

        @SerializedName("sale_active")
        private int saleActive;
        @SerializedName("sale_price")
        private String salePrice;


        public String getSalePrice() {
            return salePrice;
        }

        public int getSaleActive() {
            return saleActive;
        }

        public int getComments_count() {
            return comments_count;
        }

        public ArrayList<Likes> getLikes() {
            return likes;
        }

        public String getCurrency() {
            return currency;
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

        public int getLikes_count() {
            return likes_count;
        }

        public ArrayList<Media> getMedia() {
            return media;
        }

        public ArrayList<Liked> getLiked() {
            return liked;
        }

        public ArrayList<Favorited> getFavorited() {
            return favorited;
        }

        public void setLiked(ArrayList<Liked> liked) {
            this.liked = liked;
        }

        public void setFavorited(ArrayList<Favorited> favorited) {
            this.favorited = favorited;
        }
    }
}
