package com.example.appkhambenh.ui.ui.doctor.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.appkhambenh.ui.base.BaseViewModel
import com.example.appkhambenh.ui.data.remote.repository.doctor.ServiceOrderRepository
import com.example.appkhambenh.ui.data.remote.request.UpdateInfoClinicalExaminationRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FragmentClinicalExaminationViewModel @Inject constructor(private val serviceOrderRepository: ServiceOrderRepository) :
    BaseViewModel() {

    fun updateClinicalExamination(
        serviceMedicalHistoryId: Int,
        updateInfoClinicalExaminationRequest: UpdateInfoClinicalExaminationRequest,
    ) = viewModelScope.launch {
        loading.postValue(true)
        serviceOrderRepository.updateClinicalExamination(
            serviceMedicalHistoryId,
            updateInfoClinicalExaminationRequest
        ).let { response ->
            if (response.isSuccessful) {
                if (response.body()?.serviceMedicalHistoryId == serviceMedicalHistoryId) {
                    errorApiLiveData.postValue("Bạn đã cập nhật dịch vụ thành công")
                }
            } else {
                errorApiLiveData.postValue(response.message())
            }
        }
    }
}