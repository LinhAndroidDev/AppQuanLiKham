package com.example.appkhambenh.ui.data.remote.repository.doctor

import com.example.appkhambenh.ui.data.remote.base.ApiState
import com.example.appkhambenh.ui.data.remote.entity.AddMedicalHistoryResponse
import com.example.appkhambenh.ui.data.remote.entity.GetMedicalHistoryResponse
import com.example.appkhambenh.ui.data.remote.entity.HospitalDischargeResponse
import com.example.appkhambenh.ui.data.remote.entity.MedicalHistoryResponse
import com.example.appkhambenh.ui.data.remote.entity.UpdateAllocationResponse
import com.example.appkhambenh.ui.data.remote.entity.UpdateDiagnoseMedicalHistoryResponse
import com.example.appkhambenh.ui.data.remote.request.AddMedicalHistoryRequest
import com.example.appkhambenh.ui.data.remote.request.UpdateAllocationRequest
import com.example.appkhambenh.ui.data.remote.request.UpdateDiagnoseMedicalHistoryRequest
import kotlinx.coroutines.flow.Flow

interface MedicalHistoryRepository {
    suspend fun getListMedicalHistory(patientId: Int? = null): Flow<ApiState<MedicalHistoryResponse>>

    suspend fun getMedicalHistory(patientId: Int): Flow<ApiState<GetMedicalHistoryResponse>>

    suspend fun addMedicalHistory(addMedicalHistoryRequest: AddMedicalHistoryRequest): Flow<ApiState<AddMedicalHistoryResponse>>

    suspend fun updateDiagnoseMedicalHistory(
        medicalHistoryId: Int,
        updateDiagnoseMedicalHistoryRequest: UpdateDiagnoseMedicalHistoryRequest,
    ): Flow<ApiState<UpdateDiagnoseMedicalHistoryResponse>>

    suspend fun updateAllocation(
        medicalHistoryId: Int,
        updateAllocationRequest: UpdateAllocationRequest
    ): Flow<ApiState<UpdateAllocationResponse>>

    suspend fun hospitalDischarge(
        medicalHistoryId: Int
    ): Flow<ApiState<HospitalDischargeResponse>>
}