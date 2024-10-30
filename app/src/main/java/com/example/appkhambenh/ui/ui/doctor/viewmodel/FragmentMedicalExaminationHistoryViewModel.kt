package com.example.appkhambenh.ui.ui.doctor.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.appkhambenh.ui.base.BaseViewModel
import com.example.appkhambenh.ui.data.remote.base.doOnSuccess
import com.example.appkhambenh.ui.data.remote.entity.MedicalHistoryResponse
import com.example.appkhambenh.ui.data.remote.repository.doctor.MedicalHistoryRepository
import com.example.appkhambenh.ui.data.remote.request.AddMedicalHistoryRequest
import com.example.appkhambenh.ui.data.remote.request.UpdateAllocationRequest
import com.example.appkhambenh.ui.data.remote.request.UpdateDiagnoseMedicalHistoryRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FragmentMedicalExaminationHistoryViewModel @Inject constructor(private val medicalHistoryRepository: MedicalHistoryRepository) :
    BaseViewModel() {
    private var _medicalHistorys: MutableStateFlow<ArrayList<MedicalHistoryResponse.Data>?> =
        MutableStateFlow(null)
    val medicalHistorys = _medicalHistorys.asStateFlow()

    fun medicalHistoryPatient(patientId: Int) = viewModelScope.launch {
        medicalHistoryRepository.getListMedicalHistory(patientId = patientId)
            .handleCallApi()
            .doOnSuccess {
                _medicalHistorys.value = it.data as ArrayList<MedicalHistoryResponse.Data>
            }.collect()
    }

    fun addMedicalHistory(
        addMedicalHistoryRequest: AddMedicalHistoryRequest,
        patientId: Int,
    ) = viewModelScope.launch {
        medicalHistoryRepository.addMedicalHistory(addMedicalHistoryRequest)
            .handleCallApi()
            .doOnSuccess {
                if (it.medicalHistoryId != 0) {
                    medicalHistoryPatient(patientId)
                }
            }.collect()
//        if(response.code() == 400) {
//            errorApiLiveData.postValue("Bệnh nhân này đang trong quá trình điều trị")
//        } else {
//            errorApiLiveData.postValue("Lỗi server")
//        }
    }

    fun updateDiagnoseMedicalHistory(
        medicalHistoryId: Int,
        updateDiagnoseMedicalHistoryRequest: UpdateDiagnoseMedicalHistoryRequest,
        patientId: Int,
    ) = viewModelScope.launch {
        medicalHistoryRepository.updateDiagnoseMedicalHistory(
            medicalHistoryId,
            updateDiagnoseMedicalHistoryRequest
        ).handleCallApi()
            .doOnSuccess {
                if (it.medicalHistoryId != 0) {
                    medicalHistoryPatient(patientId)
                    errorApiLiveData.postValue("Bạn đã cập nhật chẩn đoán thành công")
                }
            }.collect()
    }

    fun updateAllocation(
        medicalHistoryId: Int,
        updateAllocationRequest: UpdateAllocationRequest,
        patientId: Int,
    ) = viewModelScope.launch {
        medicalHistoryRepository.updateAllocation(medicalHistoryId, updateAllocationRequest)
            .handleCallApi()
            .doOnSuccess {
                if (it.medicalHistoryId != 0) {
                    medicalHistoryPatient(patientId)
                    errorApiLiveData.postValue("Bạn đã cập nhật phân bổ thành công")
                }
            }.collect()
    }

    fun hospitalDischarge(
        patientId: Int,
        medicalHistoryId: Int,
    ) = viewModelScope.launch {
        medicalHistoryRepository.hospitalDischarge(medicalHistoryId)
            .handleCallApi()
            .doOnSuccess {
                if (it.medicalHistoryId != 0) {
                    medicalHistoryPatient(patientId)
                    errorApiLiveData.postValue("Bạn đã cập nhật phân bổ thành công")
                }
            }.collect()
    }
}