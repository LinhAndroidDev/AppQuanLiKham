package com.example.appkhambenh.ui.ui.doctor.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.appkhambenh.ui.base.BaseViewModel
import com.example.appkhambenh.ui.data.remote.model.Quantity
import com.example.appkhambenh.ui.data.remote.repository.doctor.AccountRepository
import com.example.appkhambenh.ui.data.remote.repository.doctor.AppointmentRepository
import com.example.appkhambenh.ui.data.remote.repository.doctor.MedicalHistoryRepository
import com.example.appkhambenh.ui.data.remote.repository.doctor.PatientRepository
import com.example.appkhambenh.ui.utils.convertApiStateTo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FragmentHomeDoctorViewModel @Inject constructor(
    private val adminRepository: PatientRepository,
    private val appointmentRepository: AppointmentRepository,
    private val medicalHistoryRepository: MedicalHistoryRepository,
    private val accountRepository: AccountRepository,
) : BaseViewModel() {
    private val _quantity = MutableStateFlow(Quantity(0, 0, 0, 0))
    val quantity = _quantity.asStateFlow()

    fun getQuantity() = viewModelScope.launch {
        withContext(Dispatchers.Main) {
            loading.value = true
        }
        combine(
            adminRepository.getListPatient(),
            appointmentRepository.getListAppointment(),
            medicalHistoryRepository.getListMedicalHistory(),
            accountRepository.getAccount()
        ) { result1, result2, result3, result4 ->
            withContext(Dispatchers.Main) {
                loading.value = false
                val adminQuantity = result1.convertApiStateTo()?.data?.size ?: 0
                val appointmentQuantity = result2.convertApiStateTo()?.data?.size ?: 0
                val medicalHistoryQuantity = result3.convertApiStateTo()?.data?.size ?: 0
                val accountQuantity = result4.convertApiStateTo()?.data?.size ?: 0
                _quantity.value = Quantity(
                    adminQuantity,appointmentQuantity,medicalHistoryQuantity, accountQuantity
                )
            }
        }.catch { e ->
            withContext(Dispatchers.Main) {
                loading.value = false
                errorApiLiveData.postValue(e.message)
            }
        }.flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }
}