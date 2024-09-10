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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FragmentTreatmentManagementViewModel @Inject constructor(
    private val medicalHistoryRepository: MedicalHistoryRepository,
    private val serviceOrderRepository: ServiceOrderRepository,
    private val patientRepository: PatientRepository
) : BaseViewModel() {
    val services: MutableStateFlow<ArrayList<ServiceOrderModel>?> = MutableStateFlow(null)
    val valueVitalChart: MutableStateFlow<ArrayList<VitalChartModel>?> = MutableStateFlow(null)
    val medicalHistorys: MutableStateFlow<GetMedicalHistoryResponse.Data?> =
        MutableStateFlow(null)
    val patient: MutableStateFlow<PatientModel?> = MutableStateFlow(null)

    fun getServiceOrder(medicalHistoryId: Int) = viewModelScope.launch {
        loading.postValue(true)
        serviceOrderRepository.getServiceOrder(medicalHistoryId).let { response ->
            loading.postValue(false)
            if(response.isSuccessful) {
                services.value = response.body()?.data
            } else {
                errorApiLiveData.postValue(response.message())
            }
        }
    }

    suspend fun getPatient(patientId: Int) {
        loading.postValue(true)
        try {
            patientRepository.getPatient(patientId = patientId).let { response ->
                loading.postValue(false)
                if (response.isSuccessful) {
                    patient.value = response.body()?.data
                }
            }
        } catch (e: Exception) {
            loading.postValue(false)
            errorApiLiveData.postValue(e.message)
        }
    }

    fun getMedicalHistory(patientId: Int, medicalHistoryId: Int) = viewModelScope.launch {
        loading.postValue(true)
        medicalHistoryRepository.getMedicalHistory(patientId).let { response ->
            loading.postValue(false)
            if(response.isSuccessful) {
                medicalHistorys.value = response.body()?.data
                getServiceOrder(medicalHistoryId)
            } else {
                errorApiLiveData.postValue(response.message())
            }
        }
    }

    fun payService(id: Int, medicalHistoryId: Int) = viewModelScope.launch {
        loading.postValue(true)
        serviceOrderRepository.payService(id).let { response ->
            loading.postValue(false)
            if(response.isSuccessful) {
                if(response.body()?.serviceMedicalHistoryId != 0) {
                    getServiceOrder(medicalHistoryId)
                    errorApiLiveData.postValue("Bạn đã thanh toán dịch vụ thành công")
                }
            } else {
                errorApiLiveData.postValue(response.message())
            }
        }
    }

    fun addService(addServiceRequest: AddServiceRequest, medicalHistoryId: Int) = viewModelScope.launch {
        loading.postValue(true)
        serviceOrderRepository.addService(addServiceRequest).let { response ->
            loading.postValue(false)
            if(response.isSuccessful) {
                if(response.body()?.serviceMedicalHistoryId != 0) {
                    getServiceOrder(medicalHistoryId)
                    errorApiLiveData.postValue("Bạn đã thêm thành công dịch vụ")
                }
            } else {
                errorApiLiveData.postValue(response.message())
            }
        }
    }

    fun updateChart(id: Int, updateChartRequest: UpdateChartRequest, patientId: Int) = viewModelScope.launch {
        loading.postValue(true)
        serviceOrderRepository.updateChart(id, updateChartRequest).let { response ->
            if(response.isSuccessful) {
                if(response.body()?.serviceMedicalHistoryId != 0) {
                    getValueVitalChart(patientId)
                    errorApiLiveData.postValue("Bạn đã cập nhật bệnh sử tiền sử thành công")
                }
            } else {
                errorApiLiveData.postValue(response.message())
            }
        }
    }

    fun getValueVitalChart(patientId: Int) = viewModelScope.launch {
        loading.postValue(true)
        patientRepository.getValueVitalChart(patientId).let { response ->
            loading.postValue(false)
            if(response.isSuccessful) {
                valueVitalChart.value = response.body()?.data
            } else {
                errorApiLiveData.postValue(response.message())
            }
        }
    }

    fun updateClinicalExamination(
        serviceMedicalHistoryId: Int,
        updateInfoClinicalExaminationRequest: UpdateInfoClinicalExaminationRequest,
        medicalHistoryId: Int
    ) = viewModelScope.launch {
        loading.postValue(true)
        serviceOrderRepository.updateClinicalExamination(serviceMedicalHistoryId, updateInfoClinicalExaminationRequest).let { response ->
            if(response.isSuccessful) {
                if(response.body()?.serviceMedicalHistoryId == serviceMedicalHistoryId) {
                    getServiceOrder(medicalHistoryId)
                    errorApiLiveData.postValue("Bạn đã cập nhật dịch vụ thành công")
                }
            } else {
                errorApiLiveData.postValue(response.message())
            }
        }
    }

    fun updateBloodTest(
        serviceMedicalHistoryId: Int,
        updateBloodTestRequest: BloodTestRequest,
        medicalHistoryId: Int
    ) = viewModelScope.launch {
        loading.postValue(true)
        serviceOrderRepository.updateBloodTest(serviceMedicalHistoryId, updateBloodTestRequest).let { response ->
            if(response.isSuccessful) {
                if(response.body()?.serviceMedicalHistoryId == serviceMedicalHistoryId) {
                    getServiceOrder(medicalHistoryId)
                    errorApiLiveData.postValue("Bạn đã cập nhật dịch vụ thành công")
                }
            } else {
                errorApiLiveData.postValue(response.message())
            }
        }
    }

    fun updateDiagnose(
        serviceMedicalHistoryId: Int,
        diagnoseRequest: DiagnoseRequest,
        medicalHistoryId: Int
    ) = viewModelScope.launch {
        loading.postValue(true)
        serviceOrderRepository.updateDiagnose(serviceMedicalHistoryId, diagnoseRequest).let { response ->
            if(response.isSuccessful) {
                if(response.body()?.serviceMedicalHistoryId == serviceMedicalHistoryId) {
                    getServiceOrder(medicalHistoryId)
                    errorApiLiveData.postValue("Bạn đã cập nhật dịch vụ thành công")
                }
            } else {
                errorApiLiveData.postValue(response.message())
            }
        }
    }
}