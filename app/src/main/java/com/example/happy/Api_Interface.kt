package com.example.happy

import retrofit2.Call
import retrofit2.http.GET


interface Api_Interface {
    @GET("/posts")
    fun getData(): Call<List<UsersItem>>
}