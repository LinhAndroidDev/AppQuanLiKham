package com.example.appkhambenh.ui.data.remote

import com.example.appkhambenh.ui.data.remote.entity.LoginResponse
import com.example.appkhambenh.ui.login.RequestLogin
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("login.php")
    fun loginUser(@Body requestLogin: RequestLogin)
    :Call<LoginResponse>
}