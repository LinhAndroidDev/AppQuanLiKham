package com.example.appkhambenh.ui.ui.doctor.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.appkhambenh.ui.base.BaseViewModel
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
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
        loading.postValue(true)
        serviceOrderRepository.getServiceOrder(medicalHistoryId)
            .flowOn(Dispatchers.IO)
            .catch { e ->
                loading.postValue(false)
                errorApiLiveData.postValue(e.message)
            }
            .collect {
                loading.postValue(false)
                _services.value = it.data
            }
    }

    suspend fun getPatient(patientId: Int) {
        loading.postValue(true)
        patientRepository.getPatient(patientId = patientId)
            .flowOn(Dispatchers.IO)
            .catch { e ->
                errorApiLiveData.postValue(e.message)
            }
            .collect {
                _patient.value = it.data
            }
    }

    fun getMedicalHistory(patientId: Int, medicalHistoryId: Int) = viewModelScope.launch {
        loading.postValue(true)
        medicalHistoryRepository.getMedicalHistory(patientId)
            .flowOn(Dispatchers.IO)
            .catch { e ->
                loading.postValue(false)
                errorApiLiveData.postValue(e.message)
            }
            .collect {
                loading.postValue(false)
                _medicalHistorys.value = it.data
                getServiceOrder(medicalHistoryId)
            }
    }

    fun payService(id: Int, medicalHistoryId: Int) = viewModelScope.launch {
        loading.postValue(true)
        serviceOrderRepository.payService(id)
            .flowOn(Dispatchers.IO)
            .catch { e ->
                loading.postValue(false)
                errorApiLiveData.postValue(e.message)
            }
            .collect {
                loading.postValue(false)
                if (it.serviceMedicalHistoryId != 0) {
                    getServiceOrder(medicalHistoryId)
                    errorApiLiveData.postValue("Bạn đã thanh toán dịch vụ thành công")
                }
            }
    }

    fun addService(addServiceRequest: AddServiceRequest, medicalHistoryId: Int) =
        viewModelScope.launch {
            loading.postValue(true)
            serviceOrderRepository.addService(addServiceRequest)
                .flowOn(Dispatchers.IO)
                .catch { e ->
                    loading.postValue(false)
                    errorApiLiveData.postValue(e.message)
                }
                .collect {
                    loading.postValue(false)
                    if (it.serviceMedicalHistoryId != 0) {
                        getServiceOrder(medicalHistoryId)
                        errorApiLiveData.postValue("Bạn đã thêm thành công dịch vụ")
                    }
                }
        }

    fun updateChart(id: Int, updateChartRequest: UpdateChartRequest, patientId: Int) =
        viewModelScope.launch {
            loading.postValue(true)
            serviceOrderRepository.updateChart(id, updateChartRequest)
                .flowOn(Dispatchers.IO)
                .catch { e ->
                    loading.postValue(false)
                    errorApiLiveData.postValue(e.message)
                }
                .collect {
                    loading.postValue(false)
                    if (it.serviceMedicalHistoryId != 0) {
                        getValueVitalChart(patientId)
                        errorApiLiveData.postValue("Bạn đã cập nhật bệnh sử tiền sử thành công")
                    }
                }
        }

    fun getValueVitalChart(patientId: Int) = viewModelScope.launch {
        loading.postValue(true)
        patientRepository.getValueVitalChart(patientId)
            .flowOn(Dispatchers.IO)
            .catch { e ->
                loading.postValue(false)
                errorApiLiveData.postValue(e.message)
            }
            .collect {
                loading.postValue(false)
                _valueVitalChart.value = it.data
            }
    }

    fun updateClinicalExamination(
        serviceMedicalHistoryId: Int,
        updateInfoClinicalExaminationRequest: UpdateInfoClinicalExaminationRequest,
        medicalHistoryId: Int,
    ) = viewModelScope.launch {
        loading.postValue(true)
        serviceOrderRepository.updateClinicalExamination(
            serviceMedicalHistoryId,
            updateInfoClinicalExaminationRequest
        )
            .flowOn(Dispatchers.IO)
            .catch { e ->
                loading.postValue(false)
                errorApiLiveData.postValue(e.message)
            }
            .collect {
                loading.postValue(false)
                if (it.serviceMedicalHistoryId == serviceMedicalHistoryId) {
                    getServiceOrder(medicalHistoryId)
                    errorApiLiveData.postValue("Bạn đã cập nhật dịch vụ thành công")
                }
            }
    }

    fun updateBloodTest(
        serviceMedicalHistoryId: Int,
        updateBloodTestRequest: BloodTestRequest,
        medicalHistoryId: Int,
    ) = viewModelScope.launch {
        loading.postValue(true)
        serviceOrderRepository.updateBloodTest(serviceMedicalHistoryId, updateBloodTestRequest)
            .flowOn(Dispatchers.IO)
            .catch { e ->
                loading.postValue(false)
                errorApiLiveData.postValue(e.message)
            }
            .collect {
                loading.postValue(false)
                if (it.serviceMedicalHistoryId == serviceMedicalHistoryId) {
                    getServiceOrder(medicalHistoryId)
                    errorApiLiveData.postValue("Bạn đã cập nhật dịch vụ thành công")
                }
            }
    }

    fun updateDiagnose(
        serviceMedicalHistoryId: Int,
        diagnoseRequest: DiagnoseRequest,
        medicalHistoryId: Int,
    ) = viewModelScope.launch {
        loading.postValue(true)
        serviceOrderRepository.updateDiagnose(serviceMedicalHistoryId, diagnoseRequest)
            .flowOn(Dispatchers.IO)
            .catch { e ->
                loading.postValue(false)
                errorApiLiveData.postValue(e.message)
            }
            .collect {
                loading.postValue(false)
                if (it.serviceMedicalHistoryId == serviceMedicalHistoryId) {
                    getServiceOrder(medicalHistoryId)
                    errorApiLiveData.postValue("Bạn đã cập nhật dịch vụ thành công")
                }
            }
    }
}