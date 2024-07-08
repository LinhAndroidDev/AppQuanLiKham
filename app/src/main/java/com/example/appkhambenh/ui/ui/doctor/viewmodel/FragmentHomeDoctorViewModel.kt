package com.example.appkhambenh.ui.ui.doctor.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.appkhambenh.ui.base.BaseViewModel
import com.example.appkhambenh.ui.data.remote.model.Quantity
import com.example.appkhambenh.ui.data.remote.repository.doctor.AdminDoctorRepository
import com.example.appkhambenh.ui.data.remote.repository.doctor.AppointmentRepository
import com.example.appkhambenh.ui.data.remote.repository.doctor.MedicalHistoryRepository
import com.example.appkhambenh.ui.utils.TokenManager
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
        try {
            async {
                val patient = adminRepository.getListPatient()
                val appoint = appointmentRepository.getListAppointment()
                val medical = medicalHistoryRepository.getListMedicalHistory()
                quantity.value = Quantity(
                    patient.body()?.data?.size ?: 0,
                    appoint.body()?.data?.size ?: 0,
                    medical.body()?.data?.size ?: 0
                )
            }.await()
            loading.postValue(false)
        } catch (e: Exception) {
            loading.postValue(false)
            errorApiLiveData.postValue(e.message)
        }
    }
}