package com.example.appkhambenh.ui.data.remote.repository.user

import com.example.appkhambenh.ui.data.remote.ApiService
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class HomeRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getUserInfo() = apiService.getUserInfo()

    suspend fun getAvatar(userId: Int) = apiService.getAvatar(userId)
}