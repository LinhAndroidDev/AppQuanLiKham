package com.example.appkhambenh.ui.data.remote.repository

import com.example.appkhambenh.ui.data.remote.ApiService
import javax.inject.Inject

class HospitalRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getListHospital() = apiService.getListHospital()
}