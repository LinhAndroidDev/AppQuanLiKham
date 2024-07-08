package com.example.appkhambenh.ui.ui.doctor.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.appkhambenh.ui.base.BaseViewModel
import com.example.appkhambenh.ui.data.remote.entity.StatePatient
import com.example.appkhambenh.ui.data.remote.repository.doctor.AppointmentRepository
import com.example.appkhambenh.ui.data.remote.request.ConfirmAppointRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FragmentAppointDoctorViewModel @Inject constructor(private val repository: AppointmentRepository) : BaseViewModel() {
    val appointments: MutableStateFlow<ArrayList<StatePatient>?> = MutableStateFlow(null)

    fun getListAppointment() = viewModelScope.launch {
        loading.postValue(true)
        repository.getListAppointment().let { response ->
            loading.postValue(false)
            if(response.isSuccessful) {
                appointments.value = response.body()?.data
            } else {
                errorApiLiveData.postValue(response.message())
            }
        }
    }

    fun confirmAppoint(id: Int) = viewModelScope.launch {
        loading.postValue(true)
        repository.confirmAppoint(id).let { response ->
            if(response.isSuccessful) {
                if(response.body()?.id != 0) {
                    getListAppointment()
                    errorApiLiveData.postValue("Bạn đã xác nhận lịch hẹn thành công")
                }
            } else {
                errorApiLiveData.postValue(response.message())
            }
        }
    }
}