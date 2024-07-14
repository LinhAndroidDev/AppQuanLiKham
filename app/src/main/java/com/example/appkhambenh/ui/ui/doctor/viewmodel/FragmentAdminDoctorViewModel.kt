package com.example.appkhambenh.ui.ui.doctor.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.appkhambenh.ui.base.BaseViewModel
import com.example.appkhambenh.ui.data.remote.entity.PatientModel
import com.example.appkhambenh.ui.data.remote.repository.doctor.MedicalHistoryRepository
import com.example.appkhambenh.ui.data.remote.repository.doctor.PatientRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class FragmentAdminDoctorViewModel @Inject constructor(
    private val patientRepository: PatientRepository,
    private val medicalHistoryRepository: MedicalHistoryRepository,
): BaseViewModel() {
    val patients: MutableStateFlow<ArrayList<PatientModel>?> = MutableStateFlow(null)
    val isRegistered = MutableLiveData<Int>()

    suspend fun getListPatient(
        fullname: String? = null,
        email: String? = null,
        citizenId: String? = null,
        healthInsurance: String? = null,
        phoneNumber: String? = null,
    ) {
        loading.postValue(true)
        try {
            patientRepository.getListPatient(fullname, email, citizenId, healthInsurance, phoneNumber).let { response->
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
        medicalHistoryRepository.getListMedicalHistory(patientId = patientId).let { response ->
            loading.postValue(false)
            if(response.isSuccessful) {
                if(response.body()?.paginate?.totalPage != 0) {
                    Log.e("GoToFragmentTreatment", "FragmentAdminDoctorViewModel")
                    isRegistered.postValue(response.body()?.data!![0].id)
                } else {
                    errorApiLiveData.postValue("Bệnh nhân này chưa được đăng kí khám")
                }
            } else {
                errorApiLiveData.postValue("Lỗi server")
            }
        }
    }

    suspend fun deletePatient(patientId: Int) {
        loading.postValue(true)
        patientRepository.deletePatient(patientId = patientId).let { response ->
            loading.postValue(false)
            if(response.isSuccessful) {
                if(response.body()?.patientId == patientId) {
                    getListPatient()
                    errorApiLiveData.postValue("Bạn đã xoá bệnh nhân thành công")
                }
            } else {
                errorApiLiveData.postValue("Lỗi server")
            }
        }
    }
}