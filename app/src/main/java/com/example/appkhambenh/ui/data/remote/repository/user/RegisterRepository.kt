package com.example.appkhambenh.ui.data.remote.repository.user

import com.example.appkhambenh.ui.data.remote.ApiService
import com.example.appkhambenh.ui.data.remote.model.RegisterModel
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class RegisterRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun registerUser(
        registerModel: RegisterModel
    ) = apiService.registerUser(registerModel)
}