package com.example.appkhambenh.ui.data.remote.repository.doctor

import com.example.appkhambenh.ui.data.remote.DoctorService
import com.example.appkhambenh.ui.data.remote.model.PatientInfoModel
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class PatientRepository @Inject constructor(private val doctorService: DoctorService) {
    suspend fun getListPatient(
        fullname: String? = null,
        email: String? = null,
        citizenId: String? = null,
        healthInsurance: String? = null,
        phoneNumber: String? = null,
    ) = doctorService.getListPatient(fullname, email, citizenId, healthInsurance, phoneNumber)

    suspend fun updateInfoPatient(idUser: Int, infoPatient: PatientInfoModel) =
        doctorService.updateInfoPatient(idUser, infoPatient)

    suspend fun getValueVitalChart(patientId: Int) = doctorService.getValueVitalChart(patientId)

    suspend fun deletePatient(patientId: Int) = doctorService.deletePatient(patientId)
}