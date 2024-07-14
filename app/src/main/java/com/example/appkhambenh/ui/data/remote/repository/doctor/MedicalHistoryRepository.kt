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

    suspend fun getMedicalHistory(patientId: Int) = doctorService.getMedicalHistory(patientId)

    suspend fun addMedicalHistory(addMedicalHistoryRequest: AddMedicalHistoryRequest) =
        doctorService.addMedicalHistory(addMedicalHistoryRequest)

    suspend fun updateDiagnoseMedicalHistory(
        medicalHistoryId: Int,
        updateDiagnoseMedicalHistoryRequest: UpdateDiagnoseMedicalHistoryRequest,
    ) = doctorService.updateDiagnoseMedicalHistory(
        medicalHistoryId,
        updateDiagnoseMedicalHistoryRequest
    )

    suspend fun updateAllocation(
        medicalHistoryId: Int,
        updateAllocationRequest: UpdateAllocationRequest
    ) = doctorService.updateAllocation(medicalHistoryId, updateAllocationRequest)

    suspend fun hospitalDischarge(
        medicalHistoryId: Int
    ) = doctorService.hospitalDischarge(medicalHistoryId)
}