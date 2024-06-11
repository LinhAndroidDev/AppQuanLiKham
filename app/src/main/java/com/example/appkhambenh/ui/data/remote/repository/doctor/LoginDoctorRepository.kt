package com.example.appkhambenh.ui.data.remote.repository.doctor

import com.example.appkhambenh.ui.data.remote.DoctorService
import dagger.hilt.android.scopes.ViewModelScoped
import okhttp3.RequestBody
import javax.inject.Inject

@ViewModelScoped
class LoginDoctorRepository @Inject constructor(private val doctorService: DoctorService) {
    suspend fun loginDoctor(email: String, password: String) =
        doctorService.loginDoctor(email, password)
}