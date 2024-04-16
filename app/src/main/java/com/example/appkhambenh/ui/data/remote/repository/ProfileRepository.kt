package com.example.appkhambenh.ui.data.remote.repository

import com.example.appkhambenh.ui.data.remote.ApiService
import com.example.appkhambenh.ui.data.remote.model.ProfileModel
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class ProfileRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getUserInfo() = apiService.getUserInfo()

    suspend fun updateProfile(profileModel: ProfileModel, userId: Int) =
        apiService.updateProfile(profileModel, userId)
}