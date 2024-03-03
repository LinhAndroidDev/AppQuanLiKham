package com.example.appkhambenh.ui.data.remote.repository

import com.example.appkhambenh.ui.data.remote.ApiService
import javax.inject.Inject

class GetInfoRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getUserInfo(token: String) = apiService.getUserInfo(token)
}