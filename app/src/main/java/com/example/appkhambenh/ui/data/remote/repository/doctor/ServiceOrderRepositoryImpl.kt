package com.example.appkhambenh.ui.data.remote.repository.doctor

import com.example.appkhambenh.ui.data.remote.DoctorService
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
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@ViewModelScoped
class ServiceOrderRepositoryImpl @Inject constructor(private val doctorService: DoctorService) : ServiceOrderRepository {
    override suspend fun getServiceOrder(id: Int): Flow<ServiceOrderResponse> = flow {
        emit(doctorService.getServiceOrder(id))
    }

    override suspend fun payService(idService: Int): Flow<PayServiceResponse> = flow {
        emit(doctorService.payService(idService))
    }

    override suspend fun addService(addServiceRequest: AddServiceRequest): Flow<AddServiceResponse> = flow {
        emit(doctorService.addService(addServiceRequest))
    }

    override suspend fun updateChart(
        id: Int,
        updateChartRequest: UpdateChartRequest,
    ): Flow<UpdateChartResponse> = flow {
        emit(doctorService.updateChart(id, updateChartRequest))
    }

    override suspend fun updateClinicalExamination(
        serviceMedicalHistoryId: Int,
        updateInfoClinicalExaminationRequest: UpdateInfoClinicalExaminationRequest,
    ): Flow<UpdateInfoClinicalExaminationResponse> = flow {
        emit(doctorService.updateClinicalExamination(serviceMedicalHistoryId, updateInfoClinicalExaminationRequest))
    }

    override suspend fun updateBloodTest(
        serviceMedicalHistoryId: Int,
        updateBloodTestRequest: BloodTestRequest,
    ): Flow<UpdateInfoClinicalExaminationResponse> = flow {
        emit(doctorService.updateBloodTest(serviceMedicalHistoryId, updateBloodTestRequest))
    }

    override suspend fun updateDiagnose(
        serviceMedicalHistoryId: Int,
        diagnoseRequest: DiagnoseRequest,
    ): Flow<DiagnoseResponse> = flow {
        emit(doctorService.updateDiagnose(serviceMedicalHistoryId, diagnoseRequest))
    }
}