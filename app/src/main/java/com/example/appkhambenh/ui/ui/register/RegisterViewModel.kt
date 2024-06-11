package com.example.appkhambenh.ui.ui.register

import com.example.appkhambenh.ui.base.BaseViewModel
import com.example.appkhambenh.ui.data.remote.ApiClient
import com.example.appkhambenh.ui.data.remote.model.RegisterModel
import com.example.appkhambenh.ui.data.remote.repository.user.RegisterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val registerRepository: RegisterRepository) :
    BaseViewModel() {
    val registerSuccessful = MutableStateFlow(false)

    suspend fun requestRegisterUser(
        registerModel: RegisterModel,
    ) {
        loading.postValue(true)
        try {
            registerRepository.registerUser(registerModel).let { response ->
                loading.postValue(false)
                if (response.isSuccessful) {
                    response.body()?.let {
                        when (it.statusCode) {
                            ApiClient.STATUS_CODE_SUCCESS -> {
                                errorApiLiveData.postValue(it.message)
                                registerSuccessful.value = true
                            }

                            else -> {
                                errorApiLiveData.postValue(it.message)
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