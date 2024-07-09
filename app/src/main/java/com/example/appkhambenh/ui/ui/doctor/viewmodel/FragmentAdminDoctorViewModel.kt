package com.example.appkhambenh.ui.ui.doctor.viewmodel

import com.example.appkhambenh.ui.base.BaseViewModel
import com.example.appkhambenh.ui.data.remote.entity.PatientModel
import com.example.appkhambenh.ui.data.remote.repository.doctor.MedicalHistoryRepository
import com.example.appkhambenh.ui.data.remote.repository.doctor.PatientRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class FragmentAdminDoctorViewModel @Inject constructor(
    private val repository: PatientRepository,
    private val medicalHistoryRepository: MedicalHistoryRepository,
): BaseViewModel() {
    val patients: MutableStateFlow<ArrayList<PatientModel>?> = MutableStateFlow(null)
    val isRegistered = MutableStateFlow(0)

    suspend fun getListPatient() {
        loading.postValue(true)
        try {
            repository.getListPatient().let { response->
                loading.postValue(false)
                if(response.isSuccessful) {
                    patients.value = response.body()?.data
                }
            }
        } catch (e: Exception) {
            loading.postValue(false)
            errorApiLiveData.postValue(e.message)
        }
    }

    suspend fun medicalHistoryPatient(patientId: Int) {
        loading.postValue(true)
        try {
            medicalHistoryRepository.getListMedicalHistory(patientId = patientId).let { response ->
                loading.postValue(false)
                if(response.isSuccessful) {
                    if(response.body()?.data?.isNotEmpty() == true) {
                        isRegistered.value = response.body()?.data!![0].id
                    } else {
                        errorApiLiveData.postValue("Bệnh nhân này chưa được đăng kí khám")
                    }
                } else {
                    errorApiLiveData.postValue("Lỗi server")
                }
            }
        } catch (e: Exception) {
            loading.postValue(false)
            errorApiLiveData.postValue(e.message)
        }
    }
}