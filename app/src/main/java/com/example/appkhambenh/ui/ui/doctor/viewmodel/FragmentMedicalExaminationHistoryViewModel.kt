package com.example.appkhambenh.ui.ui.doctor.viewmodel

import com.example.appkhambenh.ui.base.BaseViewModel
import com.example.appkhambenh.ui.data.remote.entity.MedicalHistoryResponse
import com.example.appkhambenh.ui.data.remote.repository.doctor.MedicalHistoryRepository
import com.example.appkhambenh.ui.data.remote.request.AddMedicalHistoryRequest
import com.example.appkhambenh.ui.data.remote.request.UpdateDiagnoseMedicalHistoryRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class FragmentMedicalExaminationHistoryViewModel @Inject constructor(private val medicalHistoryRepository: MedicalHistoryRepository) :
    BaseViewModel() {
    var medicalHistorys: MutableStateFlow<ArrayList<MedicalHistoryResponse.Data>?> =
        MutableStateFlow(null)

    suspend fun medicalHistoryPatient(patientId: Int) {
        loading.postValue(true)
        medicalHistoryRepository.getListMedicalHistory(patientId = patientId).let { response ->
            loading.postValue(false)
            if (response.isSuccessful) {
                medicalHistorys.value =
                    response.body()?.data as ArrayList<MedicalHistoryResponse.Data>?
            } else {
                errorApiLiveData.postValue("Lỗi server")
            }
        }
    }

    suspend fun addMedicalHistory(
        addMedicalHistoryRequest: AddMedicalHistoryRequest,
        patientId: Int,
    ) {
        loading.postValue(true)
        medicalHistoryRepository.addMedicalHistory(addMedicalHistoryRequest).let { response ->
            loading.postValue(true)
            if (response.isSuccessful) {
                if (response.body()?.medicalHistoryId != 0) {
                    medicalHistoryPatient(patientId)
                }
            } else {
                errorApiLiveData.postValue("Lỗi Server")
            }
        }
    }

    suspend fun updateDiagnoseMedicalHistory(
        patientId: Int,
        updateDiagnoseMedicalHistoryRequest: UpdateDiagnoseMedicalHistoryRequest,
    ) {
        loading.postValue(true)
        medicalHistoryRepository.updateDiagnoseMedicalHistory(
            patientId,
            updateDiagnoseMedicalHistoryRequest
        ).let { response ->
            loading.postValue(false)
            if (response.isSuccessful) {
                if (response.body()?.medicalHistoryId != 0) {
                    medicalHistoryPatient(patientId)
                    errorApiLiveData.postValue("Bạn đã cập nhật chẩn đoán thành công")
                }
            } else {
                errorApiLiveData.postValue(response.message())
            }
        }
    }
}