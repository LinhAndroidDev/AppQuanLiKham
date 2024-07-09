package com.example.appkhambenh.ui.ui.doctor.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.appkhambenh.ui.base.BaseViewModel
import com.example.appkhambenh.ui.data.remote.repository.doctor.ServiceOrderRepository
import com.example.appkhambenh.ui.data.remote.request.AddServiceRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FragmentListOfServiceViewModel @Inject constructor(private val serviceOrderRepository: ServiceOrderRepository) :
    BaseViewModel() {

    fun payService(id: Int) = viewModelScope.launch {
        loading.postValue(true)
        serviceOrderRepository.payService(id).let { response ->
            loading.postValue(false)
            if(response.isSuccessful) {
                if(response.body()?.serviceMedicalHistoryId != 0) {
                    errorApiLiveData.postValue("Bạn đã thanh toán dịch vụ thành công")
                }
            } else {
                errorApiLiveData.postValue(response.message())
            }
        }
    }

    fun addService(addServiceRequest: AddServiceRequest) = viewModelScope.launch {
        loading.postValue(true)
        serviceOrderRepository.addService(addServiceRequest).let { response ->
            loading.postValue(false)
            if(response.isSuccessful) {
                if(response.body()?.serviceMedicalHistoryId != 0) {
                    errorApiLiveData.postValue("Bạn đã thêm thành công dịch vụ")
                }
            } else {
                errorApiLiveData.postValue(response.message())
            }
        }
    }
}