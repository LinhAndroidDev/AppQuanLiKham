package com.example.appkhambenh.ui.ui.doctor.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.appkhambenh.ui.base.BaseViewModel
import com.example.appkhambenh.ui.data.remote.entity.MedicalHistoryResponse
import com.example.appkhambenh.ui.data.remote.repository.doctor.MedicalHistoryRepository
import com.example.appkhambenh.ui.data.remote.request.AddMedicalHistoryRequest
import com.example.appkhambenh.ui.data.remote.request.UpdateAllocationRequest
import com.example.appkhambenh.ui.data.remote.request.UpdateDiagnoseMedicalHistoryRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FragmentMedicalExaminationHistoryViewModel @Inject constructor(private val medicalHistoryRepository: MedicalHistoryRepository) :
    BaseViewModel() {
    private var _medicalHistorys: MutableStateFlow<ArrayList<MedicalHistoryResponse.Data>?> =
        MutableStateFlow(null)
    val medicalHistorys = _medicalHistorys.asStateFlow()

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
                _medicalHistorys.value = it.data as ArrayList<MedicalHistoryResponse.Data>
            }
    }

    fun addMedicalHistory(
        addMedicalHistoryRequest: AddMedicalHistoryRequest,
        patientId: Int,
    ) = viewModelScope.launch {
        loading.postValue(true)
        medicalHistoryRepository.addMedicalHistory(addMedicalHistoryRequest)
            .flowOn(Dispatchers.IO)
            .catch { e ->
                loading.postValue(false)
                errorApiLiveData.postValue(e.message)
            }
            .collect {
                loading.postValue(false)
                if (it.medicalHistoryId != 0) {
                    medicalHistoryPatient(patientId)
                }
            }
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
        loading.postValue(true)
        medicalHistoryRepository.updateDiagnoseMedicalHistory(
            medicalHistoryId,
            updateDiagnoseMedicalHistoryRequest
        )
            .flowOn(Dispatchers.IO)
            .catch { e ->
                loading.postValue(false)
                errorApiLiveData.postValue(e.message)
            }
            .collect {
                loading.postValue(false)
                if (it.medicalHistoryId != 0) {
                    medicalHistoryPatient(patientId)
                    errorApiLiveData.postValue("Bạn đã cập nhật chẩn đoán thành công")
                }
            }
    }

    fun updateAllocation(
        medicalHistoryId: Int,
        updateAllocationRequest: UpdateAllocationRequest,
        patientId: Int,
    ) = viewModelScope.launch {
        loading.postValue(true)
        medicalHistoryRepository.updateAllocation(medicalHistoryId, updateAllocationRequest)
            .flowOn(Dispatchers.IO)
            .catch { e ->
                loading.postValue(false)
                errorApiLiveData.postValue(e.message)
            }
            .collect {
                loading.postValue(false)
                if (it.medicalHistoryId != 0) {
                    medicalHistoryPatient(patientId)
                    errorApiLiveData.postValue("Bạn đã cập nhật phân bổ thành công")
                }
            }
    }

    fun hospitalDischarge(
        patientId: Int,
        medicalHistoryId: Int,
    ) = viewModelScope.launch {
        loading.postValue(true)
        medicalHistoryRepository.hospitalDischarge(medicalHistoryId)
            .flowOn(Dispatchers.IO)
            .catch { e ->
                loading.postValue(false)
                errorApiLiveData.postValue(e.message)
            }
            .collect {
                loading.postValue(false)
                if (it.medicalHistoryId != 0) {
                    medicalHistoryPatient(patientId)
                    errorApiLiveData.postValue("Bạn đã cập nhật phân bổ thành công")
                }
            }
    }
}