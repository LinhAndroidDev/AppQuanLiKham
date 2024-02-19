package com.example.appkhambenh.ui.ui.user.hospital

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.appkhambenh.ui.base.BaseViewModel
import com.example.appkhambenh.ui.data.remote.ApiClient
import com.example.appkhambenh.ui.data.remote.model.Hospital
import com.example.appkhambenh.ui.data.remote.repository.HospitalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HospitalViewModel @Inject constructor(private val hospitalRepository: HospitalRepository) :
    BaseViewModel() {
    var hospitalMutableLiveData = MutableLiveData<ArrayList<Hospital>>()

    suspend fun getListHospital() {
        loading.postValue(true)
        viewModelScope.launch {
            delay(500L)
            withContext(Dispatchers.Main) {
                try {
                    hospitalRepository.getListHospital().let { response ->
                        if (response.isSuccessful) {
                            loading.postValue(false)
                            response.body().let {
                                when (it?.statusCode) {
                                    ApiClient.STATUS_CODE_SUCCESS -> {
                                        hospitalMutableLiveData.postValue(it.data.content)
                                    }

                                    else -> {
                                        errorApiLiveData.postValue(it?.message)
                                    }
                                }
                            }
                        }

                    }
                } catch (e: Exception) {
                    loading.postValue(false)
                    errorApiLiveData.postValue(e.message)
                }
            }
        }
    }
}