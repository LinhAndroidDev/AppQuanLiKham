package com.example.appkhambenh.ui.data.remote.repository.doctor

import com.example.appkhambenh.ui.data.remote.entity.AddServiceResponse
import com.example.appkhambenh.ui.data.remote.entity.DiagnoseResponse
import com.example.appkhambenh.ui.data.remote.entity.PayServiceResponse
import com.example.appkhambenh.ui.data.remote.entity.ServiceOrderResponse
import com.example.appkhambenh.ui.data.remote.entity.UpdateChartResponse
import com.example.appkhambenh.ui.data.remote.entity.UpdateInfoClinicalExaminationResponse
import com.example.appkhambenh.ui.data.remote.request.AddServiceRequest
import com.example.appkhambenh.ui.data.remote.request.BloodTestRequest
import com.example.appkhambenh.ui.data.remote.request.DiagnoseRequest
import com.example.appkhambenh.ui.data.remote.request.UpdateChartRequest
import com.example.appkhambenh.ui.data.remote.request.UpdateInfoClinicalExaminationRequest
import kotlinx.coroutines.flow.Flow

interface ServiceOrderRepository {
    suspend fun getServiceOrder(id: Int): Flow<ServiceOrderResponse>

    suspend fun payService(idService: Int): Flow<PayServiceResponse>

    suspend fun addService(addServiceRequest: AddServiceRequest): Flow<AddServiceResponse>

    suspend fun updateChart(id: Int, updateChartRequest: UpdateChartRequest): Flow<UpdateChartResponse>

    suspend fun updateClinicalExamination(
        serviceMedicalHistoryId: Int,
        updateInfoClinicalExaminationRequest: UpdateInfoClinicalExaminationRequest,
    ): Flow<UpdateInfoClinicalExaminationResponse>

    suspend fun updateBloodTest(
        serviceMedicalHistoryId: Int,
        updateBloodTestRequest: BloodTestRequest
    ): Flow<UpdateInfoClinicalExaminationResponse>

    suspend fun updateDiagnose(
        serviceMedicalHistoryId: Int,
        diagnoseRequest: DiagnoseRequest
    ): Flow<DiagnoseResponse>
}