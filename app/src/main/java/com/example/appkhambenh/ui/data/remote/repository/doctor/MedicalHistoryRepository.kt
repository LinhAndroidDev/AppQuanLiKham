package com.example.appkhambenh.ui.data.remote.repository.doctor

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
    suspend fun getListMedicalHistory(patientId: Int? = null): Flow<MedicalHistoryResponse>

    suspend fun getMedicalHistory(patientId: Int): Flow<GetMedicalHistoryResponse>

    suspend fun addMedicalHistory(addMedicalHistoryRequest: AddMedicalHistoryRequest): Flow<AddMedicalHistoryResponse>

    suspend fun updateDiagnoseMedicalHistory(
        medicalHistoryId: Int,
        updateDiagnoseMedicalHistoryRequest: UpdateDiagnoseMedicalHistoryRequest,
    ): Flow<UpdateDiagnoseMedicalHistoryResponse>

    suspend fun updateAllocation(
        medicalHistoryId: Int,
        updateAllocationRequest: UpdateAllocationRequest
    ): Flow<UpdateAllocationResponse>

    suspend fun hospitalDischarge(
        medicalHistoryId: Int
    ): Flow<HospitalDischargeResponse>
}