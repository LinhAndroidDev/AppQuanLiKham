package com.example.appkhambenh.ui.data.remote.repository.doctor

import com.example.appkhambenh.ui.data.remote.DoctorService
import com.example.appkhambenh.ui.data.remote.base.ApiState
import com.example.appkhambenh.ui.data.remote.base.BaseRepository
import com.example.appkhambenh.ui.data.remote.entity.AddMedicalHistoryResponse
import com.example.appkhambenh.ui.data.remote.entity.GetMedicalHistoryResponse
import com.example.appkhambenh.ui.data.remote.entity.HospitalDischargeResponse
import com.example.appkhambenh.ui.data.remote.entity.MedicalHistoryResponse
import com.example.appkhambenh.ui.data.remote.entity.UpdateAllocationResponse
import com.example.appkhambenh.ui.data.remote.entity.UpdateDiagnoseMedicalHistoryResponse
import com.example.appkhambenh.ui.data.remote.request.AddMedicalHistoryRequest
import com.example.appkhambenh.ui.data.remote.request.UpdateAllocationRequest
import com.example.appkhambenh.ui.data.remote.request.UpdateDiagnoseMedicalHistoryRequest
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class MedicalHistoryRepositoryImpl @Inject constructor(private val doctorService: DoctorService) :
    MedicalHistoryRepository, BaseRepository() {
    override suspend fun getListMedicalHistory(patientId: Int?): Flow<ApiState<MedicalHistoryResponse>> =
        safeApiCall {
            doctorService.getListMedicalHistory(patientId)
        }

    override suspend fun getMedicalHistory(patientId: Int): Flow<ApiState<GetMedicalHistoryResponse>> =
        safeApiCall {
            doctorService.getMedicalHistory(patientId)
        }

    override suspend fun addMedicalHistory(addMedicalHistoryRequest: AddMedicalHistoryRequest): Flow<ApiState<AddMedicalHistoryResponse>> =
        safeApiCall {
            doctorService.addMedicalHistory(addMedicalHistoryRequest)
        }

    override suspend fun updateDiagnoseMedicalHistory(
        medicalHistoryId: Int,
        updateDiagnoseMedicalHistoryRequest: UpdateDiagnoseMedicalHistoryRequest,
    ): Flow<ApiState<UpdateDiagnoseMedicalHistoryResponse>> = safeApiCall {
        doctorService.updateDiagnoseMedicalHistory(
            medicalHistoryId,
            updateDiagnoseMedicalHistoryRequest
        )
    }

    override suspend fun updateAllocation(
        medicalHistoryId: Int,
        updateAllocationRequest: UpdateAllocationRequest,
    ): Flow<ApiState<UpdateAllocationResponse>> = safeApiCall {
        doctorService.updateAllocation(medicalHistoryId, updateAllocationRequest)
    }

    override suspend fun hospitalDischarge(medicalHistoryId: Int): Flow<ApiState<HospitalDischargeResponse>> =
        safeApiCall {
            doctorService.hospitalDischarge(medicalHistoryId)
        }
}