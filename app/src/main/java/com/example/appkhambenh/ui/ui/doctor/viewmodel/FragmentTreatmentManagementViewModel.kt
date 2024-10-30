package com.example.appkhambenh.ui.ui.doctor.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.appkhambenh.ui.base.BaseViewModel
import com.example.appkhambenh.ui.data.remote.base.doOnSuccess
import com.example.appkhambenh.ui.data.remote.entity.GetMedicalHistoryResponse
import com.example.appkhambenh.ui.data.remote.entity.PatientModel
import com.example.appkhambenh.ui.data.remote.entity.ServiceOrderModel
import com.example.appkhambenh.ui.data.remote.entity.VitalChartModel
import com.example.appkhambenh.ui.data.remote.repository.doctor.MedicalHistoryRepository
import com.example.appkhambenh.ui.data.remote.repository.doctor.PatientRepository
import com.example.appkhambenh.ui.data.remote.repository.doctor.ServiceOrderRepository
import com.example.appkhambenh.ui.data.remote.request.AddServiceRequest
import com.example.appkhambenh.ui.data.remote.request.BloodTestRequest
import com.example.appkhambenh.ui.data.remote.request.DiagnoseRequest
import com.example.appkhambenh.ui.data.remote.request.UpdateChartRequest
import com.example.appkhambenh.ui.data.remote.request.UpdateInfoClinicalExaminationRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FragmentTreatmentManagementViewModel @Inject constructor(
    private val medicalHistoryRepository: MedicalHistoryRepository,
    private val serviceOrderRepository: ServiceOrderRepository,
    private val patientRepository: PatientRepository,
) : BaseViewModel() {
    private val _services: MutableStateFlow<ArrayList<ServiceOrderModel>?> = MutableStateFlow(null)
    val services = _services.asStateFlow()
    private val _valueVitalChart: MutableStateFlow<ArrayList<VitalChartModel>?> =
        MutableStateFlow(null)
    val valueVitalChart = _valueVitalChart.asStateFlow()
    private val _medicalHistorys: MutableStateFlow<GetMedicalHistoryResponse.Data?> =
        MutableStateFlow(null)
    val medicalHistorys = _medicalHistorys.asStateFlow()
    private val _patient: MutableStateFlow<PatientModel?> = MutableStateFlow(null)
    val patient = _patient.asStateFlow()

    private fun getServiceOrder(medicalHistoryId: Int) = viewModelScope.launch {
        serviceOrderRepository.getServiceOrder(medicalHistoryId)
            .handleCallApi()
            .doOnSuccess {
                _services.value = it.data
            }.collect()
    }

    suspend fun getPatient(patientId: Int) {
        patientRepository.getPatient(patientId = patientId)
            .handleCallApi()
            .doOnSuccess {
                _patient.value = it.data
            }.collect()
    }

    fun getMedicalHistory(patientId: Int, medicalHistoryId: Int) = viewModelScope.launch {
        medicalHistoryRepository.getMedicalHistory(patientId)
            .handleCallApi()
            .doOnSuccess {
                _medicalHistorys.value = it.data
                getServiceOrder(medicalHistoryId)
            }.collect()
    }

    fun payService(id: Int, medicalHistoryId: Int) = viewModelScope.launch {
        serviceOrderRepository.payService(id)
            .handleCallApi()
            .doOnSuccess {
                if (it.serviceMedicalHistoryId != 0) {
                    getServiceOrder(medicalHistoryId)
                    errorApiLiveData.postValue("Bạn đã thanh toán dịch vụ thành công")
                }
            }.collect()
    }

    fun addService(addServiceRequest: AddServiceRequest, medicalHistoryId: Int) =
        viewModelScope.launch {
            serviceOrderRepository.addService(addServiceRequest)
                .handleCallApi()
                .doOnSuccess {
                    if (it.serviceMedicalHistoryId != 0) {
                        getServiceOrder(medicalHistoryId)
                        errorApiLiveData.postValue("Bạn đã thêm thành công dịch vụ")
                    }
                }.collect()
        }

    fun updateChart(id: Int, updateChartRequest: UpdateChartRequest, patientId: Int) =
        viewModelScope.launch {
            serviceOrderRepository.updateChart(id, updateChartRequest)
                .handleCallApi()
                .doOnSuccess {
                    if (it.serviceMedicalHistoryId != 0) {
                        getValueVitalChart(patientId)
                        errorApiLiveData.postValue("Bạn đã cập nhật bệnh sử tiền sử thành công")
                    }
                }.collect()
        }

    fun getValueVitalChart(patientId: Int) = viewModelScope.launch {
        patientRepository.getValueVitalChart(patientId)
            .handleCallApi()
            .doOnSuccess {
                _valueVitalChart.value = it.data
            }.collect()
    }

    fun updateClinicalExamination(
        serviceMedicalHistoryId: Int,
        updateInfoClinicalExaminationRequest: UpdateInfoClinicalExaminationRequest,
        medicalHistoryId: Int,
    ) = viewModelScope.launch {
        serviceOrderRepository.updateClinicalExamination(
            serviceMedicalHistoryId,
            updateInfoClinicalExaminationRequest
        )
            .handleCallApi()
            .doOnSuccess {
                if (it.serviceMedicalHistoryId == serviceMedicalHistoryId) {
                    getServiceOrder(medicalHistoryId)
                    errorApiLiveData.postValue("Bạn đã cập nhật dịch vụ thành công")
                }
            }.collect()
    }

    fun updateBloodTest(
        serviceMedicalHistoryId: Int,
        updateBloodTestRequest: BloodTestRequest,
        medicalHistoryId: Int,
    ) = viewModelScope.launch {
        serviceOrderRepository.updateBloodTest(serviceMedicalHistoryId, updateBloodTestRequest)
            .handleCallApi()
            .doOnSuccess {
                if (it.serviceMedicalHistoryId == serviceMedicalHistoryId) {
                    getServiceOrder(medicalHistoryId)
                    errorApiLiveData.postValue("Bạn đã cập nhật dịch vụ thành công")
                }
            }.collect()
    }

    fun updateDiagnose(
        serviceMedicalHistoryId: Int,
        diagnoseRequest: DiagnoseRequest,
        medicalHistoryId: Int,
    ) = viewModelScope.launch {
        serviceOrderRepository.updateDiagnose(serviceMedicalHistoryId, diagnoseRequest)
            .handleCallApi()
            .doOnSuccess {
                if (it.serviceMedicalHistoryId == serviceMedicalHistoryId) {
                    getServiceOrder(medicalHistoryId)
                    errorApiLiveData.postValue("Bạn đã cập nhật dịch vụ thành công")
                }
            }.collect()
    }
}