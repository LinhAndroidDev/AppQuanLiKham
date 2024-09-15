package com.example.appkhambenh.ui.data.remote.repository.doctor

import com.example.appkhambenh.ui.data.remote.DoctorService
import com.example.appkhambenh.ui.data.remote.base.ApiState
import com.example.appkhambenh.ui.data.remote.base.BaseRepository
import com.example.appkhambenh.ui.data.remote.entity.DoctorLoginResponse
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class LoginDoctorRepositoryImpl @Inject constructor(private val doctorService: DoctorService) :
    LoginDoctorRepository, BaseRepository() {
    override suspend fun loginDoctor(
        email: String,
        password: String,
    ): Flow<ApiState<DoctorLoginResponse>> = safeApiCall {
        doctorService.loginDoctor(email, password)
    }
}