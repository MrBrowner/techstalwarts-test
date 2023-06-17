package com.example.test.model

class UsersResponse
{
    var error = ""

    var page = -1
    var per_page = 0
    var total = 0
    var total_pages = 0
    var data = listOf<User>()
}