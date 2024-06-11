package com.example.appkhambenh.ui.data.remote.repository.user

import com.example.appkhambenh.ui.data.remote.ApiService
import com.example.appkhambenh.ui.data.remote.model.LoginModel
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class LoginRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun loginUser(
        loginModel: LoginModel,
    ) = apiService.loginUser(loginModel)
}