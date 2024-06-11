package com.example.appkhambenh.ui.data.remote.repository.doctor

import com.example.appkhambenh.ui.data.remote.DoctorService
import javax.inject.Inject

class AdminDoctorRepository @Inject constructor(private val doctorService: DoctorService) {
    suspend fun getListPatient() = doctorService.getListPatient()
}