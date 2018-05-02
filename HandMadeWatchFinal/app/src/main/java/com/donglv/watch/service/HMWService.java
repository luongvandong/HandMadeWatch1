package com.donglv.watch.service;

import com.donglv.watch.entity.ActionLikeResponse;
import com.donglv.watch.entity.Category;
import com.donglv.watch.entity.CategoryInfo;
import com.donglv.watch.entity.ChatItem;
import com.donglv.watch.entity.Conversation;
import com.donglv.watch.entity.HomeResponse;
import com.donglv.watch.entity.LoginResponse;
import com.donglv.watch.entity.ProductInfo;
import com.donglv.watch.entity.SimpleClass;
import com.donglv.watch.entity.UploadImageResponse;
import com.donglv.watch.entity.User;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Part;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.mime.TypedFile;


/**
 * Created by Administrator on 24/11/2015.
 */
public interface HMWService {

    @GET("/api/post")
    public void getProductOnSale(@Query("type") String type, Callback<HomeResponse> callback);

    @GET("/api/user")
    public void getUserInfo(@Header("Authorization") String token, @Header("Accept") String Accept, Callback<User> callback);

    @GET("/api/conversation")
    public void getIdMyConversation(@Header("Authorization") String token, @Header("Accept") String accept, Callback<Conversation> callback);

    @Multipart
    @POST("/api/conversation")
    public void createMyConversation(@Header("Authorization") String token, @Header("Accept") String accept, @Part("hihi") String hihi, Callback<SimpleClass> callback);


    @GET("/api/conversation/{conversation_id}/item")
    public void getAllMess(@Header("Authorization") String token, @Header("Accept") String accept, @Path("conversation_id") String post_id, Callback<ChatItem> callback);

    @Multipart
    @POST("/api/conversation/{conversation_id}/item")
    public void addMess(@Header("Authorization") String token, @Header("Accept") String accept, @Part("content") String content, @Path("conversation_id") String post_id, Callback<SimpleClass> callback);


    @Multipart
    @POST("/api/order")
    public void sendOrder(@Header("Authorization") String token, @Header("Accept") String accept, @Part("data") String data, Callback<SimpleClass> callback);


    @Multipart
    @POST("/api/post/{post_id}/favorite")
    public void favorite(@Header("Authorization") String token, @Header("Accept") String accept, @Part("hihi") String hihi, @Path("post_id") String post_id, Callback<SimpleClass> callback);


    @GET("/api/allfavorite")
    public void getMyFavorite(@Header("Authorization") String token, @Header("Accept") String accept, Callback<HomeResponse> callback);

    @Multipart
    @POST("/api/user")
    public void register(@Part("username") String userName, @Part("password") String password, @Part("email") String email, Callback<SimpleClass> callback);

    @Multipart
    @POST("/api/user")
    public void registerFB(@Part("facebook_id") String facebook_id, @Part("username") String userName, Callback<SimpleClass> callback);

    @Multipart
    @POST("/oauth/token")
    public void loginFB(@Part("client_id") String client_id, @Part("client_secret") String client_secret, @Part("username") String username, @Part("password") String password, @Part("grant_type") String grant_type, @Part("scope") String scope, Callback<LoginResponse> callback);

    @Multipart
    @POST("/oauth/token")
    public void login(@Part("grant_type") String grant_type, @Part("client_id") String client_id, @Part("client_secret") String client_secret,
                      @Part("username") String username, @Part("password") String password, @Part("scope") String scope, Callback<LoginResponse> callback);


    @Multipart
    @POST("/api/user/forgotpassword")
    public void forgotPassword(@Part("email") String email, Callback<LoginResponse> callback);


    @GET("/api/post")
    public void getAllProduct(Callback<HomeResponse> callback);

    @GET("/api/post")
    public void getAllProductWithLogin(@Header("Authorization") String token, @Header("Accept") String accept, Callback<HomeResponse> callback);


    @GET("/api/post/{post_id}")
    public void getProductInfo(@Header("Authorization") String token, @Header("Accept") String accept, @Header("hihi") String hihi, @Path("post_id") String post_id, Callback<ProductInfo> callback);

    @GET("/api/post/{post_id}")
    public void getProductInfoNoToken(@Header("Accept") String accept, @Header("hihi") String hihi, @Path("post_id") String post_id, Callback<ProductInfo> callback);

    @GET("/api/category")
    public void getAllCategory(Callback<Category> callback);


    @GET("/api/category/{category_id}")
    public void getCategoryInfo(@Path("category_id") String category_id, Callback<CategoryInfo> callback);


    @Multipart
    @POST("/api/post/{post_id}/comment")
    public void comment(@Header("Authorization") String token, @Header("Accept") String accept, @Part("content") String content, @Path("post_id") String post_id, Callback<SimpleClass> callback);


    @Multipart
    @POST("/api/post/{post_id}/like")
    public void like(@Header("Authorization") String token, @Header("Accept") String accept, @Part("hihi") String hihi, @Path("post_id") String post_id, Callback<ActionLikeResponse> callback);

    @FormUrlEncoded
    @PUT("/api/user/{user_id}")
    void putUserInfo(@Path("user_id") String user_id,
                     @Header("Authorization") String token,
                     @Header("Accept") String accept,
                     @Field("avatar_id") String avatar_id,
                     @Field("first_name") String first_name,
                     @Field("last_name") String last_name,
                     @Field("email") String email,
                     @Field("telephone") String telephone,
                     @Field("address1") String address_1,
                     @Field("address2") String address_2,
                     @Field("city") String city,
                     @Field("country") String country,
                     @Field("region_state") String region_state,
                     @Field("fax") String fax,
                     @Field("company") String company,
                     @Field("post_code") String post_code,
                     @Field("password") String password,
                     Callback<SimpleClass> callback);

    @FormUrlEncoded
    @PUT("/api/user/{user_id}")
    void putUserInfo2(@Path("user_id") String user_id,
                      @Header("Authorization") String token,
                      @Header("Accept") String accept,
                      @Field("first_name") String first_name,
                      @Field("last_name") String last_name,
                      @Field("email") String email,
                      @Field("telephone") String telephone,
                      @Field("address1") String address_1,
                      @Field("address2") String address_2,
                      @Field("city") String city,
                      @Field("country") String country,
                      @Field("region_state") String region_state,
                      @Field("fax") String fax,
                      @Field("company") String company,
                      @Field("post_code") String post_code,
                      @Field("password") String password,
                      Callback<SimpleClass> callback);

    @FormUrlEncoded
    @PUT("/api/user/{user_id}")
    void putUserInfo(@Path("user_id") String user_id,
                     @Header("Authorization") String token,
                     @Header("Accept") String accept,
                     @Field("first_name") String first_name,
                     @Field("last_name") String last_name,
                     @Field("email") String email,
                     @Field("avatar_id") String avatar_id,
                     Callback<SimpleClass> callback);

    @Multipart
    @POST("/api/device")
    public void sendFCMToken(@Header("Authorization") String token, @Header("Accept") String accept, @Part("device_id") String device_id, @Part("device_type") String device_type, Callback<SimpleClass> callback);


    @Multipart
    @POST("/api/contact_message")
    public void sendContact(@Part("name") String name, @Part("email") String email, @Part("message") String message, Callback<SimpleClass> callback);


    @Multipart
    @POST("/api/lbmedia")
    public void upLoadImage(@Header("Authorization") String token, @Header("Accept") String accept, @Part("file") TypedFile file, Callback<UploadImageResponse> callback);

    @Multipart
    @POST("/api/lbpushcenter/device/{device_token}/clear_badge")
    public void sendClearBadge(@Header("Authorization") String token, @Header("Accept") String accept, @Part("hihi") String hihi, @Path("device_token") String device_token, Callback<SimpleClass> simpleClassCallback);

    @Multipart
    @PUT("/api/user/{user_id}")
    public void setNotification(@Header("Authorization") String token, @Header("Accept") String accept, @Path("user_id") String userId, @Part("notification_enable") int notification_enable,
                                Callback<SimpleClass> simpleClassCallback);

    @Multipart
    @POST("/lbpushcenter/api/device")
    public void sendTokenFristInstall(@Part("token") String device_id, @Part("application") String device_type, Callback<SimpleClass> callback);
}



