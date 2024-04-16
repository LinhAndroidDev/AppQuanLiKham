package com.example.appkhambenh.ui.ui.user.doctor

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.appkhambenh.ui.base.BaseViewModel
import com.example.appkhambenh.ui.data.remote.model.TimeWorkingModel
import com.example.appkhambenh.ui.data.remote.repository.TimeWorkingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InfoDoctorViewModel @Inject constructor(private val repository: TimeWorkingRepository) :
    BaseViewModel() {
//    1713088800
    val timeWorking = MutableStateFlow<ArrayList<TimeWorkingModel>?>(null)

    fun getTimeWorking(id: Int, dateStart: Long) = viewModelScope.launch {
        loading.postValue(true)
        repository.getTimeWorking(id, dateStart).let {
            try {
                loading.postValue(false)
                if (it.isSuccessful) {
                    timeWorking.value = it.body()?.data?.content
                    Log.e("TimeModel: ", "${timeWorking.value}")
                } else {
                    errorApiLiveData.postValue(it.body()?.message)
                }
            } catch (e: Exception) {
                loading.postValue(false)
                errorApiLiveData.postValue(e.message)
            }
        }
    }
}