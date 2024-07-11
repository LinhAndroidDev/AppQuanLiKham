package com.example.appkhambenh.ui.ui.doctor.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.appkhambenh.ui.base.BaseViewModel
import com.example.appkhambenh.ui.data.remote.model.Quantity
import com.example.appkhambenh.ui.data.remote.repository.doctor.AppointmentRepository
import com.example.appkhambenh.ui.data.remote.repository.doctor.MedicalHistoryRepository
import com.example.appkhambenh.ui.data.remote.repository.doctor.PatientRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.ConnectException
import javax.inject.Inject

@HiltViewModel
class FragmentHomeDoctorViewModel @Inject constructor(
    private val adminRepository: PatientRepository,
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
        } catch (e: ConnectException) {
            try {
                loading.postValue(false)
                errorApiLiveData.postValue("Không thể kết nối đến server")
            } catch (e: Exception) {
                errorApiLiveData.postValue(e.message)
            }
        } catch (e: IOException) {
            loading.postValue(false)
            errorApiLiveData.postValue("Lỗi mạng, vui lòng thử lại")
        } catch (e: Exception) {
            loading.postValue(false)
            errorApiLiveData.postValue("Đã xảy ra lỗi vui lòng thử lại")
        }
    }
}