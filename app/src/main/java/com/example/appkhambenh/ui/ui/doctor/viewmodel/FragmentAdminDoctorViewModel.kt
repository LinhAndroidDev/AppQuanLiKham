package com.example.appkhambenh.ui.ui.doctor.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.appkhambenh.ui.base.BaseViewModel
import com.example.appkhambenh.ui.data.remote.entity.PatientModel
import com.example.appkhambenh.ui.data.remote.repository.doctor.MedicalHistoryRepository
import com.example.appkhambenh.ui.data.remote.repository.doctor.PatientRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FragmentAdminDoctorViewModel @Inject constructor(
    private val patientRepository: PatientRepository,
    private val medicalHistoryRepository: MedicalHistoryRepository,
) : BaseViewModel() {
    private val _patients: MutableStateFlow<ArrayList<PatientModel>?> = MutableStateFlow(null)
    val patients = _patients.asStateFlow()
    private val _patientModel: MutableStateFlow<Pair<PatientModel?, Int?>?> = MutableStateFlow(null)
    val patientModel = _patientModel.asStateFlow()

    fun getListPatient(
        fullname: String? = null,
        email: String? = null,
        citizenId: String? = null,
        healthInsurance: String? = null,
        phoneNumber: String? = null,
    ) = viewModelScope.launch {
        val result = patientRepository.getListPatient(
            fullname = fullname,
            email = email,
            citizenId = citizenId,
            healthInsurance = healthInsurance,
            phoneNumber = phoneNumber
        ).flowOn(Dispatchers.IO)
            .onStart {
                loading.postValue(true)
            }
            .catch { e ->
                loading.postValue(false)
                errorApiLiveData.postValue(e.message)
            }.collect {
                withContext(Dispatchers.Main) {
                    loading.postValue(false)
                    _patients.value = it.data
                }
            }
        Log.e("Repository", "Response: $result")
    }

    fun medicalHistoryPatient(patientId: Int) = viewModelScope.launch {
        loading.postValue(true)
        medicalHistoryRepository.getListMedicalHistory(patientId = patientId)
            .flowOn(Dispatchers.IO)
            .catch { e ->
                loading.postValue(false)
                errorApiLiveData.postValue(e.message)
            }
            .collect {
                loading.postValue(false)
                if (it.data?.isNotEmpty() == true) {
                    _patientModel.value = Pair(it.data[0].patient, it.data[0].id)
                } else {
                    errorApiLiveData.postValue("Bệnh nhân này chưa được đăng kí khám")
                }
            }
    }

    fun deletePatient(patientId: Int) = viewModelScope.launch {
        loading.postValue(true)
        patientRepository.deletePatient(patientId = patientId)
            .flowOn(Dispatchers.IO)
            .catch { e ->
                loading.postValue(false)
                errorApiLiveData.postValue(e.message)
            }
            .collect {
                loading.postValue(false)
                if (it.patientId == patientId) {
                    getListPatient()
                    errorApiLiveData.postValue("Bạn đã xoá bệnh nhân thành công")
                } else {
                    errorApiLiveData.postValue("Fail Data")
                }
            }
    }
}