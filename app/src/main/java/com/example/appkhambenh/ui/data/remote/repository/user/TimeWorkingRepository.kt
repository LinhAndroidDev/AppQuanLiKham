package com.example.appkhambenh.ui.data.remote.repository.user

import com.example.appkhambenh.ui.data.remote.ApiService
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class TimeWorkingRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getTimeWorking(id: Int, dateStart: Long) =
        apiService.getTimeWorking(id, dateStart)
}