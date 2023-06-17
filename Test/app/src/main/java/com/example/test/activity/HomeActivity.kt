package com.example.test.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.BaseAdapter
import com.example.logintest.ApiClient
import com.example.test.AppPref
import com.example.test.R
import com.example.test.Utils.toast
import com.example.test.adapter.HomeAdapter
import com.example.test.databinding.ActivityHomeBinding
import com.example.test.model.UsersResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : BaseActivity()
{
    private lateinit var adapter: HomeAdapter

    private lateinit var ui: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        ui = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(ui.root)
        title = "Home"

        adapter = HomeAdapter(context)
        ui.lvUsers.adapter = adapter

        ui.etSearch.addTextChangedListener(object : TextWatcher
        {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int)
            {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int)
            {
            }

            override fun afterTextChanged(s: Editable?)
            {
                adapter.doSearch(s.toString())
            }
        })

        getApiUsers()
    }

    private fun getApiUsers()
    {
        showProgressDialog("Please Wait..")
        ApiClient.getUsers(2, object : Callback<UsersResponse>
        {
            override fun onResponse(call: Call<UsersResponse>, response: Response<UsersResponse>)
            {
                if (isFinished) return
                dismissProgressDialog()
                if (response.isSuccessful && response.body() != null)
                {
                    val data = response.body() as UsersResponse
                    Log.e("list", gson.toJson(data.data.count()))
                    adapter.setUsers(data.data)
                } else
                {
                    val errorMsg = response.body()?.error ?: response.errorBody().toString()
                    toast("${response.code()} : ${gson.toJson(errorMsg)}")
                }
            }

            override fun onFailure(call: Call<UsersResponse>, t: Throwable)
            {
                if (isFinished) return
                dismissProgressDialog()
                toast("error=${t.message.toString()}")
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean
    {
        menuInflater.inflate(R.menu.menu_home, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        if (item.itemId == R.id.action_logout)
        {
            AppPref.clear(context)
            return true

        } else if (item.itemId == R.id.action_map)
        {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}