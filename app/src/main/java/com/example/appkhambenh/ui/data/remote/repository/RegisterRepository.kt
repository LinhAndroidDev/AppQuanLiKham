package com.example.appkhambenh.ui.data.remote.repository

import com.example.appkhambenh.ui.data.remote.ApiService
import com.example.appkhambenh.ui.data.remote.model.RegisterModel
import javax.inject.Inject

class RegisterRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun registerUser(
        registerModel: RegisterModel
    ) = apiService.registerUser(registerModel)
}