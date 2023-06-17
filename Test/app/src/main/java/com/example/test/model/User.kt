package com.example.test.model

import android.graphics.Bitmap

class User
{
    var id: Int? = null
    var email = ""
    var first_name = ""
    var last_name = ""
    var avatar = ""

    @Transient
    var bitmap: Bitmap? = null

    fun getFullName() = "$first_name $last_name"
}