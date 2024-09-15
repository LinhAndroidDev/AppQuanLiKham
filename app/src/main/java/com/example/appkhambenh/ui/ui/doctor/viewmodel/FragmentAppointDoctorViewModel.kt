package com.example.appkhambenh.ui.ui.doctor.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.appkhambenh.ui.base.BaseViewModel
import com.example.appkhambenh.ui.data.remote.base.doOnSuccess
import com.example.appkhambenh.ui.data.remote.entity.StatePatient
import com.example.appkhambenh.ui.data.remote.repository.doctor.AppointmentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FragmentAppointDoctorViewModel @Inject constructor(private val repository: AppointmentRepository) :
    BaseViewModel() {
    private val _appointments: MutableStateFlow<ArrayList<StatePatient>?> = MutableStateFlow(null)
    val appointments = _appointments.asStateFlow()

    fun getListAppointment(time: String? = null) = viewModelScope.launch {
        repository.getListAppointment(time)
            .handleCallApi()
            .doOnSuccess {
                _appointments.value = it.data
            }.collect()
    }

    fun confirmAppoint(id: Int) = viewModelScope.launch {
        repository.confirmAppoint(id)
            .handleCallApi()
            .doOnSuccess {
                if (it.id != 0) {
                    getListAppointment()
                    errorApiLiveData.postValue("Bạn đã xác nhận lịch hẹn thành công")
                } else {
                    errorApiLiveData.postValue("Fail Data")
                }
            }.collect()
    }
}