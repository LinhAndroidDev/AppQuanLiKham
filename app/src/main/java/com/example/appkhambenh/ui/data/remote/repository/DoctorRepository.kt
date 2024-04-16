package com.example.appkhambenh.ui.data.remote.repository

import com.example.appkhambenh.ui.data.remote.ApiService
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class DoctorRepository @Inject constructor(private val apiService: ApiService){
    suspend fun getListDoctor() = apiService.getListDoctor()
}