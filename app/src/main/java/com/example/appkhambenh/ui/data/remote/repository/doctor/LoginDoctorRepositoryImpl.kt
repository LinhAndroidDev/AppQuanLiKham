package com.example.appkhambenh.ui.data.remote.repository.doctor

import com.example.appkhambenh.ui.data.remote.DoctorService
import com.example.appkhambenh.ui.data.remote.entity.DoctorLoginResponse
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@ViewModelScoped
class LoginDoctorRepositoryImpl @Inject constructor(private val doctorService: DoctorService) :
    LoginDoctorRepository {
    override suspend fun loginDoctor(email: String, password: String): Flow<DoctorLoginResponse> =
        flow {
            emit(doctorService.loginDoctor(email, password))
        }
}