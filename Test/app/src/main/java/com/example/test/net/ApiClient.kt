package com.example.logintest

import android.content.Context
import android.util.Log
import com.example.logintest.model.Login
import com.example.logintest.model.LoginResponse
import com.example.test.model.UsersResponse
import com.google.gson.GsonBuilder
import okhttp3.MediaType
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

open class ApiClient(context: Context)
{
    init
    {
        if (mRetrofit == null)
        {
            val baseUrl = "https://reqres.in/"

            val builder = Retrofit.Builder()
            builder.baseUrl(baseUrl)
            builder.addConverterFactory(GsonConverterFactory.create(GsonBuilder().serializeNulls().create()))
            mRetrofit = builder.build()
        }
    }

    companion object
    {
        private val JSON: MediaType? = MediaType.parse("application/json; charset=utf-8")

        var mRetrofit: Retrofit? = null

        private fun getApiClient(): IApiClient?
        {
            return mRetrofit?.create(IApiClient::class.java)
        }

        fun login(login: Login, callback: Callback<LoginResponse>)
        {
            getApiClient()?.login(login)?.enqueue(callback)
        }

        fun getUsers(pageNumber: Int, callback: Callback<UsersResponse>)
        {
            getApiClient()?.getUsers(pageNumber)?.enqueue(callback)
        }
    }
}