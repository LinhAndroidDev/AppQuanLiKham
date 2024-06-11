package com.example.appkhambenh.ui.data.remote.repository.user

import com.example.appkhambenh.ui.data.remote.ApiService
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class SpecialistRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getListSpecialist() = apiService.getListSpecialist()
}