package com.example.appkhambenh.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appkhambenh.ui.data.remote.base.ApiState
import com.example.appkhambenh.ui.data.remote.base.doOnFailure
import com.example.appkhambenh.ui.data.remote.base.doOnLoading
import kotlinx.coroutines.flow.Flow

open class BaseViewModel : ViewModel() {
    val loading = MutableLiveData<Boolean>()
    val errorApiLiveData = MutableLiveData<String>()

    fun <T> Flow<ApiState<T>>.handleCallApi(): Flow<ApiState<T>> {
        return this.doOnLoading {
            loading.postValue(it)
        }.doOnFailure {
            errorApiLiveData.postValue(it?.message ?: "Unknown error")
        }
    }
}