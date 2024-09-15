package com.example.appkhambenh.ui.ui.doctor.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.appkhambenh.ui.base.BaseViewModel
import com.example.appkhambenh.ui.data.remote.base.doOnSuccess
import com.example.appkhambenh.ui.data.remote.entity.PatientModel
import com.example.appkhambenh.ui.data.remote.repository.doctor.MedicalHistoryRepository
import com.example.appkhambenh.ui.data.remote.repository.doctor.PatientRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FragmentAdminDoctorViewModel @Inject constructor(
    private val patientRepository: PatientRepository,
    private val medicalHistoryRepository: MedicalHistoryRepository,
) : BaseViewModel() {
    private val _patients: MutableStateFlow<ArrayList<PatientModel>?> = MutableStateFlow(null)
    val patients = _patients.asStateFlow()
    var patientModel: MutableStateFlow<Pair<PatientModel?, Int?>?> = MutableStateFlow(null)

    fun getListPatient(
        fullname: String? = null,
        email: String? = null,
        citizenId: String? = null,
        healthInsurance: String? = null,
        phoneNumber: String? = null,
    ) = viewModelScope.launch {
        patientRepository.getListPatient(
            fullname = fullname,
            email = email,
            citizenId = citizenId,
            healthInsurance = healthInsurance,
            phoneNumber = phoneNumber
        )
            .handleCallApi()
            .doOnSuccess {
                _patients.value = it.data
            }.collect()
    }

    fun medicalHistoryPatient(patientId: Int) = viewModelScope.launch {
        Log.e("Admin Doctor", "Start")
        medicalHistoryRepository.getListMedicalHistory(patientId = patientId)
            .handleCallApi()
            .doOnSuccess {
                Log.e("Admin Doctor", "Success")
                if (it.data?.isNotEmpty() == true) {
                    patientModel.value = Pair(it.data[0].patient, it.data[0].id)
                } else {
                    Log.e("Admin Doctor", "Fail")
                    errorApiLiveData.postValue("Bệnh nhân này chưa được đăng kí khám")
                }
            }.collect()
    }

    fun deletePatient(patientId: Int) = viewModelScope.launch {
        loading.postValue(true)
        patientRepository.deletePatient(patientId = patientId)
            .handleCallApi()
            .doOnSuccess {
                if (it.patientId == patientId) {
                    getListPatient()
                    errorApiLiveData.postValue("Bạn đã xoá bệnh nhân thành công")
                } else {
                    errorApiLiveData.postValue("Fail Data")
                }
            }.collect()

    }
}