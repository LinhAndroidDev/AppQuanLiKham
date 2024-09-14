package com.example.appkhambenh.ui.ui.doctor.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.appkhambenh.ui.base.BaseViewModel
import com.example.appkhambenh.ui.data.remote.entity.StatePatient
import com.example.appkhambenh.ui.data.remote.repository.doctor.AppointmentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FragmentAppointDoctorViewModel @Inject constructor(private val repository: AppointmentRepository) :
    BaseViewModel() {
    private val _appointments: MutableStateFlow<ArrayList<StatePatient>?> = MutableStateFlow(null)
    val appointments = _appointments.asStateFlow()

    fun getListAppointment(time: String? = null) = viewModelScope.launch {
        loading.postValue(true)
        repository.getListAppointment(time)
            .flowOn(Dispatchers.IO)
            .catch { e ->
                loading.postValue(false)
                errorApiLiveData.postValue(e.message)
            }
            .collect {
                loading.postValue(false)
                _appointments.value = it.data
            }
    }

    fun confirmAppoint(id: Int) = viewModelScope.launch {
        loading.postValue(true)
        repository.confirmAppoint(id)
            .flowOn(Dispatchers.IO)
            .catch { e ->
                loading.postValue(false)
                errorApiLiveData.postValue(e.message)
            }
            .collect {
                loading.postValue(false)
                if (it.id != 0) {
                    getListAppointment()
                    errorApiLiveData.postValue("Bạn đã xác nhận lịch hẹn thành công")
                } else {
                    errorApiLiveData.postValue("Fail Data")
                }
            }
    }
}