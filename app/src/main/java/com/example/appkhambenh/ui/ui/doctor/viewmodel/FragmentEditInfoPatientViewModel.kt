package com.example.appkhambenh.ui.ui.doctor.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.appkhambenh.ui.base.BaseViewModel
import com.example.appkhambenh.ui.data.remote.model.PatientInfoModel
import com.example.appkhambenh.ui.data.remote.repository.doctor.PatientRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FragmentEditInfoPatientViewModel @Inject constructor(private val repository: PatientRepository) :
    BaseViewModel() {
        val isSuccessful = MutableStateFlow(false)

    fun updateInfoPatient(id: Int, patient: PatientInfoModel) = viewModelScope.launch {
        loading.postValue(true)
        try {
            repository.updateInfoPatient(id, patient).let { response ->
                loading.postValue(false)
                if(response.isSuccessful) {
                    if(response.body()?.patientId == id) {
                        isSuccessful.value = true
                    } else {
                        errorApiLiveData.postValue(response.body()?.error)
                    }
                } else {
                    errorApiLiveData.postValue(response.message())
                }
            }
        } catch (e: Exception) {
            loading.postValue(false)
            errorApiLiveData.postValue(e.message)
        }
    }
}