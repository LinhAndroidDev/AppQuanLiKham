package com.example.appkhambenh.ui.ui.doctor.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.appkhambenh.ui.base.BaseViewModel
import com.example.appkhambenh.ui.data.remote.entity.ServiceOrderModel
import com.example.appkhambenh.ui.data.remote.repository.doctor.MedicalHistoryRepository
import com.example.appkhambenh.ui.data.remote.repository.doctor.ServiceOrderRepository
import com.example.appkhambenh.ui.data.remote.request.AddServiceRequest
import com.example.appkhambenh.ui.data.remote.request.UpdateChartRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FragmentTreatmentManagementViewModel @Inject constructor(
    private val medicalHistoryRepository: MedicalHistoryRepository,
    private val serviceOrderRepository: ServiceOrderRepository
) : BaseViewModel() {
    val services: MutableStateFlow<ArrayList<ServiceOrderModel>?> = MutableStateFlow(null)

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

    fun updateChart(id: Int, updateChartRequest: UpdateChartRequest, medicalHistoryId: Int) = viewModelScope.launch {
        loading.postValue(true)
        serviceOrderRepository.updateChart(id, updateChartRequest).let { response ->
            if(response.isSuccessful) {
                if(response.body()?.serviceMedicalHistoryId != 0) {
                    getServiceOrder(medicalHistoryId)
                    errorApiLiveData.postValue("Bạn đã cập nhật bệnh sử tiền sử thành công")
                }
            } else {
                errorApiLiveData.postValue(response.message())
            }
        }
    }
}