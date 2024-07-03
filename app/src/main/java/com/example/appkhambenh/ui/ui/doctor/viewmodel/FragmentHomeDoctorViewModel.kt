package com.example.appkhambenh.ui.ui.doctor.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.appkhambenh.ui.base.BaseViewModel
import com.example.appkhambenh.ui.data.remote.model.Quantity
import com.example.appkhambenh.ui.data.remote.repository.doctor.AdminDoctorRepository
import com.example.appkhambenh.ui.data.remote.repository.doctor.AppointmentRepository
import com.example.appkhambenh.ui.data.remote.repository.doctor.MedicalHistoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FragmentHomeDoctorViewModel @Inject constructor(
    private val adminRepository: AdminDoctorRepository,
    private val appointmentRepository: AppointmentRepository,
    private val medicalHistoryRepository: MedicalHistoryRepository
) : BaseViewModel() {
    val quantity = MutableStateFlow(Quantity(0, 0 ,0))

    fun getQuantity() = viewModelScope.launch {
        loading.postValue(true)
        async {
            quantity.value = Quantity(
                adminRepository.getListPatient().body()?.data?.size ?: 0,
                appointmentRepository.getListAppointment().body()?.data?.size ?: 0,
                medicalHistoryRepository.getListMedicalHistory().body()?.data?.size ?: 0
            )
        }.await()
        loading.postValue(false)
    }
}