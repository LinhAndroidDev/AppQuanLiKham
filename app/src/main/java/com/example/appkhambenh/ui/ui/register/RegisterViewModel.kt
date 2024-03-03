package com.example.appkhambenh.ui.ui.register

import androidx.lifecycle.MutableLiveData
import com.example.appkhambenh.ui.base.BaseViewModel
import com.example.appkhambenh.ui.data.remote.ApiClient
import com.example.appkhambenh.ui.data.remote.model.RegisterModel
import com.example.appkhambenh.ui.data.remote.repository.RegisterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val registerRepository: RegisterRepository) :
    BaseViewModel() {
    val registerSuccessful = MutableLiveData<Boolean>()

    suspend fun requestRegisterUser(
        registerModel: RegisterModel,
    ) {
        loading.postValue(true)
        try {
            registerRepository.registerUser(registerModel).let { response ->
                loading.postValue(false)
                if (response.isSuccessful) {
                    response.body().let {
                        errorApiLiveData.postValue(it?.message)
                        when (it?.statusCode) {
                            ApiClient.STATUS_CODE_SUCCESS -> {
                                registerSuccessful.postValue(true)
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