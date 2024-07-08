package com.example.appkhambenh.ui.data.remote.repository.doctor

import com.example.appkhambenh.ui.data.remote.DoctorService
import com.example.appkhambenh.ui.data.remote.request.AddMedicalHistoryRequest
import com.example.appkhambenh.ui.data.remote.request.UpdateDiagnoseMedicalHistoryRequest
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class MedicalHistoryRepository @Inject constructor(private val doctorService: DoctorService) {
    suspend fun getListMedicalHistory(patientId: Int? = null) =
        doctorService.getListMedicalHistory(patientId)

    suspend fun addMedicalHistory(addMedicalHistoryRequest: AddMedicalHistoryRequest) =
        doctorService.addMedicalHistory(addMedicalHistoryRequest)

    suspend fun updateDiagnoseMedicalHistory(
        patientId: Int,
        updateDiagnoseMedicalHistoryRequest: UpdateDiagnoseMedicalHistoryRequest,
    ) = doctorService.updateDiagnoseMedicalHistory(
        patientId,
        updateDiagnoseMedicalHistoryRequest
    )
}