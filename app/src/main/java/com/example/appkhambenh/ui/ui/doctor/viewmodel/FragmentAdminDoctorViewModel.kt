package com.example.appkhambenh.ui.ui.doctor.viewmodel

import com.example.appkhambenh.ui.base.BaseViewModel
import com.example.appkhambenh.ui.data.remote.entity.PatientModel
import com.example.appkhambenh.ui.data.remote.repository.doctor.AdminDoctorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class FragmentAdminDoctorViewModel @Inject constructor(private val repository: AdminDoctorRepository): BaseViewModel() {
    val patients: MutableStateFlow<ArrayList<PatientModel>?> = MutableStateFlow(null)

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
}