package com.example.appkhambenh.ui.data.remote.repository

import com.example.appkhambenh.ui.data.remote.ApiService
import com.example.appkhambenh.ui.data.remote.model.ChangePasswordModel
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class ChangePasswordRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun changePassword(userId: Int, changePasswordModel: ChangePasswordModel) =
        apiService.changePassword(userId, changePasswordModel)
}