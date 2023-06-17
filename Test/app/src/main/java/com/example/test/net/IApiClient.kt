package com.example.logintest

import com.example.logintest.model.Login
import com.example.logintest.model.LoginResponse
import com.example.test.model.UsersResponse
import retrofit2.Call
import retrofit2.http.*

interface IApiClient
{
    @Headers("Content-Type: application/json")
    @POST("api/login")
    fun login(@Body login: Login): Call<LoginResponse>

    @GET("api/users/")
    fun getUsers(@Query("page") number: Int): Call<UsersResponse>
}