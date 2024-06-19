package com.example.appkhambenh.ui.data.remote.repository.doctor

import com.example.appkhambenh.ui.data.remote.DoctorService
import com.example.appkhambenh.ui.data.remote.model.PatientInfoModel
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class UpdateInfoPatientRepository @Inject constructor(private val doctorService: DoctorService) {
    suspend fun updateInfoPatient(idUser: Int, infoPatient: PatientInfoModel) =
        doctorService.updateInfoPatient(idUser, infoPatient)
}