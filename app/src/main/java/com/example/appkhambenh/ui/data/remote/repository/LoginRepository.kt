package com.example.appkhambenh.ui.data.remote.repository

import com.example.appkhambenh.ui.data.remote.ApiService
import com.example.appkhambenh.ui.data.remote.model.LoginModel
import javax.inject.Inject

class LoginRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun loginUser(
        loginModel: LoginModel,
    ) = apiService.loginUser(loginModel)
}