package com.example.appkhambenh.ui.data.remote.repository.doctor

import com.example.appkhambenh.ui.data.remote.entity.DoctorLoginResponse
import kotlinx.coroutines.flow.Flow

interface LoginDoctorRepository {
    suspend fun loginDoctor(email: String, password: String): Flow<DoctorLoginResponse>
}