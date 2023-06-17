package com.example.test

import android.content.Context
import android.content.Context.MODE_PRIVATE

import android.content.SharedPreferences


open class AppPref(context: Context)
{
    init
    {
        if (preferences == null) preferences = context.getSharedPreferences("private_app", MODE_PRIVATE)
    }

    companion object
    {
        private var preferences: SharedPreferences? = null

        private val KEY_ACCESS_TOKEN = "prf.accessToken"

        fun clear(context: Context)
        {
            preferences?.edit()?.clear()?.apply()
            preferences = context.getSharedPreferences("private_app", MODE_PRIVATE)
        }

        fun getString(key: String): String
        {
            return preferences!!.getString(key, "").toString()
        }

        fun putString(key: String, value: String)
        {
            preferences!!.edit().putString(key, value).apply()
        }

        fun getAccessToken(): String
        {
            return getString(KEY_ACCESS_TOKEN)
        }

        fun setAccessToken(value: String)
        {
            putString(KEY_ACCESS_TOKEN, value)
        }
    }
}