package com.example.appkhambenh.ui.data.remote.repository.doctor

import com.example.appkhambenh.ui.data.remote.DoctorService
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class MedicalHistoryRepository @Inject constructor(private val doctorService: DoctorService) {
    suspend fun getListMedicalHistory() = doctorService.getListMedicalHistory()
}