package com.example.appkhambenh.ui.ui.user.appointment

import androidx.lifecycle.viewModelScope
import com.example.appkhambenh.ui.base.BaseViewModel
import com.example.appkhambenh.ui.data.remote.ApiClient
import com.example.appkhambenh.ui.data.remote.model.BookAppointmentModel
import com.example.appkhambenh.ui.data.remote.repository.AppointmentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MakeAppointViewModel @Inject constructor(private val repository: AppointmentRepository) :
    BaseViewModel() {
        val successful = MutableStateFlow(false)

    fun createBooking(bookAppointmentModel: BookAppointmentModel) = viewModelScope.launch {
        loading.postValue(true)
        try {
            repository.createBooking(bookAppointmentModel).let {
                loading.postValue(false)
                if(it.isSuccessful) {
                    when(it.body()?.statusCode) {
                        ApiClient.STATUS_CODE_SUCCESS -> {
                            successful.value = true
                        }

                        else -> {
                            errorApiLiveData.postValue(it.body()?.message)
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