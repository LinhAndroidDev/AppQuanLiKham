package com.example.appkhambenh.ui.ui.user.manage_appointment

import androidx.lifecycle.viewModelScope
import com.example.appkhambenh.ui.base.BaseViewModel
import com.example.appkhambenh.ui.data.remote.entity.DetailAppointment
import com.example.appkhambenh.ui.data.remote.repository.user.AppointmentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExaminationScheduleViewModel @Inject constructor(private val repository: AppointmentRepository) :
    BaseViewModel() {
        val detailAppointments = MutableStateFlow<ArrayList<DetailAppointment>?>(null)

    fun getListBooking() = viewModelScope.launch {
        loading.postValue(true)
        repository.getListBooking().let {
            try {
                loading.postValue(false)
                if(it.isSuccessful) {
                    detailAppointments.value = it.body()?.data?.content
                }
            } catch (e: Exception) {
                loading.postValue(false)
                errorApiLiveData.postValue(e.message)
            }
        }
    }
}