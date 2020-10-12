package com.example.login_android.service;

import com.example.login_android.model.Login;
import com.example.login_android.model.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserClient {

    @POST("auth/login")
    Call<User> login(@Body Login login);

    @GET("products")
    Call<ResponseBody> getSecretInfo(@Header("Authorization")String  authToken);

}
