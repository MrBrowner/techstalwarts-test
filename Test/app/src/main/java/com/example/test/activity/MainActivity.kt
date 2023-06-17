package com.example.test.activity

import android.content.Intent
import android.os.Bundle
import com.example.logintest.ApiClient
import com.example.logintest.model.Login
import com.example.logintest.model.LoginResponse
import com.example.test.AppPref
import com.example.test.Utils.toast
import com.example.test.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : BaseActivity()
{
    private lateinit var ui: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        if (AppPref.getAccessToken().isNotBlank())
        {
            startActivity(Intent(context, HomeActivity::class.java))
            finish()
        }

        ui = ActivityMainBinding.inflate(layoutInflater)
        setContentView(ui.root)
        title = "Login"

        ui.btnLogin.setOnClickListener {
            validateAndSave()
        }
    }

    private fun validateAndSave()
    {
        ui.etUsername.error = null
        ui.etPassword.error = null
        var error = false

        val un = ui.etUsername.editText!!.text.toString().trim()
        val pwd = ui.etPassword.editText!!.text.toString().trim()

        if (un.isBlank())
        {
            error = true
            ui.etUsername.error = "Field Required"
        }
        if (pwd.isBlank())
        {
            error = true
            ui.etPassword.error = "Field Required"
        }

        if (error) return

        val loginCred = Login().apply {
            email = un
            password = pwd
        }

        showProgressDialog("Please Wait..")
        ApiClient.login(loginCred, object : Callback<LoginResponse>
        {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>)
            {
                if (isFinished) return
                dismissProgressDialog()
                if (response.isSuccessful && response.body() != null)
                {
                    val data = response.body() as LoginResponse
                    toast("TOKEN = " + data.token)
                    AppPref.setAccessToken(data.token)
                    startActivity(Intent(context, HomeActivity::class.java))
                } else
                {
                    val errorMsg = response.body()?.error ?: response.errorBody().toString()
                    toast("${response.code()} : ${gson.toJson(errorMsg)}")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable)
            {
                if (isFinished) return
                dismissProgressDialog()
                toast("error=${t.message.toString()}")
            }
        })
    }
}