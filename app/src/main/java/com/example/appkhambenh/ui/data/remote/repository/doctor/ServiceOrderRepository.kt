package com.example.appkhambenh.ui.data.remote.repository.doctor

import com.example.appkhambenh.ui.data.remote.DoctorService
import com.example.appkhambenh.ui.data.remote.request.AddServiceRequest
import com.example.appkhambenh.ui.data.remote.request.BloodTestRequest
import com.example.appkhambenh.ui.data.remote.request.DiagnoseRequest
import com.example.appkhambenh.ui.data.remote.request.UpdateChartRequest
import com.example.appkhambenh.ui.data.remote.request.UpdateInfoClinicalExaminationRequest
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class ServiceOrderRepository @Inject constructor(private val doctorService: DoctorService) {
    suspend fun getServiceOrder(id: Int) = doctorService.getServiceOrder(id)

    suspend fun payService(idService: Int) = doctorService.payService(idService)

    suspend fun addService(addServiceRequest: AddServiceRequest) =
        doctorService.addService(addServiceRequest)

    suspend fun updateChart(id: Int, updateChartRequest: UpdateChartRequest) =
        doctorService.updateChart(id, updateChartRequest)

    suspend fun updateClinicalExamination(
        serviceMedicalHistoryId: Int,
        updateInfoClinicalExaminationRequest: UpdateInfoClinicalExaminationRequest,
    ) = doctorService.updateClinicalExamination(
        serviceMedicalHistoryId,
        updateInfoClinicalExaminationRequest
    )

    suspend fun updateBloodTest(
        serviceMedicalHistoryId: Int,
        updateBloodTestRequest: BloodTestRequest
    ) = doctorService.updateBloodTest(serviceMedicalHistoryId, updateBloodTestRequest)

    suspend fun updateDiagnose(
        serviceMedicalHistoryId: Int,
        diagnoseRequest: DiagnoseRequest
    ) = doctorService.updateDiagnose(serviceMedicalHistoryId, diagnoseRequest)
}