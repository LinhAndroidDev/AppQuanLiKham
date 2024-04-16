package com.example.appkhambenh.ui.data.remote.repository

import com.example.appkhambenh.ui.data.remote.ApiService
import dagger.hilt.android.scopes.ViewModelScoped
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@ViewModelScoped
class UpdateAvatarRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun updateAvatar(id: Int, avatar: MultipartBody.Part) =
        apiService.updateAvatar(id, avatar)
}