package com.example.Module1.ApiClasses;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.OPTIONS;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface API {
    @OPTIONS("/user")
    Call<TokenClass> auth(@Query("email") String email, @Query("password") String password, @Query("uuid") String uuid);
    @POST("/user")
    Call<SequreClass> register(@Query("email") String email, @Query("name") String name, @Query("password") String password, @Query("uuid") String uuid);
    @GET("/app")
    Call<AppArray> getApps(@Query("competitor") String competitor);
    @POST("/app")
    Call regApp(App app);
    @POST("/mobile")
    Call<KeyClass> regMobile(MobileClass mobile);
    @GET("/rooms")
    Call<RoomArrayClass> getRooms(@Query("token") String token, @Query("uuid") String uuid);
    @POST("/rooms")
    Call<RoomResponse> addRoom(@Query("name") String name, @Query("type") String type, @Query("token") String token, @Query("uuid") String uuid);
    @GET("/devices")
    Call<DeviceArray> getDevices(@Header("room") String room, @Header("token") String token, @Header("uuid") String uuid);
}