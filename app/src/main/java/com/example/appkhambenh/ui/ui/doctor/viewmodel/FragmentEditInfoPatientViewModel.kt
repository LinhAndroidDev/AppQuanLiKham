package com.example.appkhambenh.ui.ui.doctor.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.appkhambenh.ui.base.BaseViewModel
import com.example.appkhambenh.ui.data.remote.base.doOnSuccess
import com.example.appkhambenh.ui.data.remote.model.PatientInfoModel
import com.example.appkhambenh.ui.data.remote.repository.doctor.PatientRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FragmentEditInfoPatientViewModel @Inject constructor(private val repository: PatientRepository) :
    BaseViewModel() {
    private val _isSuccessful = MutableStateFlow(false)
    val isSuccessful = _isSuccessful.asStateFlow()

    fun updateInfoPatient(id: Int, patient: PatientInfoModel) = viewModelScope.launch {
        repository.updateInfoPatient(id, patient)
            .handleCallApi()
            .doOnSuccess {
                if (it.patientId == id) {
                    _isSuccessful.value = true
                }
            }.collect()
    }
}