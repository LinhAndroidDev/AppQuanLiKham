package com.example.appkhambenh.ui.ui.user.doctor

import com.example.appkhambenh.ui.base.BaseViewModel
import com.example.appkhambenh.ui.data.remote.model.DoctorModel
import com.example.appkhambenh.ui.data.remote.repository.DoctorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class SearchDoctorViewModel @Inject constructor(private val repository: DoctorRepository) :
    BaseViewModel() {
    private val _doctors: MutableStateFlow<ArrayList<DoctorModel>?> = MutableStateFlow(null)
    val doctors = _doctors.asSharedFlow()

    suspend fun getListDoctor() {
        try {
            loading.postValue(true)
            repository.getListDoctor().let {
                loading.postValue(false)
                if (it.isSuccessful) {
                    _doctors.value = it.body()?.data?.content
                } else {
                    errorApiLiveData.postValue(it.body()?.message)
                }
            }
        } catch (e: Exception) {
            loading.postValue(false)
            errorApiLiveData.postValue(e.message)
        }
    }
}