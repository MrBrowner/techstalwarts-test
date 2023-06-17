package com.example.test.activity

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.logintest.ApiClient
import com.example.test.AppPref
import com.google.gson.Gson

open class BaseActivity : AppCompatActivity()
{
    var isFinished = false
    var progressDialog: ProgressDialog? = null
    val gson = Gson()

    lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        context = this

        AppPref(context)
        ApiClient(context)
    }

    fun dismissProgressDialog()
    {
        if (isFinished) return
        if (progressDialog != null && progressDialog?.isShowing!!) progressDialog?.dismiss()
    }

    fun showProgressDialog(message: String)
    {
        if (isFinished) return
        if (progressDialog == null)
        {
            progressDialog = ProgressDialog(this)
            progressDialog?.setCanceledOnTouchOutside(false)
            progressDialog?.setCancelable(false)
        }
        progressDialog?.setMessage(message)
        progressDialog?.show()
    }

    override fun onStop()
    {
        super.onStop()
        progressDialog?.dismiss()
    }

    override fun onDestroy()
    {
        isFinished = true
        dismissProgressDialog()
        super.onDestroy()
    }
}/*
{
    "email": "eve.holt@reqres.in",
    "password": "cityslicka"
}
 */