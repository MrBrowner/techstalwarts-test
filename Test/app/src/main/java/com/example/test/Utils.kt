package com.example.test

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.annotation.StringRes

object Utils
{
    fun Context.str(@StringRes resId: Int) = getString(resId)

    fun Context.toast(str: String) = showToast(this, str)

    fun showToast(context: Context, text: String)
    {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show()
    }

    fun loge(tag: String, msg: String)
    {
        Log.e(tag, msg)
    }
}