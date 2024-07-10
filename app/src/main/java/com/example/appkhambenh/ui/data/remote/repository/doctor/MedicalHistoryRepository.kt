package com.example.appkhambenh.ui.data.remote.repository.doctor

import com.example.appkhambenh.ui.data.remote.DoctorService
import com.example.appkhambenh.ui.data.remote.request.AddMedicalHistoryRequest
import com.example.appkhambenh.ui.data.remote.request.UpdateAllocationRequest
import com.example.appkhambenh.ui.data.remote.request.UpdateDiagnoseMedicalHistoryRequest
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.http.Body
import retrofit2.http.Path
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

    suspend fun updateAllocation(
        patientId: Int,
        updateAllocationRequest: UpdateAllocationRequest
    ) = doctorService.updateAllocation(patientId, updateAllocationRequest)

    suspend fun hospitalDischarge(
        medicalHistoryId: Int
    ) = doctorService.hospitalDischarge(medicalHistoryId)
}